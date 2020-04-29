package com.ihrm.company.service.impl;

import com.ihrm.common.exception.ApiException;
import com.ihrm.company.es.EsCompanyRepository;
import com.ihrm.company.service.EsCompanyService;
import com.ihrm.domain.company.es.EsCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EsCompanyServiceImpl implements EsCompanyService {

    @Autowired
    private EsCompanyRepository esCompanyRepository;

    @Override
    public Boolean deleteByID(String id) {
        try {
            esCompanyRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiException("添加失败");
        }
        return true;
    }

    @Override
    public List<EsCompany> findByName(String name) {
        return esCompanyRepository.findByName(name);
//        return null;
    }

    @Override
    public Boolean save(EsCompany esCompany) {
        try {
            esCompanyRepository.save(esCompany);
        } catch (Exception e) {
            throw new ApiException("添加失败");
        }
        return true;
    }

    @Override
    public List<EsCompany> getAll() {
        List<EsCompany> esCompanyList = new ArrayList<>();
        Iterable<EsCompany> esCompanyIterable = esCompanyRepository.findAll();
        esCompanyIterable.forEach(esCompanyList::add);
        return esCompanyList;
    }

    @Override
    public void delete(List<String> ids) {
        if (!StringUtils.isEmpty(ids)) {
            List<EsCompany> esProductList = new ArrayList<>();
            for (String id : ids) {
                EsCompany esProduct = new EsCompany();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esCompanyRepository.deleteAll(esProductList);
        }
    }

//    @Override
//    public Page<EsCompany> getEsCompanyByName(String name, int pageNum, int size) {
//       esCompanyRepository.getEsCompanyByName(name, PageRequest.of(pageNum, size));
//        return null;
//    }

    @Override
    public List<EsCompany> getEsCompanyLimit(int pageNum, int size) {
        Page<EsCompany> page = esCompanyRepository.findAll(PageRequest.of(pageNum, size));
        return page.getContent();
    }
}
