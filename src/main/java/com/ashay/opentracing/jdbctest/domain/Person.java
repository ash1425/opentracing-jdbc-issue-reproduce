package com.ashay.opentracing.jdbctest.domain;

import lombok.Data;

@Data
public class Person {
    int id;
    String name;
    String city;
    int age;
}
