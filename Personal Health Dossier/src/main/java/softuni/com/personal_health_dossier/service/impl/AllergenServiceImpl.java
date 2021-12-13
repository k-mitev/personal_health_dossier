package softuni.com.personal_health_dossier.service.impl;

import org.springframework.stereotype.Service;
import softuni.com.personal_health_dossier.model.entities.AllergenEntity;
import softuni.com.personal_health_dossier.repository.AllergenRepository;
import softuni.com.personal_health_dossier.service.AllergenService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
//@Transactional
public class AllergenServiceImpl implements AllergenService {
    private final AllergenRepository allergenRepository;

    public AllergenServiceImpl(AllergenRepository allergenRepository) {
        this.allergenRepository = allergenRepository;
    }

    @Override
    public void seedAllergens(String[] allAllergens) {
        for (String allergen : allAllergens) {
            if (this.findByAllergenName(allergen).isEmpty()) {
                AllergenEntity allergenEntity = new AllergenEntity();
                allergenEntity.setName(allergen);
                this.allergenRepository.save(allergenEntity);
            }

        }
    }

    @Override
    public Optional<AllergenEntity> findByAllergenName(String name) {
        return this.allergenRepository.findByName(name);
    }
}
