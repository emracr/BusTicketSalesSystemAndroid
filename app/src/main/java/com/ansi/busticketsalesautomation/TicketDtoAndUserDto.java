package com.ansi.busticketsalesautomation;

public class TicketDtoAndUserDto {

    private TicketDto ticketDto;
    private User user;

    public TicketDtoAndUserDto(){

    }

    public TicketDtoAndUserDto(TicketDto ticketDto, User user){
        setTicketDto(ticketDto);
        setUser(user);
    }

    public TicketDto getTicketDto() {
        return ticketDto;
    }

    public void setTicketDto(TicketDto ticketDto) {
        this.ticketDto = ticketDto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
