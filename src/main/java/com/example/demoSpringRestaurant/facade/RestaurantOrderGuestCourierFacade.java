package com.example.demoSpringRestaurant.facade;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.mapper.CourierMapper;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.mapper.OrderMapper;
import com.example.demoSpringRestaurant.model.OrderCreationDto;
import com.example.demoSpringRestaurant.model.OrderDto;
import com.example.demoSpringRestaurant.service.CourierService;
import com.example.demoSpringRestaurant.service.GuestService;
import com.example.demoSpringRestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantOrderGuestCourierFacade {
    private final CourierService courierService;
    private final CourierMapper courierMapper;
    private final RestaurantService restaurantService;
    private final GuestService guestService;
    private final GuestMapper guestMapper;
    private final OrderMapper orderMapper;

    public OrderDto createOrder(OrderCreationDto orderCreationDto,
                                String restaurantId) throws DocumentNotFoundException {
        log.trace("Creating order " + orderCreationDto + "with Restaurant ID: " + restaurantId);

        var restaurantDto = restaurantService.findRestaurantById(restaurantId);
        orderCreationDto.setRestaurant(restaurantDto);
        var estimated = LocalDateTime.now().toLocalTime().plusMinutes(30);
        orderCreationDto.setEstimatedPreparationTime(estimated);

        //CourierDocument courier = null;
        //if (orderCreationDto.getCourierId() != null)
        var courier = courierService.findCourierById(orderCreationDto.getCourierId());
        var courierDocument = courierMapper.fromCourierDtoToDocument(courier);
        //GuestDocument guest = null;
        //if (orderCreationDto.getGuestId() != null)
        var guest = guestService.findGuestById(orderCreationDto.getGuestId());
        var guestDocument = guestMapper.fromGuestDtoToDocument(guest);

        //;

        log.trace("a  :" + courier);
        var orderDto = orderMapper.fromOrderCreationDtoToDto(orderCreationDto
                , courier, guest);
        var orderDocument = orderMapper.fromOrderDtoToDocument(orderDto);
        log.trace("a  :" + orderCreationDto);


        //if (orderDocument.isDelivery())
        orderDocument.setCourierDocument(courierDocument);
        orderDocument.setGuestDocument(guestDocument);
       // var orderDto = orderService.saveOrder(orderDocument);

       // log.trace("Order" + orderDto + " with Restaurant ID: " + orderDto.getId() + " created");
        return orderMapper.fromDocumentToOrderDto(orderDocument);
    }
}
