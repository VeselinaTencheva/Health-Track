package university.medicalrecordsdemo.controller.api;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.*;
import university.medicalrecordsdemo.service.physician.PhysicianService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class PhysicianApiController {
    private final PhysicianService physicianService;

    @GetMapping(value = "/api/physicians")
    public Set<PhysicianDto> getPhysicians() {
        return physicianService.findAll();
    }

    @GetMapping(value = "/api/physician/{id}")
    public PhysicianDto getPhysician(@PathVariable("id") long id) {
        return physicianService.findById(id);
    }

    @PostMapping(value = "/api/Physician")
    public PhysicianDto createPhysician(@RequestBody PhysicianDto Physician) {
        return physicianService.create(Physician);
    }

    @PutMapping(value = "/api/Physician/{id}")
    public PhysicianDto updatePhysician(@PathVariable("id") long id, @RequestBody PhysicianDto Physician) {
        return physicianService.update(id, Physician);
    }

    @DeleteMapping(value = "/api/Physician/{id}")
    public void deletePhysician(@PathVariable long id) {
        physicianService.delete(id);
    }
}