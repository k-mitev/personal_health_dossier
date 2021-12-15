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
import softuni.com.personal_health_dossier.service.*;

import java.time.LocalDate;

@Controller
public class HomeController {
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final PrescriptionService prescriptionService;
    private final ModelMapper modelMapper;

    public HomeController(PatientService patientService, PhysicianService physicianService,
                          PrescriptionService prescriptionService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.physicianService = physicianService;
        this.prescriptionService = prescriptionService;
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
            Long physicianId = physicianViewModel.getId();


            int totalPrescriptions =
                    this.prescriptionService
                            .findTotalPrescriptionsIssuedForTheCurrentDay(physicianId, LocalDate.now());
            model.addAttribute("physicianViewModel", physicianViewModel);
            model.addAttribute("totalPrescriptions", totalPrescriptions);

            return "physician-home";
        }

        return "index";
    }
}
