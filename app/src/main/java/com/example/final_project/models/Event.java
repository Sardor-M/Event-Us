package com.example.final_project.models;

public class Event {
    private String name;
    private String description;
    private String date;
    private String venue;
    private String time;

    public Event (String name, String description, String date, String venue, String time){
        this.name = name;
        this.description = description;
        this.date = date;
        this.venue= venue;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public String getVenue(){
        return venue;
    }

    public String getTime(){
        return time;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setVenue(String venue){
        this.venue = venue;
    }

    public void setTime(String time){
        this.time = time;
    }
}
