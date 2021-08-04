package com.case1;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TicketApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Train Number:");
		int trainNo = sc.nextInt();
		TrainDAO trainDAO = new TrainDAO();
		Train train = trainDAO.findTrain(trainNo);
		if(train == null) {
			sc.close();
			System.exit(0);
		}
		sc.nextLine();
		System.out.println("Enter Travel Date:");
		LocalDate travelDate = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println("Enter Number of Passengers:");
		Ticket ticket = new Ticket(travelDate,train);
		int count = sc.nextInt();
		while(count > 0) {
			sc.nextLine();
			System.out.println("Enter Passenger Name:");
			String name = sc.nextLine();
			System.out.println("Enter Age:");
			int age = sc.nextInt();
			System.out.println("Enter Gender(M/F):");
			char gender = sc.next().charAt(0);
			ticket.addPassenger(name,age,gender);
			count--;
		}
		ticket.writeTicket();
		System.out.println("Ticket Booked with PNR: " + ticket.getPnr());
		System.out.println();
		sc.close();
	}
}