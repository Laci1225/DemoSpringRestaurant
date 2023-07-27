package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.model.controller.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderWithoutGuestControllerMapper {//todo orderWithoutReferences guest+courier
    @Mapping(target = "guest",ignore = true)
    Order fromOrderDtoToOrder(OrderDto orderDto);


    @Mapping(target = "guest",ignore = true)
    OrderDto fromOrderToOrderDto(Order orderDocument);

    /*@Mapping(target = "orderDocument.courier", ignore = true)
    @Mapping(target = "orderDocument.guest", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courier.id")
    OrderCreationDto fromDocumentToOrderCreationDto(Order orderDocument);

    @BeanMapping(builder = @Builder(disableBuilder = true))//todo
    @Mapping(target = "guest",ignore = true)
    @Mapping(target = "courier", ignore = true)
    OrderDto fromOrderCreationDtoToDto(OrderCreationDto orderCreationDto,
                                       @Context CourierDto courierDto,//todo
                                       @Context GuestDto guestDto);

    @AfterMapping
    default void fromOrderCreationDtoToDto(@MappingTarget OrderDto order,
                                           @Context CourierDto courier,
                                           @Context GuestDto guest) {
        order.setCourier(courier);
        order.setGuest(guest);
    }

    @Mapping(target = "guest",ignore = true)
    OrderUpdateDto fromDocumentToOrderUpdateDto(Order orderDocument);

    @Mapping(target = "guest",ignore = true)
    Order fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);*/
}
