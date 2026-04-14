package com.myb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myb.constant.MessageConstant;
import com.myb.context.BaseContext;
import com.myb.dto.CategoryDTO;
import com.myb.dto.CategoryPageQueryDTO;
import com.myb.entity.Category;
import com.myb.exception.DeletionNotAllowedException;
import com.myb.mapper.CategoryMapper;
import com.myb.mapper.DishMapper;
import com.myb.mapper.SetmealMapper;
import com.myb.result.PageResult;
import com.myb.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /* 分页查询 */
    @Override
    public PageResult pageSelect(CategoryPageQueryDTO pageSelectDTO){
        // 设置分页查询起始页以及查询页数
        PageHelper.startPage(pageSelectDTO.getPage(),pageSelectDTO.getPageSize());
        // 调用 mapper 接口查询获得结果
        Page<Category> result = categoryMapper.pageSelect(pageSelectDTO);
        // 封装并返回查询结果
        return new PageResult(result.getTotal(),result.getResult());
    }

    /* 添加分类 */
    @Override
    public void addNewCategory(CategoryDTO categoryDTO){
        // 复制前端传入的数据
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        // 插入数据表中
        categoryMapper.insert(category);
    }

    /* 根据 id删除分类 */
    public void delete(Long id){
        // 检查要删除的分类是否关联菜品和套餐
        if(dishMapper.getCountByCategoryId(id) > 0){ // 菜品分支
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        if (setmealMapper.getCountByCategoryId(id) > 0){  // 套餐分支
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delete(id);
    }

    /* 修改分类状态 */
    @Override
    public void changeCategoryStatus(Integer status, Long id) {
        // 构建新对象承接状态以及 id
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();

        // 执行更新操作
        categoryMapper.update(category);
    }
}
