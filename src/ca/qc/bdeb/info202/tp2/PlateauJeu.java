package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

public class PlateauJeu implements Serializable {
    private static final int NB_CASES_PLATEAU = 10;
    private Case[] casesPlateau;

    public PlateauJeu(Case[] casesPlateau) {
        this.casesPlateau = casesPlateau;
    }

    public static int getNbCasesPlateau() {
        return NB_CASES_PLATEAU;
    }

    public Case[] getCasesPlateau() {
        return this.casesPlateau;
    }
}
