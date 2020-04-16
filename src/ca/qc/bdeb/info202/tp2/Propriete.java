package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
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
            joueur.setArgent(joueur.getArgent() - this.getLoyer());
            /*
                Si le joueur n'a pas fait faillite en payant le loyer, le proprietaire recoit le montant du loyer,
                sinon, le proprietaire recoit le montant du loyer moins l'argent que le joueur n'a pu payer
             */
            if (joueur.getEtatFinancier().equals(EtatFinancier.POSITIF)) {
                this.proprietaire.setArgent(this.getLoyer());
            } else {
                this.proprietaire.setArgent(this.getLoyer() + joueur.getArgent());
            }
        }
    }

    public void proposerAcheterPropriete(Joueur joueur) {
        // TODO: -Comment this method
        String reponseAchat = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Voulez-vous acheter " + this.toString() + " pour " + this.prixAchat + "$ (O ou N: ");
            reponseAchat = sc.nextLine();
        } while (!reponseAchat.equalsIgnoreCase("o") || !reponseAchat.equalsIgnoreCase("n"));

        if (reponseAchat.equalsIgnoreCase("o")) {
            this.acheterPropriete(joueur);
        }
    }

    public void acheterPropriete(Joueur acheteur) {
        acheteur.setArgent(acheteur.getArgent() - this.prixAchat);
        this.proprietaire = acheteur;
    }

    @Override
    public void survolerCase(Joueur joueur) {
        // TODO: -Maybe do something here
    }
}
