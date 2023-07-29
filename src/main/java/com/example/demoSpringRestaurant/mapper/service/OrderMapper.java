package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrderMapper.class})
@Component
public interface OrderMapper {

    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);


    @Mapping(target = "orderDocument.courier", ignore = true)
    @Mapping(target = "orderDocument.guest", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courier.id")
    @Mapping(target = "guestId", source = "orderDocument.guest.id")
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);

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

    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);

}
