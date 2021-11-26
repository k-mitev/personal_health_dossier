package softuni.com.personal_health_dossier.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.com.personal_health_dossier.model.views.PatientViewModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

@Controller
public class HomeController {
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final ModelMapper modelMapper;

    public HomeController(PatientService patientService, PhysicianService physicianService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.physicianService = physicianService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails principal, Model model) {

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
            PatientViewModel patientViewModel = this.patientService
                    .findPatientByUsername(principal.getUsername())
                    .map(entity -> modelMapper.map(entity, PatientViewModel.class))
                    .orElse(null);

            model.addAttribute("patientViewModel", patientViewModel);

            return "patient-home";
        }

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHYSICIAN"))) {
            PhysicianViewModel physicianViewModel = this.physicianService
                    .findPhysicianByUsername(principal.getUsername())
                    .map(entity -> modelMapper.map(entity, PhysicianViewModel.class))
                    .orElse(null);

            model.addAttribute("physicianViewModel", physicianViewModel);

            return "physician-home";
        }

        return "index";
    }
}
