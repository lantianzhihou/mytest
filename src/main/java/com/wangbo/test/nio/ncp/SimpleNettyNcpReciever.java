package com.wangbo.test.nio.ncp;

import java.util.Arrays;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * 相比于TCP通信，UDP不存在客户端和服务器端的实际连接，因此不需要为连接（channelPipeline）设置handler,
 * 对于服务端，只需设置启动辅助类的handler即可
 * @author 0380008788
 *
 */
public class SimpleNettyNcpReciever {
	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		//由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)//支持广播
		        .handler(new ChineseProverbServerHandler());//ChineseProverbServerHandler是业务处理类
		b.bind(port).sync().channel().closeFuture().await();
	}

	static class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
		//谚语列表
		private static final String[] DICTIONARY = { "只要功夫深，铁棒磨成针。", "旧时王谢堂前燕,飞入寻常百姓家。",
		        "洛阳亲友如相问，一片冰心在玉壶。", "一寸光阴一寸金，寸金难买寸光阴。", "老骥伏枥，志在千里，烈士暮年，壮心不已" };

		private String nextQuote() {
			//返回0-DICTIONARY.length中的一个整数。
			int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
			return DICTIONARY[quoteId];//将谚语列表中对应的谚语返回
		}
		
		/**
		 * 在这个方法中，形参packet客户端发过来的DatagramPacket对象
		 * DatagramPacket 类解释
		 * 1.官网是这么说的：
		 * The message container that is used for {@link DatagramChannel} to communicate with the remote peer.
		 * 翻译：DatagramPacket 是消息容器，这个消息容器被 DatagramChannel使用，作用是用来和远程设备交流
		 * 2.看它的源码我们发现DatagramPacket是final类不能被继承，只能被使用。我们还发现DatagramChannel最终实现了AddressedEnvelope接口，接下来我们看一下AddressedEnvelope接口。
		 * AddressedEnvelope接口官网解释如下：
		 * A message that wraps another message with a sender address and a recipient address.
		 * 翻译：这是一个消息,这个消息包含发送者和接受者消息
		 * 3.那我们知道了DatagramPacket它包含了发送者和接受者的消息，
		 * 通过content()来获取消息内容
		 * 通过sender();来获取发送者的消息
		 * 通过recipient();来获取接收者的消息。
		 * 
		 * 4.public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {}
		 *  这个DatagramPacket其中的一个构造方法，data 是发送内容;是发送都信息。
		 */
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
		        throws Exception {
			//上面说了，通过content()来获取消息内容
			ByteBuf content = packet.content();
			System.out.println(content.readableBytes());
			byte[] buffer = new byte[4096];
			content.getBytes(0, buffer,0,content.capacity());
			System.out.println(SimpleNettyNcpReciever.bytesToHex(buffer));
			
			int length = content.getByte(1) & 0xFF;
			byte[] dst = new byte[length + 3];
			content.getBytes(0, dst, 0, length + 3);
			String dataStr = SimpleNettyNcpReciever.bytesToHex(dst);
			
			String dataHeader = SimpleNettyNcpReciever
			        .bytesToHex(new byte[] { content.getByte(0) });
			if ("5A".equals(dataHeader)) {
				String dataType = dataStr.substring(4, 4+2);
				System.out.println("===dataType:" + dataType);
				
				String deviceType = dataStr.substring(6, 6+2);
				System.out.println("===deviceType:" + deviceType);
				
				// 首位为运营商代号，4表示移动；数据库不存储运营商代号
				String deviceNo = dataStr.substring(8, 8 + 8 * 2);
				System.out.println("===deviceNo:" + deviceNo);
				
				int rssi = content.getByte(15) & 0xFF;
				System.out.println("===rssi:" + (rssi < 0 ? -(rssi & 0x7F) : rssi));
				
				int batteryLevel = content.getByte(17) & 0xFF;
				System.out.println("===batteryLevel:" + batteryLevel / 10.0);
				// 温度
				int temperature = content.getByte(18) & 0xFF;
				System.out.println("===temperature:"
				        + (temperature < 0 ? -(temperature & 0x7F) : temperature));
				// 湿度
				int humidity = content.getByte(19) & 0xFF;
				System.out.println("===humidity:" + humidity + "%");
				
				// 井盖状态，最后一位0表示关闭，1表示打开
				int state = content.getByte(20) & 0xFF;
				System.out.println("===state:" + (state & 0x01));
				
				// 倾斜
				int slantAngle = content.getByte(21) & 0xFF;
				System.out.println("===slantAngle:" + slantAngle);
				
//				ctx.writeAndFlush(new DatagramPacket(
//				        Unpooled.copiedBuffer(hexStrToByteArray("A5018080")), packet.sender()));
			} else if ("AA".equals(dataHeader)) {
				
			}
			/**
			 * 重新 new 一个DatagramPacket对象，我们通过packet.sender()来获取发送者的消息。 重新发达出去！
			 */
			/*System.out.println(req);
			if ("谚语字典查询？".equals(req)) {
				ctx.writeAndFlush(new DatagramPacket(
				        Unpooled.copiedBuffer("谚语查询结果：" + nextQuote(), CharsetUtil.UTF_8),
				        packet.sender()));
			}*/
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
			cause.printStackTrace();
		}

	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * 将十进制数字节数组转成相应的2位十六进制 ，如24=>'18'
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Arrays.toString(hexStrToByteArray("D7")));
//		System.out.println(-69 & 0x7F);
//		System.out.println(bytesToHex(new byte[] {-69}));
		System.out.println(bytesToHex(new byte[] { 12,34,55,46,88,-24 }));;
		System.out.println(getBCC(new byte[] { 12,34,55,46,88,-24 }));
		
