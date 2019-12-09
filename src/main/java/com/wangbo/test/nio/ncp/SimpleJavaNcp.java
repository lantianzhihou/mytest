package com.wangbo.test.nio.ncp;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.CharsetDecoder;

import io.netty.util.CharsetUtil;

/**
 * UDP是User Datagram Protocol的简称，一种无连接的传输层协议，提供简单不可靠信息传送服务
 * @author 0380008788
 */
public class SimpleJavaNcp {
	
	public static void main(String[] args) {
		
	}
	
	static class UDPSendTest {
		public static void main(String[] args) throws Exception {
			//1.创建发送端Socket对象
			DatagramSocket socket = new DatagramSocket();
			//2.创建数据并打包
			byte[] data = new String("My name is happywindman").getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length,
			        InetAddress.getByName("127.0.0.1"), 34567);
			//3.发送数据
			socket.send(packet);
			//4.释放资源
			socket.close();
		}
	}

	static class UDPReceiveTest {
		public static void main(String[] args) throws Exception {
			//1.创建接收端Socket对象
			DatagramSocket socket = new DatagramSocket(8080);
			//2.接收数据
			byte[] receiveData = new byte[1024 * 1024];
			DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
			socket.receive(packet);
			//3.解析数据,packet中有发送数据的源IP和源port（其中port是随机产生的）
			System.out.println(packet.getPort());
			System.out.println(bytesToHex(receiveData));
			System.out.println("receive data end");
		}
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
	
	 
	/**
	 * 16进制编码字符串转字节数组
	 *<p>Title: toByteArray</p>
	 *<p>Description: </p>
	 *@param hexString
	 *@return
	 */
	public static byte[] toByteArray(String hexString) {
        hexString = hexString.toLowerCase();
	    final byte[] byteArray = new byte[hexString.length() / 2];
	    int k = 0;
	    for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
		    byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
		    byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
		    byteArray[i] = (byte) (high << 4 | low);
		    k += 2;
		}
		return byteArray;
	}
	/**
	 * 字节数组转16进制编码字符串
	 *<p>Title: toHexString</p>
	 *<p>Description: </p>
	 *@param byteArray
	 *@return
	 */
	public static String toHexString(byte[] byteArray) {
	    String str = null;
	    if (byteArray != null && byteArray.length > 0) {
	        StringBuffer stringBuffer = new StringBuffer(byteArray.length);
	        for (byte byteChar : byteArray) {
	            stringBuffer.append(String.format("%02X", byteChar));
	        }
	        str = stringBuffer.toString();
	    }
	    return str;
	 }
	
}
