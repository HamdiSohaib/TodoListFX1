package appli.user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import repository.UtilisateurRepository;
import appli.StartApplication;

public class ModificationUserController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField roleField;
    @FXML private Label messageLabel;

    private Utilisateur utilisateurSel;
    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

    public void initData(Utilisateur utilisateur) {
        this.utilisateurSel = utilisateur;
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        emailField.setText(utilisateur.getEmail());
        roleField.setText(utilisateur.getRole());
        emailField.setDisable(true);
    }

    @FXML
    protected void enregistrerModifications() {
        utilisateurSel.setNom(nomField.getText());
        utilisateurSel.setPrenom(prenomField.getText());
        utilisateurSel.setRole(roleField.getText());

        boolean ok = utilisateurRepository.mettreAJourUtilisateur(utilisateurSel);
        if (ok) {
            messageLabel.setText("Utilisateur modifié avec succès !");
            try {
                StartApplication.changeScene("user/GestionUser");
            } catch (Exception e) {
                System.out.println("Erreur navigation : " + e.getMessage());
            }
        } else {
            messageLabel.setText("Erreur lors de la modification.");
        }
    }

    @FXML
    protected void retourGestion() {
        try {
            StartApplication.changeScene("user/GestionUser");
        } catch (Exception e) {
            System.out.println("Erreur retour : " + e.getMessage());
        }
    }
}
