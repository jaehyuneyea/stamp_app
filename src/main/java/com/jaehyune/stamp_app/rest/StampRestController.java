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
    @PostMapping("/stamp")
    public Stamp addStamp(@RequestBody Stamp stamp) {
        stamp.setId(0); // because stamp adds when id = 0 and sets id automatically in db
        return stampService.save(stamp);
    }
    // update stamp
    @PutMapping("/stamp")
    public Stamp updateStamp(@RequestBody Stamp stamp) {
        return stampService.save(stamp);
    }

    // read stamp by id
    @GetMapping("/stamp/{id}")
    public Stamp findById(@PathVariable Integer id) {
        return stampService.findById(id);
    }

    // read all stamps
    @GetMapping("/stamp")
    public List<Stamp> findAll() {
        return stampService.findAll();
    }

    // delete stamp by id
    @DeleteMapping("/stamp/{id}")
    public void deleteStamp(@PathVariable Integer id) {
        stampService.delete(id);
    }
}
