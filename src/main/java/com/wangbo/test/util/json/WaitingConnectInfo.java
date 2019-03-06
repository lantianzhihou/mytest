package com.wangbo.test.util.json;

public class WaitingConnectInfo {

	private SystemInfo systemInfo;
	private ConnectParam param;

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	public ConnectParam getParam() {
		return param;
	}

	public void setParam(ConnectParam param) {
		this.param = param;
	}

	public String getUri() {
		if (systemInfo != null) {
			return "https://" + systemInfo.getIp() + ":" + systemInfo.getPort()
					+ systemInfo.getPath() + "/authorization";
		}
		return null;
	}

	public String getPassport() {
		if (systemInfo != null)
			return systemInfo.getAccount() + ":" + systemInfo.getPassword();
		return null;
	}
}
