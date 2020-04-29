package com.ihrm.company.repository;

import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 部门dao接口
 */
public interface DepartmentRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department> {


}
