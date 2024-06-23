package org.example.less_2.anno;

import lombok.Data;
import org.example.less_2.anno.lib.ObjectCreator;
import org.example.less_2.anno.lib.Random;

public class AnnotationsMain {

    public static void main(String[] args) {
        Person rndPerson = ObjectCreator.createObj(Person.class);
        System.out.println("age1 = " + rndPerson.age1);
        System.out.println("age2 = " + rndPerson.age2);

        // extPerson.isAssignableFrom(ExtPerson.class) // true
        // extPerson.isAssignableFrom(Person.class) // false
        // person.isAssignableFrom(ExtPerson.class) // true

        Person p = new Person();
        Person ep = new ExtPerson();

        System.out.println(p.getClass().isAssignableFrom(Person.class)); // true
        System.out.println(p.getClass().isAssignableFrom(ExtPerson.class)); // true

        System.out.println(ep.getClass().isAssignableFrom(Person.class)); // false
        System.out.println(ep.getClass().isAssignableFrom(ExtPerson.class)); // true

    }

    public static class ExtPerson extends Person {

    }

    public static class Person {

        @Random // рандомное число в диапазоне [0, 100)
        private int age1;

        @Random(min = 50, max = 51) // рандомное число в диапазоне [50, 51) => 50
        private int age2;

        @Random
        private String age3;

    }

}

