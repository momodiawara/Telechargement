package org.openjfx;

import org.openjfx.Observateur;

import java.util.ArrayList;

public abstract class Observe{

    public ArrayList<Observateur> observateurs = new ArrayList<>();

    public void addObservateur(Observateur ob){
        observateurs.add(ob);
    }
    public void removeObservateur(Observateur ob){
        observateurs.remove(ob);
    }

    public void removeObservateurs(){
        observateurs = new ArrayList<>();
    }

    public void notifyObservateurs(Observe o, Observe oo){
        for(Observateur ob : observateurs){
            ob.update(o, oo);
        }
    }

}
