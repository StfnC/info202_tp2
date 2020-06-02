package ca.qc.bdeb.info202.tp2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ServerSocket server;
    private Socket connection;
    private Partie partie;

    public Server() {
        this.partie = Tp2.demarerNouvellePartie();
    }

    public Server(Partie partie) {
        this.partie = partie;
    }

    public void startRunning() {
        try {
            server = new ServerSocket(54321, 20);

            while (true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whilePlaying();
                } finally {
                    closeConnections();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...");
        connection = server.accept();
        System.out.println("Now connected to: " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        oos = new ObjectOutputStream(connection.getOutputStream());
        oos.flush();

        ois = new ObjectInputStream(connection.getInputStream());

        System.out.println("Ready to communicate");
    }

    private void whilePlaying() {
        System.out.println("You are ready to play: ");

        Partie nouvellePartie = null;

        do {
            try {
                nouvellePartie = (Partie) ois.readObject();
                this.partie = nouvellePartie;
            }  catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (nouvellePartie != null);
    }

    private void sendGame() {
        try {
            oos.writeObject(this.partie);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        System.out.println("Closing connections...");
        try {
            oos.close();
            ois.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
