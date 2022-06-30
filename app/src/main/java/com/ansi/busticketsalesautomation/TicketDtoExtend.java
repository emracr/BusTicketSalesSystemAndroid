package com.ansi.busticketsalesautomation;

import java.util.List;

public class TicketDtoExtend {

    private List<TicketDto> ticketDto;
    private User user;

    public TicketDtoExtend(){

    }

    public TicketDtoExtend(List<TicketDto> ticketDto, User user){
        setTicketDto(ticketDto);
        setUser(user);
    }

    public List<TicketDto> getTicketDto() {
        return ticketDto;
    }

    public void setTicketDto(List<TicketDto> ticketDto) {
        this.ticketDto = ticketDto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
