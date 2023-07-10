package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
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


    @Mapping(target = "courierDocument", ignore = true)
    @Mapping(target = "guestDocument", ignore = true)
    OrderDocument fromOrderCreationDtoToDocument(OrderCreationDto orderCreationDto,
                                                 @Context CourierDocument courierDocument,
                                                 @Context GuestDocument guestDocument);

    @AfterMapping
    default void fromOrderCreationDtoToDocument(@MappingTarget OrderDocument orderDocument,
                                                @Context CourierDocument courierDocument,
                                                @Context GuestDocument guestDocument) {
        orderDocument.setCourierDocument(courierDocument);
        orderDocument.setGuestDocument(guestDocument);
    }

    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    @Mapping(target = "guestDto", source = "orderDocument.guestDocument")
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "courierDocument", source = "orderUpdateDto.courierDto")
    @Mapping(target = "guestDocument", source = "orderUpdateDto.guestDto")
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);

}
