package com.github.mperry;

import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Main {

    static Logger log = LogManager.getLogger(Main.class);

    public static String name = "D:/repositories/bytecode-inspector/build/libs/bytecode-inspector.jar";
    public static String fj = "D:/repositories/functionaljava";


    public static void main(String args[]) throws NotFoundException, IOException, ClassNotFoundException {
        Viewer v = new Viewer();
//        v.processJar(name);
        v.walkDir(new File(fj));
    }




}
