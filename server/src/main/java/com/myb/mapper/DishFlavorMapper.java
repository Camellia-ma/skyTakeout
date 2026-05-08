package com.myb.mapper;

import com.myb.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /* 批量插入菜品口味数据 */
    void batchInsert(List<DishFlavor> flavors);

    /* 根据菜品 id 删除 */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    /* 根据 id查询口味 */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getDishById(Long id);
}
