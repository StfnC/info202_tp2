package ca.qc.bdeb.info202.tp2;

import java.util.Scanner;

public abstract class Propriete extends Case {
    private Joueur proprietaire;
    private int prixAchat;
    private int loyer;

    public Propriete(String nom, String description, int prixAchat) {
        super(nom, description);
        this.prixAchat = prixAchat;
        this.proprietaire = null;
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

    @Override
    public void effectuerAction(Joueur joueur) {
        if (this.getProprietaire() != null) {
            this.payerLoyer(joueur);
        } else {
            this.proposerAcheterPropriete(joueur);
        }
    }

    public void payerLoyer(Joueur joueur) {
        if (!this.proprietaire.equals(joueur)) {
            if (joueur.getProprietesJoueur().size() > 1) {
                System.out.println("Vous devez payer " + this.getLoyer() * 2 + "$ a " + this.proprietaire);
                // On double le loyer si le joueur possede plus d'une propriete
                joueur.setArgent(joueur.getArgent() - (this.getLoyer() * 2));
            } else {
                System.out.println("Vous devez payer " + this.getLoyer() + "$ a " + this.proprietaire);
                joueur.setArgent(joueur.getArgent() - this.getLoyer());
            }
            /*
                Si le joueur n'a pas fait faillite en payant le loyer, le proprietaire recoit le montant du loyer,
                sinon, le proprietaire recoit le montant du loyer moins l'argent que le joueur n'a pu payer
             */
            if (joueur.getEtatFinancier().equals(EtatFinancier.POSITIF)) {
                this.proprietaire.setArgent(this.proprietaire.getArgent() + this.getLoyer());
            } else {
                this.proprietaire.setArgent(this.proprietaire.getArgent() + this.getLoyer() + joueur.getArgent());
            }
        }
    }

    public void proposerAcheterPropriete(Joueur joueur) {
        String reponseAchat = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez-vous acheter " + this.getNom() + " pour " + this.prixAchat + "$ (O ou N): ");
            reponseAchat = sc.nextLine();
        } while (!reponseAchat.equalsIgnoreCase("o") && !reponseAchat.equalsIgnoreCase("n"));

        if (reponseAchat.equalsIgnoreCase("o")) {
            this.acheterPropriete(joueur);
        }
    }

    public void acheterPropriete(Joueur acheteur) {
        if (acheteur.getArgent() >= this.prixAchat) {
            acheteur.setArgent(acheteur.getArgent() - this.prixAchat);
            this.proprietaire = acheteur;
            acheteur.ajouterPropriete(this);
        } else {
            System.out.println("Vous n'avez pas assez d'argent pour acheter cette propriete");
        }
    }

    @Override
    public void survolerCase(Joueur joueur) {
    }

    @Override
    public String toString() {
        String descriptionCase = (super.toString() + " " + this.getPrixAchat() + " " + this.getLoyer());
        if (proprietaire != null) {
            descriptionCase += " achetee par " + this.proprietaire;
        } else {
            descriptionCase += " sans proprietaire";
        }
        return descriptionCase;
    }
}
