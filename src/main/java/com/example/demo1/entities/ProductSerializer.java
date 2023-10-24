package com.example.demo1.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductSerializer extends JsonSerializer<Product> {
    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("productId", product.getProductId());
            jsonGenerator.writeStringField("productName", product.getProductName());
            jsonGenerator.writeStringField("productCategory", product.getProductCategory().name());
            jsonGenerator.writeNumberField("productRating", product.getProductRating());
            jsonGenerator.writeStringField("productCreated", formatDate(product.getCreatedDate()));
            jsonGenerator.writeStringField("productModified", formatDate(product.getModifiedDate()));
            jsonGenerator.writeEndObject();
        } catch (Exception ignored) {}
    }

    private String formatDate(Date dateToConvert){
        return new SimpleDateFormat("yyyy-MM-dd'@'HH:mm:ss").format(dateToConvert);
    }
}