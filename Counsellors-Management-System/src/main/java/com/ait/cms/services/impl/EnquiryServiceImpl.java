package com.ait.cms.services.impl;

import com.ait.cms.dto.DashboardDto;
import com.ait.cms.dto.EnquiryDto;
import com.ait.cms.entities.Counsellor;
import com.ait.cms.entities.Enquiry;
import com.ait.cms.repositories.CounsellorRepo;
import com.ait.cms.repositories.EnquiryRepo;
import com.ait.cms.services.EnquiryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Autowired
    private CounsellorRepo counsellorRepo;

    @Override
    public DashboardDto getDashboardInfo(Integer counsellorId) {

        Counsellor counsellorEntity = new Counsellor();

        counsellorEntity.setCounsellorId(counsellorId);

        Enquiry enquiry = new Enquiry();

        enquiry.setCounsellor(counsellorEntity);

        List<Enquiry> enquirieslist = enquiryRepo.findAll(Example.of(enquiry));

        int totalEnquiries = enquirieslist.size();

        int open = enquirieslist.stream()
                .filter(e -> e.getEnqStatus().equals("Open"))
                .toList().size();

        int enrolled = enquirieslist.stream()
                .filter(e -> e.getEnqStatus().equals("Enrolled"))
                .toList()
                .size();

        int lost = enquirieslist.stream()
                .filter(e -> e.getEnqStatus().equals("Lost"))
                .toList()
                .size();


        /*DashboardDto dto = new DashboardDto();
        dto.setTotalEnquiries(totalEnquiries);
        dto.setOpenEnquiries(open);
        dto.setEnrolledEnquiries(enrolled);
        dto.setLostEnquiries(lost);*/

        return DashboardDto.builder()
                .totalEnquiries(totalEnquiries)
                .openEnquiries(open)
                .enrolledEnquiries(enrolled)
                .lostEnquiries(lost)
                .build();
    }

    @Override
    public boolean upsertEnquiry(EnquiryDto enquiryDto, Integer counsellorId) {

        Enquiry enquiryEntity = new Enquiry();
        BeanUtils.copyProperties(enquiryDto, enquiryEntity);

        Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow(); // if not found throw error

        enquiryEntity.setCounsellor(counsellor);

        Enquiry savedEntity = enquiryRepo.save(enquiryEntity);

        return savedEntity.getEnquiryId() != null;
    }

    @Override
    public List<EnquiryDto> getEnquiries(Integer counsellorId) {

        List<EnquiryDto> dtoList = new ArrayList<>();

        /*Counsellor centity = new Counsellor();
        centity.setCounsellorId(counsellorId);

        Enquiry enquiry = new Enquiry();
        enquiry.setCounsellor(centity);
*/
        List<Enquiry> enquiryList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

        enquiryList.forEach(e -> {

            EnquiryDto dto1 = new EnquiryDto();
            BeanUtils.copyProperties(e, dto1);

            dtoList.add(dto1);

        });


        return dtoList;
    }

    @Override
    public List<EnquiryDto> filterEnquiries(EnquiryDto filterDto, Integer counsellorId) {


        Enquiry enquiry = new Enquiry();

        if (filterDto.getClassMode() != null && !filterDto.getClassMode().isEmpty())
            enquiry.setClassMode(filterDto.getClassMode());

        if (filterDto.getCourseName() != null && !filterDto.getCourseName().isEmpty())
            enquiry.setCourseName(filterDto.getCourseName());

        if (filterDto.getEnqStatus() != null && !filterDto.getEnqStatus().isEmpty())
            enquiry.setEnqStatus(filterDto.getEnqStatus());


        Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();

        enquiry.setCounsellor(counsellor);

        List<Enquiry> enquiryList = enquiryRepo.findAll(Example.of(enquiry));

        List<EnquiryDto> dtoList = new ArrayList<>();

        enquiryList.forEach(e -> {

            EnquiryDto dto = new EnquiryDto();

            BeanUtils.copyProperties(e, dto);
            dtoList.add(dto);
        });


        return dtoList;
    }

    @Override
    public EnquiryDto getEnquiry(Integer enquiryId) {

        Enquiry enquiry = enquiryRepo.findById(enquiryId).orElseThrow();

        EnquiryDto enquiryDto = new EnquiryDto();
        BeanUtils.copyProperties(enquiry, enquiryDto);
        return enquiryDto;
    }
}
