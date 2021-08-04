package com.case1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ticket {
	private int counter;
	private String pnr;
	private LocalDate travelDate;
	private Train train;
	private TreeMap<Passenger,Integer> passengers = new TreeMap<>();

	public Ticket(LocalDate travelDate, Train train) {
		if(travelDate.isBefore(LocalDate.now())) {
			System.out.println("Travel Date is before current date");
			System.exit(0);
		}
		else {
			this.travelDate = travelDate;
			this.train = train;
		}
	}

	public String generatePNR() throws IOException {
		FileInputStream fis = new FileInputStream("datafile");
		int data = 0;
		int[] digit = new int[3];
		while( (data = fis.read())!= -1) {
			digit[0] = (char)data - '0';
			if( (data = fis.read())!= -1)
				digit[1] = (char)data - '0';
			if( (data = fis.read())!= -1)
				digit[2] = (char)data - '0';
		}
		counter = (digit[0]*100)+(digit[1]*10)+digit[2];
		++counter;
		fis.close();
		String number =String.valueOf(counter);  
		byte[] info = number.getBytes();
		FileOutputStream fos = new FileOutputStream("datafile", true);
		fos.write(info);
		fos.close();
		this.pnr = String.valueOf(this.getTrain().getSource().charAt(0)) + String.valueOf(this.getTrain().getDestination().charAt(0)) + "_" + this.travelDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" + counter;
		return this.pnr;
	}

	public double calcPassengerFare(Passenger passenger) {
		double fare = this.getTrain().getTicketPrice();
		if(passenger.getAge() <= 12) {
			fare = fare*0.5;
		}else if(passenger.getAge() >= 60) {
			fare = fare*0.6;
		}else if(passenger.getGender() == 'F' || passenger.getGender() == 'f' ){
			fare = fare*0.75;
		}
		return fare;
	}

	public void addPassenger(String name, int age, char gender) {
		Passenger passenger = new Passenger(name, age, gender);
		double fare = calcPassengerFare(passenger);
		this.passengers.put(passenger, (int)fare);

	}

	public double calculateTotalTicketPrice() {
		double total = 0;
		for(Passenger passenger : this.passengers.keySet()) {
			total = total + this.passengers.get(passenger);
		}
		return total;
	}

	public StringBuilder generateTicket() throws IOException {
		StringBuilder sb = new StringBuilder("");
		sb.append("PNR         : " + this.generatePNR() + "\n");
		sb.append("Train No    : " + this.train.getTrainNo() + "\n");
		sb.append("Train Name  : " + this.train.getTrainName() + "\n");
		sb.append("From        : " + this.train.getSource() + "\n");
		sb.append("To          : " + this.train.getDestination() + "\n");
		sb.append("Travel Date : " + this.travelDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n");
		sb.append("Passengers  :" + "\n");
		sb.append("Name\tAge\tGender\tFare\n");
		NumberFormat formatter = new DecimalFormat("#0,000.00");
		for(Passenger passenger : this.passengers.keySet()) {
			sb.append(passenger.getName() + "\t" + passenger.getAge() + "\t" + passenger.getGender() + "\t" + formatter.format(this.passengers.get(passenger)) + "\n");
		}
		sb.append("Total Price: " + formatter.format(this.calculateTotalTicketPrice())); 
		return sb;
	}

	public void writeTicket() throws IOException {
		String str = this.generateTicket().toString();
		String fileName = this.pnr + ".txt";
		FileOutputStream fos = new FileOutputStream(fileName);
		PrintWriter pr = new PrintWriter(fos);
		pr.write(str);
		pr.close();

	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public TreeMap<Passenger, Integer> getPassengers() {
		return passengers;
	}

	public void setPassengers(TreeMap<Passenger, Integer> passengers) {
		this.passengers = passengers;
	}
}