public class ActivityAnalyzer implements Observer {
    @Override
    public void update(ActivityData activityData) {
        // Process and analyze the activity data
        analyzeActivity(activityData);
    }
    
    private void analyzeActivity(ActivityData data) {
        // In a real implementation, this would have more complex analysis logic
        String analysis = String.format(
            "Analysis for %s: Activity type %s with intensity %d suggests %s",
            data.getChildId(),
            data.getActivityType(),
            data.getIntensityLevel(),
            getActivityImpact(data)
        );
        
        System.out.println(analysis);
    }
    
    private String getActivityImpact(ActivityData data) {
        // Simple analysis based on activity type and intensity
        String activityType = data.getActivityType();
        int intensity = data.getIntensityLevel();
        
        if ("Running".equals(activityType) || "Playing".equals(activityType)) {
            if (intensity > 7) return "high physical exertion, monitor for exhaustion";
            if (intensity > 4) return "moderate exercise, beneficial activity";
            return "light play, good for development";
        } else if ("Walking".equals(activityType)) {
            if (intensity > 6) return "brisk walking, good cardio exercise";
            return "casual walking, light activity";
        } else if ("Sitting".equals(activityType)) {
            if (intensity > 3) return "engaged sitting, possibly educational activity";
            return "sedentary behavior, may need encouragement to be more active";
        } else if ("Sleeping".equals(activityType)) {
            return "rest period, important for development";
        }
        
        return "normal childhood activity";
    }
}