package com.mycontainer.ex06.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;
import org.apache.catalina.Wrapper;

public class SimpleContextMapper implements Mapper {

	/**
	 * The Container with which this Mapper is associated.
	 */
	private SimpleContext context = null;

	@Override
	public Container getContainer() {
		return context;
	}

	@Override
	public void setContainer(Container container) {
		if (!(container instanceof SimpleContext))
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
		// Has this request already been mapped?
		if (update && (request.getWrapper() != null))
			return (request.getWrapper());
		// Identify the context-relative URI to be mapped
		String contextPath = ((HttpServletRequest) request.getRequest()).getContextPath();
		String requestURI = ((HttpRequest) request).getDecodedRequestURI();
		String relativeURI = requestURI.substring(contextPath.length());
		// Apply the standard request URI mapping rules from the specification
		Wrapper wrapper = null;
		String servletPath = relativeURI;
		String pathInfo = null;
		String name = context.findServletMapping(relativeURI);
		if (name != null)
			wrapper = (Wrapper) context.findChild(name);

		// Update the Request (if requested) and return this Wrapper
		if (update) {
			request.setWrapper(wrapper);
			((HttpRequest) request).setServletPath(servletPath);
			((HttpRequest) request).setPathInfo(pathInfo);
		}
		return (wrapper);
	}

}
