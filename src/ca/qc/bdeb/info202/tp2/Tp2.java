package ca.qc.bdeb.info202.tp2;

import java.io.*;
import java.util.ArrayList;

public class Tp2 {
    public static final String NOM_FICHIER_PLATEAU = "plateau.csv";
    public static final int NB_TYPE_DE_CASES_AUTRE_QUE_DEPART = 4;

    public static ArrayList<String[]> lireFichierPlateau(String fichierPlateau) {
        // TODO: -Do this better
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
            // TODO: -Modify the catch blocks
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        int tailleLigne = lignesFichier.get(0).length;
        fichierValide &= validerTypesCasesFichier(lignesFichier);

        if (fichierValide) {
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
        // TODO: -Find efficient way to read from csv and create board tile
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

    public static void main(String[] args) {
        ArrayList<String[]> lignesFichier = lireFichierPlateau(NOM_FICHIER_PLATEAU);
        System.out.println(validerFichierPleateau(lignesFichier));
        Case[] casesPlateau = creerTableauCases(lignesFichier);
        PlateauJeu plateauJeu = new PlateauJeu(casesPlateau);
        Joueur stefan = new Joueur("stefan");
        for (Case caseActuelle : casesPlateau) {
            caseActuelle.effectuerAction(stefan);
        }

//        for (String[] valeurs : lignesFichier) {
//            for (String colonne : valeurs) {
//                System.out.print(colonne);
//            }
//            System.out.println();
//        }
//        Joueur stefan = new Joueur("stefan");
//        Joueur joueur2 = new Joueur("joueur2");
//        Terrain terrain1 = new Terrain("terrain1", "Un terrain", 100, 20);
//        Taxe taxe1 = new Taxe("Taxe 1", "une taxe", 50);
//        Case[] cases = new Case[2];
//        cases[0] = terrain1;
//        cases[1] = taxe1;
//        for (Case caseJeu : cases) {
//            if (caseJeu instanceof Propriete) {
//                ((Propriete) caseJeu).effectuerAction(stefan);
//            }
//        }
//        for (Case caseJeu : cases) {
//            if (caseJeu instanceof Propriete) {
//                ((Propriete) caseJeu).effectuerAction(joueur2);
//            }
//        }
//        System.out.println(stefan.getArgent());
//        terrain1.effectuerAction(stefan);
//        terrain1.effectuerAction(joueur2);
//        taxe1.effectuerAction(stefan);
//        taxe1.survolerCase(joueur2);
//        System.out.println(stefan.getArgent());
        System.out.println();
    }
}
