package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.*;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderWithoutGuestMapper {//todo orderWithoutReferences guest+courier
    @Mapping(target = "guestDocument",ignore = true)
    @Mapping(target = "courierDocument", source = "orderDto.courierDto")
    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    @Mapping(target = "guestDto",ignore = true)
    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);


    @Mapping(target = "orderDocument.courierDocument", ignore = true)
    @Mapping(target = "orderDocument.guestDocument", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courierDocument.id")
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);


    @Mapping(target = "guestDto",ignore = true)
    @Mapping(target = "courierDto", ignore = true)
    OrderDto fromOrderCreationDtoToDto(OrderCreationDto orderCreationDto,
                                       @Context CourierDto courierDto,//todo
                                       @Context GuestDto guestDto);

    @AfterMapping
    default void fromOrderCreationDtoToDto(@MappingTarget OrderDto orderDto,
                                           @Context CourierDto courierDto,
                                           @Context GuestDto guestDto) {
        orderDto.setCourierDto(courierDto);
        orderDto.setGuestDto(null);
    }

    @Mapping(target = "guestDto",ignore = true)
    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "guestDocument",ignore = true)
    @Mapping(target = "courierDocument", source = "orderUpdateDto.courierDto")
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);
}
