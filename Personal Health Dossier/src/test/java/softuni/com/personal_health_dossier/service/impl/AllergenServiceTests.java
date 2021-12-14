package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;
import softuni.com.personal_health_dossier.repository.AllergenRepository;


import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AllergenServiceTests {

    private AllergenServiceImpl serviceToTest;

    @Mock
    AllergenRepository mockAllergenRepository;

    @BeforeEach
    public void setup() {
        serviceToTest = new AllergenServiceImpl(mockAllergenRepository);
    }

    @Test
    public void testFindByNameShouldWork() {
        AllergenEntity allergenEntity = new AllergenEntity();
        allergenEntity.setName("eggs");

        Mockito.when(mockAllergenRepository.findByName("eggs"))
                .thenReturn(java.util.Optional.of(allergenEntity));

        AllergenEntity foundEntity = serviceToTest.findByAllergenName("eggs").orElseThrow(() -> new UsernameNotFoundException("Not found"));
        Assertions.assertEquals(allergenEntity.getName(), foundEntity.getName());

    }

    @Test
    public void testSeedAllergensShouldWorkProperly() {
        AllergenEntity allergenEntity=new AllergenEntity();
        allergenEntity.setName("eggs");
        Mockito.when(mockAllergenRepository.findByName("eggs")).thenReturn(Optional.empty());


        serviceToTest.seedAllergens(new String[]{"eggs", "milk"});
        Mockito.verify(mockAllergenRepository,Mockito.times(1) ).findByName("eggs");
        Mockito.verify(mockAllergenRepository,Mockito.times(1) ).findByName("milk");
        Mockito.verify(mockAllergenRepository,Mockito.times(0) ).findByName("nuts");
        Mockito.verify(mockAllergenRepository,Mockito.times(2) ).save(Mockito.any());


    }
}
