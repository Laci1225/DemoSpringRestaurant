package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.CourierDto;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.CourierDocument;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrderMapper.class})
@Component
public interface OrderMapper {

    @Mapping(target = "courierDocument", source = "orderDto.courierDto")
    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);


    @Mapping(target = "orderDocument.courierDocument", ignore = true)
    @Mapping(target = "courierId", source = "orderDocument.courierDocument.id")
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);


    @Mapping(target = "courierDocument", ignore = true)
    OrderDocument fromOrderCreationDtoToDocument(OrderCreationDto orderCreationDto,
                                                 @Context CourierDocument courierDocument);

    @AfterMapping
    default void fromOrderCreationDtoToDocument(@MappingTarget OrderDocument orderDocument,
                                                @Context CourierDocument courierDocument) {
        orderDocument.setCourierDocument(courierDocument);
    }

    @Mapping(target = "courierDto", source = "orderDocument.courierDocument")
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "courierDocument", source = "orderUpdateDto.courierDto")
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);

}
