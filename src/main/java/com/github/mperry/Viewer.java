package com.github.mperry;

import fj.P;
import fj.P2;
import fj.data.Java8;
import fj.data.List;
import fj.data.Option;
import fj.data.Stream;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Viewer {

    static Logger log = LogManager.getLogger(Viewer.class);

    static String classExt = ".class";
    static String jarExt = ".jar";
    static String warExt = ".war";

    boolean isClassFile(String s) {
        return s.endsWith(classExt);
    }

    boolean isJar(String s) {
        return s.endsWith(jarExt) || s.endsWith(warExt);
    }

    boolean isApplicable(File f) {
        String s = f.getAbsolutePath();
        return isJar(s) || isClassFile(s);
    }

    boolean isApplicable(JarEntry je) {
        return !je.isDirectory() && isClassFile(je.getName());
    }

    private Option<P2<Integer, Option<Version>>> processEntry(JarEntry je, ClassPool cp) {
//        log.info("processing " + je.getName());
        String s = getClassName(je.getName());
        try {
            if (!isApplicable(je)){
//                log.info("skipping " + je.getName());
                return Option.none();
            } else {
                CtClass c = cp.get(s);
                int major = c.getClassFile().getMajorVersion();
                String s2 = Version.lookup(major).map(v -> Integer.toString(v.getJavaVersion())).orSome("Version unsupported");

//                log.info("java: {}, majorVersion: {}, class: {}", s2, major, s);
                return Option.some(P.p(major, Version.lookup(major)));
            }
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            return Option.none();
        }
    }

    void processJar(String pathToJar) throws NotFoundException, IOException, ClassNotFoundException {
        processJar(new File(pathToJar));
    }

    Option<P2<Integer, Option<Version>>> processJar(File pathToJar) throws IOException, ClassNotFoundException, NotFoundException {
        JarFile jarFile = new JarFile(pathToJar);
        ClassPool cp = ClassPool.getDefault();
        ClassPath cp2 = cp.insertClassPath(pathToJar.getAbsolutePath());
//        jarFile.stream().forEach(je -> processEntry(je, cp));

        Stream<JarEntry> list = Java8.JavaStream_Stream(jarFile.stream());
        Stream<Option<P2<Integer, Option<Version>>>> list2 = list.map(je -> processEntry(je, cp));
//        log.info(list2.toStringEager());
        Option<P2<Integer, Option<Version>>> o2 = Option.join(list2.find(o -> o.isSome()));
        return o2;

    }

    String getClassName(String s) {
        String t = isClassFile(s) ? s.substring(0, s.length() - classExt.length()) : s;
        return t.replace('/', '.');
    }

    void walkDir(File f) throws IOException {

        Files.walk(f.toPath()).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {
//                log.info("processing: abs: " + filePath.toFile().getAbsolutePath() + " s: " + filePath.toString());
//                System.out.println(filePath);
                if (isJar(filePath.toFile().getAbsolutePath())) {
                    try {
//                        log.info("file: {}", filePath);
                        Option<P2<Integer, Option<Version>>> o = processJar(filePath.toFile());
                        log.info("version: {}, file: {}", o, filePath);

                    } catch (IOException | ClassNotFoundException | NotFoundException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        });

    }

}
