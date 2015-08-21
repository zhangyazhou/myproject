package com.mycontainer.ex03.connector.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

public class HttpRequest implements HttpServletRequest {

	private String contentType;
	private int contentLength;
	private InetAddress inetAddress;
	private InputStream input;
	private String method;
	private String protocol;
	private String queryString;
	private String requestURI;
	private String serverName;
	private int serverPort;
	private Socket socket;
	private boolean requestedSessionCookie;
	private String requestedSessionId;
	private boolean requestedSessionURL;

	/**
	 * The request attributes for this request.
	 */
	protected HashMap attributes = new HashMap();
	/**
	 * The authorization credentials sent with this Request.
	 */
	protected String authorization = null;
	/**
	 * The context path for this request.
	 */
	protected String contextPath = "";
	/**
	 * The set of cookies associated with this Request.
	 */
	protected ArrayList cookies = new ArrayList();
	/**
	 * An empty collection to use for returning empty Enumerations. Do not add
	 * any elements to this collection!
	 */
	protected static ArrayList empty = new ArrayList();
	/**
	 * The set of SimpleDateFormat formats to use in getDateHeader().
	 */
	protected SimpleDateFormat formats[] = { new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US) };

	/**
	 * The HTTP headers associated with this Request, keyed by name. The values
	 * are ArrayLists of the corresponding header values.
	 */
	protected HashMap headers = new HashMap();

	protected ParameterMap parameters = null;

	/**
	 * Have the parameters for this request been parsed yet?
	 */
	protected boolean parsed = false;
	protected String pathInfo = null;

	/**
	 * The reader that has been returned by <code>getReader</code>, if any.
	 */
	protected BufferedReader reader = null;

	/**
	 * The ServletInputStream that has been returned by
	 * <code>getInputStream()</code>, if any.
	 */
	protected ServletInputStream stream = null;

	public HttpRequest(InputStream input) {
		this.input = input;
	}
	
	public void addHeader(String name, String value) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList values = (ArrayList) headers.get(name);
			if (values == null) {
				values = new ArrayList();
				headers.put(name, values);
			}
			values.add(value);
		}
	}
	
	/**
	 * Parse the parameters of this request, if it has not already
	 * occurred.
	 * If parameters are present in both the query string and the request
	 * content, they are mergered.
	 * 
	 */
	protected void parseParameters() {
		if (parsed)
			return;
		ParameterMap results = parameters;
		if (results == null)
			results = new ParameterMap();
		results.setLocked(false);
		String encoding = getCharacterEncoding();
		if (encoding == null)
			encoding = "ISO-8859-1";
		//Parse any parameters specified in the query string
		String queryString = getQueryString();
		RequestUtil.parseParameters(results, queryString, encoding);
		
		//Parse any parameters specified int the input stream
		String contentType = getContentType();
		if (contentType == null)
			contentType = "";
		int semicolon = contentType.indexOf(';');
		if (semicolon >= 0) {
			contentType = contentType.substring(0, semicolon).trim();
		}
		else {
			contentType = contentType.trim();
		}
		if("POST".equals(getMethod()) && (getContentLength() > 0)&& "application/x-www-form-urlencoded".equals(contentType)) {
			try {
				int max = getContentLength();
				int len = 0;
				byte buf[] = new byte[getContentLength()];
				ServletInputStream is = getInputStream();
				while (len < max) {
					int next = is.read(buf, len, max - len);
					if (next < 0) {
						break;
					}
					len += next;
				}
				is.close();
				if (len < max) {
					throw new RuntimeException("Content length mismatch");
				}
				RequestUtil.parseParameters(results, buf, encoding);
			}
			catch (UnsupportedEncodingException e) {
				;
			}
			catch (IOException e) {
				throw new RuntimeException("Content read fail");
			}
		}
		
		//Store the final results
		results.setLocked(true);
		parsed = true;
		parameters = results;
	}
	
	public void addCookie(Cookie cookie) {
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}
	
	public ServletInputStream createInputStream() throws IOException {
		return (new RequestStream(this));
	}
	
	public InputStream getStream() {
		return input;
	}
	
	
	
	

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stubr
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, IllegalStateException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

}
