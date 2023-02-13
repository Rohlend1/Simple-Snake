package main;

import java.util.Scanner;

public class ConsoleHelper {
    public static void writeMessage(String message){
        System.out.println(message);
    }
    public static String readString(){
        return new Scanner(System.in).nextLine();
    }
    public static int readInt(){
        return new Scanner(System.in).nextInt();
    }
}
