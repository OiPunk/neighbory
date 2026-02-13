package io.oipunk.propertylab.parking.controller;

import io.oipunk.propertylab.common.ApiResponse;
import io.oipunk.propertylab.common.LocaleMessageService;
import io.oipunk.propertylab.parking.dto.ParkingAssignRequest;
import io.oipunk.propertylab.parking.dto.ParkingSpaceResponse;
import io.oipunk.propertylab.parking.service.ParkingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parking/spaces")
public class ParkingController {

    private final ParkingService parkingService;
    private final LocaleMessageService messageService;

    public ParkingController(ParkingService parkingService, LocaleMessageService messageService) {
        this.parkingService = parkingService;
        this.messageService = messageService;
    }

    @GetMapping
    public ApiResponse<List<ParkingSpaceResponse>> list() {
        return ApiResponse.of(messageService.get("parking.list.success"), parkingService.list());
    }

    @PatchMapping("/{id}/assign")
    public ApiResponse<ParkingSpaceResponse> assign(@PathVariable Long id,
                                                    @Valid @RequestBody ParkingAssignRequest request) {
        ParkingSpaceResponse response = parkingService.assign(id, request);
        return ApiResponse.of(messageService.get("parking.assign.success", id), response);
    }

    @PatchMapping("/{id}/release")
    public ApiResponse<ParkingSpaceResponse> release(@PathVariable Long id) {
        ParkingSpaceResponse response = parkingService.release(id);
        return ApiResponse.of(messageService.get("parking.release.success", id), response);
    }
}
