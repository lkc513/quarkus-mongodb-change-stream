package com.example;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@Path("/stock")
public class StockResource {

    @Inject
    StockRepository stockRepository;

    @GET
    @Path("/listAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockModel> listAll() {
        return stockRepository.listAll();
    }

    @GET
    @Path("/stocks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PanacheMongoEntityBase> getAllStocks() {
        try {
            log.info("Stock Size: {}", StockModel.findAll().stream().count());

            return StockModel.findAll().list();
        } catch (Exception exception) {
            log.error("Error fetching stocks", exception);
            throw new RuntimeException("Error fetching stocks", exception);
        }
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StockModel> getAllStocksTest() {
        return Arrays.asList(
                new StockModel("Value1", 0.2),
                new StockModel("Value2", 10.03)
        );
    }

}
