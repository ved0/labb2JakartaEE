package com.example.demo1.entities.categories;

public class ProductCategoryChangeRequest {
    Category categoryToChange;

    ProductCategoryChangeRequest(){};

    ProductCategoryChangeRequest(Category categoryToChange){
        this.categoryToChange = categoryToChange;
    }

    public String categoryAsString(){
        return categoryToChange.name();
    }

    public Category getCategoryToChange(){
        return categoryToChange;
    }
}
