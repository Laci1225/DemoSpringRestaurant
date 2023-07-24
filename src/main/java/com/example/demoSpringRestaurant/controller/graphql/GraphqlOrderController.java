package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.model.*;
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
            var orders = restaurantOrderFacade.getOrdersByRestaurantId(id);
            return orders;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @QueryMapping("order")
    public OrderDto getOrder(@Argument String id) {
        try {
            var order = orderService.getOrderById(id);
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createOrder")
    public OrderDto createOrder(@Argument @Valid @RequestBody OrderCreationDto order, @Argument String id) {
        try {
            var orderDto = restaurantOrderGuestCourierFacade.createOrder(order, id);
            return orderDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("deleteOrder")
    public OrderDto deleteOrder(@Argument String id) {
        try {
            var orderDto = orderGuestCourierFacade.deleteOrder(id);
            return orderDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateOrder")
    public OrderDto updateOrder(@Argument String id, @Valid @RequestBody @Argument OrderUpdateDto order) {
        try {
            var orderUpdate = orderService.updateOrder(id, order);
            return orderUpdate;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setNextStage")
    public OrderDto setNextState(@Argument String id) {
        try {
            var order = orderService.setNextState(id);
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setCourierToOrder")
    public OrderDto setCourierToOrder(@Argument String orderId, @Argument String courierId) {
        try {
            var order = orderCourierFacade.setCourierToOrder(orderId, courierId);
            return order;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
