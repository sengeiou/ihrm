package com.ihrm.company;

import com.ihrm.company.repository.CompanyRepository;
import com.ihrm.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyDaoTest {
    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void test() {
        Company company = companyRepository.findById("1").get();
        System.out.println(company);
    }
}
