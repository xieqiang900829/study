package com.cglib.lazyloader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private String courseName;

    private Date courseTime;
}

