package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.CommonResult;
import com.ihrm.common.entity.PageResult;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明rest Controller
@RestController
//3.设置父路径
@RequestMapping(value = "/system")
@Api(tags = "用户管理")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    /**
     * 保存
     */
    @PostMapping(value = "/user")
    @ApiOperation(value = "保存用户")
    public CommonResult save(@RequestBody User user) {

        user.setCompanyName(companyName);
        user.setCompanyId(companyId);
        //调用service完成保存企业
        userService.save(user);
        //构造返回结果
        return CommonResult.success();
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @ApiOperation(value = "分页查询用户列表")
    @GetMapping(value = "/user")
    public CommonResult findAll(int page, int size, @RequestParam Map map) {
        //获取当前的企业id
        map.put("companyId", companyId);
        //完成查询
        Page<User> userPage = userService.findAll(map, page, size);
        //构造返回结果
        PageResult<User> pageResult = new PageResult(userPage.getTotalElements(), userPage.getContent());

        return CommonResult.success(pageResult);
    }

    /**
     * 根据id查询User
     */
    @GetMapping(value = "/user/{id}")
    @ApiOperation(value = "单一查询用户")
    public CommonResult findById(@PathVariable(value = "id") String id) {
        User user = userService.findById(id);
        return CommonResult.success(user);
    }

    /**
     * 修改User
     */
    @PutMapping(value = "/user/{id}")
    @ApiOperation(value = "修改用户")
    public CommonResult update(@PathVariable(value = "id") String id, @RequestBody User user) {
        //设置修改的部门id
        user.setId(id);
        //调用service跟新
        userService.update(user);
        return CommonResult.success();
    }

    /**
     * 根据id删除
     */

    @DeleteMapping(value = "/user/{id}")
    @ApiOperation(value = "删除用户")
    public CommonResult delete(@PathVariable(value = "id") String id) {
        userService.deleteById(id);
        return CommonResult.success();
    }
}
