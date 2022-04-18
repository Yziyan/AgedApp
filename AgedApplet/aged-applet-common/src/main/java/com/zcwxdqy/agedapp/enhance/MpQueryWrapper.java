package com.zcwxdqy.agedapp.enhance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * 这是用 QueryWrapper     eg：wrapper.like("name", keyword).like("intro", keyword)
 * 加强 mybatis plus的模糊查询。
 * 泛型为【查询结果的类】
 * 返回this是为了支持链式编程
 * @param <T>
 */

public class MpQueryWrapper<T> extends QueryWrapper<T> {
    public final MpQueryWrapper<T> like(Object val, String... columns) {
        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;
        return (MpQueryWrapper<T>) nested((w) -> {
            for (String column : columns) {
                w.like(column, str).or();
            }
        });
    }
}
