package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static io.lettuce.core.KillArgs.Builder.id;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Value("${admin.defaultPassword}")
    private String defaultPassword;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        String password_md5=DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password_md5.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }


    @Override
    public Result<String> addEmployee(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(defaultPassword.getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.addEmployee(employee);
        return Result.success();
    }

    @Override
    public Result<PageResult> pageEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        /**
         * 解决方案1:不用pagehelper，手动计算分页
         *
        List<Employee> employees=employeeMapper.pageEmployee(employeePageQueryDTO);

        PageResult pageResult=new PageResult();
        pageResult.setTotal(employees.size());
        int fromIndex=(employeePageQueryDTO.getPage()-1)*employeePageQueryDTO.getPageSize();
        int toIndex=fromIndex+employeePageQueryDTO.getPageSize();
        if(toIndex>employees.size()) toIndex=employees.size();
        List<Employee> employeePage=employees.subList(fromIndex,toIndex);
        pageResult.setRecords(employeePage);

        Result<PageResult> result = new Result<>();
        result.setData(pageResult);
        result.setCode(1);
         */

        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageEmployee(employeePageQueryDTO);

        PageResult pageResult=new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());

        Result<PageResult> result=new Result<>();
        result.setCode(1);
        result.setData(pageResult);
        return result;
    }

    @Override
    public Result stopOrStart(int status, Long id) {
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        Employee employee = Employee.builder().status(status).id(id).updateTime(now).updateUser(currentId).build();
        int i=employeeMapper.update(employee);
        if(i==1){
            return Result.success();
        }
        else return Result.error("数据库修改状态码失败，请联系管理员!");
    }

    @Override
    public Result editEmployee(EmployeeDTO employeeDTO) {
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        Employee employee = Employee.builder().id(employeeDTO.getId())
                .idNumber(employeeDTO.getIdNumber())
                .name(employeeDTO.getName())
                .phone(employeeDTO.getPhone())
                .sex(employeeDTO.getSex())
                .username(employeeDTO.getUsername())
                .updateUser(currentId)
                .updateTime(now)
                .build();
        int i = employeeMapper.update(employee);
        if (i==1){
            return Result.success();
        }
        else if(i==0) {
            return Result.error("找不到指定id用户!");
        }
        else {
            return Result.error("数据库异常,请联系管理员!");
        }
    }

}
