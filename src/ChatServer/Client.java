package ChatServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;

    // вынесли в класс удобные средства ввода и вывода
    Scanner in;
    PrintStream out;
    ChatServer server;


    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        // запускаем поток - перенесли из сервера
        new Thread(this).start();

    }

    void receive(String message) { // прием сообщ и прд сообщ другим
        out.println(message);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // задаем значения ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);


            // читаем из сети и пишем в сеть
            out.println("Welcome to CHAT!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
