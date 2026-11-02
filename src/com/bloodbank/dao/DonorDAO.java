package com.bloodbank.dao;

import com.bloodbank.model.Donor;
import java.util.List;

public interface DonorDAO {
    void addDonor(Donor donor);
    List<Donor> getDonorsByCityAndGroups(String city, List<String> bloodGroups);
    List<Donor> getAllDonors();
}
