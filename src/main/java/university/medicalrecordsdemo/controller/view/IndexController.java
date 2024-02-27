package university.medicalrecordsdemo.controller.view;

// import com.example.Medical.Records.v10.data.entity.DepartmentType;
// import com.example.Medical.Records.v10.data.entity.physicians.Physician;
// import com.example.Medical.Records.v10.data.view.model.physicians.CreatePhysicianAndGPViewModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.AllArgsConstructor;
import university.medicalrecordsdemo.dto.physician.PhysicianDto;
import university.medicalrecordsdemo.model.binding.physicians.CreatePhysicianViewModel;
import university.medicalrecordsdemo.model.entity.SpecialtyType;
import university.medicalrecordsdemo.service.physician.PhysicianService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {
    private PhysicianService physicianService;
    private ModelMapper modelMapper;

    @GetMapping
    public String getIndex(Model model) {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Get the logged user's first and last name

        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                email = userDetails.getUsername();
            }
        }
        
        // Add the first and last name to the model
        model.addAttribute("email", email);
        
        return "index";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear the authentication
        SecurityContextHolder.clearContext();

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("specialities", SpecialtyType.values());
        model.addAttribute("physician", new CreatePhysicianViewModel());

        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(Model model,@Valid @ModelAttribute("physician") CreatePhysicianViewModel physician,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialities", SpecialtyType.values());
            return "/register";
        }

        LocalDate birthDate = physician.getBirthDate() == null || physician.getBirthDate().isEmpty() ? null :
        LocalDate.parse(physician.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        
        // Map the view model to DTO
        PhysicianDto createPhysicianDTO = modelMapper.map(physician,
                PhysicianDto.class);
        createPhysicianDTO.setBirthDate(birthDate); // Set the converted birthDate to the DTO
        physicianService.create(createPhysicianDTO);
        return "redirect:/physicians";
    }

    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }
}