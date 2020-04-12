package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.Scanner;

public abstract class Propriete extends Case  implements Serializable {
    private Joueur proprietaire = null;
    private int prixAchat;
    private int loyer;

    public Propriete(String nom, String description, int prixAchat) {
        super(nom, description);
        this.prixAchat = prixAchat;
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
        // TODO: -Handle the case where the player that lands is the owner, so he doesn't bankrupt by paying himself
        joueur.setArgent(joueur.getArgent() - this.getLoyer());
        /*
            Si le joueur n'a pas fait faillite en payant le loyer, le proprietaire recoit le montant du loyer,
            sinon, le proprietaire recoit le montant du loyer moins l'argent que le joueur n'a pu payer
         */
        if (joueur.getEtatFinancier().equals(EtatFinancier.POSITIF)) {
            this.getProprietaire().setArgent(this.getLoyer());
        } else {
            this.getProprietaire().setArgent(this.getLoyer() + joueur.getArgent());
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

    }
}
