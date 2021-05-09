package hu.bme.aut.freelancer_spring.service;

import hu.bme.aut.freelancer_spring.dto.TransferDto;
import hu.bme.aut.freelancer_spring.model.*;
import hu.bme.aut.freelancer_spring.model.Package;
import hu.bme.aut.freelancer_spring.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImpTest {

    @InjectMocks
    TransferServiceImp transferService;

    @Mock
    TransferRepository transferRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    TownRepository townRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    PackageRepository packageRepository;

    @Test
    void findAllTest() {
        // Arrange
        var transfers = Arrays.asList(new Transfer(), new Transfer(), new Transfer());
        when(transferRepository.findAll()).thenReturn(transfers);

        // Act
        var result = transferService.findAll();

        // Assert
        verify(transferRepository, times(1)).findAll();
        assertEquals(transfers, result);
    }

    @Test
    void saveSuccessTest() {
        // Arrange
        var carrier = new User();
        carrier.setId(1L);
        var town = new Town();
        var vehicle = new Vehicle();
        vehicle.setOwner(carrier);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(carrier));
        when(townRepository.findById(anyLong())).thenReturn(Optional.of(town));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(packageRepository.findByTransferIsNullAndTownAndDateLimitAfterOrderByCreatedAt(any(), any()))
                .thenReturn(new ArrayList<>());

        // Act
        transferService.save(mock(TransferDto.class));

        // Assert
        verify(transferRepository, times(1)).save(any());
    }

    @Test
    void saveNotFoundErrorTest() {
        // Arrange
        var carrier = new User();
        carrier.setId(1L);
        var town = new Town();
        var vehicle = new Vehicle();
        vehicle.setOwner(new User());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(carrier));
        when(townRepository.findById(anyLong())).thenReturn(Optional.of(town));
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            // Act
            transferService.save(mock(TransferDto.class));
        });

        verify(transferRepository, times(0)).save(any());
    }

    @Test
    void saveConflictErrorTest() {
        // Arrange

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(townRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            // Act
            transferService.save(mock(TransferDto.class));
        });

        verify(transferRepository, times(0)).save(any());
    }

    @Test
    void getPackagesTest() {
        // Arrange
        Transfer transfer = mock(Transfer.class);
        List<Package> expectedPackages = Arrays.asList(new Package(), new Package());

        when(transfer.getPackages()).thenReturn(expectedPackages);
        when(transferRepository.findById(anyLong())).thenReturn(Optional.of(transfer));

        // Act
        List<Package> result = transferService.getPackages(5L);

        // Assert
        assertEquals(2, result.size());
        assertEquals(expectedPackages, result);
        verify(transferRepository, times(1)).findById(anyLong());
    }

    @Test
    void getPackagesNotFoundErrorTest() {
        // Arrange
        when(transferRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            // Act
            transferService.getPackages(5L);
        });
    }
}

