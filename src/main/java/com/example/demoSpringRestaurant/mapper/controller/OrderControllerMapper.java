package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.model.controller.Order;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrderControllerMapper.class})
@Component
public interface OrderControllerMapper {

    Order fromOrderDtoToOrder(OrderDto orderDto);


    OrderDto fromOrderToOrderDto(Order orderDocument);


    /*@Mapping(target = "orderDocument.courier", ignore = true)
    @Mapping(target = "orderDocument.guest", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courier.id")
    @Mapping(target = "guestId", source = "orderDocument.guest.id")
    OrderCreationDto fromDocumentToOrderCreationDto(Order orderDocument);

    @BeanMapping(builder = @Builder(disableBuilder = true))//todo
    @Mapping(target = "courier", ignore = true)
    @Mapping(target = "guest", ignore = true)
    OrderDto fromOrderCreationDtoToDto(OrderCreationDto orderCreationDto,
                                       @Context CourierDto courierDto,
                                       @Context GuestDto guestDto);

    @AfterMapping
    default void fromOrderCreationDtoToDto(@MappingTarget OrderDto orderDto,
                                           @Context CourierDto courierDto,
                                           @Context GuestDto guestDto) {
        orderDto.setCourier(courierDto);
        orderDto.setGuest(guestDto);
    }

    OrderUpdateDto fromDocumentToOrderUpdateDto(Order orderDocument);

    Order fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);
*/
}
