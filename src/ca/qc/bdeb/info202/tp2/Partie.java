package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

public class Partie implements Serializable {
    PlateauJeu plateauJeu;
    LinkedBlockingQueue<Joueur> joueurs;
    Joueur prochainJoueur;

    public Partie(PlateauJeu plateauJeu, LinkedBlockingQueue<Joueur> joueurs, Joueur prochainJoueur) {
        this.plateauJeu = plateauJeu;
        this.joueurs = joueurs;
        this.prochainJoueur = prochainJoueur;
    }
}