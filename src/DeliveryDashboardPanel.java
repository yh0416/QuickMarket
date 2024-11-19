package quickmarket.view.dashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import quickmarket.model.User;

public class DeliveryDashboardPanel extends JPanel {
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(240, 240, 250);
    private User currentUser;
    
    public DeliveryDashboardPanel(User user) {
        this.currentUser = user;
        initialize();
    }
    
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome Panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.setBackground(backgroundColor);
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel);
        
        // Main Content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);
        
        // Simple status label
        JLabel statusLabel = new JLabel("You are logged in as: " + currentUser.getRole());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        mainPanel.add(statusLabel, BorderLayout.CENTER);
        
        add(welcomePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}