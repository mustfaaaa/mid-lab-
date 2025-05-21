import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ActivityMonitoringGUI extends JFrame implements AlertManager.AlertListener {
    private static final long serialVersionUID = 1L;
    
    private final ActivityDetector activityDetector;
    private JTextArea activityLogArea;
    private JTextArea alertLogArea;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton detectNowButton;
    
    public ActivityMonitoringGUI(ActivityDetector activityDetector, AlertManager alertManager) {
        this.activityDetector = activityDetector;
        
        // Register this GUI as an alert listener
        alertManager.addAlertListener(this);
        
        // Set up the frame
        setTitle("Kids Activity Monitoring");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create the components
        createComponents();
        
        // Set the layout
        layoutComponents();
        
        // Add event handlers
        addEventHandlers();
    }
    
    private void createComponents() {
        // Create the text areas for logging
        activityLogArea = new JTextArea();
        activityLogArea.setEditable(false);
        activityLogArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        alertLogArea = new JTextArea();
        alertLogArea.setEditable(false);
        alertLogArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        alertLogArea.setForeground(Color.RED);
        
        // Create the buttons
        startButton = new JButton("Start Monitoring");
        stopButton = new JButton("Stop Monitoring");
        stopButton.setEnabled(false);
        detectNowButton = new JButton("Detect Now");
        
        // Create status label
        statusLabel = new JLabel("Monitoring status: Stopped");
        statusLabel.setFont(new Font("Dialog", Font.BOLD, 14));
    }
    
    private void layoutComponents() {
        // Create a panel for the control buttons
        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(detectNowButton);
        
        // Create a panel for the status
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusPanel.add(statusLabel, BorderLayout.WEST);
        
        // Create a panel for the log areas
        JPanel logsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Activity log with scroll pane
        JPanel activityLogPanel = new JPanel(new BorderLayout());
        activityLogPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Activity Log", 
                TitledBorder.LEFT, TitledBorder.TOP));
        JScrollPane activityScrollPane = new JScrollPane(activityLogArea);
        activityScrollPane.setPreferredSize(new Dimension(350, 400));
        activityLogPanel.add(activityScrollPane, BorderLayout.CENTER);
        
        // Alert log with scroll pane
        JPanel alertLogPanel = new JPanel(new BorderLayout());
        alertLogPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Alert Log", 
                TitledBorder.LEFT, TitledBorder.TOP));
        JScrollPane alertScrollPane = new JScrollPane(alertLogArea);
        alertScrollPane.setPreferredSize(new Dimension(350, 400));
        alertLogPanel.add(alertScrollPane, BorderLayout.CENTER);
        
        logsPanel.add(activityLogPanel);
        logsPanel.add(alertLogPanel);
        
        // Add all panels to the frame
        add(statusPanel, BorderLayout.NORTH);
        add(logsPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void addEventHandlers() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMonitoring();
            }
        });
        
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMonitoring();
            }
        });
        
        detectNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detectActivity();
            }
        });
    }
    
    private void startMonitoring() {
        activityDetector.startDetection(2000); // 2 seconds interval
        
        // Update UI state
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        statusLabel.setText("Monitoring status: Running");
        
        logActivity("Monitoring started");
    }
    
    private void stopMonitoring() {
        activityDetector.stopDetection();
        
        // Update UI state
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        statusLabel.setText("Monitoring status: Stopped");
        
        logActivity("Monitoring stopped");
    }
    
    private void detectActivity() {
        ActivityData data = activityDetector.detectActivity();
        logActivity("Manual detection: " + data);
    }
    
    private void logActivity(String message) {
        SwingUtilities.invokeLater(() -> {
            activityLogArea.append(message + "\n");
            activityLogArea.setCaretPosition(activityLogArea.getDocument().getLength());
        });
    }
    
    private void logAlert(String message) {
        SwingUtilities.invokeLater(() -> {
            alertLogArea.append(message + "\n");
            alertLogArea.setCaretPosition(alertLogArea.getDocument().getLength());
        });
    }
    
    @Override
    public void onAlertTriggered(ActivityData data, String reason) {
        String alertMessage = String.format("[%s] ALERT for %s: %s - %s (Intensity: %d)",
                data.getFormattedTimestamp(),
                data.getChildId(),
                reason,
                data.getActivityType(),
                data.getIntensityLevel());
        
        logAlert(alertMessage);
    }
}
