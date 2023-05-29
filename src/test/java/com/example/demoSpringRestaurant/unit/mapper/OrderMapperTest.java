package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
public class OrderMapperTest {

    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    public void fromOrderDtoToEntity() {
        var result = orderMapper
                .fromOrderDtoToEntity(OrderFixture.getOrderDto());

        assertEquals(result, OrderFixture.getOrderEntity(true));
    }

    @Test
    public void fromEntityToOrderDto() {
        var result = orderMapper
                .fromEntityToOrderDto(OrderFixture.getOrderEntity(true));

        assertEquals(result, OrderFixture.getOrderDto());
    }

    @Test
    public void fromEntityToOrderCreationDto() {
        var result = orderMapper
                .fromEntityToOrderCreationDto(OrderFixture.getOrderEntity(false));

        assertEquals(result, OrderFixture.getOrderCreationDto());
    }

    @Test
    public void fromOrderCreationDtoToEntity() {
        var result = orderMapper
                .fromOrderCreationDtoToEntity(OrderFixture.getOrderCreationDto());

        assertEquals(result, OrderFixture.getOrderEntity(false));
    }

    @Test
    public void fromEntityToOrderUpdateDto() {
        var result = orderMapper
                .fromEntityToOrderUpdateDto(OrderFixture.getOrderEntity(false));

        assertEquals(result, OrderFixture.getorderUpdateDto());
    }

    @Test
    public void fromOrderUpdateDtoToEntity() {
        var result = orderMapper
                .fromOrderUpdateDtoToEntity(OrderFixture.getorderUpdateDto());

        assertEquals(result, OrderFixture.getOrderEntity(false));
    }
}
