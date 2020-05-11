package ca.qc.bdeb.info202.tp2;

public class Terrain extends Propriete {
    public Terrain(String nom, String description, int prixAchat, int loyer) {
        super(nom, description, prixAchat);
        this.setLoyer(loyer);
    }
}
