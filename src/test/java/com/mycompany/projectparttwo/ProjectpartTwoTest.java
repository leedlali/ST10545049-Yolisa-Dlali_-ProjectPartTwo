/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.projectparttwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectpartTwoTest {

    private static final String ID = "0012345678";
    private static final String CELL = "+27718693002";

    @BeforeEach
    public void setUp() {
        Message.resetState();   // every test starts with 0 messages sent
    }

    // ===================== Username =====================
    @Test
    public void testCheckUserNameValid() {
        assertTrue(ProjectpartTwo.checkUserName("kyl_1"));
        assertTrue(ProjectpartTwo.checkUserName("_"));
    }

    @Test
    public void testCheckUserNameNoUnderscore() {
        assertFalse(ProjectpartTwo.checkUserName("kyle1"));
    }

    @Test
    public void testCheckUserNameTooLong() {
        assertFalse(ProjectpartTwo.checkUserName("kyle_123"));
    }

    // ===================== Password =====================
    @Test
    public void testCheckPasswordComplexityValid() {
        assertTrue(ProjectpartTwo.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertTrue(ProjectpartTwo.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testCheckPasswordComplexityInvalid() {
        assertFalse(ProjectpartTwo.checkPasswordComplexity("password"));    // nothing required
        assertFalse(ProjectpartTwo.checkPasswordComplexity("Pass1!"));      // too short
        assertFalse(ProjectpartTwo.checkPasswordComplexity("password1!"));  // no capital
        assertFalse(ProjectpartTwo.checkPasswordComplexity("Password!!"));  // no number
        assertFalse(ProjectpartTwo.checkPasswordComplexity("Password12"));  // no special char
    }

    // ===================== Cell phone =====================
    @Test
    public void testCheckCellPhoneNumberValid() {
        assertTrue(ProjectpartTwo.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        assertFalse(ProjectpartTwo.checkCellPhoneNumber("08966553"));       // no international code
        assertFalse(ProjectpartTwo.checkCellPhoneNumber("+2783896897"));    // 8 digits
        assertFalse(ProjectpartTwo.checkCellPhoneNumber("+278389689761"));  // 10 digits
        assertFalse(ProjectpartTwo.checkCellPhoneNumber("0027838968976"));  // wrong prefix
    }

    // ===================== Register / Login =====================
    @Test
    public void testRegisterUserSuccess() {
        assertEquals("Username and password successfully captured. User registered!",
                ProjectpartTwo.registerUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testRegisterUserBadUsername() {
        assertEquals("Username is not correct. Must have _ and max 5 chars.",
                ProjectpartTwo.registerUser("kyle", "Ch&&sec@ke99!"));
    }

    @Test
    public void testRegisterUserBadPassword() {
        assertEquals("Password is not correct. Must have 8+ chars, capital letter, number, and special char.",
                ProjectpartTwo.registerUser("kyl_1", "password"));
    }

    @Test
    public void testLoginUserCorrect() {
        assertTrue(ProjectpartTwo.LoginUser("kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUserWrongPassword() {
        assertFalse(ProjectpartTwo.LoginUser("kyl_1", "wrong", "kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUserWrongUsername() {
        assertFalse(ProjectpartTwo.LoginUser("bob_", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testReturnLoginStatus() {
        assertEquals("login successful! Welcome back", ProjectpartTwo.returnLoginStatus(true));
        assertEquals("Username or password incorrect, please try again.",
                ProjectpartTwo.returnLoginStatus(false));
    }

    @Test
    public void testLoginUsername() {
        assertTrue(ProjectpartTwo.LoginUsername("kyl_1"));
        assertFalse(ProjectpartTwo.LoginUsername("kyle1"));
    }

    // ===================== Message ID =====================
    @Test
    public void testCheckMessageIDValid() {
        Message m = new Message(ID, CELL, "Hi Mike");
        assertTrue(m.checkMessageID());
    }

    @Test
    public void testCheckMessageIDTooLong() {
        Message m = new Message("12345678901", CELL, "Hi Mike");
        assertFalse(m.checkMessageID());
    }

    @Test
    public void testGeneratedMessageIDIsTenDigits() {
        Message m = new Message(CELL, "Hi Mike");
        assertEquals(10, m.getMessageID().length());
        assertTrue(m.getMessageID().matches("\\d{10}"));
        assertTrue(m.checkMessageID());
    }

    // ===================== Recipient cell =====================
    @Test
    public void testCheckRecipientCellValid() {
        assertEquals("Cell phone number successfully captured.",
                Message.checkRecipientCell("+27718693002"));
    }

    // FIXED: error text changed in the code, so check start and end of the message
    @Test
    public void testCheckRecipientCellInvalid() {
        String[] badNumbers = {"08575975889", "+2771869300", null};
        for (String bad : badNumbers) {
            String result = Message.checkRecipientCell(bad);
            assertTrue(result.startsWith("Cell phone number is incorrectly formatted"));
            assertTrue(result.endsWith("Please correct the number and try again."));
        }
    }

    @Test
    public void testCheckRecipientCellInstanceMethod() {
        Message good = new Message(ID, CELL, "Hi Mike");
        Message bad = new Message(ID, "0718693002", "Hi Mike");
        assertEquals("Cell phone number successfully captured.", good.checkRecipientCell());
        assertTrue(bad.checkRecipientCell().startsWith("Cell phone number is incorrectly formatted"));
    }

    // ===================== Message length =====================
    @Test
    public void testCheckMessageLengthShort() {
        assertEquals("Message ready to send.", Message.checkMessageLength("Hi Mike"));
    }

    @Test
    public void testCheckMessageLengthExactly250() {
        assertEquals("Message ready to send.", Message.checkMessageLength("a".repeat(250)));
    }

    @Test
    public void testCheckMessageLengthOver() {
        assertEquals("Message exceeds 250 characters by 1, please reduce the size.",
                Message.checkMessageLength("a".repeat(251)));
        assertEquals("Message exceeds 250 characters by 50, please reduce the size.",
                Message.checkMessageLength("a".repeat(300)));
    }

    // ===================== Message hash =====================
    @Test
    public void testMessageHash() {
        Message m = new Message(ID, CELL, "Hi tonight");
        assertEquals("00:0:HITONIGHT", m.getMessageHash());
    }

    @Test
    public void testMessageHashIgnoresPunctuation() {
        Message m = new Message(ID, CELL, "Hi Mike, can you join us for dinner tonight.");
        assertEquals("00:0:HITONIGHT", m.getMessageHash());
    }

    @Test
    public void testMessageHashSingleWord() {
        Message m = new Message(ID, CELL, "Hello");
        assertEquals("00:0:HELLOHELLO", m.getMessageHash());
    }

    @Test
    public void testMessageHashUsesMessageNumber() {
        new Message(ID, CELL, "First message").sentMessage(1);   // totalSent becomes 1
        Message second = new Message(ID, CELL, "Second message");
        assertEquals(1, second.getNumMessagesSent());
        assertEquals("00:1:SECONDMESSAGE", second.getMessageHash());
    }

    // ===================== Send / disregard / store =====================
    // FIXED: the sent message now includes the time it was sent
    @Test
    public void testSentMessageSend() {
        Message m = new Message(ID, CELL, "Hi Mike");
        String result = m.sentMessage(1);
        assertTrue(result.startsWith("Message successfully sent"));
        assertTrue(result.contains(m.getTimeSent()));
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testSentMessageDisregard() {
        Message m = new Message(ID, CELL, "Hi Mike");
        assertEquals("Press 0 to delete the message.", m.sentMessage(2));
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testSentMessageInvalidChoice() {
        Message m = new Message(ID, CELL, "Hi Mike");
        assertEquals("Invalid choice. Please select 1, 2 or 3.", m.sentMessage(9));
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testSentMessageStore() throws IOException {
        Path file = Paths.get(Message.STORE_FILE);
        byte[] backup = Files.exists(file) ? Files.readAllBytes(file) : null;
        try {
            Files.deleteIfExists(file);

            Message m = new Message(ID, CELL, "Hi Mike");
            assertEquals("Message successfully stored.", m.sentMessage(3));
            assertEquals(0, Message.returnTotalMessages());   // stored, not sent

            String content = new String(Files.readAllBytes(file));
            assertTrue(content.contains("\"messageID\": \"" + ID + "\""));
            assertTrue(content.contains("\"message\": \"Hi Mike\""));
        } finally {
            // put back whatever file was there before the test
            if (backup != null) {
                Files.write(file, backup);
            } else {
                Files.deleteIfExists(file);
            }
        }
    }

    // ===================== Totals and printing =====================
    @Test
    public void testReturnTotalMessagesMultiple() {
        new Message(ID, CELL, "One").sentMessage(1);
        new Message(ID, CELL, "Two").sentMessage(1);
        new Message(ID, CELL, "Three").sentMessage(1);
        assertEquals(3, Message.returnTotalMessages());
    }

    @Test
    public void testPrintMessagesEmpty() {
        assertEquals("No messages have been sent yet.", Message.printMessages());
    }

    @Test
    public void testPrintMessagesAfterSending() {
        Message m = new Message(ID, CELL, "Hi Mike");
        m.sentMessage(1);
        String printed = Message.printMessages();
        assertTrue(printed.contains(ID));
        assertTrue(printed.contains("Hi Mike"));
        assertTrue(printed.contains(CELL));
    }

    // ===================== Details / JSON / getters =====================
    // FIXED: details now has "Recipient cellphone number:", "User name:" and "Time sent:"
    @Test
    public void testDetailsFormat() {
        Message m = new Message(ID, CELL, "Hi Mike");
        String d = m.details();

        assertTrue(d.contains("Message ID:   " + ID));
        assertTrue(d.contains("Message Hash: 00:0:HIMIKE"));
        assertTrue(d.contains("Recipient cellphone number:"));
        assertTrue(d.contains(CELL));
        assertTrue(d.contains("User name:"));
        assertTrue(d.contains("Time sent:    Not sent yet"));
        assertTrue(d.contains("Message:      Hi Mike"));

        // fields appear in the right order
        assertTrue(d.indexOf("Message ID:") < d.indexOf("Message Hash:"));
        assertTrue(d.indexOf("Message Hash:") < d.indexOf("Recipient cellphone number:"));
        assertTrue(d.indexOf("Recipient cellphone number:") < d.indexOf("User name:"));
        assertTrue(d.indexOf("User name:") < d.indexOf("Time sent:"));
        assertTrue(d.indexOf("Time sent:") < d.indexOf("Message:"));
    }

    @Test
    public void testToJsonEscapesQuotes() {
        Message m = new Message(ID, CELL, "Say \"hi\"");
        assertTrue(m.toJson().contains("Say \\\"hi\\\""));
    }

    @Test
    public void testGetters() {
        Message m = new Message(ID, CELL, "Hi Mike");
        assertEquals(ID, m.getMessageID());
        assertEquals(CELL, m.getRecipient());
        assertEquals("Hi Mike", m.getMessage());
        assertEquals(0, m.getNumMessagesSent());
        assertEquals("00:0:HIMIKE", m.getMessageHash());
    }
}