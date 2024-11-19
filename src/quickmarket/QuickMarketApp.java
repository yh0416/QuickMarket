package quickmarket;

import quickmarket.view.Login;
import javax.swing.SwingUtilities;

public class QuickMarketApp {
    public static void main(String[] args) {
        // Initialize database
        quickmarket.util.DatabaseUtil.initializeDatabase();
        
        // Launch the login window
        SwingUtilities.invokeLater(() -> {
            Login loginWindow = new Login();
            loginWindow.show();
        });
    }
}