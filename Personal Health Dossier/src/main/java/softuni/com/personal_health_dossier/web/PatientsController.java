package softuni.com.personal_health_dossier.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import softuni.com.personal_health_dossier.model.bindings.*;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.services.PatientEditProfileServiceModel;
import softuni.com.personal_health_dossier.model.services.PatientServiceModel;
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


@Controller
@RequestMapping("/patients")
public class PatientsController {
    private static PatientEditProfileServiceModel editProfileServiceModel = new PatientEditProfileServiceModel();
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final HealthDossierUserService healthDossierUserService;

    public PatientsController(PatientService patientService, PhysicianService physicianService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, HealthDossierUserService healthDossierUserService) {
        this.patientService = patientService;
        this.physicianService = physicianService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.healthDossierUserService = healthDossierUserService;
    }

    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails principal) {
        PatientServiceModel patientServiceModel = patientService.findPatientById(id);
        PatientViewModel patientViewModel = modelMapper.map(patientServiceModel, PatientViewModel.class);

        if (!model.containsAttribute("patientModifyKilosBindingModel")) {
            model.addAttribute("patientModifyKilosBindingModel", new PatientModifyKilosBindingModel());
        }
        if (!model.containsAttribute("patientModifyHeightBindingModel")) {
            model.addAttribute("patientModifyHeightBindingModel", new PatientModifyHeightBindingModel());
        }
        if (!model.containsAttribute("patientModifyBloodGroupBindingModel")) {
            model.addAttribute("patientModifyBloodGroupBindingModel", new PatientModifyBloodGroupBindingModel());
        }
        if (!model.containsAttribute("patientModifyConsentBindingModel")) {
            model.addAttribute("patientModifyConsentBindingModel", new PatientModifyConsentBindingModel());
        }

        PhysicianViewModel physicianViewModel = physicianService
                .findPhysicianByUsername(principal.getUsername())
                .map(doctor -> {
                    PhysicianViewModel viewModel = modelMapper.map(doctor, PhysicianViewModel.class);
                    viewModel.setPatient(patientViewModel);
                    return viewModel;
                }).orElseThrow();

        model.addAttribute("patientViewModel", patientViewModel);
        model.addAttribute("physicianViewModel", physicianViewModel);


        return "patient-home";
    }

    @PostMapping("/modify/kilos/{id}")
    public String modifyKilos(@PathVariable Long id,
                              @Valid PatientModifyKilosBindingModel patientModifyKilosBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientModifyKilosBindingModel", patientModifyKilosBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientModifyKilosBindingModel",
                    bindingResult);

            return String.format("redirect:/patients/view/%d", id);
        }
        this.patientService.updatePatientKilos(patientModifyKilosBindingModel.getKilos(), id);

        return String.format("redirect:/patients/view/%d", id);
    }

    @PostMapping("/modify/height/{id}")
    public String modifyHeight(@PathVariable Long id,
                               @Valid PatientModifyHeightBindingModel patientModifyHeightBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientModifyHeightBindingModel", patientModifyHeightBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientModifyHeightBindingModel",
                    bindingResult);

            return String.format("redirect:/patients/view/%d", id);
        }
        this.patientService.updatePatientHeight(patientModifyHeightBindingModel.getHeight(), id);

        return String.format("redirect:/patients/view/%d", id);
    }

    @PostMapping("/modify/bloodGroup/{id}")
    public String modifyBloodGroup(@PathVariable Long id,
                                   @Valid PatientModifyBloodGroupBindingModel patientModifyBloodGroupBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientModifyBloodGroupBindingModel", patientModifyBloodGroupBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientModifyBloodGroupBindingModel",
                    bindingResult);

            return String.format("redirect:/patients/view/%d", id);
        }


        this.patientService.updatePatientBloodGroup(patientModifyBloodGroupBindingModel.getBloodGroup(), id);

        return String.format("redirect:/patients/view/%d", id);
    }

    @PostMapping("/modify/consentForOrgans/{id}")
    public String modifyConsent(@PathVariable Long id,
                                @Valid PatientModifyConsentBindingModel patientModifyConsentBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientModifyConsentBindingModel", patientModifyConsentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientModifyConsentBindingModel",
                    bindingResult);

            return "redirect:/home";
        }


        this.patientService.updatePatientConsentForDonation(patientModifyConsentBindingModel.isConsentForOrganDonationAfterDeath(), id);

        return "redirect:/home";
    }

    @GetMapping("/edit-profile/{id}")
    public String patientEditProfile(@PathVariable Long id, Model model) {
        PatientViewModel patientViewModel = modelMapper.map(this.patientService.findPatientById(id), PatientViewModel.class);
        model.addAttribute("patientViewModel", patientViewModel);
        if (!model.containsAttribute("patientEditProfileBindingModel")) {
            model.addAttribute("patientEditProfileBindingModel", new PatientEditProfileBindingModel());
            model.addAttribute("usernameExists", false);
            model.addAttribute("wrongPassword", false);
            model.addAttribute("emptyPassword", false);
        }

        return "patient-edit-profile";
    }

    @PostMapping("/edit-profile/{id}")
    public String patientEditProfileConfirm(@PathVariable Long id,
                                            @Valid PatientEditProfileBindingModel patientEditProfileBindingModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes,
                                            @AuthenticationPrincipal UserDetails principal) {

        String username = patientEditProfileBindingModel.getUsername();
        String firstName = patientEditProfileBindingModel.getFirstName();
        String lastName = patientEditProfileBindingModel.getLastName();
        String middleName = patientEditProfileBindingModel.getMiddleName();
        String mobileNumber = patientEditProfileBindingModel.getMobileNumber();
        String newPassword = patientEditProfileBindingModel.getNewPassword();
        String oldPassword = patientEditProfileBindingModel.getOldPassword();
        MultipartFile file = patientEditProfileBindingModel.getImg();


        if (username.equals("") && firstName.equals("") && lastName.equals("") &&
                middleName.equals("") && mobileNumber.equals("") && newPassword.equals("") &&
                oldPassword.equals("") && file.isEmpty()) {

            JOptionPane optionPane = new JOptionPane("You didn't change anything!", JOptionPane.INFORMATION_MESSAGE);

            JDialog dialog = optionPane.createDialog("Information!");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return String.format("redirect:/patients/edit-profile/%d", id);
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientEditProfileBindingModel", patientEditProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientEditProfileBindingModel",
                    bindingResult);
            return String.format("redirect:/patients/edit-profile/%d", id);
        }
        PatientServiceModel patientById = this.patientService.findPatientById(id);

        if (username.equals("")) {
            editProfileServiceModel.setUsername(patientById.getUsername());
        } else {
            PatientEntity patientEntity = this.patientService.findPatientByUsername(username).orElse(null);
            if (patientEntity == null) {
                editProfileServiceModel.setUsername(username);

            } else {
                redirectAttributes.addFlashAttribute("usernameExists", true);
                redirectAttributes.addFlashAttribute("patientEditProfileBindingModel", patientEditProfileBindingModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientEditProfileBindingModel",
                        bindingResult);

                return String.format("redirect:/patients/edit-profile/%d", id);
            }
        }

        if (oldPassword.equals("")) {
            editProfileServiceModel.setPassword(patientById.getPassword());
        } else {
            if (!passwordEncoder.matches(oldPassword, patientById.getPassword())) {
                redirectAttributes.addFlashAttribute("wrongPassword", true);
                redirectAttributes.addFlashAttribute("patientEditProfileBindingModel", patientEditProfileBindingModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientEditProfileBindingModel",
                        bindingResult);
                return String.format("redirect:/patients/edit-profile/%d", id);
            } else {
                if (newPassword.equals("")) {
                    redirectAttributes.addFlashAttribute("emptyPassword", true);
                    redirectAttributes.addFlashAttribute("patientEditProfileBindingModel", patientEditProfileBindingModel);
                    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientEditProfileBindingModel",
                            bindingResult);
                    return String.format("redirect:/patients/edit-profile/%d", id);
                } else {
                    editProfileServiceModel.setPassword(passwordEncoder.encode(newPassword));
                }

            }
        }

        setAllNamesAndMobileNumber(firstName, lastName, middleName, mobileNumber, patientById);
        setImgUrl(file, patientById);

        editProfileServiceModel.setId(id);
        this.patientService.updatePatient(editProfileServiceModel);

        updateSecurityContextHolder(newPassword, oldPassword, patientById.getUsername(), patientById.getPassword(), principal);

        return "redirect:/home";
    }

    private void updateSecurityContextHolder(String newPassword, String oldPassword,
                                             String patientUsername, String patientPassword, UserDetails principal) {
        Collection<SimpleGrantedAuthority> currentAuthorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!editProfileServiceModel.getUsername().equals(patientUsername)) {
            UserDetails newPrincipal = healthDossierUserService.loadUserByUsername(editProfileServiceModel.getUsername());

            if (!oldPassword.equals("") && passwordEncoder.matches(oldPassword, patientPassword) && !newPassword.equals("")) {
                authentication =
                        new UsernamePasswordAuthenticationToken(newPrincipal, editProfileServiceModel.getPassword(), currentAuthorities);
            } else {
                authentication =
                        new UsernamePasswordAuthenticationToken(newPrincipal, patientPassword, currentAuthorities);
            }

        } else {
            if (!oldPassword.equals("") && passwordEncoder.matches(oldPassword, patientPassword) && !newPassword.equals("")) {
                authentication =
                        new UsernamePasswordAuthenticationToken(principal, editProfileServiceModel.getPassword(), currentAuthorities);
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setAllNamesAndMobileNumber(String firstName, String lastName, String middleName, String mobileNumber, PatientServiceModel patientById) {
        if (firstName.equals("")) {
            editProfileServiceModel.setFirstName(patientById.getFirstName());
        } else {
            editProfileServiceModel.setFirstName(firstName);
        }

        if (middleName.equals("")) {
            editProfileServiceModel.setMiddleName(patientById.getMiddleName());
        } else {
            editProfileServiceModel.setMiddleName(middleName);
        }

        if (lastName.equals("")) {
            editProfileServiceModel.setLastName(patientById.getLastName());
        } else {
            editProfileServiceModel.setLastName(lastName);
        }

        if (mobileNumber.equals("")) {
            editProfileServiceModel.setMobileNumber(patientById.getMobileNumber());
        } else {
            editProfileServiceModel.setMobileNumber(mobileNumber);
        }
    }

    private void setImgUrl(MultipartFile file, PatientServiceModel patientById) {
        if (!file.isEmpty()) {
            try {
                String image = cloudinaryService.uploadImage(file);
                editProfileServiceModel.setImgUrl(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            editProfileServiceModel.setImgUrl(patientById.getImgUrl());
        }
    }

}
