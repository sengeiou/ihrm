package com.ihrm.company.service;

import com.ihrm.common.entity.MyPage;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.repository.CompanyRepository;
import com.ihrm.domain.company.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "companyService")
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private IdWorker idWorker;


    /**
     * 保存企业
     * 1.配置idwork到工程
     * 2.在service中注入idwork
     * 3.通过idwork生成id
     * 4.保存企业
     */
    public void add(Company company) {
        //基本属性设置
        String id = idWorker.nextId() + "";
        company.setId(id);
        //默认状态
        company.setAuditState("0");//0代表未审核，1代表审核
        company.setState(1);//0代表未激活，1代表激活
        companyRepository.save(company);
    }

    /**
     * 更新企业
     * 1.参数：Company
     * 2.根据id先查询企业
     * 3.。设置修改的属性
     * 4.调用dao完成更新
     */
    public void update(Company company) {
        Company temp = companyRepository.findById(company.getId()).get();
        temp.setName(company.getName());
        temp.setCompanyPhone(company.getCompanyPhone());
        companyRepository.save(temp);
    }

    /**
     * 删除企业
     */
    @CacheEvict(value = "company", key = "#id")
    public void deleteByID(String id) {
        companyRepository.deleteById(id);
    }

    /**
     * 根据id查询企业
     */
    @Cacheable(value = "company", key = "#id")
    public Company findById(String id) {
        Optional<Company> optional = companyRepository.findById(id);
        if (optional != null && optional.isPresent()) {
            Company company = optional.get();
            return company;
        } else {
            return null;
        }
    }

    /**
     * 查询所有企业
     */
    @Cacheable(value = "findAll")
    public MyPage<Company> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        MyPage<Company> myPage=new MyPage<>(companyRepository.findAll(pageRequest));
        return myPage;
    }
}
