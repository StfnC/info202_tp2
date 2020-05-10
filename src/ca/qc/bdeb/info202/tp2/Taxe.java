package ca.qc.bdeb.info202.tp2;

public class Taxe extends Case {
    private final float POURCENTAGE_TAXE_SANS_ARRETER = 0.1f;
    private int montantTaxe;
    private int montantTaxeSansArreter;

    public Taxe(String nom, String description, int montantTaxe) {
        super(nom, description);
        this.montantTaxe = montantTaxe;
        this.montantTaxeSansArreter = (int) (montantTaxe * POURCENTAGE_TAXE_SANS_ARRETER);
    }

    public int getMontantTaxe() {
        return this.montantTaxe;
    }

    public int getMontantTaxeSansArreter() {
        return this.montantTaxeSansArreter;
    }

    @Override
    public void effectuerAction(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() - this.montantTaxe);
    }

    @Override
    public void survolerCase(Joueur joueur) {
        joueur.setArgent(joueur.getArgent() - this.montantTaxeSansArreter);
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.getMontantTaxe();
    }
}
