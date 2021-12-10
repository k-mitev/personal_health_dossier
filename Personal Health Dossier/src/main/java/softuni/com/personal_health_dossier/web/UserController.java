package softuni.com.personal_health_dossier.web;


import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.com.personal_health_dossier.model.bindings.UserRegisterBindingModel;
import softuni.com.personal_health_dossier.model.services.UserRegisterServiceModel;
import softuni.com.personal_health_dossier.service.PatientService;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    public UserController(PatientService patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }



    @PostMapping("/login-error")
    public ModelAndView loginFailure(@ModelAttribute
                                             (UsernamePasswordAuthenticationFilter
                                                     .SPRING_SECURITY_FORM_USERNAME_KEY)
                                             String username) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bad_credentials", true);
        modelAndView.addObject("username", username);

        modelAndView.setViewName("/login");

        return modelAndView;

    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
            model.addAttribute("passwordsDontMatch", false);
            model.addAttribute("isUsed", false);
            model.addAttribute("pinExists", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                            bindingResult);
            return "redirect:register";
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", true);
            return "redirect:register";
        }

        UserRegisterServiceModel userByUsername =
                this.patientService
                        .findPatientByUsername(userRegisterBindingModel.getUsername())
                        .map(patient -> modelMapper.map(patient, UserRegisterServiceModel.class))
                        .orElse(null);

        if (userByUsername != null) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("isUsed", true);
            return "redirect:register";
        }

        UserRegisterServiceModel userByPIN =
                this.patientService
                        .findPatientByPIN(userRegisterBindingModel.getPersonalIdentificationNumber())
                        .map(patient -> modelMapper.map(patient, UserRegisterServiceModel.class))
                        .orElse(null);

        if (userByPIN != null) {
            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("pinExists", true);
            return "redirect:register";
        }
        UserRegisterServiceModel patient = modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
        this.patientService.registerAndLoginPatient(patient);

        return "redirect:/home";
    }
}
