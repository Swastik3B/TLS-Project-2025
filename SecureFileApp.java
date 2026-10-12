import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SecureFileApp {
    private JFrame frame;
    private JTextField fileField;
    private JPasswordField passwordField;
    private File selectedFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SecureFileApp().createGUI());
    }

    public void createGUI() {
        frame = new JFrame("Secure File Encryption & Decryption Tool");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        JPanel filePanel = new JPanel(new BorderLayout());
        fileField = new JTextField();
        JButton browseBtn = new JButton("Browse");
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(browseBtn, BorderLayout.EAST);

        JPanel passPanel = new JPanel(new BorderLayout());
        passwordField = new JPasswordField();
        passPanel.add(new JLabel("Password:"), BorderLayout.WEST);
        passPanel.add(passwordField, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton encBtn = new JButton("Encrypt");
        JButton decBtn = new JButton("Decrypt");
        btnPanel.add(encBtn);
        btnPanel.add(decBtn);

        JTextArea statusArea = new JTextArea();
        statusArea.setEditable(false);

        frame.add(filePanel);
        frame.add(passPanel);
        frame.add(btnPanel);
        frame.add(new JScrollPane(statusArea));

        // Browse Button
        browseBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                fileField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Encrypt Button
        encBtn.addActionListener(e -> {
            if (isValidInput(statusArea)) {
                runOpenSSL("enc", statusArea);
            }
        });

        // Decrypt Button
        decBtn.addActionListener(e -> {
            if (isValidInput(statusArea)) {
                runOpenSSL("dec", statusArea);
            }
        });

        frame.setVisible(true);
    }

    private boolean isValidInput(JTextArea statusArea) {
        if (selectedFile == null || !selectedFile.exists()) {
            statusArea.setText("Please select a valid file.");
            return false;
        }
        if (passwordField.getPassword().length == 0) {
            statusArea.setText("Password cannot be empty.");
            return false;
        }
        return true;
    }

    private void runOpenSSL(String mode, JTextArea statusArea) {
        String password = new String(passwordField.getPassword());
        String inputPath = selectedFile.getAbsolutePath();
        String outputPath = mode.equals("enc") ? inputPath + ".enc" : inputPath.replace(".enc", ".dec");

        // Build OpenSSL command
        String command = String.format("openssl enc -aes-256-cbc -salt -in \"%s\" -out \"%s\" -pass pass:%s %s",
                inputPath, outputPath, password, mode.equals("dec") ? "-d" : "");

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Read OpenSSL output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                statusArea.setText(mode.equals("enc") ? "Encryption Successful!" : "Decryption Successful!");
            } else {
                statusArea.setText("Operation Failed. Possible reasons:\n" +
                        "- Invalid key\n" +
                        "- Wrong file format\n" +
                        "- OpenSSL not in system PATH\n\n" +
                        "Details:\n" + output);
            }
        } catch (Exception e) {
            statusArea.setText("Error: " + e.getMessage());
        }
    }
}
