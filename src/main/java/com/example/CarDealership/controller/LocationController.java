package com.example.CarDealership.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CarDealership.model.Location;
import com.example.CarDealership.service.LocationService;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // POST — Save any location level
    // Usage: POST /api/locations/add
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        String response = locationService.saveLocation(location);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All locations
    // Usage: GET /api/locations/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        if (locations.isEmpty()) {
            return new ResponseEntity<>("No locations found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    // GET — All provinces only
    // Usage: GET /api/locations/provinces
    @GetMapping(value = "/provinces", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProvinces() {
        List<Location> provinces = locationService.getAllProvinces();
        if (provinces.isEmpty()) {
            return new ResponseEntity<>("No provinces found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provinces, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/locations/getById?id=uuid
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocationById(@RequestParam String id) {
        return locationService.getLocationById(id)
            .map(location -> new ResponseEntity<Object>(location, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Location not found.", HttpStatus.NOT_FOUND));
    }

    // GET — Children of a location
    // Usage: GET /api/locations/children?parentId=uuid
    @GetMapping(value = "/children", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChildren(@RequestParam String parentId) {
        List<Location> children = locationService.getChildren(parentId);
        if (children.isEmpty()) {
            return new ResponseEntity<>("No children found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    // GET — By name
    // Usage: GET /api/locations/search?name=Kigali
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByName(@RequestParam String name) {
        List<Location> locations = locationService.getByName(name);
        if (locations.isEmpty()) {
            return new ResponseEntity<>("No locations found for: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/locations/update?id=uuid
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateLocation(@RequestParam String id, @RequestBody Location location) {
        String response = locationService.updateLocation(id, location);
        if (response.equals("Location updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/locations/delete?id=uuid
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteLocation(@RequestParam String id) {
        String response = locationService.deleteLocation(id);
        if (response.equals("Location deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}