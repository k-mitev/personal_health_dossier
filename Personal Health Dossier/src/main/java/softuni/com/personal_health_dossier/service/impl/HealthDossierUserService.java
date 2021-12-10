package softuni.com.personal_health_dossier.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import softuni.com.personal_health_dossier.model.entities.BaseEntityUsers;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PharmacistEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.repository.PatientRepository;
import softuni.com.personal_health_dossier.repository.PharmacistRepository;
import softuni.com.personal_health_dossier.repository.PhysicianRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class HealthDossierUserService implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final PhysicianRepository physicianRepository;
    private final PharmacistRepository pharmacistRepository;

    public HealthDossierUserService(PatientRepository patientRepository, PhysicianRepository physicianRepository, PharmacistRepository pharmacistRepository) {
        this.patientRepository = patientRepository;
        this.physicianRepository = physicianRepository;
        this.pharmacistRepository = pharmacistRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PatientEntity patientEntity =
                this.patientRepository
                        .findByUsername(username)
                        .orElse(null);
        if (patientEntity != null) {
            return mapToUserDetails(patientEntity);
        }

        PhysicianEntity physicianEntity =
                this.physicianRepository
                        .findByUsername(username)
                        .orElse(null);

        if (physicianEntity != null) {
            return mapToUserDetails(physicianEntity);
        }

        PharmacistEntity pharmacistEntity =
                this.pharmacistRepository
                        .findByUsername(username)
                        .orElse(null);
        if (pharmacistEntity != null) {
            return mapToUserDetails(pharmacistEntity);
        }

        throw new UsernameNotFoundException("User with username '" + username + "' was not found.");
    }


    private UserDetails mapToUserDetails(BaseEntityUsers baseEntityUsers) {
        List<SimpleGrantedAuthority> authorities = baseEntityUsers
                .getRoles()
                .stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().name()))
                .collect(Collectors.toList());

        return new User(
                baseEntityUsers.getUsername(),
                baseEntityUsers.getPassword(),
                authorities
        );
    }
}
