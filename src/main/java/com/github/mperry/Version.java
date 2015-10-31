package com.github.mperry;

import fj.data.List;
import fj.data.Option;
import javassist.bytecode.ClassFile;

import static com.github.mperry.Constants.BASE_JAVA;

/**
 * Created by MarkPerry on 10/31/2015.
 */
public enum Version {
    JAVA_4(ClassFile.JAVA_4, 4),
    JAVA_5(ClassFile.JAVA_5, 5),
    JAVA_6(ClassFile.JAVA_6, 6),
    JAVA_7(ClassFile.JAVA_7, 7),
    JAVA_8(ClassFile.JAVA_8, 8),
    JAVA_9(BASE_JAVA + 9, 9);

    private int majorVersion;
    private int javaVersion;

    private Version(int major, int java) {
        majorVersion = major;
        javaVersion = java;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getJavaVersion() {
        return javaVersion;
    }

    public static Option<Version> lookup(int major) {
        return List.list(Version.values()).find(v -> v.getMajorVersion() == major);
    }

}
