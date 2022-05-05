/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation_week5;

import java.util.*;

/**
 *
 * @author halil
 */
public class Main {
    int SystemClock=0 ;
    int min = 2;
    int max = 10;
    int totalFaultyCount = 0;
    int factoryArrivalTime = 5;
    List <Integer> inspectionQueueLengths = new ArrayList();
    List <Integer> FELLengths = new ArrayList();
    PriorityQueue<Event> FEL = new PriorityQueue<>(new EventComparator());
    PriorityQueue<Event> inspectionQueue = new PriorityQueue<>(new EventComparator());
    Event progressEvent;
    int eventCount=1;
    public static void main(String[] args) {
        new Main().start_simulation();

    }
    public void start_simulation() {
        initialization_function();
        while (!(isFinished())) {
            Event event = time_advance_function();
            event_handling_function(event);
        }
        getReport();
    }
    public void initialization_function() {
        System.out.println("system initialized."+"initial arrival event generated and scheduled for t=5");
        System.out.println("initial arrival event generated and scheduled for t= "+ SystemClock);
        Event event = new Event(SystemClock,SystemClock+factoryArrivalTime,Event.EventType.Arrive);
        event.id=eventCount;
        FEL.add(event);
        eventCount++;
    }
    public  void createArriveEvent() {
        System.out.println("Arrival event generated and scheduled for t= "+ SystemClock);
        Event event = new Event(SystemClock,SystemClock+factoryArrivalTime,Event.EventType.Arrive);
        event.id=eventCount;
        FEL.add(event);
        eventCount++;
    }

    public boolean isFinished() {
        if(totalFaultyCount>=100){
            System.out.println("100 th part detected faulty");
            return true;
        }
        else {
            return false;
        }
    }
    public Event time_advance_function() {
        Event tempEvent = FEL.peek();
        SystemClock = tempEvent.eventEndTime;
        return tempEvent;
    }
    public void event_handling_function(Event event){
        if(event.eventType== Event.EventType.Arrive){
            System.out.println("System Time : "+ SystemClock);
            FELLengths.add(FEL.size());
            inspectionQueue.add(FEL.poll());
            if(progressEvent==null){
                sendElementToInspection();
            }
            createArriveEvent();
            getFel();
        }
        else if(event.eventType== Event.EventType.Inspection){
            System.out.println("System Time : "+ SystemClock);
            FEL.poll();
            progressEvent=null;
            event.eventStatus=getRandomStatus();
            if(event.eventStatus==0){
                System.out.println(totalFaultyCount+". event"+" faulty olarak belirlendi");
                totalFaultyCount++;
            }
            if(!inspectionQueue.isEmpty()){
                sendElementToInspection();
            }
            else{
                System.out.println("queue is empty");
            }
        }
    }
    public void sendElementToInspection(){
        System.out.println("progress started by inspector");
        inspectionQueue.poll();
        System.out.println("queue length : " + inspectionQueue.size());
        int random_Inspection=getInspectTime();
        int endTime=random_Inspection+SystemClock;
        Event inspectionEvent = new Event(SystemClock,endTime,Event.EventType.Inspection);
        progressEvent=inspectionEvent;
        inspectionQueueLengths.add(inspectionQueue.size());
        FEL.add(inspectionEvent);
    }
    public void getReport(){
        System.out.println("Halil Ibrahim YARIŞ- 1721221020");
        System.out.println("---------- SIMULATION REPORT -------------");
        System.out.println(" Total Simulation Time : "+ SystemClock);
        System.out.println("FEL size when simulation end : ");
        System.out.println(FEL.size());
        System.out.println("FEL situation when simulation end");
        getFel();
        System.out.println("--------------------------------------------");
        System.out.println("FEL Statistics->");
        System.out.println("Max FEL Length : "+ getMaxFELLength());
        System.out.println("Average FEL Length : "+ getAverageFELLength());
        System.out.println("Inspection Queue Statistics->");
        System.out.println("Max Inspection Queue Length : "+ getMaxQueueLength());
        System.out.println("Average Inspection Queue Length : "+getAverageQueueLength());
    }
    public void getFel(){
        List<Event> array_list = new ArrayList<>(FEL);
        for (Event event : array_list) {
            System.out.println("Event Type : " + event.eventType);
            System.out.println("Event Start Time : " + event.eventStartTime);
            System.out.println("Event End Time : " + event.eventEndTime);
            System.out.println("--------------------------------------");
        }
        System.out.println("########################################");
    }
    public double getAverageQueueLength(){
        return inspectionQueueLengths.stream()
                .mapToInt(d -> d)
                .average()
                .orElse(0.0);
    }
    public double getAverageFELLength(){
        return FELLengths.stream()
                .mapToInt(d -> d)
                .average()
                .orElse(0.0);
    }
    public int getMaxQueueLength(){

        return Collections.max(inspectionQueueLengths);
    }
    public int getMaxFELLength(){

        return Collections.max(FELLengths);
    }

    public int getInspectTime(){
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
    public int getRandomStatus() {

        return (int) Math.floor(Math.random() * (9) + 0);
    }

}
