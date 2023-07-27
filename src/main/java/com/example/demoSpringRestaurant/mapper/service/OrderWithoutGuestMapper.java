package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderWithoutGuestMapper {//todo orderWithoutReferences guest+courier
    @Mapping(target = "guest",ignore = true)
    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    @Mapping(target = "guest",ignore = true)
    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);

    @Mapping(target = "orderDocument.courier", ignore = true)
    @Mapping(target = "orderDocument.guest", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courier.id")
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);

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
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "guest",ignore = true)
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);
}
