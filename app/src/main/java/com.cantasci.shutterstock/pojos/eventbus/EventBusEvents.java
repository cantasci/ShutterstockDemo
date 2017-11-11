package com.cantasci.shutterstock.pojos.eventbus;


public class EventBusEvents {
    public static class ActivityQueryChanged {
        public String query;

        public ActivityQueryChanged() {}
        public ActivityQueryChanged(String query) { this.query=query;}

    }

    public static class ActivityQueryDefault {
        public String query;

        public ActivityQueryDefault() {}
        public ActivityQueryDefault(String query) { this.query=query;}

    }
}
