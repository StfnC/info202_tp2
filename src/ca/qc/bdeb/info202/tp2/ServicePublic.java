package ca.qc.bdeb.info202.tp2;

public class ServicePublic extends Propriete {
    private final int MULTIPLICATEUR_LOYER = 10;

    public ServicePublic(String nom, String description, int valeur) {
        super(nom, description, valeur);
    }

    // Cette methode permet de contourner le probleme de l'achat d'une case ServicePublique
    @Override
    public void proposerAcheterPropriete(Joueur joueur) {
        this.payerLoyer(joueur);
    }

    @Override
    public void payerLoyer(Joueur joueur) {
        this.setLoyer(joueur.getDeLance() * MULTIPLICATEUR_LOYER);
        joueur.setArgent(joueur.getArgent() - this.getLoyer());
    }
}
