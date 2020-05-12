package ca.qc.bdeb.info202.tp2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Tp2 {
    public static final String NOM_FICHIER_PLATEAU = "plateau.csv";
    public static final String NOM_FICHIER_SAUVEGARDE = "sauvegarde.bin";
    public static final int NB_TYPE_DE_CASES_AUTRE_QUE_DEPART = 4;
    public static final int NB_JOUEURS_MIN = 2;
    public static final int NB_JOUEURS_MAX = 3;

    public static ArrayList<String[]> lireFichierPlateau(String fichierPlateau) {
        ArrayList<String[]> lignesFichier = new ArrayList<>();
        try (
                FileReader fis = new FileReader(fichierPlateau);
                BufferedReader br = new BufferedReader(fis)) {
            String ligne = "";
            do {
                ligne = br.readLine();
                if (ligne != null) {
                    lignesFichier.add(ligne.split(","));
                }
            } while (ligne != null);
            // On enleve la 1ere ligne, elle ne contient pas de case
            lignesFichier.remove(0);
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier du plateau n'a pas ete trouve");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }

        return lignesFichier;
    }

    public static boolean validerNbCasesAutreQueDepart(int[] nbCasesAutreQueDepart) {
        boolean nbCasesValide = true;
        for (int nbCase : nbCasesAutreQueDepart) {
            nbCasesValide &= nbCase > 0;
        }
        return  nbCasesValide;
    }

    public static boolean validerTypesCasesFichier(ArrayList<String[]> lignesFichier) {
        // La 1ere case doit etre de type Depart
        boolean typeValide = lignesFichier.get(0)[0].equalsIgnoreCase("D");
        int[] nbCasesAutreQueDepart = new int[NB_TYPE_DE_CASES_AUTRE_QUE_DEPART];

        for (int i = 1; i < lignesFichier.size(); i++) {
            String[] ligne = lignesFichier.get(i);
            String typeLigne = ligne[0];
            // Il ne doit y avoir qu'une seule case de Depart
            typeValide &= !typeLigne.equalsIgnoreCase("D");
            if (typeLigne.equalsIgnoreCase("T")) {
                nbCasesAutreQueDepart[0]++;
            } else if (typeLigne.equalsIgnoreCase("Tx")) {
                nbCasesAutreQueDepart[1]++;
            } else if (typeLigne.equalsIgnoreCase("P")) {
                nbCasesAutreQueDepart[2]++;
            } else if (typeLigne.equalsIgnoreCase("SP")) {
                nbCasesAutreQueDepart[3]++;
            }
        }
        typeValide &= validerNbCasesAutreQueDepart(nbCasesAutreQueDepart);
        return typeValide;
    }

    public static boolean validerFichierPleateau(ArrayList<String[]> lignesFichier) {
        boolean fichierValide = lignesFichier.size() == PlateauJeu.getNbCasesPlateau();

        if (fichierValide) {
            int tailleLigne = lignesFichier.get(0).length;
            fichierValide &= validerTypesCasesFichier(lignesFichier);
            for (String[] ligne : lignesFichier) {
                fichierValide &= ligne.length == tailleLigne;
                try {
                    Integer.parseInt(ligne[3]);
                    Integer.parseInt(ligne[4]);
                } catch (NumberFormatException nfe) {
                    fichierValide = false;
                }
            }
        }

        return fichierValide;
    }

    public static Case[] creerTableauCases(ArrayList<String[]> lignesFichier) {
        Case[] tableauCases = new Case[PlateauJeu.getNbCasesPlateau()];
        for (int i = 0; i < tableauCases.length; i++) {
            tableauCases[i] = creerCase(lignesFichier.get(i));
        }
        return tableauCases;
    }

    public static Case creerCase(String[] infoCase) {
        String type = infoCase[0].toLowerCase();
        String nom = infoCase[1];
        String description = infoCase[2];
        int valeur = Integer.parseInt(infoCase[3]);
        int loyer = Integer.parseInt(infoCase[4]);

        Case nouvelleCase = null;

        if (type.equalsIgnoreCase("D")) {
            nouvelleCase = new Depart(nom, description, loyer);
        } else if (type.equalsIgnoreCase("T")) {
            nouvelleCase = new Terrain(nom, description, valeur, loyer);
        } else if (type.equalsIgnoreCase("Tx")) {
            nouvelleCase = new Taxe(nom, description, loyer);
        } else if (type.equalsIgnoreCase("P")) {
            nouvelleCase = new StationnementGratuit(nom, description);
        } else if (type.equalsIgnoreCase("SP")) {
            nouvelleCase = new ServicePublic(nom, description, valeur);
        }

        return nouvelleCase;
    }

    public static void afficherMenuPrincipal() {
        System.out.println("***************** Jeu de Monopoly *****************\n" +
                "1) Charger la partie de sauvegarde\n" +
                "2) Démarrer une nouvelle partie\n" +
                "3) Quitter");
    }

    public static void afficherMenuDebutTour(Joueur prochainJoueur) {
        System.out.println("C'est au tour de " + prochainJoueur + "\n" +
                "1) Lancer le dé\n" +
                "2) Sauvegarder et quitter\n" +
                "3) Mettre fin à la partie et quitter");
    }

    public static void sauvegarderPartie(Partie partie) {
        try (FileOutputStream fos = new FileOutputStream(NOM_FICHIER_SAUVEGARDE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(partie);
        } catch (FileNotFoundException e) {
            System.out.println("Pas de fichier de sauvegarde");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    public static Partie chargerSauvegardePartie() {
        Partie partie = null;
        try (FileInputStream fis = new FileInputStream(NOM_FICHIER_SAUVEGARDE);
                ObjectInputStream ois = new ObjectInputStream(fis)){
            partie = (Partie) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Pas de fichier de sauvegarde");
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la sauvegarde");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement de la sauvegarde");
        }
        return partie;
    }

    public static String demanderEntreeAuJoueur(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        return sc.nextLine();
    }

    public static String demanderChoixAuJoueur(String message, String[] options) {
        String reponse = "";
        do {
            reponse = demanderEntreeAuJoueur(message);
        } while (!Arrays.asList(options).contains(reponse.toUpperCase()));
        return reponse;
    }

    public static boolean jouerTour(Partie partie, String messageOptions, String[] options) {
        boolean jouer = true;
        Joueur joueur = partie.getProchainJoueur();
        partie.afficherEtatJoueurs();
        partie.afficherPlateau();
        afficherMenuDebutTour(joueur);
        String choixJoueur = demanderChoixAuJoueur(messageOptions, options);
        switch (choixJoueur) {
            case "1":
                int valeurDe = De.jeter();
                joueur.setDeLance(valeurDe);
                System.out.println("Vous avez obtenu: " + valeurDe);
                partie.deplacerJoueur(joueur);
                partie.faireAvancerQueue();
                for (Joueur joueurDansPartie : partie.getJoueurs()) {
                    if (joueurDansPartie.getEtatFinancier().equals(EtatFinancier.FAILLITE)) {
                        determinerGagnant(partie);
                        jouer = false;
                    }
                }
                break;
            case "2":
                sauvegarderPartie(partie);
                jouer = false;
                break;
            case "3":
                determinerGagnant(partie);
                jouer = false;
                break;
            default:
                System.out.println("Il y a une probleme");
        }
        return jouer;
    }

    public static void determinerGagnant(Partie partie) {
        Joueur gagnant = partie.getProchainJoueur();
        ArrayList<Joueur> perdants = new ArrayList<>(partie.getJoueurs());

        for (Joueur joueur : partie.getJoueurs()) {
            if (joueur.getArgent() > gagnant.getArgent()) {
                gagnant = joueur;
            }
        }

        perdants.remove(gagnant);
        System.out.println("Le gagnant est " + gagnant + " avec " + gagnant.getArgent() + "$");

        for (Joueur perdant : perdants) {
            System.out.println(perdant + " a perdu avec " + perdant.getArgent() + "$");
        }
    }

    public static LinkedBlockingQueue<Joueur> creerJoueurs() {
        LinkedBlockingQueue<Joueur> joueurs = new LinkedBlockingQueue<>();
        String[] optionsOuiNon = {"O", "N"};
        String veutAjouterJoueur = "O";
        do {
            if (joueurs.size() < NB_JOUEURS_MIN) {
                String nomJoueur = demanderEntreeAuJoueur("Entrez le nom du joueur: ");
                joueurs.add(new Joueur(nomJoueur));
            } else {
                veutAjouterJoueur = demanderChoixAuJoueur("Voulez-vous ajouter un autre joueur (O/N): ", optionsOuiNon);
                if (veutAjouterJoueur.equalsIgnoreCase("O")) {
                    String nomJoueur = demanderEntreeAuJoueur("Entrez le nom du joueur: ");
                    joueurs.add(new Joueur(nomJoueur));
                }
            }
        } while (joueurs.size() < NB_JOUEURS_MAX && veutAjouterJoueur.equalsIgnoreCase("O"));

        return joueurs;
    }

    public static Partie demarerNouvellePartie() {
        Partie partie = null;
        LinkedBlockingQueue<Joueur> joueurs = creerJoueurs();
        ArrayList<String[]> lignesPlateau = lireFichierPlateau(NOM_FICHIER_PLATEAU);
        System.out.println("Chargement du plateau de jeu ... OK");
        if (validerFichierPleateau(lignesPlateau)) {
            System.out.println("Validation du plateau de jeu ... OK");
            Case[] tableauCases = creerTableauCases(lignesPlateau);
            PlateauJeu plateauJeu = new PlateauJeu(tableauCases);
            partie = new Partie(plateauJeu, joueurs);
        } else {
            System.out.println("Le fichier du plateau n'est pas valide");
        }

        return partie;
    }

    public static void main(String[] args) {
        boolean jouer = true;
        String messageTroisOptions = "Entrez votre choix (1, 2, 3): ";
        String[] choixMenuTroisOptions = {"1", "2", "3"};
        afficherMenuPrincipal();
        Partie partie;
        String choixJeu = demanderChoixAuJoueur(messageTroisOptions, choixMenuTroisOptions);
        switch (choixJeu) {
            case "1":
                partie = chargerSauvegardePartie();
                if (partie != null) {
                    System.out.println("Chargement de la sauvegarde ... OK");
                    do {
                        jouer = jouerTour(partie, messageTroisOptions, choixMenuTroisOptions);
                    } while (jouer);
                } else {
                    System.out.println("Erreur lors de la creation de la partie");
                }
                break;
            case "2":
                partie = demarerNouvellePartie();
                if (partie != null) {
                    do {
                        jouer = jouerTour(partie, messageTroisOptions, choixMenuTroisOptions);
                    } while (jouer);
                } else {
                    System.out.println("Erreur lors de la creation de la partie");
                }
                break;
            case "3":
                System.out.println("Au revoir");
                break;
            default:
                System.out.println("Il y a une probleme");
        }
    }
}
