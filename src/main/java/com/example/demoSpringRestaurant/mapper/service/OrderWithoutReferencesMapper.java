package com.example.demoSpringRestaurant.mapper.service;

import com.example.demoSpringRestaurant.model.service.*;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderWithoutReferencesMapper {//guest+courier
    @Mapping(target = "courier",ignore = true)
    @Mapping(target = "guest",ignore = true)
    OrderDocument fromOrderDtoToDocument(OrderDto orderDto);


    @Mapping(target = "courier",ignore = true)
    @Mapping(target = "guest",ignore = true)
    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);

    //@Mapping(target = "orderDocument.courier", ignore = true)
    //@Mapping(target = "orderDocument.guest", ignore = true)
    @Mapping(target = "courierId",ignore = true)
    @Mapping(target = "guestId",ignore = true)
    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "courier",ignore = true)
    @Mapping(target = "guest",ignore = true)
    OrderDto fromOrderCreationDtoToDto(OrderCreationDto orderCreationDto);

    @Mapping(target = "courier",ignore = true)
    @Mapping(target = "guest",ignore = true)
    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    @Mapping(target = "courier",ignore = true)
    @Mapping(target = "guest",ignore = true)
    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto orderUpdateDto);
}
