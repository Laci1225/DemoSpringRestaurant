package com.example.demoSpringRestaurant.mapper.controller;

import com.example.demoSpringRestaurant.model.service.OrderCreationDto;
import com.example.demoSpringRestaurant.model.service.OrderDto;
import com.example.demoSpringRestaurant.model.service.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderControllerWithoutReferencesMapper {//guest+courier
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
