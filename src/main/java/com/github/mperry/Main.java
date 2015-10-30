package com.github.mperry;

import javassist.NotFoundException;

import java.io.IOException;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Main {


    public static String name = "D:/repositories/bytecode-inspector/build/libs/bytecode-inspector.jar";


    public static void main(String args[]) throws NotFoundException, IOException, ClassNotFoundException {
        System.out.println("hi world");
        new Viewer2().m2(name);
    }




}
