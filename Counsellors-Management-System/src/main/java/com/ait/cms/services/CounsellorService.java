package com.ait.cms.services;

import com.ait.cms.dto.CounsellorDto;

public interface CounsellorService {


    public CounsellorDto login(String email, String password);

    public boolean isEmailUnique(String email);

    public boolean register(CounsellorDto counsellorDto);

    public CounsellorDto getCounsellor(int counsellorId);


}
