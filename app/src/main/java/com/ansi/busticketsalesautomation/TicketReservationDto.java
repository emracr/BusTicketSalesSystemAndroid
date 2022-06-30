package com.ansi.busticketsalesautomation;

public class TicketReservationDto {

    private TicketReservation ticketReservation;
    private ExpeditionDto expeditionDto;

    public TicketReservationDto(){

    }

    public TicketReservationDto(TicketReservation ticketReservation, ExpeditionDto expeditionDto){
        setTicketReservation(ticketReservation);
        setExpeditionDto(expeditionDto);
    }

    public TicketReservation getTicketReservation() {
        return ticketReservation;
    }

    public void setTicketReservation(TicketReservation ticketReservation) {
        this.ticketReservation = ticketReservation;
    }

    public ExpeditionDto getExpeditionDto() {
        return expeditionDto;
    }

    public void setExpeditionDto(ExpeditionDto expeditionDto) {
        this.expeditionDto = expeditionDto;
    }
}
