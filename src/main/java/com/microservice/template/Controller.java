package com.microservice.template;

import org.codehaus.plexus.resource.loader.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class Controller {
    static Logger log = Logger.getLogger(Controller.class.getName());

    List<Thing> things = new ArrayList<>();

    @GetMapping("/get") //get all things
    public ResponseEntity<List<Thing>> getAllRequest() {
        log.info("getAllRequest");

        return ResponseEntity.ok(things);
    }

    @GetMapping("/get/{id}") //get thing by id
    public ResponseEntity<Thing> getByIdRequest(@PathVariable("id") String id) {
        log.info("getRequestById");

        int getId = Integer.parseInt(id);

        if(getId < things.size() && getId >= 0) {
            return ResponseEntity.ok(things.get(getId));
        }
        return new ResponseEntity<Thing>((Thing) null, HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping("/put/{id}") //update thing by id
    public ResponseEntity<Thing> putRequest(@PathVariable("id") String id,
                                            @Valid @RequestBody Thing entity) throws ResourceNotFoundException {
        log.info("putRequest");

        int putId = Integer.parseInt(id);

        if(putId < things.size() && putId >= 0) {
            Thing updatedThing = things.get(putId);

            if (entity.getId() != null) {
                updatedThing.setId(entity.getId());
            }
            if (entity.getValue1() != null) {
                updatedThing.setValue1(entity.getValue1());
            }
            if (entity.getIsDeleted() != null) {
                updatedThing.setIsDeleted(entity.getIsDeleted());
            }
            return ResponseEntity.ok(updatedThing);
        }
        else {
            return new ResponseEntity<Thing>((Thing) null, HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/post") //create thing
    public ResponseEntity<Thing> postRequest(@Valid @RequestBody Thing entity) {
        log.info("postRequest");

        things.add(entity);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/delete/{id}") //delete thing by id
    public ResponseEntity<Thing> deleteRequest(@PathVariable("id") String id) throws ResourceNotFoundException {
        log.info("deleteRequest");

        int delId = Integer.parseInt(id);

        if(delId < things.size() && delId >= 0) {
            Thing deletedThing = things.get(delId);
            deletedThing.setIsDeleted(true);

            return ResponseEntity.ok(deletedThing);
        }
        else {
            return new ResponseEntity<Thing>((Thing) null, HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
