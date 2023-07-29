package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.service.CourierFixture;
import com.example.demoSpringRestaurant.mapper.service.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CourierMapperImpl.class, OrderWithoutReferencesMapperImpl.class, RestaurantWithoutOrderMapperImpl.class})
public class CourierMapperTest {

    @Autowired
    CourierMapper courierMapper;


    @Test
    void fromCourierDtoToDocument() {
        var result = courierMapper
                .fromCourierDtoToDocument(CourierFixture.getCourierDto());

        assertEquals(result, CourierFixture.getCourierDocument(true));
    }

    @Test
    void fromCourierDtoToDocumentReturnsNull() {
        var result = courierMapper
                .fromCourierDtoToDocument(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToCourierDto() {
        var result = courierMapper
                .fromDocumentToCourierDto(CourierFixture.getCourierDocument(true));

        assertEquals(result, CourierFixture.getCourierDto());
    }

    @Test
    void fromDocumentToCourierDtoReturnsNull() {
        var result = courierMapper
                .fromDocumentToCourierDto(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToCourierCreationDto() {
        var result = courierMapper
                .fromDocumentToCourierCreationDto(CourierFixture.getCourierDocument(false));

        assertEquals(result, CourierFixture.getCourierCreationDto());
    }

    @Test
    void fromDocumentToCourierCreationDtoReturnsNull() {
        var result = courierMapper
                .fromDocumentToCourierCreationDto(null);

        assertNull(result);
    }

    @Test
    void fromCourierCreationDtoToDocument() {
        var result = courierMapper
                .fromCourierCreationDtoToDocument(CourierFixture.getCourierCreationDto());

        assertEquals(result, CourierFixture.getCourierDocument(false));
    }

    @Test
    void fromCourierCreationDtoToDocumentReturnsNull() {
        var result = courierMapper
                .fromCourierCreationDtoToDocument(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToCourierUpdateDto() {
        var result = courierMapper
                .fromDocumentToCourierUpdateDto(CourierFixture.getCourierDocument(false));

        assertEquals(result, CourierFixture.getCourierUpdateDto());
    }

    @Test
    void fromDocumentToCourierUpdateDtoReturnsNull() {
        var result = courierMapper
                .fromDocumentToCourierUpdateDto(null);

        assertNull(result);
    }

    @Test
    void fromCourierUpdateDtoToDocument() {
        var result = courierMapper
                .fromCourierUpdateDtoToDocument(CourierFixture.getCourierUpdateDto());

        assertEquals(result, CourierFixture.getCourierDocument(false));
    }

    @Test
    void fromCourierUpdateDtoToDocumentReturnsNull() {
        var result = courierMapper
                .fromCourierUpdateDtoToDocument(null);

        assertNull(result);
    }
}
