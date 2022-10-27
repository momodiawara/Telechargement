package org.openjfx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;



public class Table extends TableView<Telechargement> implements Observateur {


    public ObservableList<Telechargement> data;

    public Table(){
        super();

        TableColumn<Telechargement, String >  titleCol = new TableColumn<Telechargement, String >  ("Nom");
        titleCol.setCellValueFactory(new PropertyValueFactory("nom"));
        titleCol.setSortable(false);

        TableColumn<Telechargement, String >   titleColaa = new TableColumn<Telechargement, String >  ("Statut");
        titleColaa.setCellValueFactory(new PropertyValueFactory("statut"));

        TableColumn<Telechargement, String >   titleColaaa = new TableColumn<Telechargement, String >  ("Taille");
        titleColaaa.setCellValueFactory(new PropertyValueFactory("taille"));

        TableColumn<Telechargement, String >   titleColaaaa = new TableColumn<Telechargement, String >  ("Telechargé");
        titleColaaaa.setCellValueFactory(new PropertyValueFactory("telecharge"));

        TableColumn<Telechargement,Double>  titleCola= new TableColumn<Telechargement,Double> ("Pourcentage");
        titleCola.setCellValueFactory(new PropertyValueFactory("pourcentage"));
        titleCola.setCellFactory(ProgressBarTableCell.<Telechargement> forTableColumn());

        getColumns().addAll(titleCol,titleColaa,titleCola,titleColaaa,titleColaaaa);






    }

    public void link(ArrayList<Telechargement> lst){
        data = FXCollections.observableList(lst);
        setItems(data);
    }

    public Table(ArrayList<Telechargement> lst) {
        super();
        data = FXCollections.observableList(lst);
        //setItems(data);
    }

    public void supprimer(Telechargement tempTC){
        data.remove(tempTC);
    }

    @Override
    public void update(Observe observe, Observe oo) {
        if(observe instanceof Library){
            ((Telechargement)oo).addObservateur((Observateur)this);
            getItems().set(getItems().size()-1, (Telechargement) oo);
            this.refresh();
        }else{
            this.refresh();
        }
    }


}
