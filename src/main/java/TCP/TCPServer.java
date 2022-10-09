package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// ЗАДАЧА №1, создайте сервер с использованием ServerSocket. При входящем соединении на сервер,
// примите его, прочитайте из него строку. Выведите её на экран вместе с номером порта, с
// которого это соединение пришло.
public class TCPServer {
    public static final int PORT = 8080; // Порт для подключения к серверу

    public static void main(String[] args) {
        String clientLine ; // актуальная строка клиента
        char pastClientLine = 0; // предыдущая строка клиента

        // Запускаем сервер на определенном порту и принимаем соединение
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // Порт можно выбрать любой в доступном диапазоне 0-65536. Но чтобы не нарваться на уже занятый - рекомендуем использовать около 8080
            System.out.println("СЕРВЕР: сервер запустился.");
            while (true) { // в цикле(!) принимаем подключения
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // true, очистка буфера
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    clientLine = in.readLine().toUpperCase(); // Читаем строку и выводим её на экран вместе с номером порта клиента, с которого пришло соединение
                    System.out.println("СЕРВЕР: ответ клиента: " + clientLine);
                    if (clientLine.equals("НАЧИНАЕМ ИГРУ!")) {
                        out.println("???");
                        continue;
                    } else if (clientLine.charAt(0) == pastClientLine || pastClientLine == 0) {
                        out.println("OK");
                    } else {
                        out.println("NOT OK");
                        break; // выходим из игры
                    }
                    pastClientLine = clientLine.charAt(clientLine.length() - 1); // запоминаем последнюю букву города
                }
            }
        } catch (IOException e) {
            System.out.println("СЕРВЕР: запуск завершился неудачей.");
            e.printStackTrace();
        }
    }
}