//		int port = 8080;
//		new SimpleNettyNcpReciever().run(port);
	}
	
	/**
	 * 将2位十六进制字符串转换成十进制，如‘D7’=>13*16+7=(byte)215=-41
	 * @param s
	 * @return
	 */
	public static byte[] hexStrToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) 
	        ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * BCC校验（异或校验）算法求 字节数组的校验值
	 * 如{12,34,55,46,88,-24} => 十六进制字符串0C22372E58E8 => 十六进制87
	 * 地址：https://www.xuebuyuan.com/537890.html
	 * 在线工具：http://www.ip33.com/bcc.html
	 * @param data
	 * @return
	 */
	public static String getBCC(byte[] data) {
		String ret = "";
		byte BCC[] = new byte[1];
		for (int i = 0; i < data.length; i++) {
			BCC[0] = (byte) (BCC[0] ^ data[i]);
		}
		String hex = Integer.toHexString(BCC[0] & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		ret += hex.toUpperCase();
		return ret;
	}
	
	/**
	 * 采用IEEE 754标准十六进制字符串转double
	 * 如3D 73 6F 63 => 0.05943239852786064
	 * 地址：https://www.cnblogs.com/bingoj/p/11148305.html
	 * 在线工具：http://lostphp.com/hexconvert/
	 * @param bytes
	 * @return
	 */
	public static double transferForIEEE754(String hexStr) {
		StringBuffer binaryStr = new StringBuffer();
		for (int i = 0; i < hexStr.length(); i += 2) {
			String a = hexStr.substring(i, i + 2);
			int c = Integer.parseInt(a, 16);
			String item = String.format("%08d", Integer.parseInt(Integer.toBinaryString(c)));
			binaryStr.append(item);
		}
		int n = Integer.parseInt(binaryStr.substring(1, 9), 2);
		String mStr = binaryStr.substring(9, binaryStr.length() - 1);
		double sum = 0;
		for (int i = 1; i <= mStr.length(); i++) {
			if (mStr.charAt(i - 1) == '1') {
				sum = sum + Math.pow(0.5, i);
			}
		}
		double a = (Math.pow(2, n - 127)) * (1 + sum);
		return a;
	}
}
