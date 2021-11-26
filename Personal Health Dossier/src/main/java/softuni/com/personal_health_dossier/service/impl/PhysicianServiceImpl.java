package softuni.com.personal_health_dossier.service.impl;

import com.google.gson.Gson;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.PhysicianRepository;
import softuni.com.personal_health_dossier.service.PhysicianService;
import softuni.com.personal_health_dossier.service.UserRoleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@Service
public class PhysicianServiceImpl implements PhysicianService {
    private final static String PHYSICIAN_PATH = "src/main/resources/files/json/PhysicianRegisterInBG_March2019.json";


    private final Gson gson;
    private final PhysicianRepository physicianRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public PhysicianServiceImpl(Gson gson, PhysicianRepository physicianRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.gson = gson;
        this.physicianRepository = physicianRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public String readFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PHYSICIAN_PATH)));
    }

    @Override
    public void seedAllPhysicians() throws IOException {
        if (this.physicianRepository.count() != 0) {
            return;
        }
        List<List<String>> allDoctors = this.gson.fromJson(this.readFileContent(), List.class);

        UserRoleEntity userRole = this.userRoleService.findByUserRole(UserRoleEnum.PHYSICIAN);
        int index = 1;
        for (List<String> doctor : allDoctors) {
            String personalIdentificationNumber = doctor.get(0);
            String region = doctor.get(4);

            if (!String.valueOf(personalIdentificationNumber.charAt(0)).equals("*") && region.startsWith("Я")) {
                String firstName = doctor.get(1);
                String middleName = doctor.get(2);
                String lastName = doctor.get(3);

                PhysicianEntity physicianEntity = new PhysicianEntity();
                physicianEntity.setFirstName(firstName);
                physicianEntity.setMiddleName(middleName);
                physicianEntity.setLastName(lastName);
                physicianEntity.setPersonalIdentificationNumber(personalIdentificationNumber);
                physicianEntity.setRegion(region);
                physicianEntity.setUsername("physicianUsername" + index);
                physicianEntity.setPassword(passwordEncoder.encode("physicianPassword" + index));
                physicianEntity.setSpecialty(MedicalSpecialty.OTHER);
                physicianEntity.setRoles(List.of(userRole));

                this.physicianRepository.save(physicianEntity);
                index++;
            }
        }
    }

    @Override
    public Optional<PhysicianEntity> findPhysicianByUsername(String username) {
        return this.physicianRepository.findByUsername(username);
    }


}
