package com.mvc.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mvc.annotation.Controller;
import com.mvc.annotation.RequestMapping;
import com.mvc.annotation.ResponseBody;
import com.mvc.core.converter.HttpMessageConverter;

public class DispatcherServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(DispatcherServlet.class);

	private static final long serialVersionUID = 1L;
	private Properties properties = new Properties();
	private List<String> classNames = new ArrayList<String>();
	private Map<String, Object> ioc = new HashMap<String, Object>();
	private Map<String, Method> handlerMapping = new HashMap<String, Method>();
	private Map<String, Object> controllerMap = new HashMap<String, Object>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 1.加载配置文件
		doLoadConfig(config.getInitParameter("contextConfigLocation"));
		// 2.初始化所有相关联的类,扫描用户设定的包下面所有的类
		doScanner(properties.getProperty("scanControllerPackage"));
		// 3.拿到扫描到的类,通过反射机制,实例化,并且放到ioc容器中(k-v beanName-bean) beanName默认是首字母小写
		doInstance();
		// 4.初始化HandlerMapping(将url和method对应上)
		initHandlerMapping();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().endsWith(".jsp")) {
			resp.sendRedirect(req.getRequestURI());
			return;
		} else {
			this.doPost(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			doDispatch(req, resp);
			// 处理请求
		} catch (Exception e) {
			resp.getWriter().write("500!! Server Exception");
		}
	}

	private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (handlerMapping.isEmpty()) {
			return;
		}
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		url = url.replace(contextPath, "").replaceAll("/+", "/");
		if (!this.handlerMapping.containsKey(url)) {
			response.getWriter().write("404 NOT FOUND!");
			return;
		}
		Method method = this.handlerMapping.get(url);
		// 获取方法的参数列表
		Class<?>[] parameterTypes = method.getParameterTypes();
		
		// 获取请求的参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 保存参数值
		Object[] paramValues = new Object[parameterTypes.length];
		// 方法的参数列表
		for (int i = 0; i < parameterTypes.length; i++) {
			// 根据参数名称，做某些处理
			String requestParam = parameterTypes[i].getSimpleName();

			if (requestParam.equals("HttpServletRequest")) {
				// 参数类型已明确，这边强转类型
				paramValues[i] = request;
				continue;
			}
			if (requestParam.equals("HttpServletResponse")) {
				paramValues[i] = response;
				continue;
			}
			if (requestParam.equals("String")) {
				for (Entry<String, String[]> param : parameterMap.entrySet()) {
					String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
					paramValues[i] = value;
				}
			}
		}
		// 利用反射机制来调用
		try {
			Object obj = method.invoke(this.controllerMap.get(url), paramValues);// obj是method所对应的实例 在ioc容器中

			Annotation annotations[] = method.getAnnotations();
			boolean isresponseBody = false;
			for (Annotation annotation : annotations) {
				if (annotation instanceof ResponseBody) {
					isresponseBody = true;
					// LhbResponseBody responseBody = (LhbResponseBody) annotation;
					HttpMessageConverter converter = new HttpMessageConverter();
					response.getWriter().print(converter.convert(obj));
					return;
				}
			}
			if (!isresponseBody) {
				if (obj.getClass().getSimpleName().equals("String")) {
					response.sendRedirect(obj.toString());
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doLoadConfig(String location) {
		// 把web.xml中的contextConfigLocation对应value值的文件加载到留里面
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			// 用Properties文件加载文件里的内容
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关流
			if (null != resourceAsStream) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void doScanner(String packageName) {
		// 把所有的.替换成/
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("/" + packageName.replaceAll("\\.", "/")).getPath();
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				// 递归读取包
				doScanner(packageName + "." + file.getName());
			} else {
				String className = packageName + "." + file.getName().replace(".class", "");
				classNames.add(className);
			}
		}
	}

	private void doInstance() {
		if (classNames.isEmpty()) {
			return;
		}
		for (String className : classNames) {
			try {
				// 把类搞出来,反射来实例化(只有加@Controller需要实例化)
				Class<?> clazz = Class.forName(className);
				if (clazz.isAnnotationPresent(Controller.class)) {
					ioc.put(toLowerFirstWord(clazz.getSimpleName()), clazz.newInstance());
				} else {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void initHandlerMapping() {
		if (ioc.isEmpty()) {
			return;
		}
		try {
			for (Entry<String, Object> entry : ioc.entrySet()) {
				Class<? extends Object> clazz = entry.getValue().getClass();
				if (!clazz.isAnnotationPresent(Controller.class)) {
					continue;
				}
				// 拼url时,是controller头的url拼上方法上的url
				String baseUrl = "";
				if (clazz.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
					baseUrl = annotation.value();
				}
				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					if (!method.isAnnotationPresent(RequestMapping.class)) {
						continue;
					}
					RequestMapping annotation = method.getAnnotation(RequestMapping.class);
					String url = annotation.value();
					url = (baseUrl + "/" + url).replaceAll("(/\\*/)|(/+)", "/");
					handlerMapping.put(url, method);
					controllerMap.put(url, clazz.newInstance());
					logger.debug(
							"Mapped:{[" + url + "]}" + "onto "+method);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把字符串的首字母小写
	 * 
	 * @param name
	 * @return
	 */
	private String toLowerFirstWord(String name) {
		char[] charArray = name.toCharArray();
		charArray[0] += 32;
		return String.valueOf(charArray);
	}

	public static void main(String[] args) {

		// String packageName = "H:\\spring
		// could\\mvc\\target\\classes\\com\\mvc\\controller";
		// File dir = new File(packageName);
		// for (File file : dir.listFiles()) {
		// if (file.isDirectory()) {
		// // 递归读取包
		// } else {
		// String className = packageName + "." + file.getName().replace(".class", "");
		// System.out.println(className);
		// }
		// }

	}

}
