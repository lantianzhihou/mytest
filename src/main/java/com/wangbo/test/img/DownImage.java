package com.wangbo.test.img;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

import sun.net.ftp.FtpClient;

public class DownImage {

//	private static String source = "http://172.29.1.168/group1/M00/03/1C/rB0BqFpVcFSASKPdAAA8aM3-4rs593.jpg";
//	private static String source = "http://172.29.1.19:8888/group1/M00/31/7A/rB0BE1qgq3WAV_NFAAAq2N5dB0Y683.jpg";
//	private static String source = "ftp://172.29.1.103/FTP_HOME_PLATFORM/door/photo/2017-12-13/QQ%CD%BC%C6%AC20171213142307.jpg";
//    private static String source = "ftp://10.0.131.254:21/FTP_HOME/intercom/photo/2018-08-06/CgBl_VtnsSSAP73eAAAa1qIsHO8160.jpg";

    private static String source = "C:\\ceshi\\123.jpg";
	
	@Test
	public void testDownload() {
		DownImage downImage = new DownImage();
//        downImage.downLoadImage(source);
        // downImage.downLoadFtp();
        downImage.downLocalImage(source);
	}
	
    public void downLocalImage(String source) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        HttpURLConnection connection = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(source));
            fos = new FileOutputStream("C:\\ceshi\\haha.gif");
            ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int size = 0;
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
                byteArrOS.write(buf, 0, size);
            }
            fos.flush();

            String base64Image = Base64.encodeBase64String(byteArrOS.toByteArray());
            System.out.println(base64Image);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    "C:\\ceshi\\haha.txt")));
            writer.write(base64Image);

            writer.close();
        } catch (Exception e) {
            System.out.println(21312);
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (bis != null)
                    bis.close();
                if (connection != null)
                    connection.disconnect();
            } catch (IOException e) {
            }
        }
    }

	public void downLoadImage(String source) {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		HttpURLConnection connection = null;
		try {
			URL sourceUrl = new URL(source);
			connection = (HttpURLConnection) sourceUrl.openConnection();
			connection.connect();
			InputStream is = connection.getInputStream();

			bis = new BufferedInputStream(is);
			fos = new FileOutputStream("C:\\ceshi\\haha.gif");
			ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
				byteArrOS.write(buf, 0, size);
			}
			fos.flush();

			String base64Image = Base64.encodeBase64String(byteArrOS.toByteArray());
			System.out.println(base64Image);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ceshi\\haha.txt")));
			writer.write(base64Image);

			writer.close();
		} catch (Exception e) {
			System.out.println(21312);
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
				if (connection != null)
					connection.disconnect();
			} catch (IOException e) {
			}
		}
	}

	public void downLoadFtp() {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		FtpClient ftp = null;
		try {
            SocketAddress addr = new InetSocketAddress("172.29.1.103", 21);
			ftp = FtpClient.create();
			ftp.connect(addr);
			ftp.login("ibms", "ibms".toCharArray());
			ftp.setBinaryType();

            InputStream is = ftp.getFileStream("/FTP_HOME/intercom/photo/2018-09-27/4190301533__1538041574539_1.jpg");
			bis = new BufferedInputStream(is);
			fos = new FileOutputStream("C:\\ceshi\\haha.gif");
			ByteArrayOutputStream byteArrOS = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
				byteArrOS.write(buf, 0, size);
			}
			fos.flush();

			String base64Image = Base64.encodeBase64String(byteArrOS.toByteArray());
			System.out.println(base64Image);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ceshi\\haha.txt")));
			writer.write(base64Image);

			writer.close();
		} catch (Exception e) {
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
				if (ftp != null)
					ftp.close();
			} catch (IOException e) {
			}
		}
	}
	
	public void downLoadFTPUtil() {
		FTPClient ftp = new FTPClient();
		FileOutputStream fos = null;
		try {
			int reply;
			ftp.connect("172.29.1.103", 21);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login("ibms", "ibms");// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}
			ftp.changeWorkingDirectory("/FTP_HOME_PLATFORM/door/photo/2017-12-13/");// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals("20171213142307.jpg")) {

					fos = new FileOutputStream("C:\\ceshi\\haha.gif");
					ftp.retrieveFile(ff.getName(), fos);
				}
			}

			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}
	
	

	@Test
	public void testConvertStrToImage() throws IOException {
		StringBuilder result = new StringBuilder();
		try {
			// 构造一个BufferedReader类来读取文件
			BufferedReader br = new BufferedReader(new FileReader(new File("C:\\ceshi\\haha.txt")));
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());

		byte[] base64Arr = Base64.decodeBase64(result.toString());
		FileOutputStream fos = new FileOutputStream("C:\\ceshi\\haha1.gif");
		fos.write(base64Arr, 0, base64Arr.length);
		fos.flush();
		fos.close();
	}
}
