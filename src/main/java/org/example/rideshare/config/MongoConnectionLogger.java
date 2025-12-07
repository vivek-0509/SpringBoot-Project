package org.example.rideshare.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionLogger {

    private static final Logger logger = LoggerFactory.getLogger(MongoConnectionLogger.class);

    private final MongoTemplate mongoTemplate;

    public MongoConnectionLogger(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logMongoConnection() {
        try {
            String dbName = mongoTemplate.getDb().getName();
            logger.info("Successfully connected to MongoDB!");
            logger.info("Database: {}", dbName);
        } catch (Exception e) {
            logger.error(" Failed to connect to MongoDB: {}", e.getMessage());
        }
    }
}
