import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CrunchAdminClientGUI {
    private JFrame frame;
    private JLabel inputLabel;
    private JTextField userInputField;
    private JButton requestDictionaryButton;
    private JTextArea resultTextArea;
    private JButton downloadButton;

    public CrunchAdminClientGUI() {
        frame = new JFrame("Crunch Admin Client");
        inputLabel = new JLabel("Enter keywords and options:");
        userInputField = new JTextField();
        requestDictionaryButton = new JButton("Request Dictionary");
        resultTextArea = new JTextArea();
        downloadButton = new JButton("Download Dictionary");

        requestDictionaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input (keywords and options)
                String userInput = userInputField.getText();

                // Connect to the server and send the request
                sendRequestToServer(userInput);
            }
        });

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for download path
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a directory to save the dictionary");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String downloadPath = fileChooser.getSelectedFile().getPath();
                    downloadDictionary(downloadPath);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(inputLabel);
        panel.add(userInputField);
        panel.add(requestDictionaryButton);
        panel.add(downloadButton);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void sendRequestToServer(String userInput) {
        try (Socket socket = new Socket("localhost",3737);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send the request to the server
            writer.write(userInput);
            writer.newLine();
            writer.flush();

            // Receive and display the server response (dictionary)
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            resultTextArea.setText(result.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void downloadDictionary(String downloadPath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(downloadPath + "/dictionary.txt"));
            writer.write(resultTextArea.getText());
            writer.close();
            JOptionPane.showMessageDialog(frame, "Dictionary downloaded successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error downloading dictionary.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CrunchAdminClientGUI();
            }
        });
    }
}
