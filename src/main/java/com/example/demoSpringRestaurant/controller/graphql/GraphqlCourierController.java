package com.example.demoSpringRestaurant.controller.graphql;

import com.example.demoSpringRestaurant.exception.CourierDocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.exception.OrderDocumentNotFoundException;
import com.example.demoSpringRestaurant.facade.OrderCourierFacade;
import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.service.CourierService;
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
public class GraphqlCourierController {
    private final CourierService courierService;
    private final OrderCourierFacade orderCourierFacade;

    @QueryMapping("couriers")
    public List<CourierDto> getCouriers() {
        var couriers = courierService.getCouriers();
        return couriers;
    }

    @QueryMapping("courier")
    public CourierDto getCourier(@Argument String id) {
        try {
            var courier = courierService.getCourier(id);
            return courier;
        } catch (CourierDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("createCourier")
    public CourierDto createCourier(@Argument @Valid @RequestBody CourierCreationDto courier, @Argument String id) {
        var courierDto = courierService.createCourier(courier);
        return courierDto;
    }

    @MutationMapping("deleteCourier")
    public CourierDto deleteCourier(@Argument String id) {
        try {
            var courierDto = orderCourierFacade.deleteCourier(id);
            return courierDto;
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("updateCourier")
    public CourierDto updateCourier(@Argument String id, @Valid @RequestBody @Argument CourierUpdateDto courier) {
        try {
            var courierUpdate = courierService.updateCourier(id, courier);
            return courierUpdate;
        } catch (CourierDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("addOrderToCourier")
    public CourierDto addOrderToCourier(@Argument String courierId, @Argument String orderId) {
        try {
            var courierDto = orderCourierFacade.addOrderToCourier(courierId, orderId);
            return courierDto;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

    @MutationMapping("setOrderActive")
    public CourierDto setOrderActive(@Argument String courierId, @Argument String orderId) {
        try {
            var courierDto = orderCourierFacade.setOrderActive(courierId, orderId);
            return courierDto;
        } catch (CourierDocumentNotFoundException | OrderDocumentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        }
    }

}
