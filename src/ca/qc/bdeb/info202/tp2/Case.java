package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public abstract class Case implements Serializable {
    private String nom;
    private String description;

    public Case(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public abstract void effectuerAction(Joueur joueur);

    public abstract void survolerCase(Joueur joueur);

    public String getNom() {
        return this.nom;
    }

    public String getDescription() {
        return description;
    }
}
