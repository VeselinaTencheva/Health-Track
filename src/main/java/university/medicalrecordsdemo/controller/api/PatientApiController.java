package university.medicalrecordsdemo.controller.api;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.patient.*;
import university.medicalrecordsdemo.service.patient.PatientService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class PatientApiController {
    private final PatientService patientService;

    @GetMapping(value = "/api/Patient")
    public Set<PatientDto> getPatient() {
        return patientService.findAll();
    }

    @GetMapping(value = "/api/Patient/{id}")
    public PatientDto getPatient(@PathVariable("id") long id) {
        return patientService.findById(id);
    }

    @PostMapping(value = "/api/Patient")
    public PatientDto createPatient(@RequestBody PatientDto Patient) {
        return patientService.create(Patient);
    }

    @PutMapping(value = "/api/Patient/{id}")
    public PatientDto updatePatient(@PathVariable("id") long id, @RequestBody UpdatePatientDto Patient) {
        return patientService.update(id, Patient);
    }

    @DeleteMapping(value = "/api/Patient/{id}")
    public void deletePatient(@PathVariable long id) {
        patientService.delete(id);
    }
}