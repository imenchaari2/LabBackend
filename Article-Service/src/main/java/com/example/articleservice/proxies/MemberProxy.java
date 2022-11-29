package com.example.articleservice.proxies;

import com.example.articleservice.beans.MemberBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
//@FeignClient(name = "Member-SERVICE" , url = "${member-service.url}")
@FeignClient("MEMBER-SERVICE")
public interface MemberProxy {
        @GetMapping("/api/member/members")
        ResponseEntity<List<MemberBean>> getAllMembers();

        @GetMapping("/api/member/member/{id}")
        MemberBean getMemberById(@PathVariable Long id);


}
