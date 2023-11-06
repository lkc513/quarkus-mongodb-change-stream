package com.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class Watch {

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("kcdatabase");
            MongoCollection<Document> collection = database.getCollection("stock");

            // Find all documents in the collection
            FindIterable<Document> documents = collection.find();

            // Iterate through the documents and print each one
            for (Document document : documents) {
                System.out.println("Existing document in 'stock' collection: " + document.toJson());
            }

            // Creates instructions to match insert and update operations
            List<Bson> pipeline = List.of(Aggregates.match(Filters.in("operationType", Arrays.asList("insert", "update"))));

            // Creates a change stream that receives change events for the specified operations
            ChangeStreamIterable<Document> changeStream = database
                    .watch(pipeline)
                    .fullDocument(FullDocument.UPDATE_LOOKUP);

            final int[] numberOfEvents = {0};

            // Prints a message each time the change stream receives a change event, until it receives two events
            changeStream.forEach(event -> {
//                System.out.println("Received a change to the collection: " + event);

                System.out.println("Full Changes: " + event.getFullDocument().toJson());

                System.out.println("Changes: " + event.getUpdateDescription().getUpdatedFields());

                if (++numberOfEvents[0] >= 1200) {
                    System.out.println("Exit after 1200 times try!");

                    System.exit(0);
                }
            });
        }
    }

}