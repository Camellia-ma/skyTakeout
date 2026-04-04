package com.myb.controller.admin;

import com.myb.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类管理 -- 管理分类的增删改查
 **/


@RestController
@Slf4j
@RequestMapping("/admin/category")
@Tag(name = "[admin]分类管理",description = "分类管理相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

}
