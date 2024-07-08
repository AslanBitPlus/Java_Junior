package org.example.less_5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;

public class JsonMain {

  public static void main(String[] args) throws JsonProcessingException {
    ListResponse response = new ListResponse();

    User user1 = new User();
    user1.setLogin("admin");

    User user2 = new User();
    user2.setLogin("nagibator");

    User user3 = new User();
    user3.setLogin("karburator");

    User user4 = new User();
    user4.setLogin("generator");

    response.setUsers(List.of(user1, user2, user3, user4));

    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String s = writer.writeValueAsString(response);
    System.out.println(s);
  }

}
