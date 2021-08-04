package com.case1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TicketApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Scanner sc = new Scanner(System.in);
		boolean val = true;	
		try {
			while(val) {
				System.out.println("Select Option:");
				System.out.println("1: Book the ticket: \n2: Train Enquiry: \n3: Total tickets booked:");
				System.out.println("4: Print all the tickets: \n5: No of ticket booked on date: \n6: Exit:");
				int k = sc.nextInt();
				switch(k) {
				case 1:
					System.out.println("Enter the Train Number:");
					int trainNo = sc.nextInt();
					TrainDAO trainDAO = new TrainDAO();
					Train train = trainDAO.findTrain(trainNo);
					if(train == null) {
						val = false;
						break;
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
					break;
				case 2:
					System.out.println("Enter the Train Number:");
					int trainNo1 = sc.nextInt();
					TrainDAO trainDAO1 = new TrainDAO();
					Train train1 = trainDAO1.findTrain(trainNo1);
					if(train1 == null) {
						//sc.close();
						//System.exit(0);
						System.out.println("Train with given Train Number does not exist");
					}
					else {
						NumberFormat formatter = new DecimalFormat("#0,000.00");
						System.out.println("Train No: "+ train1.getTrainNo() + "\nTrain Name: " + train1.getTrainName() + "\nSource: " + train1.getSource() + "\nDestination: " + train1.getDestination() + "\nTicket Price: " + formatter.format(train1.getTicketPrice()));
						System.out.println();
					}
					break;
				case 3:
					File directoryPath = new File("D:\\WorkSpace\\Demo1");
					FilenameFilter textFilefilter = new FilenameFilter(){
						public boolean accept(File dir, String name) {
							String lowercaseName = name.toLowerCase();
							if (lowercaseName.endsWith(".txt")) {
								return true;
							} else {
								return false;
							}
						}
					};
					String filesList[] = directoryPath.list(textFilefilter);
					System.out.println("Total Number of Tickets Generated:");
					System.out.println(filesList.length);
					System.out.println();
					break;
				case 4:
					File directoryPath1 = new File("D:\\WorkSpace\\Demo1");
					FilenameFilter textFilefilter1 = new FilenameFilter(){
						public boolean accept(File dir, String name) {
							String lowercaseName = name.toLowerCase();
							if (lowercaseName.endsWith(".txt")) {
								return true;
							} else {
								return false;
							}
						}
					};
					String filesList1[] = directoryPath1.list(textFilefilter1);
					System.out.println("List of all Tickets: ");
					for(String fileName : filesList1) {
						FileInputStream fis = new FileInputStream(fileName);
						int data = 0;
						while( (data = fis.read())!= -1) {						
							System.out.print((char)data);
						}
						fis.close();
						System.out.println();
						System.out.println("-----------------------------------------------");
					}
					break;
				case 5:
					System.out.println("Enter Travel Date:");
					sc.nextLine();
					String date1 = sc.nextLine();
					LocalDate travelDate1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					String date = travelDate1.format(DateTimeFormatter.ofPattern("yyyyMMdd")) ;
					File directoryPath2 = new File("D:\\WorkSpace\\Demo1");
					FilenameFilter textFilefilter2 = new FilenameFilter(){
						public boolean accept(File dir, String name) {
							String lowercaseName = name.toLowerCase();
							if (lowercaseName.endsWith(".txt")) {
								String[] splitFile = lowercaseName.split("_");
								if((splitFile[1].compareTo(date)) == 0 ) {
									return true;
								}
								else
									return false;
							} else {
								return false;
							}
						}
					};
					String filesList2[] = directoryPath2.list(textFilefilter2);
					System.out.println("Count of Tickets Registered on " + date1 + " :");
					System.out.println(filesList2.length);
					if(filesList2.length > 0) {
						System.out.println("List of All Tickets Registered on " + date1 + " :");
						for(String fileName : filesList2) {
							System.out.println(fileName);
						}
					}
					System.out.println();
					break;	
				case 6:
					System.out.println("Program End.");
					val = false;
					break;	
				default:
					break;	
				}	
			}
		}catch(Exception e){
			System.out.println("Program End...");
		}finally {
			sc.close();
		}
	}
}