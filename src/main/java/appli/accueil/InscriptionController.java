package appli.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import appli.StartApplication;

public class InscriptionController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    protected void handleRegister() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Tous les champs sont obligatoires !");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Les mots de passe ne correspondent pas !");
            return;
        }

        if (utilisateurRepository.getUtilisateurParEmail(email) != null) {
            errorLabel.setText("Cet email est déjà utilisé.");
            return;
        }

        String hashedPassword = passwordEncoder.encode(password);
        Utilisateur nouvelUtilisateur = new Utilisateur(nom, prenom, email, hashedPassword, "utilisateur");

        boolean success = utilisateurRepository.ajouterUtilisateur(nouvelUtilisateur);
        if (success) {
            errorLabel.setText("");
            try {
                StartApplication.changeScene("Login");
            } catch (Exception e) {
                System.out.println("Erreur navigation : " + e.getMessage());
            }
        } else {
            errorLabel.setText("Erreur lors de l'inscription.");
        }
    }

    @FXML
    protected void goToLogin() {
        try {
            StartApplication.changeScene("Login");
        } catch (Exception e) {
            System.out.println("Erreur navigation : " + e.getMessage());
        }
    }
}
