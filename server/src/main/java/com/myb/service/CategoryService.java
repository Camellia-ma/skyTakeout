package com.myb.service;

import com.myb.dto.CategoryPageQueryDTO;
import com.myb.result.PageResult;

public interface CategoryService {
    /* 分页查询分类 */
    PageResult pageSelect(CategoryPageQueryDTO pageSelectDTO);


}
