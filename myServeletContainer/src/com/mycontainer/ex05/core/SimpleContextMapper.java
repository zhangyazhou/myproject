package com.mycontainer.ex05.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;
import org.apache.catalina.Wrapper;

public class SimpleContextMapper implements Mapper {

	private SimpleContext context = null;
	
	
	
	@Override
	public Container getContainer() {
		return context;
	}

	@Override
	public void setContainer(Container container) {
		if(!(container instanceof SimpleContext))
			throw new IllegalArgumentException("Illegal type of container");
		context = (SimpleContext) container;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container map(Request request, boolean update) {
		String contextPath = 
				((HttpServletRequest) request.getRequest()).getContextPath();
		String requestURI = ((HttpRequest) request).getDecodedRequestURI();
		String relativeURI = requestURI.substring(contextPath.length());
		Wrapper wrapper = null;
		String servletPath = relativeURI;
		String pathInfo = null;
		String name = context.findServletMapping(relativeURI);
		if(name != null)
			wrapper = (Wrapper) context.findChild(name);
		return (wrapper);
		
	}
	
}
