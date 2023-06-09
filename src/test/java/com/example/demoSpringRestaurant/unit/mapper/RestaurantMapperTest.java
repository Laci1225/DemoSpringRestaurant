package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
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
     void fromRestaurantDtoToEntity(){
         var result = restaurantMapper
                 .fromRestaurantDtoToEntity(RestaurantFixture.getRestaurantDto());

         assertEquals(result, RestaurantFixture.getRestaurantEntity(true));
     }
    @Test
    void fromRestaurantDtoToEntityReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantDtoToEntity(null);

        assertNull(result);
    }

    @Test
    void fromEntityToRestaurantDto(){
        var result = restaurantMapper
                .fromEntityToRestaurantDto(RestaurantFixture.getRestaurantEntity(true));

        assertEquals(result, RestaurantFixture.getRestaurantDto());
    }
    @Test
    void fromEntityToRestaurantDtoReturnsNull(){
        var result = restaurantMapper
                .fromEntityToRestaurantDto(null);

        assertNull(result);
    }

     @Test
     void fromEntityToRestaurantCreationDto(){
         var result = restaurantMapper
                 .fromEntityToRestaurantCreationDto(RestaurantFixture.getRestaurantEntity(false));

         assertEquals(result, RestaurantFixture.getRestaurantCreationDto());
     }
    @Test
    void fromEntityToRestaurantCreationDtoReturnsNull(){
        var result = restaurantMapper
                .fromEntityToRestaurantCreationDto(null);

        assertNull(result);
    }
     @Test
     void fromRestaurantCreationDtoToEntity(){
         var result = restaurantMapper
                 .fromRestaurantCreationDtoToEntity(RestaurantFixture.getRestaurantCreationDto());

         assertEquals(result, RestaurantFixture.getRestaurantEntity(false));
     }
    @Test
    void fromRestaurantCreationDtoToEntityReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantCreationDtoToEntity(null);

        assertNull(result);
    }
     @Test
     void fromEntityToRestaurantUpdateDto(){
         var result = restaurantMapper
                 .fromEntityToRestaurantUpdateDto(RestaurantFixture.getRestaurantEntity(false));

         assertEquals(result, RestaurantFixture.getRestaurantUpdateDto());
     }
    @Test
    void fromEntityToRestaurantUpdateDtoReturnsNull(){
        var result = restaurantMapper
                .fromEntityToRestaurantUpdateDto(null);

        assertNull(result);
    }
     @Test
     void fromRestaurantUpdateDtoToEntity(){
         var result = restaurantMapper
                 .fromRestaurantUpdateDtoToEntity(RestaurantFixture.getRestaurantUpdateDto());

         assertEquals(result, RestaurantFixture.getRestaurantEntity(false));
     }
    @Test
    void fromRestaurantUpdateDtoToEntityReturnsNull(){
        var result = restaurantMapper
                .fromRestaurantUpdateDtoToEntity(null);

        assertNull(result);
    }
}
