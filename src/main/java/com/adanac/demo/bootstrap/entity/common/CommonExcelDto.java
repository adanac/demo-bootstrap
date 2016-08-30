package com.adanac.demo.bootstrap.entity.common;

import com.adanac.tool.e2e.annotation.ExcelEntity;
import com.adanac.tool.e2e.annotation.ExcelProperty;

/**
 * 功能说明： 测试例子 参数说明：
 */
@ExcelEntity
public class CommonExcelDto {
	// value的值要也Excel表头的值保持一致（区分大小写）
	@ExcelProperty(value = "Id"/* , required = true */)
	private String id;
	@ExcelProperty(value = "userName", rule = MyStringCheckRule.class)
	private String userName;
	@ExcelProperty(value = "deptCode", rule = MyStringCheckRule.class)
	private String deptCode;

	@ExcelProperty(value = "Sex")
	private String sex;

	@ExcelProperty(value = "Age", regexp = "^[1-9]{1}[0-9]{1}$", regexpErrorMessage = "年龄必须在10-99岁之间")
	private Integer age;

	// @ExcelProperty(value = "Tel", defaultValue = "18678859721",
	// hasDefaultValue = true)
	// private Long tel;

	// @ExcelProperty(value = "创建时间")
	// private Timestamp createDate;

	@ExcelProperty(value = "Passwd")
	private String passwd;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "CommonExcelDto [userName=" + userName + ", deptCode=" + deptCode + ", sex=" + sex + ", age=" + age
				+ ", passwd=" + passwd + "]";
	}

}
