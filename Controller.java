package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane body;
    @FXML
    private TextField url;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnDossier;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField folder;

    private String tempURL;
    private String tempDossier = "";
    private Table table;
    private Library gestionnaire;
    private Telechargement chose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gestionnaire = new Library();

        table = new Table();
        gestionnaire.addObservateur(table);

        table.link(gestionnaire.getList());
        table.setPrefWidth(1100);
        table.setLayoutY(79);
        table.setLayoutX(0);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setColumnResizePolicy(Table.CONSTRAINED_RESIZE_POLICY);


        final DirectoryChooser directoryChooser = new DirectoryChooser();

        btnAjouter.setOnAction(e->{
            if(url.getText() != null){
                tempURL = url.getText();
                try {
                    if(tempDossier.length() != 0)
                        gestionnaire.add(tempURL, tempDossier);
                    else
                        gestionnaire.add(tempURL);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                url.clear();
            }
        });

        btnDossier.setOnAction(e->{
            Stage primaryStage = (Stage) body.getScene().getWindow();
            File dir = directoryChooser.showDialog(primaryStage);
            if(dir != null){
                tempDossier = dir.getAbsolutePath();
                folder.setText(tempDossier);
            }
        });

        table.setOnMouseClicked(e->{
            if(table.getSelectionModel().getSelectedItem() != null){
                chose = table.getSelectionModel().getSelectedItem();
                System.out.println(chose.getNom());
            }
        });

        body.getChildren().add(table);

    }

}
