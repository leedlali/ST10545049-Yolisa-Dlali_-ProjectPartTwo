/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.projectparttwo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 *
 * @author ss
 */
public class ProjectpartTwo {

    static Scanner input = new Scanner(System.in);

    private static final String LINE = "==================================================";
    private static final String THIN = "--------------------------------------------------";

    // ---------- Console layout helpers ----------
    /** Big heading with a blank line before and after, e.g. REGISTRATION. */
    static void printHeading(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println("  " + title.toUpperCase());
        System.out.println(LINE);
        System.out.println();
    }

    /** Smaller sub-heading used inside a section. */
    static void printSubHeading(String title) {
        System.out.println();
        System.out.println(THIN);
        System.out.println("  " + title);
        System.out.println(THIN);
        System.out.println();
    }

    //  Static Methods
    // Check if username contains "_" and max 5 characters
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;

    }

    // Check if password has at least 8 chars, capital letter, number, special char
    public static boolean checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    // Check if cell phone number is valid (+27 followed by 9 digits)
    public static boolean checkCellPhoneNumber(String number) {
        String regex = "^\\+27\\d{9}$";
        return Pattern.matches(regex, number);
    }

    // Register user and validate username and password
    public static String registerUser(String username, String password) {
        while (!checkUserName(username)) {
            return "Username is not correct. Must have _ and max 5 chars.";
        }
        while (!checkPasswordComplexity(password)) {
            return "Password is not correct. Must have 8+ chars, capital letter, number, and special char.";
        }
        return "Username and password successfully captured. User registered!";
    }

    static boolean LoginUser(String kyle, String password, String username, String password0) {
        return kyle.equals(username) && password.equals(password0);
    }

    static String returnLoginStatus(boolean b) {
        if (b) {
            return "login successful! Welcome back";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    static boolean LoginUsername(String kyl_1) {
        return checkUserName(kyl_1);
    }

    public static void main(String[] args) {

        //  Registration
        printHeading("Registration");
        System.out.println("Welcome, please enter the required details to register with us.");
        System.out.println();

        // Loop until valid username
        String username;
        while (true) {
            System.out.print("Enter Username (must contain '_' and max 5 chars): ");
            username = input.nextLine();
            System.out.println();
            if (checkUserName(username)) {
                break;
            }
            System.out.println("Username is not correctly formatted; please ensure that your username");
            System.out.println("contains an underscore and is no longer than five characters in length.");
            System.out.println("Example: User_");
            System.out.println();
        }

        // Loop until valid password
        String password;
        while (true) {
            System.out.print("Enter Password (8+ chars, 1 capital, 1 number, 1 special char): ");
            password = input.nextLine();
            System.out.println();
            if (checkPasswordComplexity(password)) {
                break;
            }
            System.out.println("Password is not correctly formatted; please ensure that the password");
            System.out.println("contains at least eight characters, a capital letter, a number and a special character.");
            System.out.println("Example: Password!");
            System.out.println();
        }

        // Register user
        String registerMessage = registerUser(username, password);
        System.out.println(registerMessage);
        System.out.println();
        String storedUsername = username;
        String storedPassword = password;
        String cellPhone;

        // Loop until valid cell phone
        while (true) {
            System.out.print("Enter Cell Phone (+27 followed by 9 digits): ");
            cellPhone = input.nextLine();
            System.out.println();
            if (checkCellPhoneNumber(cellPhone)) {
                System.out.println("Cell phone number successfully added.");
                break;
            }
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            System.out.println("Example: +27123456789");
            System.out.println();
        }

        // Login session
        printHeading("Login Session");
        System.out.println("Please fill in the information below to sign in.");
        System.out.println();

        boolean invalid = false;
        while (!invalid) {
            System.out.print("Enter your username: ");
            username = input.nextLine();
            System.out.print("Enter your password: ");
            password = input.nextLine();
            System.out.println();

            if (username.length() <= 5 && username.contains("_")) {
                if (password.length() >= 8
                        && password.matches(".*[A-Z].*")
                        && password.matches(".*[a-z].*")
                        && password.matches(".*[0-9].*")
                        && password.matches(".*[^a-zA-Z0-9].*")) {

                    System.out.println("Login successful! Welcome " + username + ", it is great to see you again.");
                    break;
                } else {
                    System.out.println("Username or password incorrect, please try again.");
                    System.out.println();
                }
            } else {
                System.out.println("Username or password incorrect, please try again.");
                System.out.println();
            }
        }

        // QuickChat
        printHeading("Welcome to QuickChat");

        // Requirement 5: user sets how many messages they will enter
        int maxMessages = readInt("How many messages do you want to send? ", 1, Integer.MAX_VALUE);
        int entered = 0;

        boolean keepRunning = true;   // Requirement 4: run until Quit
        while (keepRunning) {
            printSubHeading("Main Menu");
            System.out.println("  1. Send Messages");
            System.out.println("  2. Show recently sent messages");
            System.out.println("  3. Quit");
            System.out.println();

            int select = readInt("Choose an option (1-3): ", 1, 3);

            if (select == 1) {
                if (entered >= maxMessages) {
                    System.out.println();
                    System.out.println("You have already entered all " + maxMessages + " messages.");
                } else {
                    entered = sendMessages(entered, maxMessages);
                    // Requirement 6: total shown once all the messages have been sent
                    if (entered >= maxMessages) {
                        printSubHeading("Summary");
                        System.out.println("Total number of messages sent: "
                                + Message.returnTotalMessages());
                    }
                }
            } else if (select == 2) {
                printSubHeading("Recently Sent Messages");
                System.out.println("Coming Soon.");
            } else {
                printHeading("Goodbye");
                System.out.println("Thank you for using QuickChat!");
                System.out.println();
                keepRunning = false;
            }
        }

    }

    /**
     * Enters messages until the limit is reached. Returns how many have been
     * entered so far.
     */
    static int sendMessages(int numberEntered, int maxMessages) {
        String recipient = null;
        String recipientUsername = null;   // NEW: recipient's user name

        while (numberEntered < maxMessages) {
            int number = numberEntered + 1;

            printSubHeading("Message " + number + " of " + maxMessages);

            // Recipient: asked once, then reused unless the user wants a new one
            boolean askRecipient = true;
            if (recipient != null) {
                System.out.print("Send to the same recipient (" + recipient + ")? yes/no: ");
                askRecipient = !input.nextLine().trim().equalsIgnoreCase("yes");
                System.out.println();
            }
            if (askRecipient) {
                recipient = readRecipient();
                recipientUsername = readRecipientUsername();   // NEW
            }

            String text = readMessageText(number, maxMessages);

            Message msg = new Message(recipient, text);
            msg.setRecipientUsername(recipientUsername);       // NEW
            numberEntered++;

            // Send / Disregard / Store
            System.out.println();
            System.out.println("What would you like to do with this message?");
            System.out.println();
            System.out.println("  1) Send Message");
            System.out.println("  2) Disregard Message");
            System.out.println("  3) Store Message to send later");
            System.out.println();
            int SELECT = readInt("Choose an option (1-3): ", 1, 3);
            System.out.println();

            String result = msg.sentMessage(SELECT);
            System.out.println(result);

            if (SELECT == 2) {
                // wait for the user to confirm deletion
                while (!input.nextLine().trim().equals("0")) {
                    System.out.println("Press 0 to delete the message.");
                }
                System.out.println();
                System.out.println("Message deleted.");
            } else if (SELECT == 1) {
                // Requirement 7: show full details after sending
                printSubHeading("Message Details");
                System.out.println(msg.details());
            }
            System.out.println();
        }
        return numberEntered;
    }

    private static String readRecipient() {
        while (true) {
            System.out.print("Enter recipient's cellphone number with +27 followed by 9 digits (e.g. +27718693002): ");
            String number = input.nextLine().trim();
            System.out.println();
            String result = Message.checkRecipientCell(number);
            System.out.println(result);
            System.out.println();
            if (result.equals("Cell phone number successfully captured.")) {
                return number;
            }
        }
    }

    /**
     * NEW: asks for the recipient's user name (max 5 characters) and loops
     * until it is valid.
     */
    private static String readRecipientUsername() {
        while (true) {
            System.out.print("Enter recipient's user name (max "
                    + Message.MAX_RECIPIENT_USERNAME_LENGTH + " characters and an underscore, e.g. Ann_): ");
            String name = input.nextLine().trim();
            System.out.println();
            String result = Message.checkRecipientUsername(name);
            System.out.println(result);
            System.out.println();
            if (result.equals(Message.RECIPIENT_USERNAME_OK)) {
                return name;
            }
        }
    }

    private static String readMessageText(int number, int max) {
        while (true) {
            System.out.print("Enter message (250 characters max): ");
            String text = input.nextLine();
            System.out.println();

            if (text.trim().isEmpty()) {
                System.out.println("The message cannot be empty. Please try again.");
                System.out.println();
                continue;
            }

            if (text.length() <= Message.MAX_MESSAGE_LENGTH) {
                System.out.println(Message.checkMessageLength(text));
                return text;
            }

            System.out.println(Message.checkMessageLength(text));
            System.out.println("Please enter a message of less 250 characters.");
            System.out.println();
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to the error message
            }
            System.out.println();
            System.out.println("Invalid input. Please enter a number between " + min
                    + " and " + (max == Integer.MAX_VALUE ? "a larger value" : String.valueOf(max)) + ".");
            System.out.println();
        }
    }

    // ---------- Simple login (swap in your Task 1 login if you have one) ----------
    private static boolean loginFlow() {
        Map<String, String> users = new HashMap<>();
        printHeading("Register");
        System.out.print("Choose a username: ");
        String user = input.nextLine().trim();
        System.out.print("Choose a password: ");
        String pass = input.nextLine();
        users.put(user, pass);

        printHeading("Login");
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Username: ");
            String u = input.nextLine().trim();
            System.out.print("Password: ");
            String p = input.nextLine();
            System.out.println();
            if (p.equals(users.get(u))) {
                System.out.println("Login successful.");
                return true;
            }
            System.out.println("Incorrect username or password.");
            System.out.println();
        }
        return false;
    }
}

