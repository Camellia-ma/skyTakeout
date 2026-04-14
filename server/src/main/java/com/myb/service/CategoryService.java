package com.myb.service;

import com.myb.dto.CategoryDTO;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.result.PageResult;

public interface CategoryService {
    /* 分页查询分类 */
    PageResult pageSelect(CategoryPageQueryDTO pageSelectDTO);

    /* 添加新分类 */
    void addNewCategory(CategoryDTO categoryDTO);

    /* 根据 id删除分类 */
    void delete(Long id);

    /* 修改分类状态 */
    void changeCategoryStatus(Integer status, Long id);
}
