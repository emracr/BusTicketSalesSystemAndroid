package com.ansi.busticketsalesautomation;

public class Campaign {

    private int id;
    private String title;
    private String content;
    private String startingDate;
    private String deadline;
    private int state;

    public Campaign(){

    }

    public Campaign(int id, String title, String content, String startingDate, String deadline, int state){
        setId(id);
        setTitle(title);
        setContent(content);
        setStartingDate(startingDate);
        setDeadline(deadline);
        setState(state);
    }

    public Campaign(String title, String content, String startingDate, String deadline, int state){
        setTitle(title);
        setContent(content);
        setStartingDate(startingDate);
        setDeadline(deadline);
        setState(state);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
