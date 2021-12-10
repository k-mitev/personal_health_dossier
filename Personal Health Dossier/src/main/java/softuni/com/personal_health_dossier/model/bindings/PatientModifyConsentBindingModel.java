package softuni.com.personal_health_dossier.model.bindings;

public class PatientModifyConsentBindingModel {
    private boolean consentForOrganDonationAfterDeath;

    public PatientModifyConsentBindingModel() {
    }

    public boolean isConsentForOrganDonationAfterDeath() {
        return consentForOrganDonationAfterDeath;
    }

    public void setConsentForOrganDonationAfterDeath(boolean consentForOrganDonationAfterDeath) {
        this.consentForOrganDonationAfterDeath = consentForOrganDonationAfterDeath;
    }
}
