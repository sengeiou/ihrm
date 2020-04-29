package com.ihrm.company.es;

import com.ihrm.company.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CarRepository extends ElasticsearchRepository<Car, Integer> {


}
