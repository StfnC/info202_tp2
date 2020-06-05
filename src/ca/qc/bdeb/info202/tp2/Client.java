package ca.qc.bdeb.info202.tp2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket connection;
    private Partie partie;
    private String nom;
    private String serverIP;

    public Client(String nom, String serverIP) {
        this.nom = nom;
        this.serverIP = serverIP;
        this.partie = null;
    }

    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            whilePlaying();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void connectToServer() throws IOException {
        connection = new Socket(InetAddress.getByName(this.serverIP), 54321);
        System.out.println("Connection established with: " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        oos = new ObjectOutputStream(connection.getOutputStream());
        oos.flush();

        ois = new ObjectInputStream(connection.getInputStream());

        System.out.println("Ready to communicate");
    }

    private void whilePlaying() {
        String messageTroisOptions = "Entrez votre choix (1, 2, 3): ";
        String[] choixMenuTroisOptions = {"1", "2", "3"};

        try {
            this.partie = (Partie) ois.readObject();
            if (this.partie.getProchainJoueur().getNom().equals(this.nom)) {
                Tp2.jouerTour(this.partie, messageTroisOptions, choixMenuTroisOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        System.out.println("Closing connection...");
        try {
            oos.flush();
            oos.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
