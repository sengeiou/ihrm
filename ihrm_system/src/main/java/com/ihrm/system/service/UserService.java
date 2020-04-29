package com.ihrm.system.service;

import com.ihrm.common.exception.ApiException;
import com.ihrm.common.exception.GlobalExceptionHandler;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存用户
     */
    public void save(User user) {
        //设置主键的值
        //调用dao保存部门
        String id = idWorker.nextId() + "";
        user.setPassword("123456");//设置初始密码
        user.setEnableState(1);
        user.setId(id);
        userDao.save(user);
    }

    /**
     * 更新部门
     */
    public void update(User user) {
        //根据id查询用户
        User target = userDao.findById(user.getId()).get();
        //设置部门属性
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        //更新部门
        userDao.save(target);
    }

    /**
     * 根据id查询用户
     */
    public User findById(String id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser != null && optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    /**
     * 查询全部用户列表
     * 参数：map集合形式
     * hasDept
     * departmentId
     * companyId
     */
    public Page findAll(Map<String, Object> map, int page, int size) {
        //需要查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的companyId是否为空构造查询条件
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                //根据请求的部门Id构造查询条件
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), (String) map.get("departmentId")));
                }
                //根据请求的hasDept判断 是否分配部门 0未分配（departmentId = null） 1已分配(departmentId != null)
                if (!StringUtils.isEmpty(map.get("hasDept")) || "0".equals((String) map.get("hasDept"))) {
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                } else {
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //分页
        Page<User> userPage = userDao.findAll(spec, PageRequest.of(page - 1, size));
        return userPage;
    }

    /**
     * 根据id删除用户
     */

    public void deleteById(String id) {
        userDao.deleteById(id);
    }
}
