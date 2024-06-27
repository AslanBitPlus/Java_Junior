package org.example.less_3.hw;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {

    /**
     * С помощью JDBC, выполнить следующие пункты:
     * 1. Создать таблицу Person (скопировать код с семниара)
     * 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
     * 3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
     * 4. Написать метод, который загружает Имя department по Идентификатору person
     * 5. * Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name
     *   Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
     * 6. ** Написать метод, который загружает Map<String, List<String>>, в которой маппинг department.name -> <person.name>
     *   Пример:
     *   [
     *     {"department #1", ["person #1", "person #2"]},
     *     {"department #2", ["person #3", "person #4"]}
     *   ]
     *
     *  7. *** Создать классы-обертки над таблицами, и в пунктах 4, 5, 6 возвращать объекты.
     */

    public static void main(String[] args) {
        // jdbc:postgres:user@password:host:port///
        System.out.println("Подключение к базе данных");
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            System.out.println("jdbc:h2:mem:test - Ok!");
            System.out.println("Создание таблицы Department =========== ");
            createDepartmentTable(connection);
            System.out.println("Department - Ok!");
            insertDataToDepartment(connection);
            selectAllDepartment(connection);

            System.out.println("Создание таблицы Person ================");
            createPersonTable(connection);
            System.out.println("Person - Ok!");
            insertDataToPerson(connection);
            selectAllPerson(connection);

            System.out.println(getPersonDepartmentName(connection, 12));

            System.out.println(getPersonDepartments(connection));

        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    private static void createDepartmentTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
        create table department (
          id bigint primary key,
          name varchar(128) not null
        )
        """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void createPersonTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
        create table person (
          id bigint primary key,
          department_id bigint,
          name varchar(256),
          age integer,
          active boolean,
          foreign key (department_id) references department (id)
        )
        """);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void insertDataToDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into department(id, name) values\n");
            for (int i = 1; i <= 5; i++) {
                insertQuery.append(String.format("(%s, '%s')", i, "Department - " + i));

                if (i != 5) {
                    insertQuery.append(",\n");
                }
            }

            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("В Таблицу Department вставлено строк: " + insertCount);
        }
    }

    private static void insertDataToPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQuery = new StringBuilder("insert into person(id, department_id,name, age, active) values\n");
            for (int i = 1; i <= 25; i++) {
                int dep = ThreadLocalRandom.current().nextInt(1, 6);
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                insertQuery.append(String.format("(%s, %s, '%s', %s, %s)", i, dep,"Person #" + i, age, active));

                if (i != 25) {
                    insertQuery.append(",\n");
                }
            }

            int insertCount = statement.executeUpdate(insertQuery.toString());
            System.out.println("В Таблицу Person вставлено строк: " + insertCount);
        }
    }

    private static void selectAllDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
        select *
        from department
        """);
            System.out.println("================= Departments =================");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                System.out.println("[id = " + id + ", name = " + name + "]");
            }
        }
    }

    private static void selectAllPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
        select *
        from person
        """);
            System.out.println("================= Person ======================");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long dep = resultSet.getLong("department_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                boolean act = resultSet.getBoolean("active");

                System.out.println("Найдена строка: [id = " + id + ", department_id = " + dep + ", name = " + name + ", age = " + age + ", active = " + act + "]");
            }
        }
    }

    /**
     * Пункт 4
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        // FIXME: Ваш код тут

        try (PreparedStatement statement =
                     connection.prepareStatement("select name from department where id = (select department_id from person where id = ?)")) {
            statement.setLong(1, personId);
            ResultSet resultSet = statement.executeQuery();

            String name = "";
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
            return name;
        }

        // throw new UnsupportedOperationException();


    }

    /**
     * Пункт 5
     */
    private static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        Map<String, String> departments = new HashMap<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
        select department.name, person.name
        from department, person
        where department.id = person.department_id
        order by department.id
        """);
            // Добавляем все департаменты в Map
            while (resultSet.next()) {
                String perName = resultSet.getString("person.name");
                String depName = resultSet.getString("department.name");
                departments.put(perName, depName);
                // System.out.println(depName + " -> " + perName);
            }


        }
        // throw new UnsupportedOperationException();
        return departments;
    }

    /**
     * Пункт 6
     */
    private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        throw new UnsupportedOperationException();
    }

}

