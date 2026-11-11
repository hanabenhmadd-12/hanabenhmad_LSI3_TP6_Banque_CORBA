package corbaServer;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import service.BanqueImpl;
public class BanqueServer {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();
            Context ctx = new InitialContext();
            BanqueImpl banque = new BanqueImpl();
            ctx.rebind("BanqueCorba", rootPOA.servant_to_reference(banque));
            System.out.println("Serveur CORBA prêt, en attente des clients...");
            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}