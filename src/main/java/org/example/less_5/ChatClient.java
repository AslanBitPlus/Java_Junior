package org.example.less_5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class ChatClient {

  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {
//    String clientLogin = "User_" + UUID.randomUUID().toString();
//    String clientLogin = "nagibator";
    Scanner console = new Scanner(System.in);
    String clientLogin = console.nextLine();

    // 127.0.0.1 или localhost
    try (Socket server = new Socket("localhost", 8888)) {
      System.out.println("Вы успешно подключились к серверу");

      try (PrintWriter out = new PrintWriter(server.getOutputStream(), true)) {
        Scanner in = new Scanner(server.getInputStream());

        String loginRequest = createLoginRequest(clientLogin);
        out.println(loginRequest);

        String loginResponseString = in.nextLine();
        if (!checkLoginResponse(loginResponseString)) {
          // TODO: Можно обогатить причиной, чтобы клиент получал эту причину
          // (логин уже занят, ошибка аутентификации\авторизации, ...)
          System.out.println("Не удалось подключиться к серверу");
          return;
        }

        // client <----------------> server
        // client getUsers ->        server
        // client <- (getUsers|sendMessage from client) server    <--------sendMessage client2
        //

        // Отдельный поток на чтение сообщений
//        ServerListener serverListener = new ServerListener(in);
//        new Thread(serverListener).start();
        new Thread(() -> {
          while (true) {
            // TODO: парсим сообщение в AbstractRequest
            //  по полю type понимаем, что это за request, и обрабатываем его нужным образом
            String msgFromServer = in.nextLine();
            System.out.println("Сообщение от сервера: " + msgFromServer);
          }
        }).start();


        while (true) {
//          Это я решил не использовать
//          System.out.println("Что хочу сделать?");
//          System.out.println("1. Послать сообщение другу");
//          System.out.println("2. Послать сообщение всем");
//          System.out.println("3. Получить список логинов");

          String type = console.nextLine();
          SendMessageRequest request = new SendMessageRequest();
          if (type.equals("1")) {
            // TODO: считываете с консоли логин, кому отправить

            // SendMessageRequest request = new SendMessageRequest();
            request.setMessage(console.nextLine());
            request.setRecipient("unknown"); // TODO указываем логин получателя

            String sendMsgRequest = objectMapper.writeValueAsString(request);
            out.println(sendMsgRequest);
          } else if (type.equals("3")) {
            // TODO: Создаете запрос отправки "всем"
            // send(get users)
            // String msgFromServer = in.readLine();
            // ...

//            serverListener.subscribe("get users", new Consumer<String>() {
//              @Override
//              public void accept(String s) {
//                System.out.println("Список юзеров: " + s);
//              }
//            });

          } else if (type.equals("/help")) {
            System.out.println("Команды для работы в чате: ");
            // Список команд для работы в чате
            request.setMessage(type);
            request.setRecipient(clientLogin);
            String sendMsgRequest = objectMapper.writeValueAsString(request);
            out.println(sendMsgRequest);

          } else if (type.equals("/all")) {
            System.out.println("Отправить сообщение всем пользователям: ");
            //
            System.out.print("Текст сообщения: ");
            request.setMessage(type + console.nextLine());

            request.setRecipient(clientLogin);

            String sendMsgRequest = objectMapper.writeValueAsString(request);
            out.println(sendMsgRequest);




          } else if (type.equals("/list")) {
            System.out.println("Список всех пользователей чата: ");
            //
            request.setMessage(type);
            request.setRecipient(clientLogin);
            String sendMsgRequest = objectMapper.writeValueAsString(request);
            out.println(sendMsgRequest);




          } else if (type.startsWith("@")) {
            System.out.println("Отправить сообщение пользователю: " + type.substring(1));
            //
            System.out.print("Текст сообщения: ");
            request.setMessage(console.nextLine());
            request.setRecipient(type.substring(1));

            String sendMsgRequest = objectMapper.writeValueAsString(request);
            out.println(sendMsgRequest);
          } else if (type.equals("/exit")) {
            //
            break;
          }

        }
      }
    } catch (IOException e) {
      System.err.println("Ошибка во время подключения к серверу: " + e.getMessage());
    }

    System.out.println("Вы Отключились от сервера");
  }

  private static String createLoginRequest(String login) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setLogin(login);

    try {
      return objectMapper.writeValueAsString(loginRequest);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Ошибка JSON: " + e.getMessage());
    }
  }

  private static boolean checkLoginResponse(String loginResponse) {
    try {
      LoginResponse resp = objectMapper.reader().readValue(loginResponse, LoginResponse.class);
      return resp.isConnected();
    } catch (IOException e) {
      System.err.println("Ошибка чтения JSON: " + e.getMessage());
      return false;
    }
  }

  private static void sleep() {
    try {
      Thread.sleep(Duration.ofMinutes(5));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

//  private static class ServerListener implements Runnable {
//    private final Scanner in;
//    private final Map<String, List<Consumer<String>>> subscribers = new ConcurrentHashMap<>();
//
//    public ServerListener(Scanner in) {
//      this.in = in;
//    }
//
//    public void subscribe(String type, Consumer<String> consumer) {
//      List<Consumer<String>> consumers = subscribers.getOrDefault(type, new ArrayList<>());
//      consumers.add(consumer);
//      subscribers.put(type, consumers);
//    }
//
//    @Override
//    public void run() {
//      while (true) {
//        String msgFromServer = in.nextLine();
//
//        // TODO: парсим сообщение в AbstractRequest
//        //  по полю type понимаем, что это за request, и обрабатываем его нужным образом
//        String type = null;
//
//        subscribers.getOrDefault(type, List.of()).forEach(it -> {
//          it.accept(msgFromServer);
//        });
//        subscribers.remove(type);
//
//        System.out.println("Сообщение от сервера: " + msgFromServer);
//      }
//    }
//  }

}
