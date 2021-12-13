package softuni.com.personal_health_dossier.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.com.personal_health_dossier.model.bindings.AllergyAddBindingModel;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;
import softuni.com.personal_health_dossier.model.entities.AllergyEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.views.AllergyViewModel;
import softuni.com.personal_health_dossier.model.views.PatientViewModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.AllergenService;
import softuni.com.personal_health_dossier.service.AllergyService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        this.allergyService.saveAllergy(allAllergens, patientEntity, principal, allergyAddBindingModel.getRegistrationDate());
        return "redirect:/home";
    }

    @GetMapping("/all/{id}")
    public String allAllergies(@PathVariable Long id, Model model) {
        PatientViewModel patientViewModel = modelMapper.map(this.patientService.findPatientById(id), PatientViewModel.class);

        List<AllergyEntity> allergies = this.allergyService.findAllForAPatient(id);

        List<AllergyViewModel> allergyViewModels = allergies.stream().map(allergyEntity -> {
            AllergyViewModel allergyViewModel = modelMapper.map(allergyEntity, AllergyViewModel.class);
            allergyViewModel.setDoctor("д-р " + allergyEntity.getRegisteredBy().getFirstName() + " " + allergyEntity.getRegisteredBy().getLastName());
            return allergyViewModel;
        }).collect(Collectors.toList());

        List<List<AllergenEntity>> allergensList =
                allergies
                        .stream()
                        .map(AllergyEntity::getAllergens)
                        .collect(Collectors.toList());
        List<String> allergensAsStrings = new ArrayList<>();

        for (List<AllergenEntity> allergens : allergensList) {
            List<String> stringList = allergens.stream().map(AllergenEntity::getName).collect(Collectors.toList());
            String allergensAsString = String.join(", ", stringList);
            allergensAsStrings.add(allergensAsString);
        }

        for (int i = 0; i < allergyViewModels.size(); i++) {
            for (int j = 0; j < allergensAsStrings.size(); j++) {
                if (i == j) {
                    allergyViewModels.get(i).setAllergens(allergensAsStrings.get(j));
                }
            }
        }

        model.addAttribute("patientViewModel", patientViewModel);
        model.addAttribute("allergyViewModels", allergyViewModels);

        return "allergies-all";
    }

}
