package university.medicalrecordsdemo.controller.view;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("email")
    public String userEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the principal is a UserDetails object
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); // Or getEmail(), depending on your UserDetails implementation
            }
        }
        return "Guest";
    }
}
