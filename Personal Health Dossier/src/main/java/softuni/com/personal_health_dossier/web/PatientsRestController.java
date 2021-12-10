package softuni.com.personal_health_dossier.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.com.personal_health_dossier.model.views.PatientViewModel;
import softuni.com.personal_health_dossier.service.PatientService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientsRestController {
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    public PatientsRestController(PatientService patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api")
    public ResponseEntity<List<PatientViewModel>> findAll() {
        List<PatientViewModel> patientViewModels =
                patientService
                        .findAll()
                        .stream()
                        .map(patient -> modelMapper.map(patient, PatientViewModel.class))
                        .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .body(patientViewModels);
    }
}
