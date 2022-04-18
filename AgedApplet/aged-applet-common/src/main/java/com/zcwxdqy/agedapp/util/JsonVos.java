package com.zcwxdqy.agedapp.util;


import com.zcwxdqy.agedapp.exception.CommonException;
import com.zcwxdqy.agedapp.pojo.vo.result.*;

public class JsonVos {

    // 消息 + 错误码【400】
    public static JsonVo error(String msg) {
        return new JsonVo(false, msg);
    }

    // 枚举中：【消息 + 错误码】
    public static JsonVo error(CodeMsg codeMsg) {
        return new JsonVo(codeMsg);
    }

    // 消息 + 错误码
    public static JsonVo error(int code, String msg) {
        return new JsonVo(code, msg);
    }

    // 错误码【400】
    public static JsonVo error() {
        return new JsonVo(false);
    }

    // 成功码【0】
    public static JsonVo ok() {
        return new JsonVo();
    }

    // 枚举中：【消息 + 成功码】
    public static JsonVo ok(CodeMsg codeMsg) {
        return new JsonVo(codeMsg);
    }

    // 消息 + 成功码【0】
    public static JsonVo ok(String msg) {
        return new JsonVo(true, msg);
    }

    // 数据 + 消息 + 成功码【0】
    public static <T> DataJsonVo<T> ok(T data) {
        return new DataJsonVo<>(data);
    }

    // 数据 + 消息 + 成功码【0】
    public static <T> DataJsonVo<T> ok(T data, String msg) {
        return new DataJsonVo<>(msg, data);
    }

    // 详细的分页：【数据 + 总数  + 成功码：0】
    public static <T> PageJsonVo<T> ok(PageVo<T> pageVo) {
        PageJsonVo<T> pageJsonVo = new PageJsonVo<>();
        pageJsonVo.setCount(pageVo.getCount());
        pageJsonVo.setData(pageVo.getData());
        return pageJsonVo;
    }

    // 抛异常【异常信息】
    public static <T> T raise(String msg) throws CommonException {
        throw new CommonException(msg);
    }

    // 抛异常【枚举中：码 + 信息】
    public static <T> T raise(CodeMsg codeMsg) throws CommonException {
        throw new CommonException(codeMsg);
    }


}
