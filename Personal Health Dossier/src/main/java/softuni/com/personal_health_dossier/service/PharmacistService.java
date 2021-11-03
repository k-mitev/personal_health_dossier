package softuni.com.personal_health_dossier.service;

import java.io.IOException;

public interface PharmacistService {
    void seedPharmacistsFromJson() throws IOException;

    String readFileContent() throws IOException;
}
