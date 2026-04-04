package com.myb.mapper;

import com.github.pagehelper.Page;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /* 分页查询分类 */
    Page<Category> pageSelect(CategoryPageQueryDTO categoryPageQueryDTO);
}
