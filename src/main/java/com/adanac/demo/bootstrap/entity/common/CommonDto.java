package com.adanac.demo.bootstrap.entity.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommonDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9047433652716017574L;

	private String id;

	private String userName;// 姓名
	private String deptCode;// 部门
	private Integer sex;
	private Integer age;
	private String passwd;

	public CommonDto() {
		super();
	}

	public CommonDto(String name, Integer age) {
		super();
		this.userName = name;
		this.age = age;
	}

	public CommonDto(String userName, Integer sex, Integer age) {
		super();
		this.userName = userName;
		this.sex = sex;
		this.age = age;
	}

	public CommonDto(String id, String name, String pwd) {
		super();
		this.id = id;
		this.userName = name;
		this.passwd = pwd;
	}

	public CommonDto(String name, String pwd, Integer age) {
		super();
		this.userName = name;
		this.passwd = pwd;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "CommonDto [id=" + id + ", userName=" + userName + ", deptCode=" + deptCode + ", sex=" + sex + ", age="
				+ age + ", passwd=" + passwd + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userName", userName);
		map.put("deptCode", deptCode);
		map.put("sex", sex);
		map.put("age", age);
		map.put("passwd", passwd);
		return map;
	}

}
