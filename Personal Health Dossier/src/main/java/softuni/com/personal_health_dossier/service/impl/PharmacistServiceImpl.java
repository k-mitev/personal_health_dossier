package softuni.com.personal_health_dossier.service.impl;

import com.google.gson.Gson;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.PharmacistEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.PharmacistRepository;
import softuni.com.personal_health_dossier.service.PharmacistService;
import softuni.com.personal_health_dossier.service.UserRoleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PharmacistServiceImpl implements PharmacistService {
    private final static String PHARMACISTS_PATH = "src/main/resources/files/json/PharmacistsRegisterBG_ 31.12.2019.json";


    private final Gson gson;
    private final PharmacistRepository pharmacistRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public PharmacistServiceImpl(Gson gson, PharmacistRepository pharmacistRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.gson = gson;
        this.pharmacistRepository = pharmacistRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public String readFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PHARMACISTS_PATH)));
    }

    @Override
    public void seedPharmacistsFromJson() throws IOException {
        if (this.pharmacistRepository.count() != 0) {
            return;
        }
        List<List<String>> allPharmacists = this.gson.fromJson(this.readFileContent(), List.class);

        UserRoleEntity userRole = this.userRoleService.findByUserRole(UserRoleEnum.PHARMACIST);
        int index = 1;
        for (List<String> pharmacist : allPharmacists) {
            String personalIdentificationNumber = pharmacist.get(3);
            String region = pharmacist.get(4);

            if (!String.valueOf(personalIdentificationNumber.charAt(0)).equals("*") && region.startsWith("Я")) {
                String firstName = pharmacist.get(0);
                String middleName = pharmacist.get(1);
                String lastName = pharmacist.get(2);

                PharmacistEntity pharmacistEntity = new PharmacistEntity();
                pharmacistEntity.setFirstName(firstName);
                pharmacistEntity.setMiddleName(middleName);
                pharmacistEntity.setLastName(lastName);
                pharmacistEntity.setPersonalIdentificationNumber(personalIdentificationNumber);
                pharmacistEntity.setRegion(region);
                pharmacistEntity.setUsername("pharmacistUsername" + index);
                pharmacistEntity.setPassword(passwordEncoder.encode("pharmacistPassword" + index));
                pharmacistEntity.setRoles(List.of(userRole));

                this.pharmacistRepository.save(pharmacistEntity);
                index++;
            }
        }
    }


}
