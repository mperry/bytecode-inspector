package com.github.mperry;

import fj.Unit;
import javassist.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import static fj.Unit.unit;

/**
 * Created by MarkPerry on 10/30/2015.
 */
public class Viewer {


    private Unit processEntry(JarFile f, JarEntry je) throws IOException {
        InputStream fis = f.getInputStream(je);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        processBytes(bytes);
        return unit();
    }

    private void processBytes(byte[] bytes) {
    }



}
