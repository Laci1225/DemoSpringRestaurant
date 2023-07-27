package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.model.service.OrderCreationDto;
import com.example.demoSpringRestaurant.model.service.OrderDto;
import com.example.demoSpringRestaurant.model.service.OrderUpdateDto;
import com.example.demoSpringRestaurant.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor
public class GraphqlOrderController {
    private final OrderService orderService;
    private final RestaurantOrderFacade restaurantOrderFacade;
    private final RestaurantOrderGuestCourierFacade restaurantOrderGuestCourierFacade;
    private final OrderGuestCourierFacade orderGuestCourierFacade;
    private final OrderCourierFacade orderCourierFacade;

    @QueryMapping("orders")
    public List<OrderDto> getOrders(@Argument String id) {
        try {
            System.out.println("Requested orders for restaurant with ID: " + id);
            var orders = restaurantOrderFacade.getOrdersByRestaurantId(id);
            System.out.println("Orders for restaurant with ID: " + id + " returned successfully");
            return orders;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @QueryMapping("order")
    public OrderDto getOrder(@Argument String id) {
        try {
            System.out.println("Requested order with ID: " + id);
            var order = orderService.getOrderById(id);
            System.out.println("Order with ID: " + id + " returned successfully");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createOrder")
    public OrderDto createOrder(@Argument @Valid @RequestBody OrderCreationDto order, @Argument String id) {
        try {
            System.out.println("Creating an order for restaurant with ID: " + id);
            var orderDto = restaurantOrderGuestCourierFacade.createOrder(order, id);
            System.out.println("Order created successfully");
            return orderDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("deleteOrder")
    public OrderDto deleteOrder(@Argument String id) {
        try {
            System.out.println("Deleting an order with ID: " + id);
            var orderDto = orderGuestCourierFacade.deleteOrder(id);
            System.out.println("Order with ID: " + id + " deleted successfully");
            return orderDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateOrder")
    public OrderDto updateOrder(@Argument String id, @Valid @RequestBody @Argument OrderUpdateDto order) {
        try {
            System.out.println("Updating order with ID: " + id);
            var orderUpdate = orderService.updateOrder(id, order);
            System.out.println("Order with ID: " + id + " updated successfully");
            return orderUpdate;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setNextStage")
    public OrderDto setNextState(@Argument String id) {
        try {
            System.out.println("Setting next stage for order with ID: " + id);
            var order = orderService.setNextState(id);
            System.out.println("Next stage set successfully");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setCourierToOrder")
    public OrderDto setCourierToOrder(@Argument String orderId, @Argument String courierId) {
        try {
            System.out.println("Setting courier with ID: " + courierId + " to order with ID: " + orderId);
            var order = orderCourierFacade.setCourierToOrder(orderId, courierId);
            System.out.println("Courier with ID: " + courierId + " set to order with ID: " + orderId + " successfully");
            return order;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
