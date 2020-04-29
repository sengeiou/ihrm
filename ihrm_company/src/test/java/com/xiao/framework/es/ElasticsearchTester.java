package com.xiao.framework.es;

import com.ihrm.company.Car;
import com.ihrm.company.CompanyApplication;
import com.ihrm.company.es.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CompanyApplication.class)
public class ElasticsearchTester {

    @Autowired private ElasticsearchTemplate template;

    @Autowired private CarRepository carRepository;

    @Test
    public void initIndex(){
        boolean boo = template.createIndex(Car.class);
        System.out.println("boo = " + boo);
        boolean b = template.putMapping(Car.class);
        System.out.println("b = " + b);

    }

    @Test
    public void addData(){
        Car car1 = new Car(111, "甲壳虫", "大众", 2000.90, "红色", "http://www.baidu.com","1");
        Car car2 = new Car(222, "雅阁", "honda", 4000.50, "白色", "http://www.baidu.com","1");
        Car car3 = new Car(333, "老丰田", "fentian", 8000.00, "绿色", "http://www.baidu.com","1");
        Car car4 = new Car(444, "奥迪", "dazong", 9000.00, "红色", "http://www.baidu.com","1");
        Car car5 = new Car(555, "帕沙特", "dazong", 6000.00, "黄色", "http://www.baidu.com","1");

        List<Car> cars = Arrays.asList(car1, car2, car3, car4, car5);
        Iterable<Car> all = carRepository.saveAll(cars);

        System.out.println("all = " + all);
    }
}
