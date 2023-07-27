package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.service.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class RestaurantMapperTest {

    RestaurantMapper restaurantMapper= Mappers.getMapper(RestaurantMapper.class);

     @Test
     void fromRestaurantDtoToDocument(){
         var result = restaurantMapper
                 .fromRestaurantDtoToDocument(RestaurantFixture.getRestaurantDto());

         assertEquals(result, RestaurantFixture.getRestaurantDocument(true));
     }
    @Test
    void fromRestaurantDtoToDocumentReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantDtoToDocument(null);

        assertNull(result);
    }

    @Test
    void fromDocumentToRestaurantDto(){
        var result = restaurantMapper
                .fromDocumentToRestaurantDto(RestaurantFixture.getRestaurantDocument(true));

        assertEquals(result, RestaurantFixture.getRestaurantDto());
    }
    @Test
    void fromDocumentToRestaurantDtoReturnsNull(){
        var result = restaurantMapper
                .fromDocumentToRestaurantDto(null);

        assertNull(result);
    }

     @Test
     void fromDocumentToRestaurantCreationDto(){
         var result = restaurantMapper
                 .fromDocumentToRestaurantCreationDto(RestaurantFixture.getRestaurantDocument(false));

         assertEquals(result, RestaurantFixture.getRestaurantCreationDto());
     }
    @Test
    void fromDocumentToRestaurantCreationDtoReturnsNull(){
        var result = restaurantMapper
                .fromDocumentToRestaurantCreationDto(null);

        assertNull(result);
    }
     @Test
     void fromRestaurantCreationDtoToDocument(){
         var result = restaurantMapper
                 .fromRestaurantCreationDtoToDocument(RestaurantFixture.getRestaurantCreationDto());

         assertEquals(result, RestaurantFixture.getRestaurantDocument(false));
     }
    @Test
    void fromRestaurantCreationDtoToDocumentReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantCreationDtoToDocument(null);

        assertNull(result);
    }
     @Test
     void fromDocumentToRestaurantUpdateDto(){
         var result = restaurantMapper
                 .fromDocumentToRestaurantUpdateDto(RestaurantFixture.getRestaurantDocument(false));

         assertEquals(result, RestaurantFixture.getRestaurantUpdateDto());
     }
    @Test
    void fromDocumentToRestaurantUpdateDtoReturnsNull(){
        var result = restaurantMapper
                .fromDocumentToRestaurantUpdateDto(null);

        assertNull(result);
    }
     @Test
     void fromRestaurantUpdateDtoToDocument(){
         var result = restaurantMapper
                 .fromRestaurantUpdateDtoToDocument(RestaurantFixture.getRestaurantUpdateDto());

         assertEquals(result, RestaurantFixture.getRestaurantDocument(false));
     }
    @Test
    void fromRestaurantUpdateDtoToDocumentReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantUpdateDtoToDocument(null);

        assertNull(result);
    }
}
