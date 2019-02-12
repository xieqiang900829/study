package java.lang;

/**
 * Created by WD42700 on 2018/12/14.
 */
public class String {

    /**
     *
     * 类加载机制的双亲委派模型
     * 之间的层次关系被称为类加载器的双亲委派模型。该模型要求除了顶层的启动类加载器外，其余的类加载器都应该有自己的父类加载器，
     * 而这种父子关系一般通过组合（Composition）关系来实现，而不是通过继承
     *
     *
     * 由于双亲委派机制。
     * 启动类加载器BootStrapClassLoader  -> 扩展类加载器  ExtClassLoader  ->应用类加载器  ApplicationClassLoader  -> 自定义类加载器
     *
     * 类加载器在加载类的时候它会将加载任务先交给父类加载器。一直往上推、直到启动类加载器、未加载成功才有子类加载器处理
     *
     * 这样当我们自己定义了一个  String 时候  、也不会粗、出问题。用的是jdk自带的
     * 编译不会报错、但是永远不会被加载和运行
     *
     */
    public String(){

        System.out.println("我是本地的String");

    }

    public  void  sayHello(){
        System.out.println("hello  world");
    }


    public static void main(String[] args) {

    }

}
