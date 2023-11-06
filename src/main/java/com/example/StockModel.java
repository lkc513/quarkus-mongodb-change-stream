package com.example;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Data;

@Data
@MongoEntity(collection = "stock")
public class StockModel extends PanacheMongoEntity {

    public StockModel() {
    }

    @JsonbProperty("stockName")
    private String stockName;

    @JsonbProperty("stockPrice")
    private double stockPrice;

    public StockModel(String stockName, Double stockPrice) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
    }

}
