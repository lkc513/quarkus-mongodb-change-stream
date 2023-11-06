package com.example;

import com.example.StockModel;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StockRepository implements PanacheMongoRepository<StockModel> {

}
