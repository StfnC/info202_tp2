package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public class Terrain extends Propriete implements Serializable {
    public Terrain(String nom, String description, int prixAchat, int loyer) {
        super(nom, description, prixAchat);
        this.setLoyer(loyer);
    }
}
