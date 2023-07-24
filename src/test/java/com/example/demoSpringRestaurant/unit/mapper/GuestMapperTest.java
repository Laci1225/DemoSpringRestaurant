package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.GuestFixture;
import com.example.demoSpringRestaurant.mapper.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {GuestMapperImpl.class,OrderMapperImpl.class, RestaurantWithoutOrderMapperImpl.class})
public class GuestMapperTest {

    @Autowired
    GuestMapper guestMapper;


    @Test
    void fromGuestDtoToDocument() {
        var result = guestMapper
                .fromGuestDtoToDocument(GuestFixture.getGuestDto());

        assertEquals(result, GuestFixture.getGuestDocument(true));
    }

    @Test
    void fromGuestDtoToDocumentReturnsNull() {
        var result = guestMapper
                .fromGuestDtoToDocument(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToGuestDto() {
        var result = guestMapper
                .fromDocumentToGuestDto(GuestFixture.getGuestDocument(true));

        assertEquals(result, GuestFixture.getGuestDto());
    }

    @Test
    void fromDocumentToGuestDtoReturnsNull() {
        var result = guestMapper
                .fromDocumentToGuestDto(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToGuestCreationDto() {
        var result = guestMapper
                .fromDocumentToGuestCreationDto(GuestFixture.getGuestDocument(false));

        assertEquals(result, GuestFixture.getGuestCreationDto());
    }

    @Test
    void fromDocumentToGuestCreationDtoReturnsNull() {
        var result = guestMapper
                .fromDocumentToGuestCreationDto(null);

        assertNull(result);
    }

    @Test
    void fromGuestCreationDtoToDocument() {
        var result = guestMapper
                .fromGuestCreationDtoToDocument(GuestFixture.getGuestCreationDto());

        assertEquals(result, GuestFixture.getGuestDocument(false));
    }

    @Test
    void fromGuestCreationDtoToDocumentReturnsNull() {
        var result = guestMapper
                .fromGuestCreationDtoToDocument(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToGuestUpdateDto() {
        var result = guestMapper
                .fromDocumentToGuestUpdateDto(GuestFixture.getGuestDocument(false));

        assertEquals(result, GuestFixture.getGuestUpdateDto());
    }

    @Test
    void fromDocumentToGuestUpdateDtoReturnsNull() {
        var result = guestMapper
                .fromDocumentToGuestUpdateDto(null);

        assertNull(result);
    }

    @Test
    void fromGuestUpdateDtoToDocument() {
        var result = guestMapper
                .fromGuestUpdateDtoToDocument(GuestFixture.getGuestUpdateDto());

        assertEquals(result, GuestFixture.getGuestDocument(false));
    }

    @Test
    void fromGuestUpdateDtoToDocumentReturnsNull() {
        var result = guestMapper
                .fromGuestUpdateDtoToDocument(null);

        assertNull(result);
    }
}
