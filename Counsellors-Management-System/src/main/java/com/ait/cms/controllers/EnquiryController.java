package com.ait.cms.controllers;

import com.ait.cms.dto.CounsellorDto;
import com.ait.cms.dto.EnquiryDto;
import com.ait.cms.services.CounsellorService;
import com.ait.cms.services.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/Enquiry")
    public String addEnquiryPage(Model model, HttpServletRequest request) {

        EnquiryDto enquiryDto = new EnquiryDto();
        model.addAttribute("enquiryDto", enquiryDto);

        return "/addEnquiry";
    }

    @PostMapping("/addEnquiry")
    public String addEnquiry(@ModelAttribute("enquiryDto") EnquiryDto enquiryDto,
                             Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Integer cId = (Integer) session.getAttribute("COUNSELLOR_ID");

        CounsellorDto counsellor = counsellorService.getCounsellor(cId);

        //session.setAttribute("COUNSELLOR_NAME", counsellor.getName());
        model.addAttribute("counsellor_Name", counsellor.getName());

        boolean insertedEnquiry = enquiryService.upsertEnquiry(enquiryDto, cId);

        if (insertedEnquiry) {


            model.addAttribute("successMessage", "Enquiry Added Successfully");
        } else {

            model.addAttribute("errorMessage", "Enquiry Adding Failed");
        }
        return "/addEnquiry";
    }

    @GetMapping("/viewEnquiry")
    public String getEnquiry(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");

        List<EnquiryDto> enquiryList = enquiryService.getEnquiries(cid);

        model.addAttribute("enquiryList", enquiryList);

        EnquiryDto searchEnquiryDto = new EnquiryDto();
        model.addAttribute("filterDto", searchEnquiryDto);

        return "/inquiry-view";
    }

    @PostMapping("/filterEnquiry")
    public String filterEnquiries(
            @ModelAttribute("filterDto") EnquiryDto enquiryDto,
            HttpServletRequest request,
            Model model) {

        HttpSession session = request.getSession(false);
        Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");

        List<EnquiryDto> enquiryList = enquiryService.filterEnquiries(enquiryDto, cid);

        model.addAttribute("enquiryList", enquiryList);


        return "/inquiry-view";

    }


    @GetMapping("/editEnquiry")
    public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model) {

        EnquiryDto enquiryDto = enquiryService.getEnquiry(enqId);
        model.addAttribute("enquiryDto", enquiryDto);

        return "/addEnquiry";


    }


}
