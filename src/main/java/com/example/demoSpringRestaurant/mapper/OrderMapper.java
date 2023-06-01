package com.example.demoSpringRestaurant.mapper;

import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.model.OrderUpdateDto;
import com.example.demoSpringRestaurant.persistance.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface OrderMapper {
    OrderEntity fromOrderDtoToEntity(OrderDto OrderDto);

    OrderDto fromEntityToOrderDto(OrderEntity OrderEntity);

    OrderCreationDto fromEntityToOrderCreationDto(OrderEntity OrderEntity);

    OrderEntity fromOrderCreationDtoToEntity(OrderCreationDto OrderCreationDto);

    OrderUpdateDto fromEntityToOrderUpdateDto(OrderEntity OrderEntity);

    OrderEntity fromOrderUpdateDtoToEntity(OrderUpdateDto OrderUpdateDto);

}
