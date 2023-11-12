package university.medicalrecordsdemo.controller.view;

// import com.example.Medical.Records.v10.data.entity.DepartmentType;
// import com.example.Medical.Records.v10.data.entity.physicians.Physician;
// import com.example.Medical.Records.v10.data.view.model.physicians.CreatePhysicianAndGPViewModel;
import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.binding.physicians.CreatePhysicianViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.physician.PhysicianService;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {
    private PhysicianService physicianService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("logout")
    public String logout(Model model) {
        return "login";
    }

    // TODO do the register, add view models
    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(Model model) {

        model.addAttribute("specialities", SpecialtyType.values());
        model.addAttribute("physician", new CreatePhysicianViewModel());

        return "register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String registerConfirm(CreatePhysicianViewModel model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Add error messages to the model for display
            return "register";
        }

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            // Use a validation annotation to handle password matching
            bindingResult.rejectValue("confirmPassword", "PasswordMismatch", "The passwords do not match");
            return "register";
        }

        // Create physician if validation passes
        this.physicianService.create(this.modelMapper.map(model, PhysicianDto.class));

        return "login";
    }

    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }
}