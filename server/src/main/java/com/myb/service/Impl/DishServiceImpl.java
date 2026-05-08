package com.myb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myb.constant.MessageConstant;
import com.myb.constant.StatusConstant;
import com.myb.dto.DishDTO;
import com.myb.dto.DishPageQueryDTO;
import com.myb.entity.Dish;
import com.myb.entity.DishFlavor;
import com.myb.exception.DeletionNotAllowedException;
import com.myb.mapper.DishFlavorMapper;
import com.myb.mapper.DishMapper;
import com.myb.mapper.SetmealDishMapper;
import com.myb.result.PageResult;
import com.myb.service.DishService;
import com.myb.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /* 新增菜品接口 */
    @Override
    public void addDish(DishDTO dishDTO) {
        // 复制前端传入的类为新类
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        // 向菜品表中插入数据
        dishMapper.insert(dish);
        // 获取数据插入表中后数据库为数据分配的 id
        Long dishId = dish.getId();

        // 为每一条菜品口味数据增加 id
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
        }
        // 向口味表中批量插入数据
        dishFlavorMapper.batchInsert(flavors);

    }

    /* 菜品分页查询 */
    @Override
    public PageResult pageSelect(DishPageQueryDTO pageQueryDTO) {
        // 设置分页查询的起始页和每页数据量
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        // 执行查询
        Page<DishVO> page = dishMapper.pageSelect(pageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /* 批量删除菜品 */
    @Override
    @Transactional   // 需要同时操作两张数据库表格(dish 和 dish-flavor表) -- 开启事务管理，失败就回滚
    public void batchDelete(List<Long> ids) {
        // 判断当前菜品是否能够被删除 -- 是否处于起售状态
        for (Long id : ids) {
            Dish dish = dishMapper.selectById(id);
            if(Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 判断当前菜品是否能够被删除 -- 是否关联套餐
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(!setmealIds.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 执行批量删除
        /*
        * 注：此处需要操作两张数据表，需要开启事务管理
        */
        for(Long id : ids){
            // 菜品
            dishMapper.deleteById(id);
            // 菜品口味
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /* 查询回显 */
    @Override
    public DishVO getDishWithFlavorById(Long id) {
        // 根据 id 查询菜品
        Dish dish = dishMapper.selectById(id);
        // 查询口味
        List<DishFlavor> flavors = dishFlavorMapper.getDishById(id);
        // 封装返回格式
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /* 修改菜品信息 */
    @Override
    public void updateDish(DishDTO dishDTO) {
        // 复制菜品类
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        // 修改菜品基本信息
        dishMapper.updateDish(dish);

        // 修改菜品口味信息 -- 遵循先删除原有口味数据再插入新数据原则
        dishFlavorMapper.deleteByDishId(dish.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 为每一条口味数据设置菜品 id
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
        }
        dishFlavorMapper.batchInsert(flavors);
    }

    /* 起售禁售菜品 */
    @Override
    public void changeDishStatus(Long id, Integer status) {
        // 构建新的菜品对象
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        // 执行更新操作
        dishMapper.updateDish(dish);
    }
}
