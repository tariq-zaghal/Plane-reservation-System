package com.tariq;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Flight {
    public static Scanner sc = new Scanner(System.in);
    private static Seat[][] seats;
    public static File passenger = new File("passengers.txt"); //the file

    public static void main(String[] args) throws IOException {
        seats = new Seat[][]{
                {new Seat("A1"), new Seat("C1")},
                {new Seat("A2"), new Seat("C2")},
                {new Seat("A3"), new Seat("C3")},
                {new Seat("A4"), new Seat("B4"), new Seat("C4"), new Seat("D4")},
                {new Seat("A5"), new Seat("B5"), new Seat("C5"), new Seat("D5")},
                {new Seat("A6"), new Seat("B6"), new Seat("C6"), new Seat("D6")},
                {new Seat("A7"), new Seat("B7"), new Seat("C7"), new Seat("D7")},
                {new Seat("A8"), new Seat("B8"), new Seat("C8"), new Seat("D8")},
                {new Seat("A9"), new Seat("B9"), new Seat("C9"), new Seat("D9")},
                {new Seat("A10"), new Seat("B10"), new Seat("C10"), new Seat("D10")},
                {new Seat("A11"), new Seat("B11"), new Seat("C11"), new Seat("D11")},
                {new Seat("A12"), new Seat("B12"), new Seat("C12"), new Seat("D12")},

        };


        int order =0;
        //here we take the number of the order from the user, validate it, and run the chosen method
        while(order !=6) {
            orderList(); //displays the menu to the user
            try {
                order = sc.nextInt();
            }catch (InputMismatchException e){
              order = 7;

            }
            // handles invalid requests:
            while(!(order>=1 && order<=6)){
                System.out.println("the request is invalid, please enter a valid request: ");
                orderList();
                order = sc.nextInt();
            }
            //chooses the proper method to execute
            options(order);
        }


    }


//shows the list of options available
    public static void orderList(){
        System.out.println("1. Read passengers file.\n" +
                "2. Reserve a new empty seat.\n" +
                "3. Delete a reserved seat.\n" +
                "4. Delete all reserved seats.\n" +
                "5. Update passengers file.\n" +
                "6. Quit. \n");
    }

    //this function contain the functions that can be ordered by the user
    public static void options(int order) throws IOException{
        switch (order) {
            case 1:
                passengersFileRead();
                break;
            case 2:
                reserveSeat();
                break;
            case 3:
                cancelSeat();
                break;
            case 4:
                cancelAllReservations();
                break;
            case 5:
                writeOnFile();
                break;
            case 6: System.out.println("Thank you for using our program!!");
                break;

            default: System.out.println("enter a valid request");
        }
    }


//takes the passenger file, reads it,and reserves seats as suitable
    public static void passengersFileRead(){

//since it's a file it could throw a FileNotFoundException
        Scanner fl = null;
        try{
            fl = new Scanner(passenger);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        };

        while (fl.hasNextLine()){
            String fileRow = fl.nextLine();
            String[] fileInput = fileRow.split(": "); //seperates the names from the seat number
            String seatNumber = fileInput[0]; //this is the seat number field
            //System.out.println(seatNumber+" "+ fileInput[1]); //that's the name field
            int rowNum;
            int colNum;
            if(!Seat.isValid(seatNumber)){
                System.out.println("Error: this seat is not on our plane!!");
            }
            if(Seat.isValid(seatNumber)){
                    rowNum = Seat.getRow(seatNumber);
                    colNum = Seat.getColumn(seatNumber);

                try {

                    if (seats[rowNum][colNum].isEmpty()) {
                        seats[rowNum][colNum].setPassengerName(fileInput[1]);//sets the seat to a paddengers name
                        System.out.println(seats[rowNum][colNum].toString());
                    }
                    else
                        System.out.println("the seat no: " + seats[rowNum][colNum].getSeatNumber() + " is already full!");
                }catch (ArrayIndexOutOfBoundsException ignored){
                }
            }
        }
    }




    //this method reserves a seat
    public static void reserveSeat(){

        String seatNum;
        int rowNum;
        int colNum;

        while(true){
            System.out.println("enter a seat to reserve or -1 to exit: ");
            seatNum = sc.next();

            //exists the method if -1 is entered
            try {
                if(Integer.parseInt(seatNum) == -1){
                    break;
                }
            }catch (NumberFormatException e){

            }


            //check if the seat actually exists. if not, it asks to enter a legit seat, hence the loop.
            while (!Seat.isValid(seatNum)){
                System.out.println("please enter a VALID seat number:");
                seatNum = sc.next();
            }

            //locates the seat in the array
            if(!(seatNum.charAt(0) == 'C' && Integer.parseInt(seatNum.substring(1))>=1 && Integer.parseInt(seatNum.substring(1))<=3)) {
                rowNum = (Integer.parseInt(seatNum.substring(1)) - 1);
                colNum = ((int) seatNum.charAt(0)) - 65;
            }else{
                rowNum = (Integer.parseInt(seatNum.substring(1)) - 1);
                colNum = ((int) seatNum.charAt(0)) - 66;
            }

            //checks whether the seat is empty or not
            if(seats[rowNum][colNum].isEmpty()){
                System.out.println("this seat is empty!"+
                        "\nTo complete your reservation please enter the name of the passenger: ");
                seats[rowNum][colNum].setPassengerName(sc.next());
                System.out.println("have a happy flight!!");
                System.out.println(seats[rowNum][colNum].toString());
                break;
            }else{
                System.out.println("this seat is already reserved! Try to reserve a different seat or press -1 for cancellation");
                reserveSeat();
            }


        }


    }

    //this function cancel the seat reservation (order 3)
    public static void cancelSeat(){
        String seatNum;
        int rowNum;
        int colNum;
//the while loop makes sure that the entered number is correct
        while(true) {
            System.out.println("enter a seat to reserve or -1 to exit: ");
            seatNum = sc.next();

            //exists the method if -1 is entered
            try {
                if(Integer.parseInt(seatNum) == -1){
                    break;
                }
            }catch (NumberFormatException e){

            }

            //check if the seat actually exists. if not, it asks to enter a legit seat, hence the loop.
            while (!Seat.isValid(seatNum)){
                System.out.println("please enter a VALID seat number:");
                seatNum = sc.next();
            }


            //locates the seat in the array

            //the if statement checks for the seats in the first 3 rows and mutates the seat locating algorithm to reach them
            if(!(seatNum.charAt(0) == 'C' && Integer.parseInt(seatNum.substring(1))>=1 && Integer.parseInt(seatNum.substring(1))<=3)) {
                rowNum = (Integer.parseInt(seatNum.substring(1)) - 1);
                colNum = ((int) seatNum.charAt(0)) - 65;
            }else{
                rowNum = (Integer.parseInt(seatNum.substring(1)) - 1);
                colNum = ((int) seatNum.charAt(0)) - 66;
            }

            //checks if the seat whether the seat is reserved or empty. if reserved it cancels the reservation
            if(!seats[rowNum][colNum].isEmpty()){
                seats[rowNum][colNum].setPassengerName(null);
                System.out.println("your reservation is cancelled!" +
                        "\nWe hope to see you soon on another flight");
                break;
            }else{
                System.out.println("this seat is not reserved!"+
                        "\nyou may have entered the wrong seat\n" +
                        "please check again ");
                cancelSeat(); //calls again to enter a different seat
            }

        }
    }

    //this method cancel all reservations made by setting the names to null
    public static void cancelAllReservations(){
        for(int i = 0; i<seats.length;i++){
            for (int j =0; j<seats[i].length;j++){
                if(!(seats[i][j]==null)) {
                    seats[i][j].setPassengerName(null);
                }
            }
        }
    }

    //this method excuted order 5, which is updating the passenger file with all the reserved seats
    public static void writeOnFile() throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(passenger);
        }catch (IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i<seats.length;i++){
            for (int j =0; j<seats[i].length;j++) {
                if(!(seats[i][j].getPassengerName() == null))
                writer.write(seats[i][j].toString());  //prints the reserved seats to the file

            }
        }
        writer.close();
        System.out.println("All reserved seats are now transferred to the Passenger file!");
    }


}