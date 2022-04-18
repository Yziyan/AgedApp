package com.zcwxdqy.agedapp.enhance;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zcwxdqy.agedapp.pojo.vo.request.page.base.PageReqVo;
import com.zcwxdqy.agedapp.pojo.vo.result.PageVo;
import com.zcwxdqy.agedapp.util.Streams;

import java.util.List;
import java.util.function.Function;


/**
 * 加强 Mybatis plus分页功能
 * 泛型为返回查询参数的类 eg:Mypage<>()
 * @param <T>
 */

public class MpPage<T> extends Page<T> {
    private final PageReqVo reqVo;

    public MpPage(PageReqVo reqVo) {
        super(reqVo.getPage(), reqVo.getSize());
        this.reqVo = reqVo;
    }

    private <N> PageVo<N> commonBuildVo(List<N> data) {
        // 将返回的参数更新一下，因为客户传过来的可能有问题【以数据库差的为准】
        reqVo.setPage(getCurrent());
        reqVo.setSize(getSize());

        // 返回总页数、总数、数据
        PageVo<N> pageVo = new PageVo<>();
        pageVo.setCount(getTotal());
        pageVo.setPages(getPages());
        pageVo.setData(data);
        return pageVo;
    }

    // 直接返回分页查询后的数据
    public PageVo<T> buildVo() {
        return commonBuildVo(getRecords());
    }

    // 将查询的数据【po -> vo】转换后在返回
    public <R> PageVo<R> buildVo(Function<T, R> function) {
        return commonBuildVo(Streams.map(getRecords(), function));
    }
}
