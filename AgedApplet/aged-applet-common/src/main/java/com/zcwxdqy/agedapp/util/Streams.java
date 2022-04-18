package com.zcwxdqy.agedapp.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;



public class Streams {

    /*
     1、函数式接口 Function<T, R>
     2、将T转成R
     */
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> function) {
        /*
        函数式编程：
        1、用stream拿到map对象
        2、用map遍历这个集合
        3、遍历一次就会调用一次lambda表达式
        4、lambda表达式可以变成方法调用
        5、collect(Collectors.toList())遍历后的集合返回一个集合
         */
        return list.stream().map(function).collect(Collectors.toList());
    }
}
