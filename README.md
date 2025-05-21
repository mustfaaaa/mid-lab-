<h2>Principle used:</h2>

I am using Low Coupling (GRASP):
It Minimize dependencies between components. Changes in one component should have minimal impact on others. The "Activity Detection" component doesn't need to know how the data was collected or who receives the alerts; it just needs the analyzed data and publishes the detection event. Similarly, the "Alerting" component doesn't need to know how the activity was detected, only that an alert needs to be sent.
                 It helps us to Increases system flexibility, robustness, and reusability.

<h2>Pattern used:</h2>
I am using observer pattern:
It Defines a one-to-many dependency between objects so that when one       object changes state, all its dependents are notified automatically. As it Directly supports structured way for components to react to state changes in other components without tight coupling.
