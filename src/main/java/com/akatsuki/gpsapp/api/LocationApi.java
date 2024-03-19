package com.akatsuki.gpsapp.api;

import com.akatsuki.gpsapp.exceptions.CustomizedException;
import com.akatsuki.gpsapp.models.dto.request.LocationRequestDto;
import com.akatsuki.gpsapp.models.dto.response.LocationResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.akatsuki.gpsapp.models.dto.response.TagResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping(path = "",
        consumes = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.MULTIPART_FORM_DATA_VALUE})
public interface LocationApi {

    @GetMapping("/locations")
    List<LocationResponseDto> getAllLocations();

    @GetMapping("/locations/{id} ")
    ResponseEntity<LocationResponseDto> getLocation(@PathVariable long id);

    @PostMapping("/locations")
    ResponseEntity<LocationResponseDto> createLocation(@ModelAttribute LocationRequestDto
                                                               locationRequestDto) throws CustomizedException;

    @PutMapping("/locations/{id}")
    ResponseEntity<String> updateLocation(@PathVariable long id,
                                          @RequestBody LocationRequestDto locationRequestDto)
            throws CustomizedException;

   @DeleteMapping("/locations/{id}")
   ResponseEntity<Void> deleteLocation(@PathVariable long id);

    @GetMapping(path = "/tags/{tagId}")
    public ResponseEntity<List<LocationResponseDto>> getLocationForTag(
            @PathVariable(name = "tagId", required = true) Long tagId
    ) throws CustomizedException;

    @GetMapping(path = "/tags")
    public ResponseEntity<List<TagResponseDto>> getTags() throws CustomizedException;


    @GetMapping(path = "/tags/ownedTags")
    public ResponseEntity<List<TagResponseDto>> getOwnedTags() throws CustomizedException;

    @GetMapping(path = "/tags/sharedTags")
    public ResponseEntity<List<TagResponseDto>> getSharedTags() throws CustomizedException;

}
