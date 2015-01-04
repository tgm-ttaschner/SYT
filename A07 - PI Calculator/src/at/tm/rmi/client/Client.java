package at.tm.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import at.tm.rmi.server.HelloInterface;


public class Client {

    private Client() {}

    public static void main(String[] args) {

//        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.0.22",5052);
            HelloInterface stub = (HelloInterface) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}