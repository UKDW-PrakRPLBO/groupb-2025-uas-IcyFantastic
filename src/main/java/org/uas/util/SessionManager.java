package org.uas.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager implements Serializable {
    private static final long serialVersionUID = 1l;
    private static final String SESSION_FILE = "session.ser";
    private SessionManager() {
        loadSession();
    }

    private static SessionManager instance;
    private boolean isLoggedIn = false;

    // Static method to get the singleton instance
    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        saveSession();
    }

    private void loadSession() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))){
            SessionManager loadedSession = (SessionManager) ois.readObject();
            this.isLoggedIn = loadedSession.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            this.isLoggedIn = false;
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))){
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Gagal Menyimpan Sesi: " + e.getMessage());
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to simulate login
    public void login() {
        this.isLoggedIn = true;
        saveSession();
    }

    // Method to simulate logout
    public void logout() {
        this.isLoggedIn = false;
        try {
            File sessionFile = new File(SESSION_FILE);
            if (sessionFile.exists()) {
                sessionFile.delete();
            }
        } catch (SecurityException e) {
            System.err.println("Gagal Menghapus Sesi: " + e.getMessage());
        }
    }
}
