package quickmarket.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import quickmarket.model.User;
import quickmarket.view.dashboard.DeliveryDashboardPanel;
import quickmarket.view.dashboard.StoreDashboardPanel;

public class Dashboard {

    private JFrame frame;
    private User currentUser;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(240, 240, 250);

    public Dashboard(User user) {
        this.currentUser = user;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("QuickMarket - " + currentUser.getRole());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // Add top navigation bar
        mainPanel.add(createTopBar(), BorderLayout.NORTH);

        // Add side navigation based on role
        mainPanel.add(createSideNav(), BorderLayout.WEST);

        // Add main content area
        mainPanel.add(createMainContent(), BorderLayout.CENTER);

        frame.add(mainPanel);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(primaryColor);
        topBar.setPreferredSize(new Dimension(800, 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Add logo/title
        JLabel titleLabel = new JLabel("QuickMarket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        // Add user info and logout
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel(currentUser.getUsername() + " (" + currentUser.getRole() + ")");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutButton.addActionListener(e -> handleLogout());

        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(userPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSideNav() {
        JPanel sideNav = new JPanel();
        sideNav.setPreferredSize(new Dimension(200, 600));
        sideNav.setBackground(Color.WHITE);
        sideNav.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        sideNav.setLayout(new BoxLayout(sideNav, BoxLayout.Y_AXIS));

        // Add navigation buttons based on role
        if (currentUser.getRole().equals("Store Owner") || currentUser.getRole().equals("Store Staff")) {
            addNavButton(sideNav, "Dashboard", e -> showStoreDashboard());
            addNavButton(sideNav, "Products", e -> showProducts());
            addNavButton(sideNav, "Orders", e -> showOrders());
            addNavButton(sideNav, "Reports", e -> showReports());
        } else {
            addNavButton(sideNav, "Dashboard", e -> showDeliveryDashboard());
            addNavButton(sideNav, "Deliveries", e -> showDeliveries());
            addNavButton(sideNav, "Route Planning", e -> showRoutePlanning());
        }

        return sideNav;
    }

    private void addNavButton(JPanel panel, String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.addActionListener(listener);

        panel.add(button);
    }

    private JPanel createMainContent() {
        contentPanel = new JPanel(new BorderLayout());  // Initialize the content panel
        contentPanel.setBackground(backgroundColor);

        // Show default content based on role
        if (currentUser.getRole().equals("Store Owner") || currentUser.getRole().equals("Store Staff")) {
            showStoreDashboard();
        } else {
            showDeliveryDashboard();
        }

        return contentPanel;
    }
    private JPanel contentPanel;

    private void styleButton(JButton button) {
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(primaryColor);
        button.setBorder(new LineBorder(primaryColor));
        button.setFocusPainted(false);
    }

    private void showStoreDashboard() {
        // Clear the content panel
        contentPanel.removeAll();

        // Add new dashboard panel
        StoreDashboardPanel dashboardPanel = new StoreDashboardPanel(currentUser);
        contentPanel.add(dashboardPanel, BorderLayout.CENTER);

        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }

  private void showDeliveryDashboard() {
        // Clear the content panel
        contentPanel.removeAll();
        
        // Add new dashboard panel
        DeliveryDashboardPanel dashboardPanel = new DeliveryDashboardPanel(currentUser);
        contentPanel.add(dashboardPanel, BorderLayout.CENTER);
        
        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }

private void showProducts() {
        // Clear the content panel
        contentPanel.removeAll();
        
        // Create a placeholder panel
        JPanel placeholderPanel = new JPanel(new BorderLayout());
        placeholderPanel.setBackground(backgroundColor);
        placeholderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("Products Management - Coming Soon ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        placeholderPanel.add(messageLabel, BorderLayout.CENTER);
        
        // Add to content panel
        contentPanel.add(placeholderPanel, BorderLayout.CENTER);
        
        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showOrders() {
        // Clear the content panel
        contentPanel.removeAll();
        
        // Create a placeholder panel
        JPanel placeholderPanel = new JPanel(new BorderLayout());
        placeholderPanel.setBackground(backgroundColor);
        placeholderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("Orders Management - Coming Soon ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        placeholderPanel.add(messageLabel, BorderLayout.CENTER);
        
        // Add to content panel
        contentPanel.add(placeholderPanel, BorderLayout.CENTER);
        
        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showReports() {
        // Clear the content panel
        contentPanel.removeAll();
        
        // Create a placeholder panel
        JPanel placeholderPanel = new JPanel(new BorderLayout());
        placeholderPanel.setBackground(backgroundColor);
        placeholderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel messageLabel = new JLabel("Reports - Coming Soon ");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        placeholderPanel.add(messageLabel, BorderLayout.CENTER);
        
        // Add to content panel
        contentPanel.add(placeholderPanel, BorderLayout.CENTER);
        
        // Refresh the display
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showDeliveries() {
        // TODO: Implement deliveries view
        System.out.println("Showing deliveries");
    }

    private void showRoutePlanning() {
        // TODO: Implement route planning view
        System.out.println("Showing route planning");
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            Login loginWindow = new Login();
            loginWindow.show();
        }
    }

    public void show() {
        frame.setVisible(true);
    }
}
