package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;
import softuni.com.personal_health_dossier.model.entities.PatientEntity;
import softuni.com.personal_health_dossier.model.entities.PhysicianEntity;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.AllergyRepository;
import softuni.com.personal_health_dossier.service.AllergenService;
import softuni.com.personal_health_dossier.service.PhysicianService;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class AllergyServiceTests {

    private AllergyServiceImpl serviceToTest;

    @Mock
    AllergyRepository mockAllergyRepository;

    @Mock
    PhysicianService mockPhysicianService;

    @Mock
    AllergenService mockAllergenService;

    @BeforeEach
    public void setup() {
        serviceToTest = new AllergyServiceImpl(mockAllergyRepository, mockPhysicianService, mockAllergenService);
    }

    @Test
    public void testSaveAllergy() {
        AllergenEntity allergenEntity = new AllergenEntity();
        allergenEntity.setName("eggs");


        Mockito.when(mockAllergenService.findByAllergenName(Mockito.anyString()))
                .thenReturn(java.util.Optional.of(allergenEntity));

        UserRoleEntity physicianRole = new UserRoleEntity();
        physicianRole.setRole(UserRoleEnum.PHYSICIAN);

        PhysicianEntity physicianEntity = new PhysicianEntity();
        physicianEntity.setUsername("physician");
        physicianEntity.setPassword("123");
        physicianEntity.setRoles(List.of(physicianRole));

        List<SimpleGrantedAuthority> authorities = physicianEntity
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        UserDetails principal = new User(physicianEntity.getUsername(), physicianEntity.getPassword(), authorities);

        Mockito.when(mockPhysicianService.findPhysicianByUsername(principal.getUsername()))
                .thenReturn(java.util.Optional.of(physicianEntity));

        PatientEntity patientEntity=new PatientEntity();
        patientEntity.setUsername("patient");

        serviceToTest.saveAllergy(new String[]{"eggs","milk"},patientEntity,principal, LocalDate.now());

        Mockito.verify(mockAllergyRepository,Mockito.times(1)).save(Mockito.any());
    }
}
