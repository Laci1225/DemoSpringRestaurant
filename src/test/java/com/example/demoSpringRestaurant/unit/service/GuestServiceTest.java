package com.example.demoSpringRestaurant.unit.service;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import com.example.demoSpringRestaurant.fixtures.GuestFixture;
import com.example.demoSpringRestaurant.fixtures.GuestFixture;
import com.example.demoSpringRestaurant.mapper.GuestMapper;
import com.example.demoSpringRestaurant.model.GuestUpdateDto;
import com.example.demoSpringRestaurant.model.GuestCreationDto;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.document.GuestDocument;
import com.example.demoSpringRestaurant.persistance.repository.GuestRepository;
import com.example.demoSpringRestaurant.service.GuestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;


    @Mock
    private GuestMapper guestMapper;


    @Test
    void getGuestsShouldGetAllGuest() {
        when(guestMapper.fromDocumentToGuestDto(any(GuestDocument.class)))
                .thenReturn(GuestFixture.getGuestDto());
        when(guestRepository.findAll()).thenReturn(GuestFixture.getGuestDocumentList());

        //act / when
        var guestDtoList = guestService.getGuests();

        //assert / then
        assertThat(guestDtoList).usingRecursiveComparison().isEqualTo(GuestFixture.getGuestDtoList());
        verify(guestRepository, times(1)).findAll();
        verifyNoMoreInteractions(guestRepository);
    }

    @Test
    void getGuestShouldGetAGuest() {
        when(guestMapper.fromDocumentToGuestDto(any(GuestDocument.class)))
                .thenReturn(GuestFixture.getGuestDto());
        when(guestRepository.findById(anyString()))
                .thenReturn(Optional.ofNullable(GuestFixture.getGuestDocument(true)));

        //act / when
        var guestDto = guestService.getGuest("1234");

        //assert / then
        assertThat(guestDto).usingRecursiveComparison().isEqualTo(GuestFixture.getGuestDto());
        verify(guestRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(guestRepository);
    }

    @Test
    void updateGuestShouldUpdateOneGuest() throws DocumentNotFoundException {
        //arrange / given
        when(guestMapper.fromDocumentToGuestDto(any(GuestDocument.class)))
                .thenReturn(GuestFixture.getGuestDto());
        when(guestMapper.fromGuestUpdateDtoToDocument(any(GuestUpdateDto.class)))
                .thenReturn(GuestFixture.getGuestDocument(false));
        when(guestRepository.save(any(GuestDocument.class)))
                .thenReturn(GuestFixture.getGuestDocument(true));
        when(guestRepository.findById(anyString())).thenReturn(Optional.of(GuestFixture.getGuestDocument(true)));
        //act / when
        var guestUpdate = guestService.updateGuest("1L", GuestFixture.getGuestUpdateDto());

        //assert / then
        assertThat(guestUpdate).usingRecursiveComparison().isEqualTo(GuestFixture.getGuestDto());
        verify(guestRepository, times(1)).findById(anyString());
        verify(guestRepository, times(1)).save(any(GuestDocument.class));
        verifyNoMoreInteractions(guestRepository);
    }


}
