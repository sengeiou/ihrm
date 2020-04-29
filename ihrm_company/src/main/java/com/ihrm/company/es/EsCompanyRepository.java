package com.ihrm.company.es;

import com.ihrm.domain.company.es.EsCompany;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsCompanyRepository extends ElasticsearchRepository<EsCompany, String> {

    /**
     * 根据名称搜索
     *
     * @param name
     * @return
     */
    List<EsCompany> findByName(String name);


    void getEsCompanyByName(String name, PageRequest of);
}
