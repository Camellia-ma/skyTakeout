package com.myb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface DishMapper {
    /* 根据分类 id 计算关联菜品数量 */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer getCountByCategoryId(Long categoryId);
}
