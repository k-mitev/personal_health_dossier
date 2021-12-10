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
import softuni.com.personal_health_dossier.model.bindings.MedicalCenterAddBindingModel;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.services.MedicalCenterAddServiceModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.MedicalCenterService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import javax.validation.Valid;

@Controller
@RequestMapping("/medical-centers")
public class MedicalCentersController {
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final MedicalCenterService medicalCenterService;
    private final ModelMapper modelMapper;

    public MedicalCentersController(PhysicianService physicianService, PatientService patientService, MedicalCenterService medicalCenterService, ModelMapper modelMapper) {
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.medicalCenterService = medicalCenterService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addMedicalCenter(Model model, @AuthenticationPrincipal UserDetails principal) {
        if (!model.containsAttribute("physicianViewModel")) {
            PhysicianViewModel physicianViewModel =
                    this.physicianService
                            .findPhysicianByUsername(principal.getUsername())
                            .map(doc -> modelMapper.map(doc, PhysicianViewModel.class))
                            .orElseThrow(() -> new UsernameNotFoundException("Physician with username " + principal.getUsername() + " cannot be found."));

            model.addAttribute("physicianViewModel", physicianViewModel);
        }

        if (!model.containsAttribute("medicalCenterAddBindingModel")) {
            model.addAttribute("medicalCenterAddBindingModel", new MedicalCenterAddBindingModel());
            model.addAttribute("patientDontExist", false);
            model.addAttribute("wrongDates", false);
        }
        return "add-medical-center";
    }

    @PostMapping("/add")
    public String addMedicalCenterConfirm(@Valid MedicalCenterAddBindingModel medicalCenterAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes,
                                          @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("medicalCenterAddBindingModel", medicalCenterAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.medicalCenterAddBindingModel"
                    , bindingResult);
            return "redirect:/medical-centers/add";
        }

        String patientPin = medicalCenterAddBindingModel.getPatientPin();
        PatientEntity patientEntity = this.patientService.findPatientByPIN(patientPin).orElse(null);
        if (patientEntity == null) {
            redirectAttributes.addFlashAttribute("medicalCenterAddBindingModel", medicalCenterAddBindingModel);
            redirectAttributes.addFlashAttribute("patientDontExist", true);

            return "redirect:add";
        }

        if (medicalCenterAddBindingModel.getDischargeDateAndTime()
                .isBefore(medicalCenterAddBindingModel.getHospitalizationDateAndTime())) {

            redirectAttributes.addFlashAttribute("medicalCenterAddBindingModel", medicalCenterAddBindingModel);
            redirectAttributes.addFlashAttribute("wrongDates", true);

            return "redirect:add";
        }

        MedicalCenterAddServiceModel medicalCenterAddServiceModel =
                modelMapper.map(medicalCenterAddBindingModel, MedicalCenterAddServiceModel.class);

        medicalCenterAddServiceModel.setPhysicianUsername(principal.getUsername());

        this.medicalCenterService.saveMedicalCenter(medicalCenterAddServiceModel);

        return "redirect:/home";
    }
}
