package ca.qc.bdeb.info202.tp2;

public class ServicePublic extends Propriete {
    private final int MULTIPLICATEUR_LOYER = 10;

    public ServicePublic(String nom, String description, int loyer) {
        super(nom, description, loyer);
    }

    @Override
    public void payerLoyer(Joueur joueur) {
        this.setLoyer(joueur.getDeLance() * MULTIPLICATEUR_LOYER);
        super.payerLoyer(joueur);
    }
}
