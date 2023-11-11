package university.medicalrecordsdemo.controller.api;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.sickLeave.*;
import university.medicalrecordsdemo.service.sickLeave.SickLeaveService;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class SickLeaveApiController {
    private final SickLeaveService sickLeaveService;

    @GetMapping(value = "/api/SickLeaves")
    public Set<SickLeaveDto> getSickLeaves() {
        return sickLeaveService.findAll();
    }

    @GetMapping(value = "/api/SickLeave/{id}")
    public SickLeaveDto getSickLeave(@PathVariable("id") long id) {
        return sickLeaveService.findById(id);
    }

    @PostMapping(value = "/api/SickLeave")
    public SickLeaveDto createSickLeave(@RequestBody SickLeaveDto SickLeave) {
        return sickLeaveService.create(SickLeave);
    }

    @PutMapping(value = "/api/SickLeave/{id}")
    public SickLeaveDto updateSickLeave(@PathVariable("id") long id, @RequestBody UpdateSickLeaveDto SickLeave) {
        return sickLeaveService.update(id, SickLeave);
    }

    @DeleteMapping(value = "/api/SickLeave/{id}")
    public void deleteSickLeave(@PathVariable long id) {
        sickLeaveService.delete(id);
    }
}