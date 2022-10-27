package org.openjfx;

import org.openjfx.Observe;
import org.openjfx.Telechargement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Library extends Observe {

    private ArrayList<Telechargement> lstTelechargement;

    public Library(){
        lstTelechargement = new ArrayList<Telechargement>();
    }

    public void add(String url) throws MalformedURLException {
        Telechargement tempTC = new Telechargement(url);
        lstTelechargement.add(tempTC);
        notifyObservateurs(this, tempTC);
    }

    public void add(String url, String dossier) throws MalformedURLException {
        Telechargement tempTC = new Telechargement(url, dossier);
        lstTelechargement.add(tempTC);
        notifyObservateurs(this, tempTC);
    }

    public void annulerTelechargement(int i){
        lstTelechargement.get(i).annuler();
    }

    public void pauserTelechargement(int i){
        lstTelechargement.get(i).pauser();
    }

    public void reprendreTelechargement(int i){
        lstTelechargement.get(i).lancer();
    }

    public ArrayList<Telechargement> getList(){
        return lstTelechargement;
    }


    public boolean someLibraryMethod() {
        return true;
    }

}

