package com.example.demo1.service;

import com.example.demo1.entities.Product;
import com.example.demo1.entities.categories.Category;
import com.example.demo1.service.exceptions.InvalidProductFormatException;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class Warehouse {
    private final List<Product> products;


    public Warehouse() {
        products = new CopyOnWriteArrayList<>();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public int totalProductsInWarehouse() {
        return products.size();
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(Product productToAdd) {
        Product newProduct = new Product(productToAdd.getProductName(), productToAdd.getProductCategory(), productToAdd.getProductRating(), new Date());
        if (productToAdd.getProductName() == null || productToAdd.getProductName().isEmpty()) {
            throw new InvalidProductFormatException("The name of the product can't be empty or null!");
        } else if (productToAdd.getProductCategory().name().equalsIgnoreCase("non_existent")) {
            throw new InvalidProductFormatException("The category must be a valid category!");
        } else if (productToAdd.getProductRating() < 1 || productToAdd.getProductRating() > 10) {
            throw new InvalidProductFormatException("The rating needs to be between 1-10");
        }
        if (products.isEmpty()) {
            newProduct.setProductId(1);
        } else {
            newProduct.setProductId(products.get(products.size() - 1).getProductId() + 1);
        }
        products.add(newProduct);
    }

    public Product findProduct(int id) {
        return products.stream()
                .filter(product -> product.getProductId() == id)
                .findFirst()
                .orElseThrow(() -> new InvalidProductFormatException("There is no product with the provided Id!"));
    }

    public List<Product> findByCategory(Category category) {
        return products.stream()
                .filter(product -> product.getProductCategory() == category)
                .sorted((product1, product2) -> product1.getProductName().compareToIgnoreCase(product2.getProductName()))
                .toList();
    }

    public List<Product> findModifiedProducts() {
        return products.stream()
                .filter(product -> product.getCreatedDate() != product.getModifiedDate())
                .toList();
    }

    public List<Product> findProductsFromDate(Date fromDate) {
        return products.stream()
                .filter(product -> product.getCreatedDate().after(fromDate))
                .toList();
    }

    public List<Category> getCategoriesWithAtLeastOneProduct() {
        return products.stream()
                .map(Product::getProductCategory)
                .distinct()
                .toList();
    }

    public long amountOfProductsInCategory(Category category) {
        return products.stream()
                .filter(product -> product.getProductCategory() == category)
                .count();
    }


    public Map<Character, Integer> getMapWithFirstLetterOfProductsAndTheirSum() {
        return products.stream()
                .map(product -> product.getProductName().toUpperCase().charAt(0))
                .collect(Collectors.toMap(Function.identity(),
                        character -> 1,
                        Integer::sum));
    }

    public List<Product> getProductsWithMaxRatingFromThisMonthAndNewestFirst() {
        return products.stream()
                .filter(product -> product.getProductRating() == (byte) 10)
                .filter(product -> convertDate(product.getCreatedDate()).getMonth().equals(getTodaysMonth()))
                .filter(product -> convertDate(product.getCreatedDate()).getYear() == getCurrentYear())
                .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))
                .toList();
    }

    private Month getTodaysMonth() {
        return convertDate(new Date()).getMonth();
    }

    private int getCurrentYear() {
        return convertDate(new Date()).getYear();
    }

    private LocalDate convertDate(Date dateToConvert) {
        return LocalDate.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }
}
