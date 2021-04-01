package com.github.mperry;

import fj.data.List;
import fj.data.Option;
import javassist.bytecode.ClassFile;

import static com.github.mperry.Constants.BASE_JAVA;

/**
 * Created by MarkPerry on 10/31/2015.
 */
public enum Version {
    JAVA1(ClassFile.JAVA_1, 1),
    JAVA2(ClassFile.JAVA_2, 2),
    JAVA3(ClassFile.JAVA_3, 3),
    JAVA4(ClassFile.JAVA_4, 4),
    JAVA5(ClassFile.JAVA_5, 5),
    JAVA6(ClassFile.JAVA_6, 6),
    JAVA7(ClassFile.JAVA_7, 7),
    JAVA8(ClassFile.JAVA_8, 8),
    JAVA9(BASE_JAVA + 9, 9),
    JAVA10(BASE_JAVA + 10, 10),
    JAVA11(BASE_JAVA + 11, 11),
    JAVA12(BASE_JAVA + 12, 12),
    JAVA13(BASE_JAVA + 13, 13),
    JAVA14(BASE_JAVA + 14, 14),
    JAVA15(BASE_JAVA + 15, 15),
    JAVA16(BASE_JAVA + 16, 16),
    JAVA17(BASE_JAVA + 17, 17);

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
