package com.classloader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WD42700 on 2019/1/12.
 *
 * ServiceLoader的实现原理,jdk已经自带了  建立这个类只是为了方便理解
 *  最好还是使用jdk自带的
 *
 */
public class SimpleServiceLoader {

    private static final String PREFIX = "/META-INF/services/";

    public static <T> List<T> load(Class<T> cls) throws IOException {
        List<String> implClasses = readServiceFile(cls);
        List<T> implList = new ArrayList<T>();
        for (String implClass : implClasses) {
            Class<T> c = null;
            try {
                c = (Class<T>) Class.forName(implClass);
                implList.add(c.newInstance());
            } catch (Exception e) {
                return new ArrayList<T>();
            }
        }
        return implList;
    }

    private static List<String> readServiceFile(Class<?> cls) throws IOException {
        String infName = cls.getCanonicalName();//获取类的全路劲、和getName 差不多，不过内部类时候会有点区别、最好用这个方法
        String fileName = cls.getResource(PREFIX+infName).getPath();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line = "";
            List<String> implClasses = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                implClasses.add(line);
            }
            return implClasses;
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fileName);
            return new ArrayList<String>();
        } catch (IOException ioe) {
            System.out.println("Read file failed: " + fileName);
            return new ArrayList<String>();
        }
    }

    public static void main(String[] args) throws IOException {
        List<Compute> implList = load(Compute.class);
        if (implList != null && implList.size() >0) {
            for (Compute matcher: implList) {
                System.out.println(matcher.add(11));
            }
        }
    }

}
