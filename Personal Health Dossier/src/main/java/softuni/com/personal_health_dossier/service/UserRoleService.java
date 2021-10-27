package softuni.com.personal_health_dossier.service;

import softuni.com.personal_health_dossier.model.entities.UserRoleEntity;
import softuni.com.personal_health_dossier.model.entities.enums.UserRoleEnum;

public interface UserRoleService {
    UserRoleEntity findByUserRole(UserRoleEnum userRoleEnum);
    void seedRoles();
}
