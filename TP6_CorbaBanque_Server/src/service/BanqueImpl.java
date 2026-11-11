package service;
import java.util.HashMap;
import java.util.Map;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemotePOA;

public class BanqueImpl extends IBanqueRemotePOA {

    private Map<Integer, Compte> comptes = new HashMap<>();

    @Override
    public void creerCompte(Compte cpte) {
        comptes.put(cpte.code, cpte);
        System.out.println("Compte créé : " + cpte.code + " (solde = " + cpte.solde + ")");
    }

    @Override
    public void verser(float mt, int code) {
        Compte c = comptes.get(code);
        if (c == null) {
            throw new RuntimeException("Compte introuvable : " + code);
        }
        c.solde += mt;
        System.out.println("Versement de " + mt + " sur le compte " + code);
    }

    @Override
    public void retirer(float mt, int code) {
        Compte c = comptes.get(code);
        if (c == null) {
            throw new RuntimeException("Compte introuvable : " + code);
        }
        if (c.solde < mt) {
            throw new RuntimeException("Solde insuffisant pour le compte " + code);
        }
        c.solde -= mt;
        System.out.println("Retrait de " + mt + " du compte " + code);
    }

    @Override
    public Compte getCompte(int code) {
        Compte c = comptes.get(code);
        if (c == null) {
            throw new RuntimeException("Compte introuvable : " + code);
        }
        return c;
    }

    @Override
    public Compte[] getComptes() {
        return comptes.values().toArray(new Compte[0]);
    }

    @Override
    public double conversion(float mt) {
        // Taux utilisé : 1 € = 3.4 DT
        return mt * 3.4;
    }
}