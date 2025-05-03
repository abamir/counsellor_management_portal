package com.ait.cms.repositories;

import com.ait.cms.entities.Counsellor;
import com.ait.cms.entities.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnquiryRepo extends JpaRepository<Enquiry,Integer > {

    //SELECT * FROM enquiry_table WHERE counsellorId=?
    public  List<Enquiry> findByCounsellorCounsellorId(Integer counsellorId);



}
