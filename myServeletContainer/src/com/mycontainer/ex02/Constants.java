package com.mycontainer.ex02;

import java.io.File;

public class Constants {
	public static final String WEB_ROOT = 
			System.getProperty("user.dir") + File.separator + "webroot";
	
	public static void main(String[] args) {
		System.out.println(Constants.WEB_ROOT);
	}
}
