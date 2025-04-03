package appli.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import repository.UtilisateurRepository;
import model.Utilisateur;
import session.SessionUtilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import appli.StartApplication;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    protected void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Utilisateur utilisateur = utilisateurRepository.getUtilisateurParEmail(email);

        if (utilisateur != null && passwordEncoder.matches(password, utilisateur.getMdp())) {
            SessionUtilisateur.getInstance().sauvegardeSession(utilisateur);
            errorLabel.setVisible(false);
            try {
                StartApplication.changeScene("Accueil");
            } catch (Exception e) {
                System.out.println("Erreur de redirection : " + e.getMessage());
            }
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    protected void goToRegister() {
        try {
            StartApplication.changeScene("Inscription");
        } catch (Exception e) {
            System.out.println("Erreur navigation : " + e.getMessage());
        }
    }
}
