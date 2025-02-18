package it.unibas.service;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SecurityEventManager {

    @Getter
    private static SecurityEventManager instance = new SecurityEventManager();
    private List<ISecurityEventObserver> observers = new ArrayList<>();

    private SecurityEventManager() {}

    public void subscribe(ISecurityEventObserver observer) {
        observers.add(observer);
    }

    public void notifyEvent(String event) {
        for (ISecurityEventObserver observer : observers) {
            observer.update(event);
        }
    }
}