/**
 * One QuickChat message plus the shared bookkeeping (sent list, total sent).
 */
class Message {

    public static final int MAX_MESSAGE_LENGTH = 250;
    public static final String STORE_FILE = "storedMessages.json";

    // NEW: recipient user name rules
    public static final int MAX_RECIPIENT_USERNAME_LENGTH = 5;
    public static final String RECIPIENT_USERNAME_OK = "Recipient user name successfully captured.";

    // NEW: format used when stating the time the message was sent
    private static final DateTimeFormatter TIME_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String NOT_SENT = "Not sent yet";

    // Shared across all messages while the program is running
    private static final List<Message> sentMessages = new ArrayList<>();
    private static int totalSent = 0;

    private final String messageID;      // random ten-digit number
    private final int numMessagesSent;   // starts at 0, goes up as messages are sent
    private final String recipient;
    private final String message;
    private final String messageHash;
    private String recipientUsername = "";   // NEW: recipient's user name
    private String timeSent = NOT_SENT;      // NEW: set when the message is sent

    /**
     * Normal use: the ID is generated automatically.
     */
    public Message(String recipient, String message) {
        this(generateMessageID(), recipient, message);
    }

    /**
     * Lets unit tests supply a fixed ID so the hash can be predicted.
     */
    public Message(String messageID, String recipient, String message) {
        this.messageID = messageID;
        this.numMessagesSent = totalSent;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    // ---------- Required methods ----------
    /**
     * Message ID must be no more than ten characters.
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    /**
     * Recipient must start with +27 followed by exactly 9 digits.
     */
    public String checkRecipientCell() {
        return checkRecipientCell(recipient);
    }

    public static String checkRecipientCell(String number) {
        if (number != null && number.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
                + "international code followed by 9 didgits. Please correct the number and try again.";
    }

    /**
     * NEW: recipient user name must not be empty and may be at most 5
     * characters.
     */
    public static String checkRecipientUsername(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "The recipient user name cannot be empty. Please try again.";
        }
        if (name.length() > MAX_RECIPIENT_USERNAME_LENGTH) {
            return "Recipient user name is too long. It must be "
                    + MAX_RECIPIENT_USERNAME_LENGTH + " characters or fewer.";
        }
        return RECIPIENT_USERNAME_OK;
    }

    /**
     * Message must be 250 characters or fewer.
     */
    public static String checkMessageLength(String text) {
        if (text.length() <= MAX_MESSAGE_LENGTH) {
            return "Message ready to send.";
        }
        int over = text.length() - MAX_MESSAGE_LENGTH;
        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }

    /**
     * Hash = first two digits of ID : message number : FIRSTWORDLASTWORD (all
     * caps).
     */
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = clean(words[0]);
        String last = clean(words[words.length - 1]);
        String firstTwo = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (firstTwo + ":" + numMessagesSent + ":" + first + last).toUpperCase();
    }

