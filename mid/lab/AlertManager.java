import java.util.ArrayList;
import java.util.List;
public class AlertManager implements Observer {
    private List<AlertListener> listeners = new ArrayList<>();
    
    public interface AlertListener {
        void onAlertTriggered(ActivityData data, String reason);
    }
    
    public void addAlertListener(AlertListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void update(ActivityData activityData) {
        // Analyze the activity data to determine if an alert should be triggered
        String alertReason = checkAlertCondition(activityData);
        if (alertReason != null) {
            triggerAlert(activityData, alertReason);
        }
    }
    
    private String checkAlertCondition(ActivityData data) {
        // Define conditions that would trigger an alert
        if (data.getIntensityLevel() > 8) {
            return "High intensity activity detected";
        } else if ("Running".equals(data.getActivityType())) {
            return "Running activity detected";
        } else if ("Playing".equals(data.getActivityType()) && data.getIntensityLevel() > 5) {
            return "High intensity playing detected";
        }
        return null; // No alert condition met
    }
    
    private void triggerAlert(ActivityData data, String reason) {
        // Notify all listeners about the alert
        for (AlertListener listener : listeners) {
            listener.onAlertTriggered(data, reason);
        }
    }
}
