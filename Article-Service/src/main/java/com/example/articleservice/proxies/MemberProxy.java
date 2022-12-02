package com.example.articleservice.proxies;

import com.example.articleservice.beans.MemberBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "MEMBER-SERVICE")
public interface MemberProxy {
        @GetMapping("/api/member/members")
        @PreAuthorize("hasRole('ADMIN')")
        List<MemberBean> getAllMembers();
        @GetMapping("/api/member/member/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        MemberBean getMemberById(@PathVariable Long id);
}
