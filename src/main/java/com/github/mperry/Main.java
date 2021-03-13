package com.github.mperry;

import fj.P2;
import fj.data.Option;
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

    public static String bytecode = "c:/repos/bytecode-inspector/build/libs/bytecode-inspector.jar";
    public static String fj = "c:/repos/functionaljava/core/build";
    public static String CURRENT_PATH = ".";
    public static String DEFAULT_PATH = CURRENT_PATH;


    public static void main(String args[]) throws NotFoundException, IOException, ClassNotFoundException {
        Viewer v = new Viewer();
//        v.processJar(name);
        File f = new File(args.length > 0 ? args[0] : DEFAULT_PATH);
        if (f.isDirectory()) {
            v.walkDir(f);
        } else {
            Option<P2<Integer, Option<Version>>> o = v.processJar(f);
            log.info("version: {}, file: {}", o, f.toPath());
        }

    }




}
