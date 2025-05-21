public class ActivityData {
    private final String activityType;
    private final int intensityLevel;
    private final long timestamp;
    private final String childId;
    
    public ActivityData(String childId, String activityType, int intensityLevel) {
        this.childId = childId;
        this.activityType = activityType;
        this.intensityLevel = intensityLevel;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getChildId() {
        return childId;
    }
    
    public String getActivityType() {
        return activityType;
    }
    
    public int getIntensityLevel() {
        return intensityLevel;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
    
    @Override
    public String toString() {
        return "ActivityData [child=" + childId +
               ", type=" + activityType + 
               ", intensity=" + intensityLevel + 
               ", time=" + getFormattedTimestamp() + "]";
    }
}