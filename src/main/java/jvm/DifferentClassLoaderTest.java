package jvm;

import java.io.InputStream;

/**
 * Created by WD42700 on 2019/4/15.
 */
public class DifferentClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream stream = getClass().getResourceAsStream(fileName);
                if (stream == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[stream.available()];
                    // 将流写入字节数组b中
                    stream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.loadClass(name);
            }
        };
        Object obj = classLoader.loadClass("Tjvm.DifferentClassLoaderest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof DifferentClassLoaderTest);

      //  DifferentClassLoaderTest obj2 = (DifferentClassLoaderTest) classLoader.loadClass("jvm.DifferentClassLoaderTest").newInstance();


    }

}
