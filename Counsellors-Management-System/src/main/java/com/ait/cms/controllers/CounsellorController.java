package com.ait.cms.controllers;

import com.ait.cms.dto.CounsellorDto;
import com.ait.cms.dto.DashboardDto;
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

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @Autowired
    private EnquiryService enquiryService;

    // http://localhost:8080/
    @GetMapping("/")
    public String index(Model model) {

        CounsellorDto counsellorDto = new CounsellorDto();
        model.addAttribute("counsellor", counsellorDto);


        return "index";
    }
    // To Handle Login Request

    @PostMapping("/login")
    public String handleLogin(CounsellorDto counsellorDto,
                              Model model,
                              HttpServletRequest request) {
        CounsellorDto dto = counsellorService.login(counsellorDto.getEmail(), counsellorDto.getPassword());

        if (dto == null) {

            model.addAttribute("ErrorMassage", "Invalid Email or Password! ");
            CounsellorDto dto1 = new CounsellorDto();
            model.addAttribute("counsellor", dto1);
            return "index";
        } else {

            //After Login Success storing ID in to session object.
            HttpSession session = request.getSession(true);
            session.setAttribute("COUNSELLOR_ID", dto.getCounsellorId());

            return "redirect:/dashboard";

        }

    }

    //Logout Request


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
    // To Build DashBoard

    @GetMapping("/dashboard")
    public String buildDashboard(Model model, HttpServletRequest request) {



        HttpSession session = request.getSession(false);
        Integer cid = (Integer) session.getAttribute("COUNSELLOR_ID");


        CounsellorDto counsellor = counsellorService.getCounsellor(cid);
        model.addAttribute("counsellor", counsellor);

        DashboardDto dashboardInfo = enquiryService.getDashboardInfo(cid);

        model.addAttribute("dashboardInfo", dashboardInfo);

        return "dashboardReport";

    }

    //To Show RegistrationPage.

    @GetMapping("/register")
    public String registerPage(Model model) {

        CounsellorDto counsellorDto = new CounsellorDto();

        /*To Map Form field Data to the dto object we are sending
         dto to view using model.addAttribute() method

        " it is call Form Binding "
         */
        model.addAttribute("counsellorDto", counsellorDto);

        return "registerView";

    }


    //TO Handle the Registration Request
    @PostMapping("/register")
    public String handleRegistration(@ModelAttribute("counsellorDto") CounsellorDto counsellorDto, Model model) {

        boolean status = counsellorService.isEmailUnique(counsellorDto.getEmail());

        if (!status) {

            boolean registered = counsellorService.register(counsellorDto);
            if (registered) {

                model.addAttribute("successMessage", "Registration Successfully");
            } else {

                model.addAttribute("errorMessage", "Registration Failed");
            }
        } else {
            model.addAttribute("errorMessage", "Email Already Exists");
        }

        return "registerView";
    }

}
