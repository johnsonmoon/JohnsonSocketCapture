package com.xuyihao.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.xuyihao.capture.SocketCapture;

/**
 * created by xuyihao on 2016/5/30
 * @author Johnson
 * @description 测试类，包含主方法
 * */
public class JohnsonMain {

	public static void main(String[] args) throws IOException {
		SocketCapture socketCapture = new SocketCapture(8093);
		socketCapture.printSocketStreamInNewThread();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String command = reader.readLine();
			if(command.equals("close server")){
				socketCapture.closeServerSocket();
			}else if(command.equals("exit")){
				socketCapture.closeServerSocket();
				break;
			}else if(command.contains("change port=")){
				int port = Integer.parseInt(command.substring(command.indexOf("change port=")+1));
				socketCapture.reopenNewServerSocket(port);
				socketCapture.printSocketStreamInNewThread();
			}else{
				System.out.println("Please input correctlly:");
				System.out.println("input like:    close server || exit || change pot= || more");
			}
		}
	}

}
