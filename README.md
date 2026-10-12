# Secure File Encryption and Decryption Tool

## About the Project
This project is a Java-based desktop application that provides file
encryption and decryption using OpenSSL. It offers a graphical user
interface (GUI) that allows users to select files and protect their
contents using a password.

## Objectives
- Protect file contents through encryption.
- Implement file encryption and decryption using Java and OpenSSL.
- Provide a simple and user-friendly graphical interface.
- Understand practical applications of cryptography and data security.

## Technologies Used
- Java
- Java Swing (GUI)
- OpenSSL
- AES-256-CBC Encryption
- File Handling

## Features
- Browse and select files.
- Enter a password for encryption or decryption.
- Encrypt files using AES-256-CBC.
- Decrypt previously encrypted files.
- Display operation status and error messages.
- Save encrypted and decrypted output as separate files.

## Project Files
- `SecureFileApp.java` — Main Java application.
- `PlainText.txt` — Sample input file.
- `PlainText.txt.enc` — Sample encrypted file.
- `PlainText.txt.dec` — Sample decrypted file.
- `tls project.docx` — Project documentation.
- `Screenshot*.png` — Project screenshots.

## Requirements
- Java Development Kit (JDK)
- OpenSSL installed and available in the system PATH
- Windows operating system (the current implementation uses `cmd.exe`)

## How to Run

1. Install the JDK and OpenSSL.
2. Ensure that Java and OpenSSL are available from the command line.
3. Download or clone this repository.
4. Open a terminal in the folder containing `SecureFileApp.java`.
5. Compile the Java file:

   `javac SecureFileApp.java`

6. Run the application:

   `java SecureFileApp`

7. Select a file using the Browse button.
8. Enter a password.
9. Click Encrypt to create an encrypted file.
10. Select the encrypted file and enter the same password to decrypt it.

## Learning Outcomes
- Understanding symmetric-key encryption.
- Integrating Java applications with OpenSSL.
- Developing desktop applications using Java Swing.
- Performing file handling and managing application errors.

## Security Note
This project is intended for educational purposes. Do not use it to
protect sensitive files without further security improvements and testing.

## Author
Swastik Bishoi
