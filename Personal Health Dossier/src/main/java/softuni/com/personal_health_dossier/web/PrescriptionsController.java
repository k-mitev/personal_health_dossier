package softuni.com.personal_health_dossier.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.com.personal_health_dossier.model.bindings.PrescriptionAddBindingModel;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.services.PrescriptionAddServiceModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;
import softuni.com.personal_health_dossier.service.PrescriptionService;

import javax.validation.Valid;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionsController {
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final PrescriptionService prescriptionService;
    private final ModelMapper modelMapper;

    public PrescriptionsController(PhysicianService physicianService, PatientService patientService, PrescriptionService prescriptionService, ModelMapper modelMapper) {
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addPrescription(Model model, @AuthenticationPrincipal UserDetails principal) {
        if (!model.containsAttribute("physicianViewModel")) {
            PhysicianViewModel physicianViewModel =
                    this.physicianService
                            .findPhysicianByUsername(principal.getUsername())
                            .map(doc -> modelMapper.map(doc, PhysicianViewModel.class))
                            .orElseThrow(() -> new UsernameNotFoundException("Physician with username " + principal.getUsername() + " cannot be found."));

            model.addAttribute("physicianViewModel", physicianViewModel);
        }
        if (!model.containsAttribute("prescriptionAddBindingModel")) {
            model.addAttribute("prescriptionAddBindingModel", new PrescriptionAddBindingModel());
        }

        if (!model.containsAttribute("patientDontExist")) {
            model.addAttribute("patientDontExist", false);
        }
        return "add-prescription";
    }

    @PostMapping("/add")
    public String addPrescriptionConfirm(@Valid PrescriptionAddBindingModel prescriptionAddBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes,
                                         @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("prescriptionAddBindingModel", prescriptionAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.prescriptionAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }

        String patientPin = prescriptionAddBindingModel.getPatientPin();
        PatientEntity patientEntity = this.patientService.findPatientByPIN(patientPin).orElse(null);
        if (patientEntity == null) {
            redirectAttributes.addFlashAttribute("prescriptionAddBindingModel", prescriptionAddBindingModel);
            redirectAttributes.addFlashAttribute("patientDontExist", true);

            return "redirect:add";
        }
        PrescriptionAddServiceModel addServiceModel = modelMapper.map(prescriptionAddBindingModel, PrescriptionAddServiceModel.class);
        addServiceModel.setPhysicianUsername(principal.getUsername());

        this.prescriptionService.savePrescription(addServiceModel);
        return "redirect:/home";
    }
}
