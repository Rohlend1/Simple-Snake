package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    private static final Map<String, Connection> connectionMap = new ConcurrentHashMap<>();
    private static class Handler extends Thread{
        public Socket socket;
        public Handler(Socket socket){
            this.socket = socket;
        }
        public void run(){
            ConsoleHelper.writeMessage("" + socket.getRemoteSocketAddress());

            try(Connection connection = new Connection(socket)){
                String userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED,userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection,userName);
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED,userName));
                ConsoleHelper.writeMessage("Конец");
            }
            catch (Exception e){
                ConsoleHelper.writeMessage("Ошибка");
            }


        }
        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
           while(true){
               connection.send(new Message(MessageType.NAME_REQUEST));
               Message temp = connection.receive();
               if(temp.getType() != MessageType.USER_NAME) continue;
               if(temp.getData().isEmpty()) continue;
               if(connectionMap.containsKey(temp.getData())) continue;
               connectionMap.put(temp.getData(),connection);
               connection.send(new Message(MessageType.NAME_ACCEPTED));
               return temp.getData();
           }
        }
        private void notifyUsers(Connection connection, String userName) throws IOException{
            for(Map.Entry<String, Connection> pair : connectionMap.entrySet()){
                if(!pair.getKey().equals(userName)){
                    connection.send(new Message(MessageType.USER_ADDED, pair.getKey()));
                }
            }
        }
        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
            while(true){
               Message temp = connection.receive();
               if(temp.getType() == MessageType.TEXT){
                   Message msg = new Message(MessageType.TEXT,userName+": "+temp.getData());
                   sendBroadcastMessage(msg);
               }
               else {
                   ConsoleHelper.writeMessage("Ошибка");
               }
            }
        }
    }



    public static void main(String[] args){
       try(ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
           System.out.println("Сервер запущен");
           while (true) {
               Socket socket = serverSocket.accept();
               new Handler(socket).start();
           }
       }
       catch (IOException e){
           ConsoleHelper.writeMessage("Произошла ошибка");
       }
    }
    public static void sendBroadcastMessage(Message message){
        for(Map.Entry<String, Connection> pair: connectionMap.entrySet()){
            try{
                pair.getValue().send(message);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


