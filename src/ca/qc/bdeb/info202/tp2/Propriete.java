package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public abstract class Propriete extends Case  implements Serializable {
    private Joueur proprietaire = null;
    private int prixAchat;
    private int loyer;

    public Propriete(String nom, String description, int prixAchat) {
        super(nom, description);
        this.prixAchat = prixAchat;
    }

    public int getPrixAchat() {
        return this.prixAchat;
    }

    public Joueur getProprietaire() {
        return this.proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
    }

    public int getLoyer() {
        return this.loyer;
    }

    public void setLoyer(int loyer) {
        this.loyer = loyer;
    }
}
