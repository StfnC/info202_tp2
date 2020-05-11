package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {
    private final int ARGENT_DEPART = 400;

    private String nom;
    private int argent;
    private EtatFinancier etatFinancier = EtatFinancier.POSITIF;
    private int deLance;
    private int indexCaseActuelle;
    private ArrayList<Propriete> proprietesJoueur;

    public Joueur(String nom) {
        this.nom = nom;
        this.argent = ARGENT_DEPART;
        this.indexCaseActuelle = 0;
        this.proprietesJoueur = new ArrayList<>();
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

    public int getIndexCaseActuelle() {
        return this.indexCaseActuelle;
    }

    public void setIndexCaseActuelle(int indexCaseActuelle) {
        this.indexCaseActuelle = indexCaseActuelle;
    }

    public ArrayList<Propriete> getProprietesJoueur() {
        return this.proprietesJoueur;
    }

    public void ajouterPropriete(Propriete propriete) {
        this.proprietesJoueur.add(propriete);
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
