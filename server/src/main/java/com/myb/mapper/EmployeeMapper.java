package com.myb.mapper;

import com.github.pagehelper.Page;
import com.myb.annotation.AutoFill;
import com.myb.dto.EmployeeLoginDTO;
import com.myb.dto.EmployeePageQueryDTO;
import com.myb.entity.Employee;
import com.myb.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

    /* 根据员工名称查询员工 -- 用于员工登录 */
    Employee selectByUsername(EmployeeLoginDTO employeeLoginDTO);

    /* 插入新员工 */
    @AutoFill(value = OperationType.INSERT)
    void insertEmployee(Employee employee);

    /* 分页查询 */
    Page<Employee> pageSelect(EmployeePageQueryDTO pageSelectDTO);

    /* 更新员工信息 */
    @AutoFill(value = OperationType.UPDATE)
    void updateEmployee(Employee employee);

    /* 查询回显 */
    Employee selectById(Long id);
}