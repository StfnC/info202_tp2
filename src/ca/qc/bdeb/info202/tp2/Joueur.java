package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public class Joueur implements Serializable {
    private String nom;
    private int argent = 800;
    private EtatFinancier etatFinancier = EtatFinancier.POSITIF;
    private int deLance;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
        if (this.argent < 0) {
            this.etatFinancier = EtatFinancier.FAILLITE;
        }
    }

    public int getDeLance() {
        return deLance;
    }

    public void setDeLance(int deLance) {
        this.deLance = deLance;
    }

    public EtatFinancier getEtatFinancier() {
        return etatFinancier;
    }
}
