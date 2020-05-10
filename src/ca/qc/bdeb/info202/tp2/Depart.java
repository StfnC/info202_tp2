package ca.qc.bdeb.info202.tp2;

public class Depart extends Case {
    private int montantPourPasserDepart;

    public Depart(String nom, String description, int montantPourPasserDepart) {
        super(nom, description);
        this.montantPourPasserDepart = montantPourPasserDepart;
    }

    @Override
    public void effectuerAction(Joueur joueur) {
        this.survolerCase(joueur);
    }

    @Override
    public void survolerCase(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() + Math.abs(this.montantPourPasserDepart));
    }
}
