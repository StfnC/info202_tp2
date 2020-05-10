package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

public class Partie implements Serializable {
    PlateauJeu plateauJeu;
    LinkedBlockingQueue<Joueur> joueurs;
    Joueur prochainJoueur;

    public Partie(PlateauJeu plateauJeu, LinkedBlockingQueue<Joueur> joueurs) {
        this.plateauJeu = plateauJeu;
        this.joueurs = joueurs;
        this.prochainJoueur = joueurs.peek();
    }

    public void deplacerJoueur(Joueur joueur) {
        int valeurDe = joueur.getDeLance();
        // Cette equation permet d'obtenir un indice dans les limites du tableau
        int positionFinale = (joueur.getIndexCaseActuelle() + valeurDe) % this.plateauJeu.getCasesPlateau().length;
        for (int i = joueur.getIndexCaseActuelle() + 1; i <= (joueur.getIndexCaseActuelle() + valeurDe); i++) {
            int nouvellePosition = i % this.plateauJeu.getCasesPlateau().length;
            if (nouvellePosition == positionFinale) {
                this.plateauJeu.getCasesPlateau()[nouvellePosition].effectuerAction(joueur);
            } else {
                this.plateauJeu.getCasesPlateau()[nouvellePosition].survolerCase(joueur);
            }
        }
        joueur.setIndexCaseActuelle(positionFinale);
    }
}
