package org.example.less_4;

import java.sql.*;

public class ConnectToMySqlDB {
    public static void main(String[] args) {
        /*
        try{
            String url = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "434315101";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection con = DriverManager.getConnection(url, username, password)){

                System.out.println("Соединение с БД Store выполнено успешно!");
            }
        }
        catch(Exception ex){
            System.out.println("Ошибка соединения...");

            System.out.println(ex);
        }

         */
        readData();

    }

    public static void readData() {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test",
                    "root", "434315101");

            // mydb is database
            // mydbuser is name of database
            // mydbuser is password of database

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "select * from author");
            int id;
            String f_name;
            String l_name;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                f_name = resultSet.getString("first_name").trim();
                l_name = resultSet.getString("last_name").trim();
                System.out.println(id
                        + ". " + f_name
                        + " "+ l_name);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
