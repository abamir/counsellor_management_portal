package com.ait.cms.services;

import com.ait.cms.dto.DashboardDto;
import com.ait.cms.dto.EnquiryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EnquiryService {

    public DashboardDto getDashboardInfo(Integer counsellorId);

    public boolean upsertEnquiry(EnquiryDto enquiryDto , Integer counsellorId);

    public List<EnquiryDto> getEnquiries(Integer counsellorId);

    public  List<EnquiryDto> filterEnquiries(EnquiryDto filterDto, Integer counsellorId);

    //to Edit the Enquiries
    public EnquiryDto getEnquiry(Integer enquiryId);


}
