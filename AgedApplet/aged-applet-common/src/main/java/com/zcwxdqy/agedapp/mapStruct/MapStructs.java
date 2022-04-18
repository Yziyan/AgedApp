package com.zcwxdqy.agedapp.mapStruct;

import com.zcwxdqy.agedapp.pojo.po.Skill;
import com.zcwxdqy.agedapp.pojo.po.User;
import com.zcwxdqy.agedapp.pojo.vo.request.LoginReqVo;
import com.zcwxdqy.agedapp.pojo.vo.request.save.SkillReqVo;
import com.zcwxdqy.agedapp.pojo.vo.request.save.UserReqVo;
import com.zcwxdqy.agedapp.pojo.vo.response.SkillVo;
import com.zcwxdqy.agedapp.pojo.vo.response.UserVo;
import com.zcwxdqy.agedapp.pojo.vo.result.LoginVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

 /*
  1、 ReqVo -> Po
  2、 Po -> Vo
  3、 uses = 使用的转换器
 */
@Mapper(uses = {
        MapStructFormatter.class
})
public interface MapStructs {

    // 生成实例对象。可以调用下面的方法
    MapStructs INSTANCE = Mappers.getMapper(MapStructs.class);

    /*
     1、Po -> vo     【用来将从数据库查到的数据过滤成 vo返回给前端】
     2、可以解决转换类型不匹配、参数名不匹配的问题。
     （1）source：源对象
     （2）target：目标对象
     （3）qualifiedBy：找转换器中的方法
     */
    @Mapping(source = "createdTime",
            target = "createdTime",
            qualifiedBy = MapStructFormatter.Date2Millis.class)
    SkillVo po2vo(Skill po);

     @Mapping(source = "createdTime",
             target = "createdTime",
             qualifiedBy = MapStructFormatter.Date2Millis.class)
     @Mapping(source = "loginTime",
             target = "loginTime",
             qualifiedBy = MapStructFormatter.Date2Millis.class)
     UserVo po2vo(User po);

     LoginVo po2loginVo(User po);


    // reqVo -> po  【用来做数据库保存】
    Skill reqVo2po(SkillReqVo reqVo);
    User reqVo2po(UserReqVo reqVo);

}
