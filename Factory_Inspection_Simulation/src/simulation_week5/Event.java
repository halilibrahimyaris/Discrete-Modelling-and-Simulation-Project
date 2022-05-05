/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation_week5;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author halil
 */
public class Event implements Serializable,Comparable {
    @Override
    public int compareTo(Object o) {
        return 0;
    }

    enum EventType{
        Arrive,
        Inspection

    }
    int id;
    int eventStartTime;
    int eventEndTime;
    int eventStatus;
    EventType eventType;

    Event(int startTime , int endTime,EventType eventType){

        this.eventStartTime=startTime;
        this.eventEndTime=endTime;
        this.eventType=eventType;
    }
}
class EventComparator implements Comparator<Event> {
    public int compare(Event event1, Event event2) {
        if (event1.eventEndTime > event2.eventEndTime)
            return 1;
        else if (event1.eventEndTime < event2.eventEndTime)
            return -1;
        return 0;
    }
}
