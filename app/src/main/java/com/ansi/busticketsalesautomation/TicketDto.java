package com.ansi.busticketsalesautomation;

public class TicketDto {

    private Ticket ticket;
    private ExpeditionDto expeditionDto;

    public TicketDto(){

    }

    public TicketDto(Ticket ticket, ExpeditionDto expeditionDto){
        setTicket(ticket);
        setExpeditionDto(expeditionDto);
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public ExpeditionDto getExpeditionDto() {
        return expeditionDto;
    }

    public void setExpeditionDto(ExpeditionDto expeditionDto) {
        this.expeditionDto = expeditionDto;
    }
}
