package university.medicalrecordsdemo.controller.api;

import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.appointment.*;
import university.medicalrecordsdemo.service.appointment.AppointmentService;

@RestController
@AllArgsConstructor
public class AppointmentApiController {
    private final AppointmentService appointmentService;

    @GetMapping(value = "/api/appointments")
    public Set<AppointmentDto> getAppointments() {
        return appointmentService.findAll();
    }

    @GetMapping(value = "/api/appointments/{id}")
    public AppointmentDto getAppointment(@PathVariable("id") long id) {
        return appointmentService.findById(id);
    }

    @PostMapping(value = "/api/appointments")
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointment) {
        return appointmentService.create(appointment);
    }

    @PutMapping(value = "/api/appointments/{id}")
    public AppointmentDto updateAppointment(@PathVariable("id") long id,
            @RequestBody UpdateAppointmentDto appointment) {
        return appointmentService.update(id, appointment);
    }

    @DeleteMapping(value = "/api/appointments/{id}")
    public void deleteAppointment(@PathVariable long id) {
        appointmentService.delete(id);
    }
}