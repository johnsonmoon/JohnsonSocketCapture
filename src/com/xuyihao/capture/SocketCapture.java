package com.xuyihao.capture;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * created by xuyihao on 2016/5/30
 * @author Johnson
 * @description Socket 抓包类                   
 * */
public class SocketCapture extends Thread{

	/**
	 * fields
	 * */
	private ServerSocket serverSocket;
	private boolean ifCloseServerSocketPort;
	private boolean ifCloseAllSockets;
	private int socketAmount = 0;
	private ArrayList<SocketThread> threads = new ArrayList<>();

	/**
	 * constructor
	 * @param portNumber 端口号
	 * */
	public SocketCapture(int portNumber){
		try{
			this.serverSocket = new ServerSocket(portNumber);
			this.serverSocket.setSoTimeout(5000);
			this.ifCloseServerSocketPort = false;
			this.ifCloseAllSockets = false;
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
				this.ifCloseServerSocketPort = true;
				this.serverSocket.close();
			}catch(IOException e){
				System.out.println("Server Socket can not been closed!");
			}
		}
	}

	/**
	 * @method closeSocket
	 * @author johnson
	 * @description close the socket
	 */
	public void closeAllSocket(){
		this.ifCloseAllSockets = true;
	}

	/**
	 * @method printSocketStream
	 * @author johnson
	 * @description print the message that socket has received
	 * @attention it will start a new thread for listening packages catching
	 * */
	public void printSocketStreamInNewThread(){
		this.ifCloseAllSockets = false;
		this.start();
	}

	/**
	 * @method run for ServerSocket
	 * */
	@Override
	public void run(){
		while(!this.ifCloseServerSocketPort){
			if(this.ifCloseAllSockets){
				for(int i = 0; i < this.threads.size(); i++){
					this.threads.get(i).closeSocket();
				}
			}
			try{
				Socket socket = this.serverSocket.accept();
				this.socketAmount++;
				SocketThread thread = new SocketThread(socket, this.socketAmount);
				this.threads.add(thread);
				thread.start();
			}catch(IOException e){
				//e.printStackTrace();
			}
		}
	}

	/**
	 * @class inner class socket thread
	 */
	private class SocketThread extends Thread{
		/**
		 * fields
		 */
		private boolean ifCloseSocket = false;
		private Socket socket;
		private int socketNumber = 0;
		private InputStreamReader reader;

		/**
		 * @constructor
		 */
		public SocketThread(Socket s, int number){
			this.ifCloseSocket = false;
			this.socket = s;
			this.socketNumber = number;
			try{
				this.reader = new InputStreamReader(this.socket.getInputStream());
			}catch(Exception e){
				System.out.println("Can not get the InputStream!");
			}
		}

		/**
		 * @method
		 * @author johnson
		 * @description close the current thread
		 */
		public void closeSocket(){
			this.ifCloseSocket = true;
		}

		/**
		 * @method run for socket
		 */
		@Override
		public void run(){
			try{
				OutputStreamWriter writer = new OutputStreamWriter(this.socket.getOutputStream());
				System.out.println("SocketNumber:       " + this.socketNumber);
				System.out.println("InetAddress:        " + socket.getInetAddress().toString());
				System.out.println("LocalAddress:       " + socket.getLocalAddress().toString());
				System.out.println("Port:               " + socket.getPort());
				System.out.println("LocalPort:          " + socket.getLocalPort());
				System.out.println("ReceiveBufferSize:  " + socket.getReceiveBufferSize());
				while(true){
					if(this.ifCloseSocket){
						this.reader.close();
						break;
					}
					System.out.println("SocketNumber:       " + this.socketNumber);
					char[] c = new char[32];
					int length = 0;
					while((length = reader.read(c)) != -1){
						String s = String.valueOf(c);
						if (s.contains("--~%%~--")){//the reason to stop read from the stream is a '~' in the last position
							writer.write("This is server and I had received your message!");
							writer.flush();
							System.out.print(s.substring(0, s.indexOf("--~%%~--")) + "\n");
						}else{
							System.out.print(s);
						}
					}
				}
				System.out.println("SocketNumber:  "+this.socketNumber+" thread stopped!");
			}catch(Exception e) {
				//do nothing
			}
		}
	}
}