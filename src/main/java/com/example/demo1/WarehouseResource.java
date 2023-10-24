package com.example.demo1;

import com.example.demo1.entities.Product;
import com.example.demo1.entities.categories.Category;
import com.example.demo1.entities.categories.ProductCategoryChangeRequest;
import com.example.demo1.interceptor.Logging;
import com.example.demo1.service.Warehouse;
import com.example.demo1.service.exceptions.InvalidCategoryException;
import com.example.demo1.service.exceptions.InvalidProductFormatException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Logging
public class WarehouseResource {
    private @Inject Warehouse warehouse;
    private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

    public WarehouseResource() {
    }


    public WarehouseResource(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!\nWelcome to the warehouse!";
    }

    // add products
    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(@NotNull @Valid Product product) {
        try {
            warehouse.addProduct(product);
            logger.info("The product '" + product.getProductName() + "' was added successfully!");
            return Response.status(Response.Status.CREATED)
                    .entity("Product added successfully!")
                    .build();
        } catch (InvalidProductFormatException e) {
            logger.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/products")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllProducts() {
        if (warehouse.getAllProducts().isEmpty()) {
            logger.info("Warehouse is empty");
            return Response.status(Response.Status.OK).entity("No products in the warehouse!").build();
        }
        logger.info("All products in the warehouse returned!");
        List<Product> allProducts = warehouse.getAllProducts();
        return Response.status(Response.Status.OK).entity(allProducts).build();
    }

    @GET
    @Path("/products/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("productId") @Min(1) int productId) {
        try {
            Product productToFind = warehouse.findProduct(productId);
            logger.info("Found and returned the searched product, " + productToFind.getProductName());
            return Response.status(Response.Status.OK).entity(productToFind).build();

        } catch (InvalidProductFormatException e) {
            logger.info((e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/products/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeProductCategory(@PathParam("productId") int productId, ProductCategoryChangeRequest category) {
        try {
            Product productToChange = warehouse.findProduct(productId);
            if (Category.isValidCategory(category.categoryAsString()) && !category.categoryAsString().equals("NON_EXISTENT")) {
                productToChange.changeProductCategory(category.getCategoryToChange());
                logger.info("User changed category of product with Id, " + productId);
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                throw new InvalidCategoryException("This category does not existing!");
            }
        } catch (WebApplicationException e) {
            logger.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/products/category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@QueryParam("type") @NotEmpty String category) {
        try {
            if (Category.isValidCategory(category) && !category.equalsIgnoreCase(Category.NON_EXISTENT.name())) {
                List<Product> productsToReturn = warehouse.findByCategory(Category.valueOf(category.toUpperCase()));
                if (!productsToReturn.isEmpty()) {
                    logger.info("Returned a list with products from category, " + category.toUpperCase());
                    return Response.status(Response.Status.OK).entity(productsToReturn).build();
                } else {
                    logger.info("No products in this category, " + category.toUpperCase());
                    return Response.status(Response.Status.OK)
                            .entity("No products in this category!")
                            .build();
                }
            } else {
                throw new InvalidCategoryException(category + " is not a category!");
            }
        } catch (InvalidCategoryException e) {
            logger.info("User asked for invalid category, " + category);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}