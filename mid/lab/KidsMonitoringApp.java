public class KidsMonitoringApp {
    public static void main(String[] args) {
        // Create components
        String childId = "Child-001";
        SensorDataProvider sensorProvider = new IoTSensorProvider(childId);
        ActivityDetector activityDetector = new ActivityDetector(sensorProvider);
        AlertManager alertManager = new AlertManager();
        ActivityAnalyzer activityAnalyzer = new ActivityAnalyzer();
        
        // Register observers
        activityDetector.registerObserver(alertManager);
        activityDetector.registerObserver(activityAnalyzer);
        
        // Create and display the GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            ActivityMonitoringGUI gui = new ActivityMonitoringGUI(activityDetector, alertManager);
            gui.setVisible(true);
        });
    }
}