package com.xuyihao.test;

import com.xuyihao.capture.SocketCapture;

/**
 * created by xuyihao on 2016/5/30
 * @author Johnson
 * @description 测试类，包含主方法
 * */
public class JohnsonMain {

	public static void main(String[] args) {
		SocketCapture socketCapture = new SocketCapture(8093);
		socketCapture.printSocketStream();
	}

}
