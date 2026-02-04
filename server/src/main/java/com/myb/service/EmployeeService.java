package com.myb.service;


import com.myb.dto.EmployeeDTO;
import com.myb.dto.EmployeeLoginDTO;
import com.myb.dto.EmployeePageQueryDTO;
import com.myb.entity.Employee;
import com.myb.result.PageResult;

public interface EmployeeService {
    /* 员工登录 */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /* 新增员工 */
    void addNewEmployee(EmployeeDTO employeeDTO);

    /* 分页查询员工 */
    PageResult pageSelect(EmployeePageQueryDTO pageSelectDTO);

    /* 启用或禁用员工账号 */
    void updateStatus(Integer status, Long id);

    /* 查询回显 */
    Employee selectById(Long id);

    /* 修改员工信息 */
    void updateEmployee(EmployeeDTO employeeDTO);
}
