package io.oipunk.neighbory.parking.service;

import io.oipunk.neighbory.exception.ResourceNotFoundException;
import io.oipunk.neighbory.parking.dto.ParkingAssignRequest;
import io.oipunk.neighbory.parking.dto.ParkingSpaceResponse;
import io.oipunk.neighbory.parking.entity.ParkingSpace;
import io.oipunk.neighbory.parking.repository.ParkingSpaceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpaceResponse> list() {
        return parkingSpaceRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public ParkingSpaceResponse assign(Long id, ParkingAssignRequest request) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSpace", id));
        space.setOccupied(true);
        space.setOwnerName(normalize(request.ownerName()));
        return toResponse(parkingSpaceRepository.save(space));
    }

    @Transactional
    public ParkingSpaceResponse release(Long id) {
        ParkingSpace space = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ParkingSpace", id));
        space.setOccupied(false);
        space.setOwnerName(null);
        return toResponse(parkingSpaceRepository.save(space));
    }

    private ParkingSpaceResponse toResponse(ParkingSpace space) {
        return new ParkingSpaceResponse(space.getId(), space.getCode(), space.isOccupied(), space.getOwnerName());
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
