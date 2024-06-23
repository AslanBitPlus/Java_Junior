package org.example.less_1.hw;

import org.example.less_1.StreamDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Homework {

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static void main(String[] args) {
        List<String> departments = List.of("Отдел продаж"
                , "Бухгалтерия"
                , "Отдел кадров"
                , "Склад"
                , "Отдел IT"
                , "Бездельники");

        // List<Person> persons = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person("Person # " + i);
            // person.setName("Person # " + i);
            person.setDepart(getRandom(departments));
            person.setAge(ThreadLocalRandom.current().nextInt(18, 50));
            person.setSalary(ThreadLocalRandom.current().nextInt(15_000, 50_000) * 1.0);

            persons.add(person);
            person.toString();

        }
        System.out.println("Самый молодой сотрудник - " + findMostYoungestPerson(persons));
        System.out.println("Отдел с самой низкой зарплатой - " + findMostExpensiveDepartment(persons));
        System.out.println("Группировка по Отделам");
        System.out.println(groupByDepartment(persons));
        System.out.println("Самый старый сотрудник");
        System.out.println(getDepartmentOldestPerson(persons));
        System.out.println("Самая большая зарплата");
        System.out.println(findMostExpeensiveDepartment(persons));
        System.out.println("Минимальные зарплаты по отделам");
        System.out.println(getDepartmentOldPerson(persons));

    }


    static class Person {
        private String name;
        private int age;
        private double salary;
        private String depart;

        public Person(String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", depart='" + depart + '\'' +
                    '}';
        }
    }

    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        // FIXME: ваша реализация здесь
         Optional<Homework.Person> person = people.stream()
                .min((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        return person;
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Person findMostExpeensiveDepartment(List<Person> people) {
        // FIXME: ваша реализация здесь
        Optional<Person> first = people.stream()
                .max((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()));

        Person person = first.get();
        return person;
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static String findMostExpensiveDepartment(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream()
                .min((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary())).get().getDepart();
    }




    /**
     * Сгруппировать сотрудников по департаментам
     */

    static Map<String, List<Person>> groupByDepartment(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream()
                .collect(Collectors.groupingBy(Homework.Person::getDepart));

    }


    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        // FIXME: ваша реализация здесь
        return null;
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream()
                .collect(Collectors.toMap(
                        Person::getDepart, // b -> people.getAge()
                        Function.identity(), // b -> b
                        (a, b) -> {
                            if (a.getAge() > b.getAge()) {
                                return a;
                            }
                            return b;
                        }
                ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static Map<String, Person> getDepartmentOldPerson(List<Person> people) {
        // FIXME: ваша реализация здесь
        return people.stream()
                .collect(Collectors.toMap(
                        Person::getDepart, // b -> people.getSalary()
                        Function.identity(), // b -> b
                        (a, b) -> {
                            if (a.getSalary() < b.getSalary()) {
                                return a;
                            }
                            return b;
                        }
                ));

    }


}

