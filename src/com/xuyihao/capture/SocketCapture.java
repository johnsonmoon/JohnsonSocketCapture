package com.xuyihao.capture;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * created by xuyihao on 2016/5/30
 * @author Johnson
 * @description Socket 抓包类                   
 * */
public class SocketCapture {

	/**
	 * fields
	 * */
	private ServerSocket serverSocket;
	
	/**
	 * constructor                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	 * */
	public SocketCapture(){
		
	}
	
	/**
	 * constructor2
	 * @param portNumber 端口号
	 * */
	public SocketCapture(int portNumber){
		try{
			this.serverSocket = new ServerSocket(portNumber);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Socket创建失败!");
		}
	}
	
	
		
}