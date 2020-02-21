package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table("price")
public class ProductEntity {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("id")
    @NotNull
    private Integer id;

    @Column("price")
    @JsonProperty("currentPrice")
    @NotNull
    private Float currentPrice;

    @Column("currency_code")
    @JsonProperty("currencyCode")
    @NotNull
    private String currencyCode;


    @Column("created_by")
    @JsonProperty("createdBy")
    private String createdBy;

    @Column("updated_by")
    @JsonProperty("updatedBy")
    private String updatedBy;

    @Column("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime createdTime;

    @Column("updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime updatedTime;

}
