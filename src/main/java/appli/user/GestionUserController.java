package appli.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Utilisateur;
import repository.UtilisateurRepository;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {

    @FXML private TableView<Utilisateur> tableauUser;
    @FXML private Button btnSupprimer;

    private final UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
    private final ObservableList<Utilisateur> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[][] colonnes = {
                {"ID", "idUtilisateur"},
                {"Nom", "nom"},
                {"Prénom", "prenom"},
                {"Email", "email"},
                {"Rôle", "role"},
        };

        for (String[] col : colonnes) {
            TableColumn<Utilisateur, String> tableCol = new TableColumn<>(col[0]);
            tableCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(col[1]));
            tableauUser.getColumns().add(tableCol);
        }

        data.addAll(utilisateurRepository.getTousLesUtilisateurs());
        tableauUser.setItems(data);
        btnSupprimer.setDisable(true);
    }

    @FXML
    void cliqueTableauEvent(MouseEvent event) {
        Utilisateur selected = tableauUser.getSelectionModel().getSelectedItem();
        btnSupprimer.setDisable(selected == null);

        if (event.getClickCount() == 2 && selected != null) {
            try {
                appli.StartApplication.changeScene("user/modificationUser");
                ModificationUserController controller = (ModificationUserController)
                        appli.StartApplication.getControllerFromStage();
                assert controller != null;
                controller.initData(selected);
            } catch (Exception e) {
                System.out.println("Erreur de redirection : " + e.getMessage());
            }
        }
    }

    @FXML
    void supprimerUtilisateur() throws SQLException {
        Utilisateur selected = tableauUser.getSelectionModel().getSelectedItem();
        try {
            if (selected != null)
                if (utilisateurRepository.supprimerUtilisateurParEmail(selected.getEmail())) {
                    tableauUser.getItems().remove(selected);
                    btnSupprimer.setDisable(true);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
