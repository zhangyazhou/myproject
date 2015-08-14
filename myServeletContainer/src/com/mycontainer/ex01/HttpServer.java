package com.mycontainer.ex01;

import java.io.File;

public class HttpServer {
	 /** WEB_ROOT is the directory where our HTML and other files reside.
	   *  For this package, WEB_ROOT is the "webroot" directory under the working
	   *  directory.
	   *  The working directory is the location in the file system
	   *  from where the java command was invoked.
	   */
	  public static final String WEB_ROOT =
	    System.getProperty("user.dir") + File.separator  + "webroot";

	  // shutdown command
	  private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	  // the shutdown command received
	  private boolean shutdown = false;
}
