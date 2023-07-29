package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.RestaurantDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.facade.OrderGuestCourierFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderFacade;
import com.example.demoSpringRestaurant.facade.RestaurantOrderGuestCourierFacade;
import com.example.demoSpringRestaurant.mapper.controller.OrderControllerMapper;
import com.example.demoSpringRestaurant.model.service.OrderCreationDto;
import com.example.demoSpringRestaurant.model.controller.Order;
import com.example.demoSpringRestaurant.model.service.OrderUpdateDto;
import com.example.demoSpringRestaurant.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@AllArgsConstructor@Slf4j
public class GraphqlOrderController {
    private final OrderService orderService;
    private final RestaurantOrderFacade restaurantOrderFacade;
    private final RestaurantOrderGuestCourierFacade restaurantOrderGuestCourierFacade;
    private final OrderGuestCourierFacade orderGuestCourierFacade;
    private final OrderCourierFacade orderCourierFacade;
    private final OrderControllerMapper orderControllerMapper;

    @QueryMapping("orders")
    public List<Order> getOrders(@Argument String id) {
        try {
            log.debug("Requested orders for restaurant with ID: " + id);
            var orders = restaurantOrderFacade.getOrdersByRestaurantId(id).stream()
                    .map(orderControllerMapper::fromOrderDtoToOrder).toList();
            log.debug("Orders for restaurant with ID: " + id + " returned successfully");
            return orders;
        } catch (RestaurantDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @QueryMapping("order")
    public Order getOrder(@Argument String id) {
        try {
            log.debug("Requested order with ID: " + id);
            var orderDto = orderService.getOrderById(id);
            var order = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Order with ID: " + id + " returned successfully");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createOrder")
    public Order createOrder(@Argument @Valid @RequestBody OrderCreationDto order, @Argument String id) {
        try {
            log.debug("Creating an order for restaurant with ID: " + id);
            var orderDto = restaurantOrderGuestCourierFacade.createOrder(order, id);
            var orderResponse = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Order created successfully");
            return orderResponse;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("deleteOrder")
    public Order deleteOrder(@Argument String id) {
        try {
            log.debug("Deleting an order with ID: " + id);
            var orderDto = orderGuestCourierFacade.deleteOrder(id);
            var order = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Order with ID: " + id + " deleted successfully");
            return order;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateOrder")
    public Order updateOrder(@Argument String id, @Valid @RequestBody @Argument OrderUpdateDto order) {
        try {
            log.debug("Updating order with ID: " + id);
            var orderDto = orderService.updateOrder(id, order);
            var orderResponse = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Order with ID: " + id + " updated successfully");
            return orderResponse;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setNextStage")
    public Order setNextState(@Argument String id) {
        try {
            log.debug("Setting next stage for order with ID: " + id);
            var orderDto = orderService.setNextState(id);
            var order = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Next stage set successfully");
            return order;
        } catch (OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setCourierToOrder")
    public Order setCourierToOrder(@Argument String orderId, @Argument String courierId) {
        try {
            log.debug("Setting courier with ID: " + courierId + " to order with ID: " + orderId);
            var orderDto = orderCourierFacade.setCourierToOrder(orderId, courierId);
            var order = orderControllerMapper.fromOrderDtoToOrder(orderDto);
            log.debug("Courier with ID: " + courierId + " set to order with ID: " + orderId + " successfully");
            return order;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
