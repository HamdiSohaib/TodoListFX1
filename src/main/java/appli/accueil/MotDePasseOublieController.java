package appli.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import repository.UtilisateurRepository;
import service.EmailService;

public class MotDePasseOublieController {

    @FXML private TextField emailField;
    @FXML private TextField codeField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label infoLabel;

    private Utilisateur utilisateurTrouve;
    private String codeVerif;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    @FXML
    protected void envoyerCode() {
        String email = emailField.getText();
        utilisateurTrouve = utilisateurRepository.getUtilisateurParEmail(email);
        if (utilisateurTrouve == null) {
            infoLabel.setText("Aucun compte trouvé avec cet email.");
            return;
        }

        codeVerif = EmailService.genererCode();
        EmailService.envoyerEmail(email, "Réinitialisation du mot de passe", "Votre code : " + codeVerif);
        infoLabel.setText("Code envoyé à : " + email);
    }

    @FXML
    protected void verifierCodeEtChangerMDP() {
        if (!codeField.getText().equals(codeVerif)) {
            infoLabel.setText("Code invalide !");
            return;
        }

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.equals(confirmPassword)) {
            infoLabel.setText("Les mots de passe ne correspondent pas !");
            return;
        }

        utilisateurTrouve.setMdp(newPassword);
        utilisateurRepository.mettreAJourUtilisateur(utilisateurTrouve);
        infoLabel.setText("Mot de passe mis à jour !");
    }
}
