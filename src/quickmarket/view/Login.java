package quickmarket.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import quickmarket.util.DatabaseUtil;
import quickmarket.model.User;

public class Login {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private Color primaryColor = new Color(70, 130, 180); // Steel Blue
    private Color backgroundColor = new Color(240, 240, 250);
    
    public Login() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("QuickMarket Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(backgroundColor);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(backgroundColor);
        
        // Logo/Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(backgroundColor);
        JLabel titleLabel = new JLabel("QuickMarket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);
        titlePanel.add(titleLabel);
        
        // Subtitle
        JPanel subtitlePanel = new JPanel();
        subtitlePanel.setBackground(backgroundColor);
        JLabel subtitleLabel = new JLabel("Welcome back! Please login to your account.");
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
        
        // Username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = createStyledTextField();
        
        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField = createStyledPasswordField();
        
        // Role selection
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
        formPanel.add(roleLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(roleComboBox, gbc);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(backgroundColor);
        
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        
        JButton registerButton = createStyledButton("Register");
        registerButton.setBackground(new Color(240, 240, 240));
        registerButton.setForeground(primaryColor);
        registerButton.addActionListener(e -> {
            Register registerWindow = new Register();
            registerWindow.show();
        });
        
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 10, 0);
        buttonPanel.add(loginButton, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(registerButton, gbc);
        
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
        
        if (text.equals("Login")) {
            button.setBackground(primaryColor);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
            button.setBorder(new LineBorder(primaryColor));
        }
        
        return button;
    }

    private void handleLogin() {
        try {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            if (username.trim().isEmpty()) {
                showError("Please enter a username");
                return;
            }
            
            if (password.trim().isEmpty()) {
                showError("Please enter a password");
                return;
            }
            
            User user = DatabaseUtil.validateLogin(username, password);
            
            if (user != null) {
                if (user.getRole().equals(role)) {
                    frame.dispose(); // Close login window
                    Dashboard dashboard = new Dashboard(user);
                    dashboard.show();
                } else {
                    showError("Invalid role selected for this user");
                }
            } else {
                showError("Invalid username or password");
            }
        } catch (Exception ex) {
            showError("Database error: " + ex.getMessage());
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