package com.ihrm.company.controller;

import com.ihrm.common.entity.CommonResult;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "企业管理")
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
@Slf4j
//@EnableAutoConfiguration
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //保存企业
//    @RequestMapping(value = "",method = RequestMethod.POST)
    @ApiOperation(value = "保存企业")
    @PostMapping("/save")
    public CommonResult save(@RequestBody Company company) {

        companyService.add(company);
        return CommonResult.success();
    }

    //根据id更新企业
    @ApiOperation(value = "更新企业")
    @PutMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable(value = "id") String id, @RequestBody Company company) {
        company.setId(id);
        companyService.update(company);
        return CommonResult.success();
    }

    //根据id删除企业
    @ApiOperation(value = "删除企业")
    @DeleteMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable(value = "id") String id) {
        companyService.deleteByID(id);
        return CommonResult.success();
    }

    //根据id查询企业
    @ApiOperation(value = "单一查询企业")
    @GetMapping(value = "/findByID/{id}")

    public CommonResult findById(@PathVariable(value = "id") String id) {
        Company company = companyService.findById(id);
//        Result result = new Result(ResultCode.SUCCESS);
//        result.setData(company);
//        return result;
        return CommonResult.success(company);
    }

    //查询全部企业列表
    @ApiOperation(value = "查询所有企业")
    @GetMapping(value = "/findAll/{page}/{size}")
    public CommonResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        MyPage<Company> companyPage = companyService.findAll(page, size);
        return CommonResult.success(companyPage);
    }
}
