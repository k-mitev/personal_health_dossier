package softuni.com.personal_health_dossier.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.com.personal_health_dossier.service.PhysicianService;
import softuni.com.personal_health_dossier.service.UserRoleService;

@Component
public class DBInit implements CommandLineRunner {
    private final UserRoleService userRoleService;
    private final PhysicianService physicianService;

    public DBInit(UserRoleService userRoleService, PhysicianService physicianService) {
        this.userRoleService = userRoleService;
        this.physicianService = physicianService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedRoles();
        this.physicianService.seedAllPhysicians();
    }
}
