package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.document.OrderDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RestaurantWithoutOrderMapper.class})
public interface OrderMapper {

    OrderDocument fromOrderDtoToDocument(OrderDto OrderDto);

    OrderDto fromDocumentToOrderDto(OrderDocument orderDocument);

    OrderCreationDto fromDocumentToOrderCreationDto(OrderDocument orderDocument);

    OrderDocument fromOrderCreationDtoToDocument(OrderCreationDto OrderCreationDto);

    OrderUpdateDto fromDocumentToOrderUpdateDto(OrderDocument orderDocument);

    OrderDocument fromOrderUpdateDtoToDocument(OrderUpdateDto OrderUpdateDto);

}
