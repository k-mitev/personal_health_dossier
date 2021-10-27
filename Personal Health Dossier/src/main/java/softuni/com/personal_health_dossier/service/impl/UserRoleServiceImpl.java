package softuni.com.personal_health_dossier.service.impl;

import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;
import softuni.com.personal_health_dossier.repository.UserRoleRepository;
import softuni.com.personal_health_dossier.service.UserRoleService;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRoleEntity findByUserRole(UserRoleEnum userRoleEnum) {
        return this.userRoleRepository.findByRole(userRoleEnum);
    }

    @Override
    public void seedRoles() {
        if (this.userRoleRepository.count() == 0) {
            Arrays.stream(UserRoleEnum.values()).forEach(role -> {
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                userRoleEntity.setRole(role);
                this.userRoleRepository.save(userRoleEntity);
            });

        }
    }
}
