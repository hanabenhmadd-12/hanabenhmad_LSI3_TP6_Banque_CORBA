package corbaClient;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.InputStream;
import java.util.Properties;

public class BanqueClient {

    public static void main(String[] args) {
        try {

            Properties props = new Properties();
            try (InputStream in = BanqueClient.class.getResourceAsStream("/jndi.properties")) {
                if (in != null) {
                    props.load(in);
                }
            }
            Context ctx = new InitialContext(props);


            org.omg.CORBA.Object ref = (org.omg.CORBA.Object) ctx.lookup("BanqueCorba");
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(ref);
            System.out.println("Connexion au serveur CORBA réussie.");

            System.out.println("\n--- Création des comptes ---");
            banque.creerCompte(new Compte(1, 1000f));
            banque.creerCompte(new Compte(2, 500f));
            System.out.println("Comptes 1 et 2 créés.");

            System.out.println("\n--- Versement de 200 sur le compte 1 ---");
            banque.verser(200f, 1);

            System.out.println("\n--- Retrait de 300 du compte 1 ---");
            banque.retirer(300f, 1);

            System.out.println("\n--- Consultation du compte 1 ---");
            Compte c = banque.getCompte(1);
            System.out.println("Code = " + c.code + " | Solde = " + c.solde);

            System.out.println("\n--- Liste de tous les comptes ---");
            Compte[] comptes = banque.getComptes();
            for (Compte cpt : comptes) {
                System.out.println("Code = " + cpt.code + " | Solde = " + cpt.solde);
            }

            System.out.println("\n--- Conversion de 100 EUR en DT ---");
            double dt = banque.conversion(100f);
            System.out.println("100 EUR = " + dt + " DT");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}