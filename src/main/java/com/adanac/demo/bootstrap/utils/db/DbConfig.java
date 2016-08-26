package com.adanac.demo.bootstrap.utils.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 数据库访问配置文件各参数的获取
 * 
 * @author adanac
 *
 */
public class DbConfig {
	// 数据库及server配置文件路径
	private static final String ACTIONPATH = "conf/db/config.properties";
	private static DbConfig instance = null;

	private String db_username = null;// 用户名
	private String db_password = null;// 密码
	private String db_type = null; // 数据库类型
	private String db_name = null;// 数据库名称
	private String ip = null;// 数据库ip

	private DbConfig() {
	}

	public String getDb_username() {
		return db_username;
	}

	public String getDb_password() {
		return db_password;
	}

	public String getDb_type() {
		return db_type;
	}

	public void setDb_type(String db_type) {
		this.db_type = db_type;
	}

	public String getDb_name() {
		return db_name;
	}

	public String getIp() {
		return ip;
	}

	public static DbConfig getInstance() {
		if (instance == null) {
			instance = new DbConfig().getNewDbConfig();
		}
		return instance;
	}

	private DbConfig getNewDbConfig() {

		DbConfig dc = new DbConfig();
		Properties prop = new Properties();
		String path = null;
		FileInputStream fis = null;

		try {
			path = DbConfig.class.getClassLoader().getResource("").toURI().getPath();
			fis = new FileInputStream(new File(path + ACTIONPATH));
			prop.load(fis);
			dc.db_username = prop.getProperty("db_username");
			dc.db_password = prop.getProperty("db_password");
			dc.db_type = prop.getProperty("db_type");
			dc.db_name = prop.getProperty("db_name");
			dc.ip = prop.getProperty("ip");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dc;
	}
}
