package com.ansi.busticketsalesautomation;

public class TicketAndUserDto {

    private Ticket ticket;
    private User user;

    public TicketAndUserDto(){

    }

    public TicketAndUserDto(Ticket ticket, User user){
        setTicket(ticket);
        setUser(user);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
