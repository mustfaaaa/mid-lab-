public class IoTSensorProvider implements SensorDataProvider {
    private final String childId;
    
    public IoTSensorProvider(String childId) {
        this.childId = childId;
    }
    
    // In a real implementation, this would interface with actual IoT devices
    @Override
    public ActivityData collectData() {
        // Simulate collecting data from IoT sensors
        // In a real app, this would fetch data from actual sensors
        
        // Randomly choose an activity type for demo purposes
        String[] activityTypes = {"Running", "Walking", "Sitting", "Sleeping", "Playing"};
        String randomActivity = activityTypes[(int)(Math.random() * activityTypes.length)];
        
        // Random intensity level from 1-10
        int randomIntensity = (int)(Math.random() * 10) + 1;
        
        return new ActivityData(childId, randomActivity, randomIntensity);
    }
}