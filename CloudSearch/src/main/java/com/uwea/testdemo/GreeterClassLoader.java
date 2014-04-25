package com.uwea.testdemo;

import java.io.*;

public class GreeterClassLoader extends ClassLoader {

    // basePath gives the path to which this class
    // loader appends "/.class" to get the
    // full path name of the class file to load
    private String basePath;

    public GreeterClassLoader(String basePath) {

        this.basePath = basePath;
    }

    public GreeterClassLoader(ClassLoader parent, String basePath) {

        super(parent);
        this.basePath = basePath;
    }

    protected Class findClass(String className)
        throws ClassNotFoundException {

        byte classData[];

        // Try to load it from the basePath directory.
        classData = getTypeFromBasePath(className);
        if (classData == null) {
            throw new ClassNotFoundException();
        }

        // Parse it
        return defineClass(className, classData, 0,
            classData.length);
    }

    private byte[] getTypeFromBasePath(String typeName) {

        FileInputStream fis;
        String fileName = basePath + File.separatorChar
            + typeName.replace('.', File.separatorChar)
            + ".class";

        try {
            fis = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(fis);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            int c = bis.read();
            while (c != -1) {
                out.write(c);
                c = bis.read();
            }
        }
        catch (IOException e) {
            return null;
        }

        return out.toByteArray();
    }
}