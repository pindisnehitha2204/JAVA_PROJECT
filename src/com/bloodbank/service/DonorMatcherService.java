package com.bloodbank.service;

import com.bloodbank.model.Donor;
import com.bloodbank.dao.DonorDAO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business Service handling Donor eligibility validation and compatibility search algorithms.
 */
public class DonorMatcherService {
    private DonorDAO donorDAO;

    public DonorMatcherService(DonorDAO donorDAO) {
        this.donorDAO = donorDAO;
    }

    /**
     * Evaluates donor eligibility (minimum 56-day gap required between whole blood donations)
     */
    public boolean isEligibleToDonate(Donor donor) {
        if (donor.getLastDonationDate() == null) {
            return true; // First time donor
        }
        long daysSinceLastDonation = ChronoUnit.DAYS.between(donor.getLastDonationDate(), LocalDate.now());
        return daysSinceLastDonation >= 56;
    }

    /**
     * Returns list of compatible blood groups for a given recipient blood type
     */
    public List<String> getCompatibleDonorTypes(String recipientBloodGroup) {
        List<String> compatibleTypes = new ArrayList<>();
        switch (recipientBloodGroup.toUpperCase()) {
            case "A+":
                compatibleTypes.add("A+"); compatibleTypes.add("A-");
                compatibleTypes.add("O+"); compatibleTypes.add("O-");
                break;
            case "A-":
                compatibleTypes.add("A-"); compatibleTypes.add("O-");
                break;
            case "B+":
                compatibleTypes.add("B+"); compatibleTypes.add("B-");
                compatibleTypes.add("O+"); compatibleTypes.add("O-");
                break;
            case "B-":
                compatibleTypes.add("B-"); compatibleTypes.add("O-");
                break;
            case "AB+":
                compatibleTypes.add("A+"); compatibleTypes.add("A-");
                compatibleTypes.add("B+"); compatibleTypes.add("B-");
                compatibleTypes.add("AB+"); compatibleTypes.add("AB-");
                compatibleTypes.add("O+"); compatibleTypes.add("O-");
                break;
            case "AB-":
                compatibleTypes.add("AB-"); compatibleTypes.add("A-");
                compatibleTypes.add("B-"); compatibleTypes.add("O-");
                break;
            case "O+":
                compatibleTypes.add("O+"); compatibleTypes.add("O-");
                break;
            case "O-":
                compatibleTypes.add("O-");
                break;
            default:
                break;
        }
        return compatibleTypes;
    }

    /**
     * Finds active, eligible, and compatible donors matching recipient location
     */
    public List<Donor> findMatchingDonors(String recipientBloodGroup, String city) {
        List<String> validGroups = getCompatibleDonorTypes(recipientBloodGroup);
        List<Donor> candidateDonors = donorDAO.getDonorsByCityAndGroups(city, validGroups);

        return candidateDonors.stream()
                .filter(this::isEligibleToDonate)
                .filter(Donor::isAvailable)
                .collect(Collectors.toList());
    }
}