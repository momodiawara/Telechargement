package org.openjfx;

import com.sun.javafx.tk.Toolkit;
import javafx.concurrent.Task;
import org.openjfx.Observe;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Telechargement extends Observe implements Runnable {

    static enum Statut{
        ANNULE,
        PAUSE,
        TERMINE,
        TELECHARGEMENT,
        ERREUR;
    }

    private static final int TAILLE_MAX = 1024;

    private URL url;
    private String nom, dossierCible;
    private long tailleTotale, donneesTelechargees;
    private double pourcentage;
    private Statut statut;
    private boolean checked;

    public Telechargement(String nUrl) throws MalformedURLException {
        url = checkUrl(nUrl);
        tailleTotale = -1;
        donneesTelechargees = 0;
        statut = Statut.TELECHARGEMENT;
        dossierCible = "";
        lancer();
    }

    public Telechargement(String  nUrl,String nDossier) throws MalformedURLException {
        url = checkUrl(nUrl);
        tailleTotale = -1;
        donneesTelechargees = 0;
        statut = Statut.TELECHARGEMENT;
        dossierCible = nDossier;
        lancer();
    }
    public String getNom(){
        return nom;
    }

    public String getStatut(){
        return statut.toString();
    }

    public String getDossier(){
        return dossierCible;
    }

    public long getTaille(){
        return tailleTotale;
    }

    public long getTelecharge(){
        return donneesTelechargees;
    }

    public double getPourcentage(){
        return pourcentage;
    }

    public void lancer(){
        if(checked){
            System.out.println("Lancer");
            Thread temp = new Thread(this);
            temp.start();
        }else{
            erreur();
        }
    }

    public void pauser(){
        changerStatut(Statut.PAUSE);
    }

    public void reprendre(){
        changerStatut(Statut.TELECHARGEMENT);
        lancer();
    }

    public void annuler(){
        changerStatut(Statut.ANNULE);
    }

    public void erreur(){
        changerStatut(Statut.ERREUR);
    }

    @Override
    public void run() {
        RandomAccessFile tempFile = null;
        InputStream tempStream = null;
        int speed = 0;
        try {

            HttpURLConnection tempCon = (HttpURLConnection) url.openConnection();

            tempCon.setConnectTimeout(1000);

            tempCon.setRequestProperty("Range", "bytes=" + donneesTelechargees + "-");

            tempCon.connect();

            if((tempCon.getResponseCode() / 100) != 2){

                //  HTTP/1.0 200 OK
                //  HTTP/1.0 404 Error Not Found
                erreur();
            }

            int tempLength = tempCon.getContentLength();

            if(tempLength < 1){
                erreur();


            }else{
                tailleTotale = tempLength;
            }

            if(dossierCible.length() > 0 ){
                dossierCible += "\\";
            }
            String tempPath = dossierCible + nom;
            System.out.println("nommm" + nom);
            tempFile = new RandomAccessFile(tempPath, "rw");


            tempFile.seek(donneesTelechargees);

            tempStream = tempCon.getInputStream();

            while(statut == Statut.TELECHARGEMENT){

                byte[] data;
                if(tailleTotale - donneesTelechargees > TAILLE_MAX){
                    data = new byte[TAILLE_MAX];
                }else{
                    data = new byte[(int)(tailleTotale - donneesTelechargees)];
                }

                int tempData = tempStream.read(data);

                if(tempData == -1){
                    break;
                }

                tempFile.write(data,0, tempData);

                donneesTelechargees += tempData;

                pourcentage = ((double) donneesTelechargees / (double)tailleTotale) ;
                changerStatut(Statut.TELECHARGEMENT);
                notifyObservateurs(this, this);
            }

            if(statut == Statut.TELECHARGEMENT){
                changerStatut(Statut.TERMINE);
                notifyObservateurs(this, this);
            }
        }catch (Exception e){
            erreur();
        }finally {

            // Close file.
            if (tempFile != null) {
                try {
                    tempFile.close();
                } catch (Exception e) {
                    erreur();
                    notifyObservateurs(this, this);
                }
            }

            // Close connection to server.
            if (tempStream != null) {
                try {
                    tempStream.close();
                } catch (Exception e) {
                    notifyObservateurs(this, this);
                }
            }

        }
    }

    public void setNom(String str){
        nom = str;
        notifyObservateurs(this, this);
    }

    public URL checkUrl(String url) throws MalformedURLException {
        URL result = null;
        if(url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")){
            System.out.println("Checked");
            try{
                result = new URL(url);
                String tempStr = result.getPath();
                setNom(tempStr.substring(tempStr.lastIndexOf('/')+1, tempStr.length())) ;
                System.out.println(nom);
                checked = true;
                System.out.println("URL CHECCKED");
            }catch (Exception ex  ){
                System.out.println("Erreur checkkkkd");
            }
        }

        return result;
    }

    public void changerStatut(Statut st){
        statut = st;
        notifyObservateurs(this, this);
    }

}
