package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.model.service.CourierCreationDto;
import com.example.demoSpringRestaurant.model.service.CourierDto;
import com.example.demoSpringRestaurant.model.service.CourierUpdateDto;
import com.example.demoSpringRestaurant.service.CourierService;
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
@AllArgsConstructor
@Slf4j
public class GraphqlCourierController {
    private final CourierService courierService;
    private final OrderCourierFacade orderCourierFacade;

    @QueryMapping("couriers")
    public List<CourierDto> getCouriers() {
        log.debug("Requested all couriers");
        var courierList = courierService.getCouriers();
        log.debug("Couriers returned successfully");
        return courierList;
    }

    @QueryMapping("courier")
    public CourierDto getCourier(@Argument String id) {
        try {
            log.debug("Requested all couriers");
            var courierList = courierService.getCourier(id);
            log.debug("Couriers returned successfully");
            return courierList;
        } catch (CourierDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("createCourier")
    public CourierDto createCourier(@Argument @Valid @RequestBody CourierCreationDto courier, @Argument String id) {
        log.debug("Creating a courier"); //TODO
        var courierDto = courierService.createCourier(courier);
        log.debug("Created a courier successfully");
        return courierDto;
    }

    @MutationMapping("deleteCourier")
    public CourierDto deleteCourier(@Argument String id) {
        try {
            log.debug("Deleting a courier");
            var courier = orderCourierFacade.deleteCourier(id);
            log.debug("Courier with ID: " + id + " deleted successfully");
            return courier;
        } catch (DocumentNotFoundException e) {
            log.warn("Deleting a courier were unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("updateCourier")
    public CourierDto updateCourier(@Argument String id, @Valid @RequestBody @Argument CourierUpdateDto courier) {
        try {
            log.debug("Updating courier with ID: " + id);
            var courierDto = courierService.updateCourier(id, courier);
            log.debug("Updating courier with ID: " + id + " was successful");
            return courierDto;
        } catch (DocumentNotFoundException e) {
            log.warn("Updating a courier was unsuccessful due to: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("addOrderToCourier")
    public CourierDto addOrderToCourier(@Argument String courierId, @Argument String orderId) {
        try {
            log.debug("Adding an order to a courier");
            var courier =  orderCourierFacade.addOrderToCourier(courierId, orderId);
            log.debug("Order added to a courier successfully");
            return courier;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @MutationMapping("setOrderActive")
    public CourierDto setOrderActive(@Argument String courierId, @Argument String orderId) {
        try {
            log.debug("Setting an order active in a courier");
            var courier = orderCourierFacade.setOrderActive(courierId, orderId);
            log.debug("CAn order with ID: " + courierId + " set in a courier successfully");
            return courier;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
