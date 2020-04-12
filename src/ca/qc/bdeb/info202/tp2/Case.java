package ca.qc.bdeb.info202.tp2;

public abstract class Case {
    private String nom;
    private String description;

    public Case(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public abstract void effectuerAction(Joueur joueur);

    public abstract void survolerCase(Joueur joueur);
}
