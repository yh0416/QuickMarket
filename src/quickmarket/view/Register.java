package quickmarket.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import quickmarket.util.DatabaseUtil;
import quickmarket.model.User;

public class Register {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private Color primaryColor = new Color(70, 130, 180); // Steel Blue
    private Color backgroundColor = new Color(240, 240, 250);
    
    public Register() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("QuickMarket Registration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(backgroundColor);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(backgroundColor);
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(backgroundColor);
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);
        titlePanel.add(titleLabel);
        
        // Subtitle
        JPanel subtitlePanel = new JPanel();
        subtitlePanel.setBackground(backgroundColor);
        JLabel subtitleLabel = new JLabel("Please fill in the information below");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitlePanel.add(subtitleLabel);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = createStyledTextField();
        
        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField = createStyledPasswordField();
        
        // Confirm Password
        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordField = createStyledPasswordField();
        
        // Role
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        String[] roles = {"Store Owner", "Store Staff", "Delivery Admin", "Delivery Staff"};
        roleComboBox = createStyledComboBox(roles);
        
        // Add components to form panel
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);
        gbc.gridy = 4;
        formPanel.add(confirmPassLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(confirmPasswordField, gbc);
        gbc.gridy = 6;
        formPanel.add(roleLabel, gbc);
        gbc.gridy = 7;
        formPanel.add(roleComboBox, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(backgroundColor);
        
        JButton registerButton = createStyledButton("Register");
        registerButton.addActionListener(e -> handleRegistration());
        
        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(primaryColor);
        cancelButton.setBorder(new LineBorder(primaryColor));
        cancelButton.addActionListener(e -> frame.dispose());
        
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        buttonGbc.insets = new Insets(5, 0, 5, 0);
        
        buttonGbc.gridy = 0;
        buttonPanel.add(registerButton, buttonGbc);
        buttonGbc.gridy = 1;
        buttonPanel.add(cancelButton, buttonGbc);
        
        // Add all panels to main panel
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);
        
        frame.add(mainPanel);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(300, 35));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBorder(new LineBorder(new Color(200, 200, 200)));
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        if (text.equals("Register")) {
            button.setBackground(primaryColor);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
            button.setBorder(new LineBorder(primaryColor));
        }
        
        return button;
    }
    
    private void handleRegistration() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            // Enhanced validation
            if (username.trim().isEmpty()) {
                showError("Please enter a username");
                usernameField.requestFocus();
                return;
            }
            
            if (password.trim().isEmpty()) {
                showError("Please enter a password");
                passwordField.requestFocus();
                return;
            }
            
            if (password.length() < 6) {
                showError("Password must be at least 6 characters long");
                passwordField.requestFocus();
                return;
            }
            
            if (!password.equals(confirmPass)) {
                showError("Passwords do not match");
                confirmPasswordField.requestFocus();
                return;
            }
            
            // Check if username already exists
            if (DatabaseUtil.usernameExists(username)) {
                showError("Username already exists. Please choose another.");
                usernameField.requestFocus();
                return;
            }
            
            // Create new user
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole(role);
            
            // Attempt to register user in database
            boolean success = DatabaseUtil.registerUser(newUser);
            
            if (success) {
                showMessage("Registration successful!", "Welcome to QuickMarket");
                frame.dispose();
            } else {
                showError("Registration failed. Please try again.");
            }
            
        } catch (Exception ex) {
            showError("Error during registration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(
            frame,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(
            frame,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void show() {
        frame.setVisible(true);
    }
}