package com.dds.utils;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AppUtils {
	public static AppUtils instances = new AppUtils();
	
	private ServletContext context = null;

	private WebApplicationContext ctx = null;

	private AppUtils(){
		super();
	}
	public static AppUtils getInstances(){
		return instances;
	}
	public ServletContext getServletContext() {
		return context;
	}
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	public WebApplicationContext getWebApplicationContext(){
		if(ctx == null){
			if(getServletContext()==null){
				throw new RuntimeException("servletContext is null.");
			}
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		}
		return ctx;
	}
	
	public Object getBean(String beanName){
		return getWebApplicationContext().getBean(beanName);
	}
	public <T> T getBean(Class<T> requiredType) {
		return getWebApplicationContext().getBean(requiredType);
	}
	public <T> T getBean(String name, Class<T> requiredType) {
		return getWebApplicationContext().getBean(name,requiredType);
	}
	public File getRootFile(){
		return  new File(getServletContext().getRealPath("/"));
	}
	public ApplicationContext loadApplicationContext(String filePath){
		File rootFile = new File(getServletContext().getRealPath("/"));
		File contextFile = new File(rootFile, filePath);
		ApplicationContext context = new FileSystemXmlApplicationContext(contextFile.getAbsolutePath());
		return context;
	}
}