    /**
     * 1 = send, 2 = disregard, 3 = store. Returns the text the system must
     * show.
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                timeSent = LocalDateTime.now().format(TIME_FORMAT);   // NEW: record time sent
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent at " + timeSent + ".";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice. Please select 1, 2 or 3.";
        }
    }

    /**
     * All messages sent while the program has been running.
     */
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.details()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    /**
     * Total number of messages sent.
     */
    public static int returnTotalMessages() {
        return totalSent;
    }

    /**
     * Appends this message to storedMessages.json (a JSON array).
     */
    public void storeMessage() {
        Path file = Paths.get(STORE_FILE);
        try {
            String existing = Files.exists(file)
                    ? new String(Files.readAllBytes(file), StandardCharsets.UTF_8).trim()
                    : "";
            String entry = toJson();
            String output;
            if (existing.isEmpty() || existing.equals("[]")) {
                output = "[\n" + entry + "\n]\n";
            } else {
                int end = existing.lastIndexOf(']');
                output = existing.substring(0, end).trim() + ",\n" + entry + "\n]\n";
            }
            Files.write(file, output.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Could not store the message: " + e.getMessage());
        }
    }

    // ---------- Helpers ----------
    /**
     * Full details: ID, Hash, Recipient (cell), Recipient user name, Time
     * sent, Message.
     */
    public String details() {
        return "Message ID:   " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient cellphone number:    " + recipient + System.lineSeparator()
                + "User name:    " + recipientUsername + System.lineSeparator()
                + "Time sent:    " + timeSent + System.lineSeparator()
                + "Message:      " + message + System.lineSeparator();
    }

    public String toJson() {
        return "  {\n"
                + "    \"messageID\": \"" + esc(messageID) + "\",\n"
                + "    \"messageHash\": \"" + esc(messageHash) + "\",\n"
                + "    \"recipient\": \"" + esc(recipient) + "\",\n"
                + "    \"recipientUsername\": \"" + esc(recipientUsername) + "\",\n"
                + "    \"timeSent\": \"" + esc(timeSent) + "\",\n"
                + "    \"message\": \"" + esc(message) + "\"\n"
                + "  }";
    }

    private static String generateMessageID() {
        long n = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        return String.format("%010d", n);   // always ten digits, may start with 0
    }

    private static String clean(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    /**
     * For unit tests: clear the shared state.
     */
    public static void resetState() {
        sentMessages.clear();
        totalSent = 0;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }

    // NEW getters / setter
    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername == null ? "" : recipientUsername;
    }

    public String getTimeSent() {
        return timeSent;
    }
}
