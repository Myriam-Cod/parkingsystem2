package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        //Corrected
        double duration = (outHour - inHour) / 1000;
        duration = duration / (60*60);

        if(duration < 0.5) {
            ticket.setPrice(0);
        }else if(ticket.getForCurrentUser()) {
        	  switch (ticket.getParkingSpot().getParkingType()){
              case CAR: {
                  ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR) * 0.95);
                  break;
              }
              case BIKE: {
                  ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR) * 0.95);
                  break;
              }
              default: throw new IllegalArgumentException("Unkown Parking Type");
          }
        }else {
        	switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        	}
        }
      
    }
}