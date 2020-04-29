package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.repository.DepartmentRepository;
import com.ihrm.domain.company.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentService extends BaseService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department) {
        //设置主键的值
        //调用dao保存部门
        String id = idWorker.nextId() + "";
        department.setId(id);
        departmentRepository.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department) {
        //根据id查询部门
        Department dept = departmentRepository.findById(department.getId()).get();
        //设置部门属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        //更新部门
        departmentRepository.save(department);
    }

    /**
     * 根据id查询部门
     */
    public Department findById(String id) {
        return departmentRepository.findById(id).get();
    }

    /**
     * 查询全部部门列表
     */
    public List<Department> findAll(String companyId) {
        /**
         * 只查询companyId
         * 很多地方都需要companyId查询
         * 很多的对象中都具有companyId
         */
//        Specification<Department> specification = new Specification<Department>() {
//            /**
//             * 用户构造查询条件
//             * @param root 包含了所有对象数据
//             * @param criteriaQuery  一般不用
//             * @param criteriaBuilder 构造查询条件
//             * @return
//             */
//            @Override
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//               //根据企业id查询
//                return criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
//            }
//        };
        return departmentRepository.findAll(getSpec(companyId));
    }

    /**
     * 根据id删除部门
     */

    public void deleteById(String id) {
        departmentRepository.deleteById(id);
    }
}
