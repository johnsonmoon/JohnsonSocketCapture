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
		System.out.println("Please input a port to start a new ServerSocket:");
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		int port = Integer.parseInt(r.readLine());
		SocketCapture socketCapture = new SocketCapture(port);
		socketCapture.printSocketStreamInNewThread();
		System.out.println("ServerSocket in port "+port+" started!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("------Tip-------:");
		System.out.println("Input  \"close server\"  to close the current ServerSocket;");
		System.out.println("Input  \"change port=\"  to start a new ServerSocket in new port;");
		System.out.println("Input  \"exit\"          to stop the program;");
		System.out.println("----------------:");
		while(true){
			String command = reader.readLine();
			if(command.equals("close server")){
				socketCapture.closeAllSocket();
				socketCapture.closeServerSocket();
				System.out.println("ServerSocket closed!");
				System.out.println("------Tip-------:");
				System.out.println("Input  \"close server\"  to close the current ServerSocket;");
				System.out.println("Input  \"change port=\"  to start a new ServerSocket in new port;");
				System.out.println("Input  \"exit\"          to stop the program;");
				System.out.println("----------------:");
			}else if(command.equals("exit")){
				socketCapture.closeAllSocket();
				socketCapture.closeServerSocket();
				break;
			}else if(command.contains("change port=")){
				int portC = Integer.parseInt(command.substring(command.indexOf("change port=")+12));
				socketCapture = new SocketCapture(portC);
				socketCapture.printSocketStreamInNewThread();
				System.out.println("New ServerSocket in port "+portC+" started!");
				System.out.println("------Tip-------:");
				System.out.println("Input  \"close server\"  to close the current ServerSocket;");
				System.out.println("Input  \"change port=\"  to start a new ServerSocket in new port;");
				System.out.println("Input  \"exit\"          to stop the program;");
				System.out.println("----------------:");
			}else{
				System.out.println("Please input correctly:");
				System.out.println("------Tip-------:");
				System.out.println("Input  \"close server\"  to close the current ServerSocket;");
				System.out.println("Input  \"change port=\"  to start a new ServerSocket in new port;");
				System.out.println("Input  \"exit\"          to stop the program;");
				System.out.println("----------------:");
			}
		}
		System.out.println("Exit successfully!");
		System.exit(1);
	}

}
