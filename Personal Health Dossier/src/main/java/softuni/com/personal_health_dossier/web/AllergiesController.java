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
import softuni.com.personal_health_dossier.model.bindings.AllergyAddBindingModel;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.AllergenService;
import softuni.com.personal_health_dossier.service.AllergyService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import javax.validation.Valid;

@Controller
@RequestMapping("/allergies")
public class AllergiesController {
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final AllergenService allergenService;
    private final AllergyService allergyService;
    private final ModelMapper modelMapper;

    public AllergiesController(PhysicianService physicianService, PatientService patientService, AllergenService allergenService, AllergyService allergyService, ModelMapper modelMapper) {
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.allergenService = allergenService;
        this.allergyService = allergyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String addAllergy(Model model, @AuthenticationPrincipal UserDetails principal) {

        if (!model.containsAttribute("physicianViewModel")) {
            PhysicianViewModel physicianViewModel =
                    this.physicianService
                            .findPhysicianByUsername(principal.getUsername())
                            .map(doc -> modelMapper.map(doc, PhysicianViewModel.class))
                            .orElseThrow(() -> new UsernameNotFoundException("Physician with username " + principal.getUsername() + " cannot be found."));

            model.addAttribute("physicianViewModel", physicianViewModel);
        }

        if (!model.containsAttribute("allergyAddBindingModel")) {
            model.addAttribute("allergyAddBindingModel", new AllergyAddBindingModel());
            model.addAttribute("patientDontExist", false);
        }


        return "add-allergy";
    }

    @PostMapping("/add")
    public String addAllergyConfirm(@Valid AllergyAddBindingModel allergyAddBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("allergyAddBindingModel", allergyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.allergyAddBindingModel"
                    , bindingResult);
            return "redirect:/allergies/add";
        }
        String patientPin = allergyAddBindingModel.getPatientPin();
        PatientEntity patientEntity = this.patientService.findPatientByPIN(patientPin).orElse(null);
        if (patientEntity == null) {
            redirectAttributes.addFlashAttribute("allergyAddBindingModel", allergyAddBindingModel);
            redirectAttributes.addFlashAttribute("patientDontExist", true);

            return "redirect:add";
        }

        String allergens = allergyAddBindingModel.getAllergens();
        String[] allAllergens = allergens.split(",(\\s+)?");
        this.allergenService.seedAllergens(allAllergens);

        this.allergyService.saveAllergy(allAllergens,patientEntity,principal,allergyAddBindingModel.getRegistrationDate());
        return "redirect:/home";
    }
}
