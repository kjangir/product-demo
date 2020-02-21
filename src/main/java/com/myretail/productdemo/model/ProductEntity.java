package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
@AllArgsConstructor
@Builder
@Table("price")
public class ProductEntity {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    @JsonProperty("id")
    @NotNull
    @ApiModelProperty(required = true, value = "Product ID", example = "13860427", position = 1)
    private Integer id;

    @Column("price")
    @JsonProperty("currentPrice")
    @NotNull
    @ApiModelProperty(required = true, value = "Product Price", example = "21.11", position = 2)
    private Float currentPrice;

    @Column("currency_code")
    @JsonProperty("currencyCode")
    @NotNull
    @ApiModelProperty(value = "Product Price Currency Code", example = "USD", position = 3)
    @Builder.Default
    private String currencyCode = "USD";

    @Column("created_by")
    @JsonProperty("createdBy")
    @ApiModelProperty(hidden = true)
    private String createdBy;

    @Column("updated_by")
    @JsonProperty("updatedBy")
    @ApiModelProperty(hidden = true)
    private String updatedBy;

    @Column("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @ApiModelProperty(hidden = true)
    @Builder.Default
    LocalDateTime createdTime = LocalDateTime.now();

    @Column("updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @ApiModelProperty(hidden = true)
    @Builder.Default
    LocalDateTime updatedTime = LocalDateTime.now();

}
