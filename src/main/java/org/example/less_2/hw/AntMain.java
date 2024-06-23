package org.example.less_2.hw;
/**
 * В существующий класс ObjectCreator добавить поддержку аннотации RandomDate (по аналогии с Random):
 * 1. Аннотация должна обрабатываться только над полями типа java.util.Date
 * 2. Проверить, что min < max
 * 3. В поле, помеченной аннотацией, нужно вставлять рандомную дату,
 * UNIX-время которой находится в диапазоне [min, max)
 *
 * 4. *** Добавить поддержку для типов Instant, ...
 * 5. *** Добавить атрибут Zone и поддержку для типов LocalDate, LocalDateTime
 */

/**
 * Примечание:
 * Unix-время - количество милисекунд, прошедших с 1 января 1970 года по UTC-0.
 */

import org.example.less_2.anno.AnnotationsMain;
import org.example.less_2.hw.AntMain;
import org.example.less_2.hw.ObjCreator;
import org.example.less_2.hw.Rnd;

import java.util.Date;

public class AntMain {
    public static void main(String[] args) {
        // AntMain.Person rndPerson = ObjCreator.createObj(AntMain.Person.class);
        Person person = ObjCreator.createObj(Person.class);
        System.out.println("age1 = " + person.age1);
        System.out.println("age2 = " + person.age2);
        System.out.println("BearthDay = " + person.bearthDay);
        System.out.println(person.toString());

        Person person1 = ObjCreator.createObj(Person.class);
        System.out.println("age1 = " + person1.age1);
        System.out.println("age2 = " + person1.age2);
        System.out.println("BearthDay = " + person1.bearthDay);


        System.out.println(person1.toString());

    }

    public static class Person {

        @Rnd // рандомное число в диапазоне [0, 100)
        private int age1;

        @Rnd(min = 50, max = 51) // рандомное число в диапазоне [50, 51) => 50
        private int age2;

        @Rnd
        private String age3;

        @RndDate
        private Date bearthDay;

        public Person() {

        }


        @Override
        public String toString() {
            return "Person{" +
                    "age1=" + age1 +
                    ", age2=" + age2 +
                    ", age3='" + age3 + '\'' +
                    ", bearthDay=" + bearthDay +
                    '}';
        }
    }

}


