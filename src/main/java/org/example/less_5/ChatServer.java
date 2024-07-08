package org.example.less_5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

  static ArrayList<String> users = new ArrayList<String>();
  static String cmdhelp = "/help - Помощь";
  static String cmdlist = "/users - список всех польховатедей чата";
  static String cmdall = "/all - отправить сообщение всем пользователям";
  static String cmdexit = "/exit - выход из чата";

  private final static ObjectMapper objectMapper = new ObjectMapper();

  // Socket - абстракция, к которой можно подключиться
  // ip-address + port - socket
  // network - сеть - набор соединенных устройств
  // ip-address - это адрес устройства в какой-то сети
  // 8080 - http
  // 443 - https
  // 35 - smtp
  // 21 - ftp
  // 5432 - стандартный порт postgres
  // клиент подключается к серверу

  /**
   * Порядок взаимодействия:
   * 1. Клиент подключается к серверу
   * 1.1. Сервер отправляет клиенту список команд для работы в чате
   * 2. Клиент посылает сообщение, в котором указан логин. Если на сервере уже есть подключеный клиент с таким логином, то соедение разрывается
   * 3. Клиент может посылать 4 типа команд:
   * 3.1. /help - список команд для работы в чате
   * 3.2. /list - получить список других пользователей чата
   * 3.3. /all - отправить сообщение всем с содержимым message
   * 3.4 @login message - отправить личное сообщение с содержимым message другому клиенту с логином login
   */

  // 1324.132.12.3:8888
  public static void main(String[] args) {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    try (ServerSocket server = new ServerSocket(8888)) {
      System.out.println("Сервер запущен");
      // startInfo();
      while (true) {
        System.out.println("Ждем клиентского подключения");
        Socket client = server.accept();
        ClientHandler clientHandler = new ClientHandler(client, clients);
        new Thread(clientHandler).start();
      }
    } catch (IOException e) {
      System.err.println("Ошибка во время работы сервера: " + e.getMessage());
    }
  }

  private static class ClientHandler implements Runnable {
    private final Socket client;
    private final Scanner in;
    private final PrintWriter out;
    private final Map<String, ClientHandler> clients;
    private String clientLogin;

    public ClientHandler(Socket client, Map<String, ClientHandler> clients) throws IOException {
      this.client = client;
      this.clients = clients;
      this.in = new Scanner(client.getInputStream());
      this.out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
      System.out.print("Подключен новый клиент: ");

      try {
        String loginRequest = in.nextLine();
        LoginRequest request = objectMapper.reader().readValue(loginRequest, LoginRequest.class);
        this.clientLogin = request.getLogin();
        System.out.println(request.getLogin());
      } catch (IOException e) {
        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
        String unsuccessfulResponse = createLoginResponse(false);
        out.println(unsuccessfulResponse);
        doClose();
        return;
      }

      System.out.println("Запрос от клиента: " + clientLogin);
      // Проверка, что логин не занят
      if (clients.containsKey(clientLogin)) {
        String unsuccessfulResponse = createLoginResponse(false);
        out.println(unsuccessfulResponse);
        doClose();
        return;
      }

      clients.put(clientLogin, this);
      String successfulLoginResponse = createLoginResponse(true);
      out.println(successfulLoginResponse);
      // Отправляем список команд для работы в чате клиенту
      out.println(cmdhelp);
      out.println(cmdlist);
      out.println(cmdall);
      out.println("@username, Сообщение пользователю username");
      out.println(cmdexit);
      //
      users.add(clientLogin);
      while (true) {
        String msgFromClient = in.nextLine();

        final String type;
        try {
          AbstractRequest request = objectMapper.reader().readValue(msgFromClient, AbstractRequest.class);
          type = request.getType();
        } catch (IOException e) {
          System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
          sendMessage("Не удалось прочитать сообщение: " + e.getMessage());
          continue;
        }

        if (SendMessageRequest.TYPE.equals(type)) {
          // Клиент прислал SendMessageRequest

          final SendMessageRequest request;
          try {
            request = objectMapper.reader().readValue(msgFromClient, SendMessageRequest.class);
          } catch (IOException e) {
            System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
            sendMessage("Не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
            continue;
          }

          ClientHandler clientTo = clients.get(request.getRecipient());
          if (clientTo == null) {
            sendMessage("Клиент с логином [" + request.getRecipient() + "] не найден");
            continue;
          } else if (request.getMessage().startsWith("/help")) {
            // Сообщение клиенту, что он отправил команду на выполнение ...
            // out.println("[" + request.getRecipient() + " <-: " + request.getMessage());
            // Печать в консоли Сервера о запросе команды
            System.out.println("[" + request.getRecipient() + "] <-: " + request.getMessage());
            //
            out.println(cmdhelp);
            out.println(cmdlist);
            out.println(cmdall);
            out.println("@username, Сообщение пользователю username");
            out.println(cmdexit);
            continue;
          } else if (request.getMessage().startsWith("/list")) {
            System.out.println("[" + request.getRecipient() + "] <-: "
                    + request.getMessage());
            out.println(users);
            continue;
          }
          clientTo.sendMessage(request.getMessage());




        } else if (false) { // DisconnectRequest.TYPE.equals(type)
          break;




        } else {
          System.err.println("Неизвестный тип сообщения: " + type);
          sendMessage("Неизвестный тип сообщения: " + type);
          continue;
        }
      }

      doClose();
    }

    private void doClose() {
      try {
        in.close();
        out.close();
        client.close();
      } catch (IOException e) {
        System.err.println("Ошибка во время отключения клиента: " + e.getMessage());
      }
    }

    public void sendMessage(String message) {
      // TODO: нужно придумать структуру сообщения
      out.println(message);
    }

    private String createLoginResponse(boolean success) {
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setConnected(success);
      try {
        return objectMapper.writer().writeValueAsString(loginResponse);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Не удалось создать loginResponse: " + e.getMessage());
      }
    }

    @Override
    public String toString() {
      return "ClientHandler{" +
              "clients=" + clients +
              '}';
    }
  }

}
