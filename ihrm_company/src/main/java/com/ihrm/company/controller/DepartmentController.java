package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.CommonResult;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//1.解决跨域
@CrossOrigin
//2.声明rest Controller
@RestController
//3.设置父路径
@Api(tags = "部门管理")
@RequestMapping(value = "/company")
@Slf4j
//@EnableAutoConfiguration
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 保存
     */
    @ApiOperation(value = "保存部门")
    @PostMapping(value = "/department")
    public CommonResult save(@RequestBody Department department) {
        //设置保存的企业id，企业id：目前使用固定值1，以后在解决
        department.setCompanyId(companyId);
        //调用service完成保存企业
        departmentService.save(department);
        //构造返回结果
        return CommonResult.success();
    }

    /**
     * 查询部门列表
     *
     * @return
     */
    @ApiOperation(value = "查询所有部门")
    @GetMapping(value = "/department")
    public CommonResult findAll() {
        //指定企业id
        Company company = companyService.findById(companyId);
        //完成查询
        List<Department> list = departmentService.findAll(companyId);
        //构造返回结果
        DeptListResult deptListResult = new DeptListResult(company, list);
        return CommonResult.success(deptListResult);
    }

    /**
     * 根据id查询department
     */
    @GetMapping(value = "/department/{id}")
    @ApiOperation(value = "查询指定部门")
    public CommonResult findById(@PathVariable(value = "id") String id) {
        Department department = departmentService.findById(id);
        return CommonResult.success(department);
    }

    /**
     * 修改department
     */
    @PutMapping(value = "/department/{id}")
    @ApiOperation(value = "修改部门")
    public CommonResult update(@PathVariable(value = "id") String id, @RequestBody Department department) {
        //设置修改的部门id
        department.setId(id);
        //调用servic跟新
        departmentService.update(department);
        return CommonResult.success();
    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "删除部门")
    @DeleteMapping(value = "/department/{id}")
    public CommonResult delete(@PathVariable(value = "id") String id) {
        departmentService.deleteById(id);
        return CommonResult.success();
    }
}
