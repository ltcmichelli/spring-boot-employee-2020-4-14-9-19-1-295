package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    private ParkingBoyService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ParkingBoy>> getParkingBoyList() {
        try {
            List<ParkingBoy> resultParkingBoyList = service.getAllList();
            return new ResponseEntity<>(resultParkingBoyList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ParkingBoy> addParkingBoy(@RequestBody ParkingBoy newParkingBoy) {
        try {
            ParkingBoy parkingBoy = service.addParkingBoy(newParkingBoy);
            return new ResponseEntity<>(parkingBoy, HttpStatus.CREATED);

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
