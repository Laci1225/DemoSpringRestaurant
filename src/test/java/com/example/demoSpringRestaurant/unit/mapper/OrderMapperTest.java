package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.OrderFixture;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapperImpl;
import com.example.demoSpringRestaurant.mapper.RestaurantWithoutOrderMapperImpl;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderMapperImpl.class, RestaurantWithoutOrderMapperImpl.class})
public class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;// = Mappers.getMapper(OrderMapper.class);

    @Test
    public void fromOrderDtoToDocument() {
        var result = orderMapper
                .fromOrderDtoToDocument(OrderFixture.getOrderDto());

        assertEquals(result, OrderFixture.getOrderDocument(true));
    }

    @Test
    public void fromOrderDtoToDocumentReturnsNull() {
        var result = orderMapper
                .fromOrderDtoToDocument(null);

        assertNull(result);
    }
    @Test
    public void fromDocumentToOrderDto() {
        var result = orderMapper
                .fromDocumentToOrderDto(OrderFixture.getOrderDocument(true));

        assertEquals(result, OrderFixture.getOrderDto());
    }

    @Test
    public void fromDocumentToOrderDtoReturnsNull() {
        var result = orderMapper
                .fromDocumentToOrderDto(null);

        assertNull(result);
    }
    @Test
    public void fromDocumentToOrderCreationDto() {
        var result = orderMapper
                .fromDocumentToOrderCreationDto(OrderFixture.getOrderDocument(false));

        assertEquals(result, OrderFixture.getOrderCreationDto());
    }
    @Test
    public void fromDocumentToOrderCreationDtoReturnsNull() {
        var result = orderMapper
                .fromDocumentToOrderCreationDto(null);

        assertNull(result);
    }

    @Test
    public void fromOrderCreationDtoToDocument() {
        var result = orderMapper
                .fromOrderCreationDtoToDto(OrderFixture.getOrderCreationDto(),
                        OrderFixture.getOrderDto().getCourierDto(),
                        OrderFixture.getOrderDto().getGuestDto());

        assertEquals(result, OrderFixture.getOrderDtoWithoutId() );
    }
    @Test
    public void fromOrderCreationDtoToDocumentReturnsNull() {
        var result = orderMapper
                .fromOrderCreationDtoToDto((OrderCreationDto) null,null,null);

        assertNull(result);
    }

    @Test
    public void fromDocumentToOrderUpdateDto() {
        var result = orderMapper
                .fromDocumentToOrderUpdateDto(OrderFixture.getOrderDocument(false));

        assertEquals(result, OrderFixture.getorderUpdateDto());
    }
    @Test
    public void fromDocumentToOrderUpdateDtoReturnsNull() {
        var result = orderMapper
                .fromDocumentToOrderUpdateDto(null);

        assertNull(result);
    }

    @Test
    public void fromOrderUpdateDtoToDocument() {
        var result = orderMapper
                .fromOrderUpdateDtoToDocument(OrderFixture.getorderUpdateDto());

        assertEquals(result, OrderFixture.getOrderDocument(false));
    }
    @Test
    public void fromOrderUpdateDtoToDocumentReturnsNull() {
        var result = orderMapper
                .fromOrderUpdateDtoToDocument(null);

        assertNull(result);
    }
}
