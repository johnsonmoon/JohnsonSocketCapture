package com.xuyihao.capture;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
			System.out.println("Server socket 创建失败!");
		}
	}
	
	/**
	 * @method closeServerSocket
	 * @author johnson
	 * @description 关闭Server Socket的方法
	 * */
	public void closeServerSocket(){
		if(!this.serverSocket.isClosed()){
			try{
				this.serverSocket.close();
			}catch(IOException e){
				System.out.println("Server Socket can not been closed!");
			}
		}
	}
	
	/**
	 * @method printSocketStream
	 * @author johnson
	 * @description print the message that socket has received
	 * */
	public void printSocketStream(){
		while(true){
			try{
				Socket socket = this.serverSocket.accept();
				System.out.println(socket.getInetAddress().toString());
				System.out.println(socket.getLocalAddress().toString());
				System.out.println(socket.getPort());
				System.out.println(socket.getLocalPort());
				System.out.println(socket.getReceiveBufferSize());
				InputStream in = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(in);
				char[] c = new char[1];
				while(reader.read(c) != -1){
					System.out.print(c);
				}
				in.close();
				reader.close();
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}	
}