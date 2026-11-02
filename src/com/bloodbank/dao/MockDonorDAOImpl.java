package com.bloodbank.dao;

import com.bloodbank.model.Donor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockDonorDAOImpl implements DonorDAO {
    private List<Donor> donorList = new ArrayList<>();

    public MockDonorDAOImpl() {
        // Pre-populating sample donors
        donorList.add(new Donor("Alice", "O-", "Mumbai", true));
        donorList.add(new Donor("Bob", "A+", "Mumbai", true));
        donorList.add(new Donor("Charlie", "B+", "Guntur", true));
    }

    @Override
    public void addDonor(Donor donor) {
        donorList.add(donor);
    }

    @Override
    public List<Donor> getDonorsByCityAndGroups(String city, List<String> bloodGroups) {
        return donorList.stream()
                .filter(d -> d.getCity().equalsIgnoreCase(city))
                .filter(d -> bloodGroups.contains(d.getBloodGroup().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorList;
    }
}
