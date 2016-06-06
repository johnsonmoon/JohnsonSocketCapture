package com.xuyihao.test;

import com.xuyihao.url.connectors.HttpUtil;
import com.xuyihao.url.enums.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by johnson on 16-6-4.
 * @author johnson
 * @description for testing socket capture
 */
public class TestMain {
    /**
     *@fields for socket
     */
    private static Socket socket;

    /**
     * fields for httpUtil
     */
    private static HttpUtil httpUtil;
    private static String actionURL;
    private static HashMap<String, String> parameters;

    public static void main(String[] args) throws IOException{
        System.out.println("Please choose a function to use:");
        System.out.println("-----Tip-----:");
        System.out.println("Input \"1\" to use socket;");
        System.out.println("Input \"2\" to use HttpUtil;");
        System.out.println("--------------");
        int i =Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
        switch (i){
            case 1:
                useSocket();
                break;
            case 2:
                useHttpUtil();
                break;
            default:
                System.out.println("Input error!");
                break;
        }
    }

    /**
     * @author johnson
     * @method use socket to send message
     */
    public static void useSocket()throws IOException{
        System.out.println("Please input your destiny address(like 127.0.0.1:8080):");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String actionURL = "";
        String url = "";
        int port = 0;
        while(true){
            actionURL = reader.readLine();
            url = actionURL.substring(0, actionURL.indexOf(":"));
            port = Integer.parseInt(actionURL.substring(actionURL.indexOf(":") + 1));
            System.out.println("Your input: "+url+" & "+port+"");
            System.out.println("Please check your input, is it correct?(y/n)");
            String re = reader.readLine();
            if(re.equals("y") || re.equals("Y")){
                break;
            }else{
                System.out.println("Input your action url again!");
            }
        }
        socket = new Socket(url, port);
        socket.setSoTimeout(10000);
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        System.out.println("Socket created! Now you can send your message to the server!");
        System.out.println("-----Tip-----:");
        System.out.println("Input \"exit\" to stop the program;");
        System.out.println("Input \"send\" to send the message;");
        System.out.println("--------------");
        String command = "";
        String messageSend = "";
        while(true){
            command = reader.readLine();
            if(command.equals("exit")){
                break;
            }else if(command.equals("send")){
                if(messageSend.equals("")){
                    System.out.println("Please input something to be sent before send!\n");
                }else {
                    writer.write(messageSend+"--~%%~--");
                    writer.flush();
                    System.out.println("Message sent!\n");
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                InputStreamReader reader = new InputStreamReader(socket.getInputStream());
                                char[] c = new char[1];
                                while (reader.read(c) != -1) {
                                    System.out.print(c);
                                }
                                System.out.print("\n");
                            }catch (IOException e){
                                //do nothing
                            }
                        }
                    }.start();
                }
            }else{
                messageSend = command;
                System.out.println("Message to be send:  \""+messageSend+"\"");
                System.out.println("Input send to send the message:\n");
            }
        }
        writer.close();
        System.out.println("Program exit!");
    }

    /**
     * @author johnson
     * @method use HttpUtil to send Http Request
     */
    public static void useHttpUtil()throws IOException{
        httpUtil = new HttpUtil(Platform.LINUX);
        parameters = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please input your URL address(without \"Http://\"):");
        actionURL = "Http://" + reader.readLine();
        System.out.println(actionURL + " added!");
        System.out.println("-----Tip-----:");
        System.out.println("To start a request, please first input your parameters;");
        System.out.println("Please input your parameters with format: \"key=value\";");
        System.out.println("Send request by input \"send\";");
        System.out.println("Clear parameter HashMap by input \"clear\";");
        System.out.println("Exit program by input \"exit\";");
        System.out.println("--------------");
        String in = "";
        while(true){
            in = reader.readLine();
            if(in.equals("send")){
                new Thread(){
                    @Override
                    public void run(){
                        System.out.println("Request sending...........\n");
                        System.out.println(httpUtil.executePostByUsual(actionURL, parameters));
                        System.out.println("Request send successfully!");
                    }
                }.start();
                System.out.println("-----Tip-----:");
                System.out.println("To start a request, please first input your parameters;");
                System.out.println("Please input your parameters with format: \"key=value\";");
                System.out.println("Send request by input \"send\";");
                System.out.println("Clear parameter HashMap by input \"clear\";");
                System.out.println("Exit program by input \"exit\";");
                System.out.println("--------------");
            }else if(in.equals("clear")){
                parameters.clear();
                System.out.println("Parameters cleared successfully!\n");
            }else if(in.equals("exit")){
                break;
            }else if(in.contains("=")){
                String key = in.substring(0, in.indexOf("="));
                String value = in.substring(in.indexOf("=") + 1);
                parameters.put(key, value);
                System.out.println("Parameter key \""+key+"\", value \""+value+"\" added!");
            }else{
                System.out.println("Please input correctly!");
                System.out.println("-----Tip-----:");
                System.out.println("To start a request, please first input your parameters;");
                System.out.println("Please input your parameters with format: \"key=value\";");
                System.out.println("Send request by input \"send\";");
                System.out.println("Clear parameter HashMap by input \"clear\";");
                System.out.println("Exit program by input \"exit\";");
                System.out.println("--------------");
            }
        }
        System.out.println("Exit successfully!");
    }
}
