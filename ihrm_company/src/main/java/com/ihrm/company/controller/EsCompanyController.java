package com.ihrm.company.controller;

import com.ihrm.common.entity.CommonResult;
import com.ihrm.company.service.EsCompanyService;
import com.ihrm.domain.company.es.EsCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "搜索企业管理")
@RestController
@RequestMapping(value = "es/company")
//@EnableAutoConfiguration
public class EsCompanyController {
    @Autowired
    private EsCompanyService esCompanyService;

    @GetMapping("/getAll")
    @ApiOperation(value = "查询全部EsCompany信息")
    public CommonResult getAll() {
        esCompanyService.getAll();
        return CommonResult.success();
    }

    @GetMapping("/getEsCompanyByName")
    @ApiOperation(value = "根据企业名查询EsCompany信息")
    @ApiImplicitParam(name = "name", value = "企业名", required = true, dataType = "String", paramType = "query")
    public CommonResult getEsCompanyByName(String name) {
        return CommonResult.success(esCompanyService.findByName(name));
    }

    @GetMapping("/getEsCompanyLimit")
    @ApiOperation(value = "根据企业名查询EsCompany信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "条数", dataType = "Integer")
    })
    public CommonResult getEsCompanyLimit(@RequestParam(defaultValue = "0", required = false) Integer pageNum,
                                          @RequestParam(defaultValue = "5", required = false) Integer size) {
        return CommonResult.success(esCompanyService.getEsCompanyLimit(pageNum, size));
    }

    @DeleteMapping("/deleteByID")
    @ApiOperation(value = "刪除EsCompany信息")
    @ApiImplicitParam(name = "id", value = "EsCompany", required = true, dataType = "String")
    public CommonResult deleteByID(@PathVariable(value = "id") String id) {
        Boolean deleteByID = esCompanyService.deleteByID(id);
        if (deleteByID) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping("/saveEsCompany")
    @ApiOperation(value = "添加EsCompany信息")
    @ApiImplicitParam(name = "esCompany", value = "企业信息", required = true, dataType = "EsCompany")
    public CommonResult saveEsCompany(@RequestBody EsCompany esCompany) {
        Boolean isSuccess = esCompanyService.save(esCompany);
        if (isSuccess) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
