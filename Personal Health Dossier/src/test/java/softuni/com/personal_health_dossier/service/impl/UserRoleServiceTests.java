package softuni.com.personal_health_dossier.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.UserRoleRepository;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTests {
    private UserRoleServiceImpl serviceToTest;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @BeforeEach
    public void setup() {
        serviceToTest = new UserRoleServiceImpl(mockUserRoleRepository);
    }

    @Test
    public void testFindByUserRole() {
        UserRoleEntity entity = new UserRoleEntity();
        entity.setRole(UserRoleEnum.PHYSICIAN);

        Mockito.when(mockUserRoleRepository.findByRole(UserRoleEnum.PHYSICIAN))
                .thenReturn(entity);

        UserRoleEntity byUserRole = serviceToTest.findByUserRole(UserRoleEnum.PHYSICIAN);
        Assertions.assertEquals(byUserRole.getRole(), entity.getRole());
    }


}
