package com.myb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.entity.Category;
import com.myb.mapper.CategoryMapper;
import com.myb.result.PageResult;
import com.myb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult pageSelect(CategoryPageQueryDTO pageSelectDTO){
        // 设置分页查询起始页以及查询页数
        PageHelper.startPage(pageSelectDTO.getPage(),pageSelectDTO.getPageSize());
        // 调用 mapper 接口查询获得结果
        Page<Category> result = categoryMapper.pageSelect(pageSelectDTO);
        // 封装并返回查询结果
        return new PageResult(result.getTotal(),result.getResult());
    }
}
