package com.ait.cms.repositories;

import com.ait.cms.dto.CounsellorDto;
import com.ait.cms.entities.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {




   //SELECT * FROM counsellor WHERE email=? AND password=?
   public Counsellor findByEmailAndPassword(String email, String password);

   //SELECT * FROM counsellor WHERE email=?
   public Optional<Counsellor> findByEmail(String email);
}
