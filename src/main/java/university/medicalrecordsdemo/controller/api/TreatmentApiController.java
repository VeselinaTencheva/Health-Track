package university.medicalrecordsdemo.controller.api;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.treatment.*;
import university.medicalrecordsdemo.service.treatment.TreatmentService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class TreatmentApiController {
    private final TreatmentService treatmentService;

    @GetMapping(value = "/api/Treatments")
    public Set<TreatmentDto> getTreatments() {
        return treatmentService.findAll();
    }

    @GetMapping(value = "/api/Treatment/{id}")
    public TreatmentDto getTreatment(@PathVariable("id") long id) {
        return treatmentService.findById(id);
    }

    @PostMapping(value = "/api/Treatment")
    public TreatmentDto createTreatment(@RequestBody CreateTreatmentDto Treatment) {
        return treatmentService.create(Treatment);
    }

    @PutMapping(value = "/api/Treatment/{id}")
    public TreatmentDto updateTreatment(@PathVariable("id") long id, @RequestBody UpdateTreatmentDto Treatment) {
        return treatmentService.update(id, Treatment);
    }

    @DeleteMapping(value = "/api/Treatment/{id}")
    public void deleteTreatment(@PathVariable long id) {
        treatmentService.delete(id);
    }
}