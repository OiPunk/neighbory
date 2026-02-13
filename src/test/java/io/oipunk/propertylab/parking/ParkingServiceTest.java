package io.oipunk.propertylab.parking;

import io.oipunk.propertylab.exception.ResourceNotFoundException;
import io.oipunk.propertylab.parking.dto.ParkingAssignRequest;
import io.oipunk.propertylab.parking.entity.ParkingSpace;
import io.oipunk.propertylab.parking.repository.ParkingSpaceRepository;
import io.oipunk.propertylab.parking.service.ParkingService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        parkingService = new ParkingService(parkingSpaceRepository);
    }

    @Test
    void listShouldReturnAllSpaces() {
        when(parkingSpaceRepository.findAll()).thenReturn(List.of(space(1L, false), space(2L, true)));
        assertThat(parkingService.list()).hasSize(2);
    }

    @Test
    void assignShouldSetOccupiedAndOwner() {
        ParkingSpace space = space(1L, false);
        when(parkingSpaceRepository.findById(1L)).thenReturn(Optional.of(space));
        when(parkingSpaceRepository.save(space)).thenReturn(space);

        var response = parkingService.assign(1L, new ParkingAssignRequest("  张三  "));

        assertThat(response.occupied()).isTrue();
        assertThat(response.ownerName()).isEqualTo("张三");
    }

    @Test
    void releaseShouldClearOwner() {
        ParkingSpace space = space(1L, true);
        space.setOwnerName("张三");
        when(parkingSpaceRepository.findById(1L)).thenReturn(Optional.of(space));
        when(parkingSpaceRepository.save(space)).thenReturn(space);

        var response = parkingService.release(1L);

        assertThat(response.occupied()).isFalse();
        assertThat(response.ownerName()).isNull();
    }

    @Test
    void shouldThrowWhenSpaceMissing() {
        when(parkingSpaceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> parkingService.assign(1L, new ParkingAssignRequest("a")))
                .isInstanceOf(ResourceNotFoundException.class);
        assertThatThrownBy(() -> parkingService.release(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void assignShouldHandleBlankOwnerName() {
        ParkingSpace space = space(1L, false);
        when(parkingSpaceRepository.findById(1L)).thenReturn(Optional.of(space));
        when(parkingSpaceRepository.save(space)).thenReturn(space);

        var response = parkingService.assign(1L, new ParkingAssignRequest("   "));

        assertThat(response.ownerName()).isNull();
    }

    @Test
    void assignShouldHandleNullOwnerName() {
        ParkingSpace space = space(1L, false);
        when(parkingSpaceRepository.findById(1L)).thenReturn(Optional.of(space));
        when(parkingSpaceRepository.save(space)).thenReturn(space);

        var response = parkingService.assign(1L, new ParkingAssignRequest(null));

        assertThat(response.ownerName()).isNull();
    }

    private ParkingSpace space(Long id, boolean occupied) {
        ParkingSpace space = new ParkingSpace();
        space.setId(id);
        space.setCode("P-001");
        space.setOccupied(occupied);
        return space;
    }
}
