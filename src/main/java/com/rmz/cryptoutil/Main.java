package com.rmz.cryptoutil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by SBT-Mehdiev-RR on 26.07.2017.
 */
public class Main {

    public Main() {
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.print("Incorrect Call! Not found incoming parameters!\r\n");
            System.exit(1);
        }
        // Вызов необходимого метода
        try {
            Class<?> clazz = Class.forName("ru.sbt.qa.tools.cryptoutil." + args[0]);
            Constructor<?> constructor = clazz.getConstructor();
            Object classObject = constructor.newInstance();
            Method method = clazz.getMethod(args[0], String.class);
            method.invoke(classObject, args[1]);
        } catch (ClassNotFoundException ce) {
            System.out.print("Class! " + args[0] + "not found!\n");
            System.exit(1);
        } catch (Exception ie) {
            System.out.print("Exception_in_method_ru.sbt.qa.tools.cryptoutil." + args[0] + "!\r\n");
            System.exit(1);
        }
    }
}
