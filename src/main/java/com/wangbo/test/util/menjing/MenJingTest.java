package com.wangbo.test.util.menjing;

public class MenJingTest {

	public static void main(String[] args) throws Exception {
		System.out.println(getLoginPass("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCr0U5lU9O5mHVtb3ZZQIic6yn1Bhsho+JQODAOo9CeJ6f87TpBJylMPeDoPlHgGcfo6i73Taz4o6DWhb/Evv3McDfPd8T7saPl9aSL6RmhOlAEmebKnGSWoGBK10KIXvuk9ZYo5FVSN1fafrOJ7pTjanB0UFjz4Ox2Cn+hfl1EAQIDAQAB"));
	}
	
	public static String getLoginPass(String publicKey) throws Exception {
		return RSAUtils
		        .encryptBASE64(RSAUtils.encryptByPublicKey("1234Qwer".getBytes(), publicKey));
	}
}
