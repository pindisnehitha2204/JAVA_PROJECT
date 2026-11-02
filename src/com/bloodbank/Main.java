package com.bloodbank;

import com.bloodbank.dao.DonorDAO;
import com.bloodbank.dao.MockDonorDAOImpl;
import com.bloodbank.model.Donor;
import com.bloodbank.service.DonorMatcherService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DonorDAO donorDAO = new MockDonorDAOImpl();
        DonorMatcherService matcherService = new DonorMatcherService(donorDAO);
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("   WELCOME TO BLOOD DONOR MANAGEMENT SYSTEM      ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register New Donor");
            System.out.println("2. Search Compatible Donors");
            System.out.println("3. Display All Registered Donors");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");

            int choice = 0;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
            } else {
                scanner.nextLine();
                System.out.println("Invalid input! Please enter a number (1-4).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- NEW DONOR REGISTRATION ---");
                    System.out.print("Enter Donor Name: ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter Blood Group (e.g., A+, B+, O-, AB+): ");
                    String bloodGroup = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Enter City: ");
                    String city = scanner.nextLine().trim();

                    System.out.print("Is Donor Available to Donate Now? (true/false): ");
                    boolean available = true;
                    if (scanner.hasNextBoolean()) {
                        available = scanner.nextBoolean();
                        scanner.nextLine();
                    } else {
                        scanner.nextLine();
                    }

                    // Create and store donor
                    Donor newDonor = new Donor(name, bloodGroup, city, available);
                    donorDAO.addDonor(newDonor);
                    System.out.println("\n[SUCCESS] Donor registered successfully!");
                    break;

                case 2:
                    System.out.println("\n--- SEARCH COMPATIBLE DONORS ---");
                    System.out.print("Enter Recipient Blood Group: ");
                    String recipientGroup = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Enter City: ");
                    String searchCity = scanner.nextLine().trim();

                    System.out.println("\nSearching compatible donors for (" 
                                       + recipientGroup + ") in " + searchCity + "...");

                    List<Donor> matchedDonors = matcherService.findMatchingDonors(recipientGroup, searchCity);

                    System.out.println("--------------------------------------------------");
                    if (matchedDonors.isEmpty()) {
                        System.out.println("No matching compatible donors found.");
                    } else {
                        System.out.println("Matched Donors (" + matchedDonors.size() + "):");
                        for (Donor donor : matchedDonors) {
                            System.out.println("- Name: " + donor.getName() 
                                               + " | Type: " + donor.getBloodGroup() 
                                               + " | City: " + donor.getCity());
                        }
                    }
                    System.out.println("--------------------------------------------------");
                    break;

                case 3:
                    System.out.println("\n--- ALL REGISTERED DONORS ---");
                    List<Donor> allDonors = donorDAO.getAllDonors();
                    if (allDonors.isEmpty()) {
                        System.out.println("No donors registered yet.");
                    } else {
                        for (Donor donor : allDonors) {
                            System.out.println("- Name: " + donor.getName() 
                                               + " | Blood Group: " + donor.getBloodGroup() 
                                               + " | City: " + donor.getCity() 
                                               + " | Available: " + donor.isAvailable());
                        }
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("\nExiting system. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice! Select a number between 1 and 4.");
            }
        }

        scanner.close();
    }
}