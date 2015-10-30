package com.github.mperry;

import fj.data.Java8;
import fj.data.List;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Viewer {

    static Logger log = LogManager.getLogger(Viewer.class);

    boolean isClassFile(String s) {
        return s.endsWith(".class");
    }

    boolean isApplicable(JarEntry je) {
        return !je.isDirectory() && isClassFile(je.getName());
    }

    private void processEntry(JarEntry je, ClassPool cp) {
//        log.info("processing " + je.getName());
        String s = getClassName(je.getName());
        try {
            if (!isApplicable(je)){
//                log.info("skipping " + je.getName());
            } else {
                CtClass c = cp.get(s);
                int major = c.getClassFile().getMajorVersion();
                log.info("majorVersion: {}, class: {}", major, s);
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    void processJar(String pathToJar) throws IOException, ClassNotFoundException, NotFoundException {
        JarFile jarFile = new JarFile(pathToJar);
        ClassPool cp = ClassPool.getDefault();
        ClassPath cp2 = cp.insertClassPath(new File(pathToJar).getAbsolutePath());
//        jarFile.stream().forEach(je -> processEntry(je, cp));
        List<JarEntry> list = Java8.JavaStream_List(jarFile.stream());
        list.forEach(je -> processEntry(je, cp));
    }

    String getClassName(String s) {
        String t = s;
        String ext = ".class";
        if (isClassFile(s)) {
            t = s.substring(0, s.length() - ext.length());
        }
        return t.replace('/', '.');
    }

}
