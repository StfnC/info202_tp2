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
            if (joueur.getNbProprietes() > 1) {
                // On double le loyer si le joueur possede plus de 2 proprietes
                joueur.setArgent(joueur.getArgent() - (this.getLoyer() * 2));
            } else {
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
            System.out.println("Voulez-vous acheter " + this.toString() + " pour " + this.prixAchat + "$ (O ou N): ");
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
            acheteur.setNbProprietes(acheteur.getNbProprietes() + 1);
        } else {
            System.out.println("Vous n'avez pas assez d'argent pour acheter cette propriete ");
        }
    }

    @Override
    public void survolerCase(Joueur joueur) {
    }
}
