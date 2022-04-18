package com.zcwxdqy.agedapp.enhance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 这是用 LambdaQueryWrapper     eg：wrapper.like(skill::getName, keyword).like(skill::getIntro, keyword)
 * 加强 mybatis plus的模糊查询。
 * 泛型为【查询结果的类】
 * 返回this是为了支持链式编程
 * @param <T>
 */

public class MpLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {
    @SafeVarargs
    public final MpLambdaQueryWrapper<T> like(Object val, SFunction<T, ?>... funcs) {
        if (val == null) return this;
        String str = val.toString();
        if (str.length() == 0) return this;
        //  遍历将每一个条件加上。
        return (MpLambdaQueryWrapper<T>) nested((w) -> {
            for (SFunction<T, ?> func : funcs) {
                w.like(func, str).or();
            }
        });
    }
}
