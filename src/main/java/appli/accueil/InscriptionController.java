package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Utilisateur;
import repository.UtilisateurRepository;

import java.io.IOException;

public class InscriptionController {
    @FXML
    private TextField confirmationMdpField;
    @FXML
    private Label connectedText;
    @FXML
    private TextField emailField;
    @FXML
    private Button inscriptionButton; //aaa
    @FXML
    private TextField mdpField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private Label welcomeText;

    @FXML
    private TextField nomRole1;

    UtilisateurRepository repo = new UtilisateurRepository();

    @FXML
    void redirectionInscription(ActionEvent event) {
        System.out.println("Nom :  " + nomField.getText());
        System.out.println("Prenom :  " + prenomField.getText());
        System.out.println("Email :  " + emailField.getText());
        System.out.println("Mot de Passe :  " + mdpField.getText());

        if (confirmationMdpField.getText().equals(mdpField.getText())) {
            connectedText.setText("Vous voilà Inscrit !");
            connectedText.setVisible(true);
        } else {
            connectedText.setText("Erreur : Les deux mot de passe sont différents !");
        }
        if (emailField.getText().equals("email")) {
            connectedText.setText("Erreur : Email déjà utilisé !");
        }

        if (confirmationMdpField.getText().isEmpty() || nomField.getText().isEmpty() || prenomField.getText().isEmpty() || emailField.getText().isEmpty() || mdpField.getText().isEmpty()) {
            connectedText.setText("Erreur : Champ(s) vide(s) !");
        }
    }

    @FXML
    void redirectionConnexion() throws IOException {
        StartApplication.changeScene("accueil/Login");
    }

    @FXML
    public void Inscription(ActionEvent event) {
        repo.ajouterUtilisateur(new Utilisateur(nomField.getText(), prenomField.getText(),emailField.getText(),mdpField.getText(),nomRole1.getText()));
        System.out.println("Vous êtes bien inscrit ! ");
        connectedText.setText("Vous voilà Inscrit !");
        connectedText.setVisible(true);


    }
}

