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
import softuni.com.personal_health_dossier.model.bindings.ImmunizationAddBindingModel;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.services.ImmunizationAddServiceModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.ImmunizationService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import javax.validation.Valid;

@Controller
@RequestMapping("/immunizations")
public class ImmunizationsController {
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final ImmunizationService immunizationService;
    private final ModelMapper modelMapper;

    public ImmunizationsController(PhysicianService physicianService, PatientService patientService, ImmunizationService immunizationService, ModelMapper modelMapper) {
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.immunizationService = immunizationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addImmunization(Model model, @AuthenticationPrincipal UserDetails principal) {
        if (!model.containsAttribute("physicianViewModel")) {
            PhysicianViewModel physicianViewModel =
                    this.physicianService
                            .findPhysicianByUsername(principal.getUsername())
                            .map(doc -> modelMapper.map(doc, PhysicianViewModel.class))
                            .orElseThrow(() -> new UsernameNotFoundException("Physician with username " + principal.getUsername() + " cannot be found."));

            model.addAttribute("physicianViewModel", physicianViewModel);
        }

        if(!model.containsAttribute("immunizationAddBindingModel")){
            model.addAttribute("immunizationAddBindingModel",new ImmunizationAddBindingModel());
            model.addAttribute("patientDontExist",false);
        }
        return "add-immunization";
    }

    @PostMapping("/add")
    public String addImmunizationConfirm(@Valid ImmunizationAddBindingModel immunizationAddBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes,
                                         @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("immunizationAddBindingModel", immunizationAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.immunizationAddBindingModel"
                    , bindingResult);
            return "redirect:add";
        }

        String patientPin = immunizationAddBindingModel.getPatientPin();
        PatientEntity patientEntity = this.patientService.findPatientByPIN(patientPin).orElse(null);
        if (patientEntity == null) {
            redirectAttributes.addFlashAttribute("immunizationAddBindingModel", immunizationAddBindingModel);
            redirectAttributes.addFlashAttribute("patientDontExist", true);

            return "redirect:add";
        }

        ImmunizationAddServiceModel immunizationAddServiceModel =
                modelMapper.map(immunizationAddBindingModel, ImmunizationAddServiceModel.class);
        immunizationAddServiceModel.setPhysicianUsername(principal.getUsername());

        this.immunizationService.saveImmunization(immunizationAddServiceModel);

        return "redirect:/home";
    }
}
