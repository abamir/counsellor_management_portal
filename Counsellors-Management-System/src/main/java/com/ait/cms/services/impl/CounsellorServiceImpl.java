package com.ait.cms.services.impl;

import com.ait.cms.dto.CounsellorDto;
import com.ait.cms.entities.Counsellor;
import com.ait.cms.repositories.CounsellorRepo;
import com.ait.cms.services.CounsellorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounsellorServiceImpl implements CounsellorService {


    @Autowired
    CounsellorRepo counsellorRepo;

    @Override
    public CounsellorDto login(String email, String password) {


        Counsellor entity = counsellorRepo.findByEmailAndPassword(email, password);

        if (entity != null) {

            CounsellorDto dto = new CounsellorDto();

            BeanUtils.copyProperties(entity, dto);

            return dto;

        }
        return null;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Optional<Counsellor> ByEmail = counsellorRepo.findByEmail(email);

        return ByEmail.isPresent();
    }

        @Override
        public boolean register (CounsellorDto counsellorDto){

            Counsellor entity = new Counsellor();

            BeanUtils.copyProperties(counsellorDto, entity);

            Counsellor savedEntity = counsellorRepo.save(entity);

            return savedEntity.getCounsellorId() != null;
        }

    @Override
    public CounsellorDto getCounsellor(int counsellorId) {

        Optional<Counsellor> counsellor = counsellorRepo.findById(counsellorId);

        CounsellorDto dto = new CounsellorDto();

        BeanUtils.copyProperties(counsellor.get(), dto);

        return dto;
    }
}
