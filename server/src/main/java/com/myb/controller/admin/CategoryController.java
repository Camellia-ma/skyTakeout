package com.myb.controller.admin;

import com.myb.context.BaseContext;
import com.myb.dto.CategoryDTO;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.result.PageResult;
import com.myb.result.Result;
import com.myb.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /* 分类分页查询 */
    @GetMapping("/page")
    @Operation(summary = "[分类管理]-->分页查询",description = "分类的分页查询")
    public Result<PageResult> pageSelect(CategoryPageQueryDTO pageSelectDTO) {
        log.info("分类分页查询 <--> page ==> {} | pageSize ==> {}",pageSelectDTO.getPage(),pageSelectDTO.getPageSize());
        PageResult result = categoryService.pageSelect(pageSelectDTO);
        return Result.success(result);
    }

    /* 新增分类 */
    @PostMapping
    @Operation(summary = "[分类管理]-->新增分类",description = "添加新的分类")
    public Result addNewCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类 <--> categoryDTO => {}",categoryDTO);
        categoryService.addNewCategory(categoryDTO);
        return Result.success();
    }

    /* 根据 id 删除分类 */
    @DeleteMapping
    @Operation(summary = "[分类管理]-->删除分类",description = "根据 id 删除分类")
    public Result deleteCategory(Long id){
        log.info("删除分类 <--> id ==> {} | 操作id ==> {}",id, BaseContext.getCurrentId());
        categoryService.delete(id);
        return Result.success();
    }

}
