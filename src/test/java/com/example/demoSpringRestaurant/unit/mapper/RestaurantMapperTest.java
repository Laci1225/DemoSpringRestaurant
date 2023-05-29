package com.example.demoSpringRestaurant.unit.mapper;

import com.example.demoSpringRestaurant.fixtures.RestaurantFixture;
import com.example.demoSpringRestaurant.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
     void fromEntityToRestaurantDto(){
         var result = restaurantMapper
                 .fromEntityToRestaurantDto(RestaurantFixture.getRestaurantEntity(true));

         assertEquals(result, RestaurantFixture.getRestaurantDto());
     }
     @Test
     void fromEntityToRestaurantCreationDto(){
         var result = restaurantMapper
                 .fromEntityToRestaurantCreationDto(RestaurantFixture.getRestaurantEntity(false));

         assertEquals(result, RestaurantFixture.getRestaurantCreationDto());
     }
     @Test
     void fromRestaurantCreationDtoToEntity(){
         var result = restaurantMapper
                 .fromRestaurantCreationDtoToEntity(RestaurantFixture.getRestaurantCreationDto());

         assertEquals(result, RestaurantFixture.getRestaurantEntity(false));
     }
     @Test
     void fromEntityToRestaurantUpdateDto(){
         var result = restaurantMapper
                 .fromEntityToRestaurantUpdateDto(RestaurantFixture.getRestaurantEntity(false));

         assertEquals(result, RestaurantFixture.getRestaurantUpdateDto());
     }
     @Test
     void fromRestaurantUpdateDtoToEntity(){
         var result = restaurantMapper
                 .fromRestaurantUpdateDtoToEntity(RestaurantFixture.getRestaurantUpdateDto());

         assertEquals(result, RestaurantFixture.getRestaurantEntity(false));
     }
}
