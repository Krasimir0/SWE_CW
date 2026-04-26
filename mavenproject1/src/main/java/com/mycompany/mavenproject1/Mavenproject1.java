/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mavenproject1;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author krasipetranov
 */
public class Mavenproject1 extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private BookPitchController controller = new BookPitchController();
    private Member currentUser;
    private Booking currentBooking;
    private Pitch selectedPitch;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> locationCombo;
    private JTextField dateField;
    private JTextArea pitchesArea;
    private JLabel summaryLabel;
    private JTextField timeSlotField;
    private JLabel paymentAmountLabel;
    private JLabel confirmLabel;

    public Mavenproject1() {
        setTitle("UrbanPulse – Book Pitch");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createSearchPanel(), "search");
        mainPanel.add(createBookingSummaryPanel(), "summary");
        mainPanel.add(createPaymentPanel(), "payment");
        mainPanel.add(createConfirmationPanel(), "confirmation");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    // ------------------- GUI panels -------------------
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel title = new JLabel("Member Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword());
            Member m = controller.authenticate(email, pass);
            if (m != null) {
                currentUser = m;
                locationCombo.setModel(new DefaultComboBoxModel<>(
                        controller.getLocations().toArray(new String[0])));
                cardLayout.show(mainPanel, "search");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Location:"));
        locationCombo = new JComboBox<>();
        top.add(locationCombo);
        top.add(new JLabel("Date (yyyy-mm-dd):"));
        dateField = new JTextField(LocalDate.now().toString(), 10);
        top.add(dateField);
        JButton searchBtn = new JButton("Search");
        top.add(searchBtn);
        panel.add(top, BorderLayout.NORTH);

        pitchesArea = new JTextArea(10, 40);
        pitchesArea.setEditable(false);
        panel.add(new JScrollPane(pitchesArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton selectBtn = new JButton("Select & Continue");
        bottom.add(selectBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> performSearch());
        selectBtn.addActionListener(e -> selectPitch());
        return panel;
    }

    private void performSearch() {
        String loc = (String) locationCombo.getSelectedItem();
        LocalDate date;
        try {
            date = LocalDate.parse(dateField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date");
            return;
        }
        List<Pitch> available = controller.searchAvailablePitches(loc, date);
        StringBuilder sb = new StringBuilder();
        for (Pitch p : available) {
            sb.append("ID ").append(p.getPitchId()).append(" - ").append(p).append("\n");
        }
        if (available.isEmpty()) {
            sb.append("No pitches available.");
        }
        pitchesArea.setText(sb.toString());
    }

    private void selectPitch() {
        String input = JOptionPane.showInputDialog(this, "Enter Pitch ID from list:");
        if (input == null) {
            return;
        }
        try {
            int id = Integer.parseInt(input.trim());
            List<Pitch> list = controller.searchAvailablePitches(
                    (String) locationCombo.getSelectedItem(),
                    LocalDate.parse(dateField.getText().trim()));
            selectedPitch = list.stream().filter(p -> p.getPitchId() == id)
                    .findFirst().orElse(null);
            if (selectedPitch == null) {
                JOptionPane.showMessageDialog(this, "Invalid pitch ID");
            } else {
                String validity = controller.checkMembershipValidity();
                if (!validity.equals("Valid")) {
                    JOptionPane.showMessageDialog(this,
                            "Membership: " + validity + " – cannot book.");
                } else {
                    summaryLabel.setText("<html><b>Pitch:</b> " + selectedPitch
                            + "<br><b>Date:</b> " + dateField.getText()
                            + "<br><b>Price:</b> £" + selectedPitch.calculatePrice() + "</html>");
                    cardLayout.show(mainPanel, "summary");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error parsing input");
        }
    }

    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        summaryLabel = new JLabel();
        panel.add(summaryLabel, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("Time slot (e.g., 10:00-11:00):"));
        timeSlotField = new JTextField("10:00-11:00", 12);
        bottom.add(timeSlotField);
        JButton confirmBtn = new JButton("Confirm Booking");
        bottom.add(confirmBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        confirmBtn.addActionListener(e -> {
            if (selectedPitch == null) {
                return;
            }
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            currentBooking = controller.initiateBooking(
                    selectedPitch, date, timeSlotField.getText().trim());
            if (currentBooking == null) {
                JOptionPane.showMessageDialog(this,
                        "Could not create booking. Check membership.");
            } else {
                paymentAmountLabel.setText("Amount to pay: £"
                        + currentBooking.getPrice());
                cardLayout.show(mainPanel, "payment");
            }
        });
        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        paymentAmountLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(paymentAmountLabel, BorderLayout.NORTH);
        JButton payBtn = new JButton("Proceed Payment");
        panel.add(payBtn, BorderLayout.CENTER);
        payBtn.addActionListener(e -> {
            Payment p = controller.processPayment(currentBooking);
            if (p != null && p.isConfirmed()) {
                confirmLabel.setText("<html><h2>Booking Confirmed!</h2>"
                        + currentBooking + "<br>Transaction: "
                        + p.getTransactionRef() + "</html>");
                cardLayout.show(mainPanel, "confirmation");
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed.");
            }
        });
        return panel;
    }

    private JPanel createConfirmationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        confirmLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(confirmLabel, BorderLayout.CENTER);
        JButton doneBtn = new JButton("Exit");
        doneBtn.addActionListener(e -> System.exit(0));
        panel.add(doneBtn, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Mavenproject1().setVisible(true));
    }
}
