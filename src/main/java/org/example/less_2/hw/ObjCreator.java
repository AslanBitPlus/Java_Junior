package org.example.less_2.hw;

import java.lang.reflect.Constructor;

public class ObjCreator {

    public static <T> T createObj(Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();

            T obj = constructor.newInstance();
            RndAntProcessor.processAnnotation(obj);
            System.out.println(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ниче не получилось: " + e.getMessage());
            return null; // throw new IllegalStateException
        }
    }

}


