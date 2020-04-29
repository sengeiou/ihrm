package com.ihrm.company.service;

import com.ihrm.domain.company.es.EsCompany;

import java.util.List;

/**
 * 企业ES搜索管理
 */
public interface EsCompanyService {
    /**
     * 根据Id删除
     */
    Boolean deleteByID(String id);

    /**
     * 根据名称搜索
     */
    List<EsCompany> findByName(String name);

    /**
     * 保存企业
     */
    Boolean save(EsCompany esCompany);

    /**
     * 查询全部
     */
    List<EsCompany> getAll();

    /**
     * 批量删除商品
     */
    void delete(List<String> ids);

    /**
     * 分页
     */
//    Page<EsCompany> getEsCompanyByName(String name,int pageNum,int size);


    List<EsCompany> getEsCompanyLimit(int pageNum, int size);
}
