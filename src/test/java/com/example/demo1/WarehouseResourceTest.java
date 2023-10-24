package com.example.demo1;

import com.example.demo1.service.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test the endpoints of WarehouseResource")
@ExtendWith(MockitoExtension.class)
public class WarehouseResourceTest {
    @Mock
    Warehouse warehouse;
    Dispatcher dispatcher;

    @BeforeEach
    public void setupTest() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        var warehouseResource = new WarehouseResource(warehouse);
        dispatcher.getRegistry().addSingletonResource(warehouseResource);
    /*    ExceptionMapper<GenericException> mapper = new GenericExceptionMapper();
        dispatcher.getProviderFactory().registerProviderInstance(mapper);*/
    }

    @Test
    @DisplayName("Accessing the root of the application at '/'")
    void accessTheRootOfTheApplicationAndReturnStatus200() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Hello, World!\nWelcome to the warehouse!", response.getContentAsString());
    }

    @Nested
    @DisplayName("Entering the warehouse with products")
    class enteringWarehouse {

        @Test
        @DisplayName("Warehouse at '/products' with no products (is empty)")
        void accessProductsInAnEmptyWarehouseAndReturnStatus200() throws Exception {
            MockHttpRequest request = MockHttpRequest.get("/products");
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("No products in the warehouse!", response.getContentAsString());
        }

        @Test
        @DisplayName("Add a product using the POST-method to the warehouse at '/products'")
        void addAProductToTheWarehouseAndReturnStatus201() throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonBody = objectMapper.createObjectNode();
            jsonBody.put("productName", "Microwave");
            jsonBody.put("productCategory", "kitchen");
            jsonBody.put("productRating", 5);

            MockHttpRequest request = MockHttpRequest.post("/products");
            request.header("content-type", "application/json");
            request.contentType("application/json");
            request.content(objectMapper.writeValueAsBytes(jsonBody));
            MockHttpResponse response = new MockHttpResponse();

            dispatcher.invoke(request, response);

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertEquals("Product added successfully!", response.getContentAsString());
        }
    }
}
