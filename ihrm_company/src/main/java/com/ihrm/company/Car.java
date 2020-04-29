package com.ihrm.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "cars",shards = 1,replicas = 1)
public class Car implements Serializable {
    private static final long serialVersionUID = 7959505035525576386L;

    @Id
    private Integer id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private String brand;

    private Double price;

    private String color;

    @Field(index = false,type = FieldType.Keyword)
    private String image;

    @Field(index = false,type = FieldType.Text)
    private String test;
}
