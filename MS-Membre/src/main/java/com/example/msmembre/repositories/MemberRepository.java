package com.example.msmembre.repositories;

import com.example.msmembre.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByCin(String cin);
    List<Member>findByFirstNameStartingWith(String caractere);
    Member findByEmail(String email);
}
