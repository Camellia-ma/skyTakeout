package com.myb.mapper;

import com.github.pagehelper.Page;
import com.myb.annotation.AutoFill;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.entity.Category;
import com.myb.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /* 分页查询分类 */
    Page<Category> pageSelect(CategoryPageQueryDTO categoryPageQueryDTO);

    /* 新增分类 */
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /* 根据 id 删除分类 */
    void delete(Long id);

    /* 更新操作 */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);
}
