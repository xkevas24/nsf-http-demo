package com.qz.nsf.ddeemmoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DdeemmooApplication {
	public static String global_version = "V1.0.0";
	public static String global_color = null;
	public static void main(String[] args) {
		SpringApplication.run(DdeemmooApplication.class, args);
		// System.out.println(args);
		if (args.length > 0) {
			global_version = args[0];  // 通过启动参数设置全局变量的值
			global_color = args[1];  // 通过启动参数设置全局变量的值
		}

		System.out.println("Version: " + global_version);
		System.out.println("Color: " + global_color);
	}



}

