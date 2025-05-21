import java.util.ArrayList;
import java.util.List;

import com.kidsmonitoring.core.Observer;
import com.kidsmonitoring.core.Subject;
import com.kidsmonitoring.model.ActivityData;
import com.kidsmonitoring.sensors.SensorDataProvider;

public class ActivityDetector implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private final SensorDataProvider sensorDataProvider;
    private ActivityData latestActivityData;
    private boolean isRunning = false;
    
    public ActivityDetector(SensorDataProvider sensorDataProvider) {
        this.sensorDataProvider = sensorDataProvider;
    }
    
    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(latestActivityData);
        }
    }
    
    /**
     * Method to detect activity from sensors and notify observers
     */
    public ActivityData detectActivity() {
        // Get data from the sensor provider
        latestActivityData = sensorDataProvider.collectData();
        
        // Notify all registered observers about the new activity
        notifyObservers();
        
        return latestActivityData;
    }
    
    /**
     * Start continuous activity detection in a separate thread
     */
    public void startDetection(int intervalMillis) {
        if (isRunning) return;
        
        isRunning = true;
        Thread detectionThread = new Thread(() -> {
            while (isRunning) {
                detectActivity();
                
                try {
                    Thread.sleep(intervalMillis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        detectionThread.setDaemon(true);
        detectionThread.start();
    }
    
    /**
     * Stop the activity detection
     */
    public void stopDetection() {
        isRunning = false;
    }
}