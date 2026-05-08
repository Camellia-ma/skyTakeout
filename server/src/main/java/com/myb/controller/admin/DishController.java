package com.myb.controller.admin;


import com.myb.dto.DishDTO;
import com.myb.dto.DishPageQueryDTO;
import com.myb.result.PageResult;
import com.myb.result.Result;
import com.myb.service.DishService;
import com.myb.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理，负责接收和发送关于菜品的数据
 **/


@RestController
@RequestMapping("/admin/dish")
@Tag(name = "[admin]菜品管理",description = "管理菜品的增删改查")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;


    /* 新增菜品接口 */
    @PostMapping
    @Operation(summary = "[菜品管理]新增菜品",description = "新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品 <--> name ==> {}", dishDTO.getName());
        dishService.addDish(dishDTO);
        return Result.success();
    }

    /* 菜品分页查询 */
    @GetMapping("/page")
    @Operation(summary = "[菜品管理]分页查询",description = "菜品分页查询")
    public Result<PageResult> pageSelect(DishPageQueryDTO pageQueryDTO){
        log.info("菜品分页查询 <--> page ==> {} | pageSize ==> {}", pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        PageResult result = dishService.pageSelect(pageQueryDTO);
        return Result.success(result);
    }

    /* 批量删除菜品 */
    @DeleteMapping
    @Operation(summary = "[菜品管理]批量删除",description = "根据 id 批量删除菜品")
    public Result batchDeleteDish(@RequestParam List<Long> ids){
        log.info("批量删除菜品 <--> ids ==> {}", ids);
        dishService.batchDelete(ids);
        return Result.success();
    }

    /* 根据 id 查询菜品 -- 查询回显 */
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id){
        log.info("查询回显 <--> id ==> {}", id);
        DishVO dish = dishService.getDishWithFlavorById(id);
        return Result.success(dish);
    }

    /* 修改菜品 */
    @PutMapping
    @Operation(summary = "[菜品管理]修改菜品",description = "修改菜品信息")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品 <--> name => {}", dishDTO.getName());
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /* 起售/禁售菜品 */
    @PostMapping("/status/{status}")
    @Operation(summary = "[菜品管理]起售禁售",description = "起售禁售菜品")
    public Result changeDishStatus(@PathVariable Integer status,Long id){
        log.info("起售 / 禁售 <--> status ==> {} | id ==> {}", status,id);
        dishService.changeDishStatus(id,status);
        return Result.success();
    }

}

