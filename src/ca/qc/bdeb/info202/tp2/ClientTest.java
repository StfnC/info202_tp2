package ca.qc.bdeb.info202.tp2;

public class ClientTest {
    public static void main(String[] args) {
        String nomJoueur = Tp2.demanderEntreeAuJoueur("Entrez le nom du joueur: ");
        Client client = new Client(nomJoueur, "localhost");
        client.startRunning();
    }
}
