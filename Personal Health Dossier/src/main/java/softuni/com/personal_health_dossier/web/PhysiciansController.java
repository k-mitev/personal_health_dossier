package softuni.com.personal_health_dossier.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.com.personal_health_dossier.model.bindings.PhysicianAddBindingModel;
import softuni.com.personal_health_dossier.model.bindings.PhysicianEditProfileBindingModel;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;
import softuni.com.personal_health_dossier.model.services.PhysicianAddServiceModel;
import softuni.com.personal_health_dossier.model.services.PhysicianEditProfileServiceModel;
import softuni.com.personal_health_dossier.model.views.PatientViewModel;
import softuni.com.personal_health_dossier.model.views.PhysicianViewModel;
import softuni.com.personal_health_dossier.service.CloudinaryService;
import softuni.com.personal_health_dossier.service.PatientService;
import softuni.com.personal_health_dossier.service.PhysicianService;
import softuni.com.personal_health_dossier.service.impl.HealthDossierUserService;

import javax.swing.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


@Controller
@RequestMapping("/physicians")
public class PhysiciansController {
    private static PhysicianEditProfileServiceModel serviceModel = new PhysicianEditProfileServiceModel();
    private final PhysicianService physicianService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final HealthDossierUserService healthDossierUserService;

    public PhysiciansController(PhysicianService physicianService, PatientService patientService,
                                ModelMapper modelMapper, CloudinaryService cloudinaryService,
                                PasswordEncoder passwordEncoder, HealthDossierUserService healthDossierUserService) {
        this.physicianService = physicianService;
        this.patientService = patientService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.healthDossierUserService = healthDossierUserService;
    }

    @GetMapping("/edit-profile/{id}")
    public String editPhysicianProfile(@PathVariable Long id, Model model) {
        PhysicianViewModel physicianViewModel = modelMapper.map(findPhysicianById(id), PhysicianViewModel.class);


        if (!model.containsAttribute("physicianEditProfileBindingModel")) {
            model.addAttribute("physicianEditProfileBindingModel", new PhysicianEditProfileBindingModel());
        }
        if (!model.containsAttribute("usernameExists")) {
            model.addAttribute("usernameExists", false);
        }
        if (!model.containsAttribute("wrongPassword")) {
            model.addAttribute("wrongPassword", false);
        }
        model.addAttribute("physicianViewModel", physicianViewModel);

        return "physician-edit-profile";
    }

    @PostMapping("/edit-profile/{id}")
    public String editPhysicianProfileConfirm(@PathVariable Long id,
                                              @Valid PhysicianEditProfileBindingModel physicianEditProfileBindingModel,
                                              BindingResult bindingResult,
                                              RedirectAttributes redirectAttributes) {

        String username = physicianEditProfileBindingModel.getUsername();
        String firstName = physicianEditProfileBindingModel.getFirstName();
        String lastName = physicianEditProfileBindingModel.getLastName();
        String middleName = physicianEditProfileBindingModel.getMiddleName();
        String mobileNumber = physicianEditProfileBindingModel.getMobileNumber();
        String newPassword = physicianEditProfileBindingModel.getNewPassword();
        String oldPassword = physicianEditProfileBindingModel.getOldPassword();
        String region = physicianEditProfileBindingModel.getRegion();
        String img = physicianEditProfileBindingModel.getImg().getOriginalFilename();
        MedicalSpecialty specialty = physicianEditProfileBindingModel.getSpecialty();

        if (username.equals("") && firstName.equals("") && lastName.equals("") &&
                middleName.equals("") && mobileNumber.equals("") && newPassword.equals("") &&
                oldPassword.equals("") && region.equals("") && img != null &&
                img.equals("") && specialty == null) {

            JOptionPane optionPane = new JOptionPane("You didn't change anything!", JOptionPane.INFORMATION_MESSAGE);

            JDialog dialog = optionPane.createDialog("Information!");
            dialog.setAlwaysOnTop(true); // to show top of all other application
            dialog.setVisible(true); // to set the dialog visible
            return String.format("redirect:/physicians/edit-profile/%d", id);

        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("physicianEditProfileBindingModel", physicianEditProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.physicianEditProfileBindingModel",
                    bindingResult);
            return String.format("redirect:/physicians/edit-profile/%d", id);
        }


        PhysicianEntity physician = this.findPhysicianById(id);


        if (username.equals("")) {
            serviceModel.setUsername(physician.getUsername());
        } else {
            PhysicianEntity physicianEntity = this.physicianService.findPhysicianByUsername("physician" + username).orElse(null);
            if (physicianEntity == null) {
                serviceModel.setUsername("physician" + username);


            } else {
                redirectAttributes.addFlashAttribute("usernameExists", true);
                redirectAttributes.addFlashAttribute("physicianEditProfileBindingModel", physicianEditProfileBindingModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.physicianEditProfileBindingModel",
                        bindingResult);

                return String.format("redirect:/physicians/edit-profile/%d", id);
            }
        }
        if (firstName.equals("")) {
            serviceModel.setFirstName(physician.getFirstName());
        } else {
            serviceModel.setFirstName(firstName);
        }

        if (middleName.equals("")) {
            serviceModel.setMiddleName(physician.getMiddleName());
        } else {
            serviceModel.setMiddleName(middleName);
        }

        if (lastName.equals("")) {
            serviceModel.setLastName(physician.getLastName());
        } else {
            serviceModel.setLastName(lastName);
        }

        if (mobileNumber.equals("")) {
            serviceModel.setMobileNumber(physician.getMobileNumber());
        } else {
            serviceModel.setMobileNumber(mobileNumber);
        }

        if (region.equals("")) {
            serviceModel.setRegion(physician.getRegion());
        } else {
            serviceModel.setRegion(region);
        }

        if (specialty == null) {
            serviceModel.setSpecialty(physician.getSpecialty());
        } else {
            serviceModel.setSpecialty(specialty);
        }

        if (oldPassword.equals("")) {
            serviceModel.setPassword(physician.getPassword());
        } else {
            if (!passwordEncoder.matches(oldPassword, physician.getPassword())) {
                redirectAttributes.addFlashAttribute("wrongPassword", true);
                redirectAttributes.addFlashAttribute("physicianEditProfileBindingModel", physicianEditProfileBindingModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.physicianEditProfileBindingModel",
                        bindingResult);
                return String.format("redirect:/physicians/edit-profile/%d", id);
            } else {
                if (newPassword.equals("")) {
                    redirectAttributes.addFlashAttribute("physicianEditProfileBindingModel", physicianEditProfileBindingModel);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.physicianEditProfileBindingModel",
                            bindingResult);
                    return String.format("redirect:/physicians/edit-profile/%d", id);
                } else {
                    serviceModel.setPassword(passwordEncoder.encode(newPassword));
                }

            }
        }


