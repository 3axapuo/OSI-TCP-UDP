package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static final int PORT = 8080; // Порт для подключения к серверу
    public static final String HOST = "localhost"; // имя (localhost) или IP (127.0.0.1) - адрес для подключения к серверу

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverLine = null;
        while (true) {
            try (Socket clientSocket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // true, очистка буфера
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                if (serverLine == null) { // начало игры
                    out.println("начинаем игру!");
                } else {
                    String input = scanner.nextLine();
                    if ("end".equals(input)) break; // выходим в случае набора текста end
                    out.println(input); // отсылаем серверу строку
                }
                // Считываем ответ сервера из строки
                serverLine = in.readLine().toUpperCase();
                if (serverLine.equals("???")) {
                    System.out.println("КЛИЕНТ: напиши первый город. Для выхода из игры напиши end.");
                }
                if (serverLine.equals("OK")) System.out.println("Продолжаем играть!");
                if (serverLine.equals("NOT OK")) {
                    System.out.println("Не правильно! \n Игра закончена.");
                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

