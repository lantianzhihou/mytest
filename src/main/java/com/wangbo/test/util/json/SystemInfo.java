package com.wangbo.test.util.json;

public class SystemInfo {
	private Integer id;

	private String sysType;

	private String sysName;

	private String ip;

	private Integer port;

	private String path;

	private String account;

	private String password;

	private Double doubleNum;

	private Float floatNum;

	private Boolean booleanNum;

	private Long longNum;

	public Long getLongNum() {
		return longNum;
	}

	public void setLongNum(Long longNum) {
		this.longNum = longNum;
	}

	public Double getDoubleNum() {
		return doubleNum;
	}

	public void setDoubleNum(Double doubleNum) {
		this.doubleNum = doubleNum;
	}

	public Float getFloatNum() {
		return floatNum;
	}

	public void setFloatNum(Float floatNum) {
		this.floatNum = floatNum;
	}

	public Boolean getBooleanNum() {
		return booleanNum;
	}

	public void setBooleanNum(Boolean booleanNum) {
		this.booleanNum = booleanNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType == null ? null : sysType.trim();
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName == null ? null : sysName.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	@Override
	public String toString() {
		return "SystemInfo [sysType=" + sysType + ", sysName=" + sysName + ", ip=" + ip + "]";
	}

}