        MultipartFile file = physicianEditProfileBindingModel.getImg();
        if (!file.isEmpty()) {
            try {
                String image = cloudinaryService.uploadImage(file);
                serviceModel.setImgUrl(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            serviceModel.setImgUrl(physician.getImgUrl());
        }

        serviceModel.setId(id);
        this.physicianService.updatePhysician(serviceModel);

        updateSecurityContextHolder(newPassword, oldPassword, physician.getUsername(), physician.getPassword());


        return "redirect:/home";
    }

    @GetMapping("/add")
    public String addPhysician(Model model, @AuthenticationPrincipal UserDetails principal) {
        if (principal.getUsername().startsWith("physician") &&
                principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PHYSICIAN"))) {
            PhysicianViewModel physicianViewModel = this.physicianService
                    .findPhysicianByUsername(principal.getUsername())
                    .map(ph -> modelMapper.map(ph, PhysicianViewModel.class))
                    .orElseThrow(() -> new UsernameNotFoundException("Physician not found!"));
            model.addAttribute("physicianViewModel", physicianViewModel);
            model.addAttribute("patientViewModel", new PatientViewModel());
        } else {
            PatientViewModel patientViewModel = this.patientService.findPatientByUsername(principal.getUsername())
                    .map(patient -> modelMapper.map(patient, PatientViewModel.class))
                    .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
            model.addAttribute("patientViewModel", patientViewModel);
            model.addAttribute("physicianViewModel", new PhysicianViewModel());
        }
        if (!model.containsAttribute("physicianAddBindingModel")) {
            model.addAttribute("physicianAddBindingModel", new PhysicianAddBindingModel());
            model.addAttribute("usernameExists", false);
        }
        return "add-physician";
    }

    @PostMapping("/add")
    public String addPhysicianConfirm(@Valid PhysicianAddBindingModel physicianAddBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("physicianAddBindingModel", physicianAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.physicianAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }
        PhysicianEntity physicianEntity = this.physicianService.findPhysicianByUsername(physicianAddBindingModel.getUsername())
                .orElse(null);

        if (physicianEntity != null) {
            redirectAttributes.addFlashAttribute("physicianAddBindingModel", physicianAddBindingModel);
            redirectAttributes.addFlashAttribute("usernameExists", true);
            return "redirect:add";
        }

        PhysicianAddServiceModel addServiceModel = modelMapper.map(physicianAddBindingModel, PhysicianAddServiceModel.class);

        if (!physicianAddBindingModel.getImg().isEmpty()) {
            try {
                String image = this.cloudinaryService.uploadImage(physicianAddBindingModel.getImg());
                addServiceModel.setImgUrl(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.physicianService.savePhysician(addServiceModel);
        return "redirect:/home";

    }

    private void updateSecurityContextHolder(String newPassword, String oldPassword, String physicianUsername, String physicianPassword) {
        Collection<SimpleGrantedAuthority> currentAuthorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!serviceModel.getUsername().equals(physicianUsername)) {
            UserDetails newPrincipal = healthDossierUserService.loadUserByUsername(serviceModel.getUsername());
            if (!oldPassword.equals("") && passwordEncoder.matches(oldPassword, physicianPassword) && !newPassword.equals("")) {
                authentication =
                        new UsernamePasswordAuthenticationToken(newPrincipal, serviceModel.getPassword(), currentAuthorities);
            } else {
                authentication =
                        new UsernamePasswordAuthenticationToken(newPrincipal, physicianPassword, currentAuthorities);
            }

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private PhysicianEntity findPhysicianById(Long id) {
        return this.physicianService
                .findPhysicianById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Physician with " + id + " does not exist!"));
    }

}
