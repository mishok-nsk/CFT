package ru.cft.shift.task6.server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server(8090);
            Thread serverThread = new Thread(server::startServer);
            serverThread.start();

            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            serverThread.interrupt();
        } catch (Exception e) {

        }
    }
}
