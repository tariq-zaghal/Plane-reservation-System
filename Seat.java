package com.tariq;

public class Seat {
    private String seatNumber;
    private String passengerName;

    Seat(String seatNumber){
        this.seatNumber = seatNumber;
        passengerName = null;
    }

//this method checks if the seat is valid or not
    public static boolean isValid(String seatNumber) {
        if(seatNumber.length()<2||seatNumber.length()>3) //since all the seat numbers exist between A1-D12 , all of which are between 2-3 characters
            return false;
        int rowCheck;
        char colCheck = seatNumber.charAt(0);

        try{
          rowCheck =Integer.parseInt(seatNumber.substring(1));
        }
        catch (NumberFormatException n){
            return false;
        }
//checks if the seat is in 1-3 rows or 4-12 rows
        return ((colCheck == 'A' || colCheck == 'C') && rowCheck >= 1 && rowCheck <= 3) || (rowCheck >= 4 && rowCheck <= 12) && (colCheck >= 'A' && colCheck <= 'D');

    }

    //checks if the seat is empty, the seat is empty when the passengerName field is null
    public boolean isEmpty(){
        if(passengerName == null)
            return true;
        else
            return false;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    //gets the number of the row from 0-11
    public static int getRow(String seatNumber) {
        return (Integer.parseInt(seatNumber.substring(1)) - 1);
    }

    //gets the number of the column by converting  A B C D to numbers from 0-3
    public static int getColumn(String seatNumber){
        //the if statement is to deal with the first three rows
        if(!(seatNumber.charAt(0) == 'C' && Integer.parseInt(seatNumber.substring(1))>=1 && Integer.parseInt(seatNumber.substring(1))<=3)) {
            return ((int) seatNumber.charAt(0)) - 65;
        }else{
            return  ((int) seatNumber.charAt(0)) - 66;
        }
    }

    @Override
    public String toString() {
        return  seatNumber + " : " + passengerName+"\n";
    }
}
