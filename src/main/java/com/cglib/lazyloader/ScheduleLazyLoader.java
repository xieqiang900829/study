package com.cglib.lazyloader;

import net.sf.cglib.proxy.LazyLoader;

import java.util.Calendar;

public class ScheduleLazyLoader implements LazyLoader {

    public Object loadObject() throws Exception {
        System.out.println("before LazyLoader init...you can query from db...");
        Schedule schedule = new Schedule();
        schedule.setCourseName("English");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,3,28);
        schedule.setCourseTime(calendar.getTime());
        System.out.println("after LazyLoader init...");
        return schedule;
    }
}
