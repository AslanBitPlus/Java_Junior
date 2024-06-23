package org.example.less_2.hw;

import java.lang.reflect.Field;
import java.util.Date;

public class RndAntProcessor {

    public static void processAnnotation(Object obj) {
        // найти все поля класса, над которыми стоит аннотация @Rnd
        // вставить туда рандомное число в диапазоне [0, 100)
        //
        // найти все поля класса, над которыми стоит аннотация @RndDate
        // вставить туда рандомное число в диапазоне [01.01.2024, 01.01.2025)

        java.util.Random random = new java.util.Random();
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            // obj.getClass().isAssignableFrom(AnnotationsMain.Person.class)

            if (field.isAnnotationPresent(Rnd.class) && field.getType().isAssignableFrom(int.class)) {
                Rnd annotation = field.getAnnotation(Rnd.class);
                int min = annotation.min();
                int max = annotation.max();

                try {
                    field.setAccessible(true); // чтобы можно было изменять финальные поля
                    field.set(obj, random.nextInt(min, max));
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }

            if (field.isAnnotationPresent(RndDate.class) && field.getType().isAssignableFrom(Date.class)) {
                RndDate annotation = field.getAnnotation(RndDate.class);
                long min = annotation.min();
                long max = annotation.max();

                try {
                    field.setAccessible(true); // чтобы можно было изменять финальные поля
                    field.set(obj, new Date(random.nextLong(min, max)));
                    // field.set(obj, random.nextLong(min, max));
                    System.out.println("=======================");
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }

        }

    }

}


