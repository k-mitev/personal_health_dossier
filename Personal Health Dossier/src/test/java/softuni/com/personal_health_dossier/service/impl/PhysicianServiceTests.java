package softuni.com.personal_health_dossier.service.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.com.personal_health_dossier.model.entities.enums.MedicalSpecialty;
import softuni.com.personal_health_dossier.model.services.PhysicianEditProfileServiceModel;
import softuni.com.personal_health_dossier.repository.PhysicianRepository;
import softuni.com.personal_health_dossier.service.UserRoleService;

@ExtendWith(MockitoExtension.class)
public class PhysicianServiceTests {

    private PhysicianServiceImpl serviceToTest;
    private Gson gson;
    private ModelMapper modelMapper;
    @Mock
    PhysicianRepository mockPhysicianRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @Mock
    UserRoleService mockUserRoleService;


    @BeforeEach
    public void setup() {
        serviceToTest = new PhysicianServiceImpl(gson, mockPhysicianRepository,
                mockPasswordEncoder, mockUserRoleService, modelMapper);
    }

    @Test
    public void testUpdatePhysician() {
        PhysicianEditProfileServiceModel model = new PhysicianEditProfileServiceModel();
        model.setId(1L);
        model.setSpecialty(MedicalSpecialty.OTHER);
        model.setUsername("physician");
        model.setPassword("123");
        model.setImgUrl("");
        model.setRegion("Qmbol");
        model.setFirstName("");
        model.setMiddleName("");
        model.setLastName("");
        model.setMobileNumber("");

        serviceToTest.updatePhysician(model);

        Mockito.verify(mockPhysicianRepository, Mockito.times(1)).updatePhysicianEntity(Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyLong());
    }
}
