package com.myb.controller.admin;

import com.myb.constant.JwtClaimsConstant;
import com.myb.dto.EmployeeDTO;
import com.myb.dto.EmployeeLoginDTO;
import com.myb.dto.EmployeePageQueryDTO;
import com.myb.entity.Employee;
import com.myb.properties.JwtProperties;
import com.myb.result.PageResult;
import com.myb.result.Result;
import com.myb.service.EmployeeService;
import com.myb.utils.JwtUtil;
import com.myb.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 员工管理 -- 员工登录、退出登录以及员工的增删改查
 **/

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "[admin]员工管理",description = "员工管理相关接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;   // 引入自定义的 JWT 生成器 -- 用于构建 JWT 令牌

    /* 员工登录 */
    @PostMapping("/login")
    @Operation(summary = "[员工管理]-->员工登录",description = "员工登录操作")
    public Result<EmployeeLoginVO> empLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录 <---> name ==> {}",employeeLoginDTO.getUsername());
        Employee employee = employeeService.login(employeeLoginDTO);   // 执行查询操作

        /* 查询成功后组装返回数据 */
        // 1、为登录员工生成 JWT 令牌
        Map<String,Object> claims = new HashMap<>();   // 登录信息 <" empId ",登录员工id>
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());   // 向 map 内写入员工id
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),    // 登录密钥
                jwtProperties.getAdminTtl(),           // 令牌生效时间
                claims
        );
        //2、构建返回数据 employeeLoginVO
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        return Result.success(employeeLoginVO);
    }

    /* 退出登录 */
    @PostMapping("/logout")
    @Operation(summary = "[员工管理]-->退出登录",description = "员工退出登录")
    public Result<String> logout(){
        return Result.success();
    }

    /* 新增员工 */
    @PostMapping
    @Operation(summary = "[员工管理]-->新增员工",description = "新增员工操作")
    public Result addNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工 <---> name ==> {}",employeeDTO.getName());
        employeeService.addNewEmployee(employeeDTO);
        return Result.success();
    }

    /* 分页查询员工  -- 使用PageHelper */
    @GetMapping("/page")
    @Operation(summary = "[员工管理]-->查询员工",description = "分页查询员工")
    public Result<PageResult> pageSelect(EmployeePageQueryDTO  pageSelectDTO){
        log.info("分页查询员工 <---> page ==> {} | pageSize ==> {}",pageSelectDTO.getPage(),pageSelectDTO.getPageSize());
        PageResult result = employeeService.pageSelect(pageSelectDTO);
        return Result.success(result);
    }

    /* 启用或禁用员工账号 */
    @PostMapping("/status/{status}")
    @Operation(summary = "[员工管理]-->账号管理",description = "启用或禁用员工账号")
    public Result updateStatus(@PathVariable Integer status,Long id){
        log.info("启用或禁用账号 <---> id ==> {} | status ==> {}",id,status);
        employeeService.updateStatus(status,id);
        return Result.success();
    }

    /* 查询回显 -- 根据id查询员工 */
    @GetMapping("/{id}")
    @Operation(summary = "[员工管理]-->查询回显",description = "根据 id 查询员工信息")
    public Result<Employee> selectByID(@PathVariable Long id){
        log.info("查询回显 <---> id ==> {}",id);
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    /* 修改员工信息 */
    @PutMapping
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改员工信息 <---> name ==> {}",employeeDTO.getName());
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

    // TODO 完善修改密码功能

}
