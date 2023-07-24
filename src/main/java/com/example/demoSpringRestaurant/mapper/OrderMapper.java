package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrderMapper.class})
@Component
public interface OrderMapper {

    @Mapping(target = "courierDocument", source = "orderDto.courierDto")
    @Mapping(target = "guestDocument", source = "orderDto.guestDto")
    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    @Mapping(target = "guestDto", source = "orderDocument.guestDocument")
    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);


    @Mapping(target = "orderDocument.courierDocument", ignore = true)
    @Mapping(target = "orderDocument.guestDocument", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courierDocument.id")
    @Mapping(target = "guestId", source = "orderDocument.guestDocument.id")
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);

    @BeanMapping(builder = @Builder(disableBuilder = true))//todo
    @Mapping(target = "courierDto", ignore = true)
    @Mapping(target = "guestDto", ignore = true)
    OrderDto fromOrderCreationDtoToDto(OrderCreationDto orderCreationDto,
                                       @Context CourierDto courierDto,
                                       @Context GuestDto guestDto);

    @AfterMapping
    default void fromOrderCreationDtoToDto(@MappingTarget OrderDto orderDto,
                                           @Context CourierDto courierDto,
                                           @Context GuestDto guestDto) {
        orderDto.setCourierDto(courierDto);
        orderDto.setGuestDto(guestDto);
    }

    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    @Mapping(target = "guestDto", source = "orderDocument.guestDocument")
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "courierDocument", source = "orderUpdateDto.courierDto")
    @Mapping(target = "guestDocument", source = "orderUpdateDto.guestDto")
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);

}
