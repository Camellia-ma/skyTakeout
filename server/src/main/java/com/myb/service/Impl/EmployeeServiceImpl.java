package com.myb.service.Impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.myb.constant.PasswordConstant;
import com.myb.constant.StatusConstant;
import com.myb.dto.EmployeeDTO;
import com.myb.dto.EmployeeLoginDTO;
import com.myb.dto.EmployeePageQueryDTO;
import com.myb.entity.Employee;
import com.myb.mapper.EmployeeMapper;
import com.myb.result.PageResult;
import com.myb.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    /* 员工登录 */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        return  employeeMapper.selectByUsername(employeeLoginDTO);
    }

    @Override
    /* 新增员工 */
    public void addNewEmployee(EmployeeDTO employeeDTO) {
        // 1、创建一个对象来承载新增的员工
        Employee employee = new Employee();
        // 2、调用框架进行对象属性复制
        BeanUtils.copyProperties(employeeDTO, employee);
        // 3、设置默认值 -- 前端传入的 RequestBody 中未设置默认的密码以及状态
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);  // 设置默认密码
        employee.setStatus(StatusConstant.ENABLE);                // 设置默认状态为启用
        // 4、操作数据库
        employeeMapper.insertEmployee(employee);
    }

    @Override
    /* 分页查询员工 */
    public PageResult pageSelect(EmployeePageQueryDTO pageSelectDTO) {
        // 设置分页查询每页展示条数以及查询的页数
        PageHelper.startPage(pageSelectDTO.getPage(),pageSelectDTO.getPageSize());
        // 执行查询
        Page<Employee> result = employeeMapper.pageSelect(pageSelectDTO);
        // 组合输出
        long total = result.getTotal(); // 查询到的记录数
        List<Employee> records = result.getResult(); // 查询到的记录
        return new PageResult(total,records);
    }

    @Override
    /* 启用或禁用账号 */
    public void updateStatus(Integer status, Long id) {
        // 构建一个 employee 对象来执行更新操作
        Employee employee = Employee.builder()
                            .id(id)
                            .status(status)
                            .build();
        // 执行更新操作
        employeeMapper.updateEmployee(employee);
    }

    @Override
    /* 查询回显 */
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        // 创建一个新员工对象并将复制端传入的参数
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.updateEmployee(employee);
    }


}
