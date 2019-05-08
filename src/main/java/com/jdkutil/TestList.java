package com.jdkutil;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by WD42700 on 2019/5/6.
 */
public class TestList {

    /***
     *
     * https://blog.csdn.net/zcyzsy/article/details/82790381
     *
     * list add、remove 、clear的时候 modcount++。记录修改的次数
     *
     *
     *
     * foreach的实现原理就是迭代器Iterator(使用下标遍历则不会用到迭代器、也不会抛那个异常)
     * AbstractList 里面有个字段修改次数：modcount
     * 内部类Iterator   有个预期修改次数：expectmodcount
     * 执行迭代器中方法的时候（Iterator的两个方法next()、remove()都会校验）、如果发现modcount和expectmodcount 不一致。会抛异常
     * 每次用到迭代器的时候会初始化 expectmodcount = modcount
     *
     *
     *
     * foreach循环本质也是迭代器遍历。foreach在jvm中还是会解析成Iterator来执行
     *
     * 数组元素每次修改的时候都是数组拷贝方法 。System.arraycopy。这个方法只是将后面的元素整体往前移动了，最后一个 位置 =null。这样 可以让GC回收
     *
     * 类似CAS机制
     *
     * 为什么List使用下标不回报错、直接remove(element) 则报错？？ 通过下标遍历 不用用到迭代器，所以不回抛那个异常
     * 因为使用下标的时候使用不回走迭代器不会那个判断
     *
     * ArrayList.Iterator 内部类
     * 只有在使用迭代器遍历的时候才会跑这个异常 。ConcurrentModificationException
     *
     * Lists.newArrayList() 为什么会调用 itr.remove() 。？？？？暂时不用纠结这个
     *
     *list或者hashmap遍历 都有这个问题 ？？？
     *
     */


    /**
     * foreach本质也是迭代器  list.remove() 迭代器遍历 抛异常
     */
    @Test
    public void testDel(){
        ArrayList<Integer> list  = Lists.newArrayList();
       for(int i=1;i<=1;i++){
            list.add(i);
        }
        for(Integer ele : list){
            if(ele == 1)
                list.remove(0);
        }
        System.out.println(list.toString());
    }


    /**
     * list.remove() 迭代器遍历 抛异常
     */
    @Test
    public  void  testDel2(){
        ArrayList<Integer>  list  = Lists.newArrayList();
        for(int i=1;i<=5;i++){
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==3)
                list.remove(integer);
        }
        System.out.println(list.toString());
    }


    /**
     * 通过list.remove()  下标遍历 不会用到迭代器，所以不回抛那个异常
     * 这个不会报错、但是和预期不一致。因为在下标i一直在自增，但是在遍历到下标4、5的时候，其实已经没有对应的元素了
     * 使用下标从大到小递减遍历 、则没有这个问题
     *
     */
    @Test
    public void testDel3() {
        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(2);
        /*list.add(2);
        list.add(2);
        list.add(2);*/

        list.remove(0);

        //list.remove(new Integer(2));
       for(int i=0;i<list.size();i++){
            if(list.get(i)==2){
                list.remove(i);
                break;
            }
        }

        System.out.println(list.toString());
    }


    /**
     * 使用迭代器的remove、而不是list的remove  正常返回
     * itr.remove() 迭代器遍历 抛异常
     *
     */
    @Test
    public void testDel4() {
        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer integer = iterator.next();
            if (integer == 2)
                iterator.remove();//java.util.ArrayList.Itr.remove()  871行做了这个操作 expectedModCount = modCount;  所以不会报错
        }
        System.out.println(list.toString());
    }


    /**
     * 使用下标从大到小递减遍历 、正确
     */
    @Test
    public void testDel5() {
        ArrayList<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(2);
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) == 2) {
                list.remove(i);
            }
        }
        System.out.println(list.toString());

    }

    @Test
    public void test(){
        int a = 10;
        int b =(a << 2);
        int c =(a >> 2);
        System.out.println(b+"      "+c);
    }

}
