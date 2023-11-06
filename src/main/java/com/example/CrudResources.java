package com.example;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
@ApplicationScoped
public class CrudResources {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("kcdatabase");
            MongoCollection<Document> collection = database.getCollection("stock");

            try {
                // Inserts a sample document into the "stock" collection and print its ID
                InsertOneResult insertResult = collection.insertOne(new Document("stockName", "test_2").append("stockPrice", 0.22));
                log.info("Success! Inserted document id: " + insertResult.getInsertedId());

                // Updates the sample document and prints the number of modified documents
                UpdateResult updateResult = collection.updateOne(Filters.eq("stockName", "test_1"), Updates.set("stockPrice", 9999.77));
                log.info("Updated " + updateResult.getModifiedCount() + " document.");

                // Deletes the sample document and prints the number of deleted documents
                DeleteResult deleteResult = collection.deleteOne(Filters.eq("stockName", "test_anh"));
                log.info("Deleted " + deleteResult.getDeletedCount() + " document.");

                // Prints a message if any exceptions occur during the operations
            } catch (MongoException me) {
                log.error("Unable to insert, update, or replace due to an error: " + me);
            }
        }
    }

}

