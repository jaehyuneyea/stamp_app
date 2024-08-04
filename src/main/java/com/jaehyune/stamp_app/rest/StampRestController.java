package com.jaehyune.stamp_app.rest;

import com.jaehyune.stamp_app.entity.Stamp;
import com.jaehyune.stamp_app.service.StampService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StampRestController {

    private StampService stampService;

    public StampRestController(StampService stampService) {
        this.stampService = stampService;
    }

    // add stamp
    @PostMapping("/stamps")
    public Stamp addStamp(@RequestBody Stamp stamp) {
        stamp.setId(0); // because stamp adds when id = 0 and sets id automatically in db
        return stampService.save(stamp);
    }
    // update stamp
    @PutMapping("/stamps")
    public Stamp updateStamp(@RequestBody Stamp stamp) {
        return stampService.save(stamp);
    }

    // read stamp by id
    @GetMapping("/stamps/{id}")
    public Stamp findById(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        return stampService.findById(id);
    }

    // read all stamps
    @GetMapping("/stamps")
    public List<Stamp> findAll() {
        return stampService.findAll();
    }

    // delete stamp by id
    @DeleteMapping("/stamps/{id}")
    public void deleteStamp(@PathVariable Integer id) {
        if (id < 0) {
            throw new IdNotFoundException("Invalid ID: " + id);
        }
        stampService.delete(id);
    }
}
