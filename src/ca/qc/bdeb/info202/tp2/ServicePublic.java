package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public class ServicePublic extends Propriete {
    private final int MULTIPLICATEUR_LOYER = 10;

    public ServicePublic(String nom, String description, int prixAchat) {
        super(nom, description, prixAchat);
    }

    @Override
    public void payerLoyer(Joueur joueur) {
        this.setLoyer(joueur.getDeLance() * MULTIPLICATEUR_LOYER);
        super.payerLoyer(joueur);
    }
}
