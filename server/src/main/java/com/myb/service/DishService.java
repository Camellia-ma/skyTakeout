package com.myb.service;

import com.myb.dto.DishDTO;
import com.myb.dto.DishPageQueryDTO;
import com.myb.result.PageResult;
import com.myb.vo.DishVO;

import java.util.List;

public interface DishService {
    /* 新增菜品 */
    void addDish(DishDTO dishDTO);

    /* 分页查询 */
    PageResult pageSelect(DishPageQueryDTO pageQueryDTO);

    /* 批量删除菜品 */
    void batchDelete(List<Long> ids);

    /* 查询回显 */
    DishVO getDishWithFlavorById(Long id);

    /* 修改菜品 */
    void updateDish(DishDTO dishDTO);

    /* 起售或禁售菜品 */
    void changeDishStatus(Long id, Integer status);
}
