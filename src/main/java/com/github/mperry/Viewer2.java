package com.github.mperry;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Viewer2 {

    static Logger log = LogManager.getLogger(Viewer2.class);

    void m1(String pathToJar) throws IOException, ClassNotFoundException {
        m1(new File(pathToJar));
    }

    void m1(File pathToJar) throws IOException, ClassNotFoundException {

        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();
//        jarFile.stream().forEach(je -> processEntry(je));

        URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);

        }
    }

    private void processEntry(JarEntry je, ClassPool cp) {
        log.info("processing " + je.getName());
        String s = getClassName(je.getName());

        try {
            if (je.isDirectory() || !je.getName().endsWith(".class")){

                log.info("skipping " + je.getName());
            } else {
                CtClass c = null;
                c = cp.get(s);
                int major = c.getClassFile().getMajorVersion();
                log.info("class: " + s + " verson: " + major);
            }

        } catch (NotFoundException e) {
            System.out.println(e);
        }

    }

    void m2(String pathToJar) throws IOException, ClassNotFoundException, NotFoundException {
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();
        ClassPool cp = ClassPool.getDefault();
        ClassPath cp2 = cp.insertClassPath(new File(pathToJar).getAbsolutePath());
//        URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            log.info("processing " + je.getName());
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                log.info("skipping " + je.getName());
                continue;
            }
            String className = getClassName(je.getName());
//            className = className.replace('/', '.');
            CtClass ct = cp.get(className);
            int major = ct.getClassFile().getMajorVersion();

            System.out.println("class: " + className + " verson: " + major);

        }
    }


    void m3(String pathToJar) throws IOException, ClassNotFoundException, NotFoundException {

        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();

        ClassPool cp = ClassPool.getDefault();
//        cp.insertClassPath(new ClassClassPath(this.getClass()));
        ClassPath cp2 = cp.insertClassPath(new File(pathToJar).getAbsolutePath());

        jarFile.stream().forEach(je -> processEntry(je, cp));

    }

    String getClassName(String s) {
        String t = s;
        if (s.endsWith(".class")) {
            t = s.substring(0, s.length() - 6);
        }

        return t.replace('/', '.');

    }

}
