package university.medicalrecordsdemo.controller.api;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.diagnosis.DiagnosisDto;
import university.medicalrecordsdemo.dto.diagnosis.UpdateDiagnosisDto;
import university.medicalrecordsdemo.service.diagnosis.DiagnosisService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class DiagnosisApiController {
    private final DiagnosisService diagnosisService;

    @GetMapping(value = "/api/diagnosis")
    public Set<DiagnosisDto> getDiagnosis() {
        return diagnosisService.findAll();
    }

    @GetMapping(value = "/api/diagnosis/{id}")
    public DiagnosisDto getDiagnosis(@PathVariable("id") long id) {
        return diagnosisService.findById(id);
    }

    @PostMapping(value = "/api/diagnosis")
    public DiagnosisDto createDiagnosis(@RequestBody DiagnosisDto diagnosis) {
        return diagnosisService.create(diagnosis);
    }

    @PutMapping(value = "/api/diagnosis/{id}")
    public DiagnosisDto updateDiagnosis(@PathVariable("id") long id, @RequestBody UpdateDiagnosisDto diagnosis) {
        return diagnosisService.update(id, diagnosis);
    }

    @DeleteMapping(value = "/api/diagnosis/{id}")
    public void deleteDiagnosis(@PathVariable long id) {
        diagnosisService.delete(id);
    }
}