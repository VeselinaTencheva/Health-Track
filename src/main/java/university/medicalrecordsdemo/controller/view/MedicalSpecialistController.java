//package com.example.Medical.Records.v10.controller.view;
//
//import com.example.Medical.Records.v10.data.entity.DepartmentType;
//import com.example.Medical.Records.v10.data.entity.physicians.GeneralPractitioner;
//import com.example.Medical.Records.v10.data.entity.physicians.MedicalSpecialist;
//import com.example.Medical.Records.v10.service.generalPractitioner.GeneralPractitionerService;
//import com.example.Medical.Records.v10.service.medicalSpecialist.MedicalSpecialistService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@AllArgsConstructor
//@RequestMapping("/medical-specialists")
//public class MedicalSpecialistController {
//    private MedicalSpecialistService medicalSpecialistService;
//
//    @GetMapping
//    public String getMedicalSpecialists(Model model) {
//        final List<MedicalSpecialist> medicalSpecialists = medicalSpecialistService.findAll();
//        model.addAttribute("ms", medicalSpecialists);
//        return "medical-specialist/all";
//    }
//
//    @GetMapping("/create")
//    public String createMedicalSpecialist(Model \model) {
//        model.addAttribute("departments", DepartmentType.values());
//        model.addAttribute("ms", new MedicalSpecialist());
//        return "/medical-specialist/create";
//    }
//
//    @PostMapping("/create")
//    public String createMedicalSpecialist(@ModelAttribute MedicalSpecialist medicalSpecialist) {
//        medicalSpecialistService.create(medicalSpecialist);
//        return "redirect:/all";
//    }
//
//    @GetMapping("/update/{id}")
//    public String editMedicalSpecialist(Model model, @PathVariable Long id) {
//        model.addAttribute("ms", medicalSpecialistService.findById(id));
//        model.addAttribute("departments", DepartmentType.values());
//        return "/medical-specialist/edit";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateMedicalSpecialist(Model model, @PathVariable long id, MedicalSpecialist medicalSpecialist) {
//        medicalSpecialistService.update(id, medicalSpecialist);
//        return "redirect:/all";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteMedicalSpecialist(@PathVariable long id) {
//        medicalSpecialistService.delete(id);
//        return "redirect:/all";
//    }
//}
