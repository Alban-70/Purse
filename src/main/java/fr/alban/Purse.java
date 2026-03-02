package fr.alban;

import fr.alban.Exception.*;

public class Purse {

    private final int operationsMax;
    private CodeSecret codeSecret;
    private int operations;
    private float solde;
    private float plafond;


    public Purse (float plafond, int operationsMax, CodeSecret codeSecret) {
        this.plafond = plafond;
        this.operationsMax = operationsMax;
        this.codeSecret = codeSecret;
    }


    public float getSolde() {
        return solde;
    }

    public void controlePreOp(float montant) throws NbOpMaxAtteintException, MontantNegatifException, RejetSurCodeBloque {
        if (codeSecret.estBloque() ) throw new RejetSurCodeBloque();
        if (operations >= operationsMax) {
            throw new NbOpMaxAtteintException();
        }
        if (montant < 0) {
            throw new MontantNegatifException();
        }

    }

    public void credit(float montant) throws DepassementPlafondException, NbOpMaxAtteintException, MontantNegatifException, RejetSurCodeBloque {
        controlePreOp(montant);
        if (solde + montant > plafond) {
            throw new DepassementPlafondException();
        }
        solde += montant;
        operations++;
    }

    // COMMENTAIRE sdfberhre

    public void debit(float montant, String pinCode) throws MontantNegatifException, MontantNegatifException, NbOpMaxAtteintException, RejetSurCodeFauxException, RejetSurCodeBloque {
        controlePreOp(montant);
        if (codeSecret.verifierCode(pinCode) == false ) throw new RejetSurCodeFauxException();
        if (solde < montant) {
            throw new MontantNegatifException();
        }
        solde -= montant;
        operations++;
    }
}