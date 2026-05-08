package com.myb.mapper;

import com.github.pagehelper.Page;
import com.myb.annotation.AutoFill;
import com.myb.dto.DishPageQueryDTO;
import com.myb.entity.Dish;
import com.myb.enumeration.OperationType;
import com.myb.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /* 根据分类 id 计算关联菜品数量 */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer getCountByCategoryId(Long categoryId);

    /* 向菜品表中插入数据 */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /* 分页查询菜品 */
    Page<DishVO> pageSelect(DishPageQueryDTO pageQueryDTO);

    /* 根据 id 查询 */
    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);

    /* 根据 id 删除菜品 */
    @Delete("delete  from dish where id = #{id}")
    void deleteById(Long id);

    /* 更新菜品信息 */
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(Dish dish);
}
