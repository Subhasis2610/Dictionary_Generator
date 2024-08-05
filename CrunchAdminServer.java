import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CrunchAdminServer {
    private static final String DICTIONARY_FILE_PATH = "local_dictionary.txt"; // Path to your local dictionary file
    private ServerSocket serverSocket;

    public CrunchAdminServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Server is listening on port " + serverSocket.getLocalPort() + "...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> generateDictionary(String clientMessage) {
        String[] words = clientMessage.split("\\s+");
        Map<String, String> generatedDictionary = new HashMap<>();

        // Load the local dictionary
        Map<String, String> localDictionary = loadLocalDictionary();

        for (String word : words) {
            // Check if the word is in the local dictionary
            String meaning = localDictionary.getOrDefault(word, "Meaning not found");
            generatedDictionary.put(word, meaning);
        }

        return generatedDictionary;
    }

    private Map<String, String> loadLocalDictionary() {
        Map<String, String> localDictionary = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();
                    localDictionary.put(word, meaning);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return localDictionary;
    }

    private void sendDictionaryToClient(Map<String, String> dictionary, BufferedWriter writer) throws IOException {
        // Send the dictionary to the client
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue());
            writer.newLine();
        }
        writer.flush();
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            // Read client request (keywords and options)
            String clientMessage = reader.readLine();

            // Process client request and generate dictionary
            Map<String, String> dictionary = generateDictionary(clientMessage);

            // Send the dictionary to the client
            sendDictionaryToClient(dictionary, writer);

            // Close the connection
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            CrunchAdminServer server = new CrunchAdminServer(3737); // Use your desired port
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
