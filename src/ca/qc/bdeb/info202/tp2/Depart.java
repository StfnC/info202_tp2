package ca.qc.bdeb.info202.tp2;

public class Depart extends Case {
    private final int MONTANT_RECU_POUR_PASSER_DEPART = 25;

    public Depart(String nom, String description) {
        super(nom, description);
    }

    @Override
    public void effectuerAction(Joueur joueur) {
        this.survolerCase(joueur);
    }

    @Override
    public void survolerCase(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + this.MONTANT_RECU_POUR_PASSER_DEPART);
    }
}
