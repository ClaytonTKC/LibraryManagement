package org.isaacwallace.librarymanagement.Member.Presentation;

import org.isaacwallace.librarymanagement.Member.Business.MemberService;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public ResponseEntity<List<MemberResponseModel>> getAllMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.memberService.getAllMembers());
    }

    @GetMapping("{memberid}")
    public ResponseEntity<MemberResponseModel> getMemberById(@PathVariable String memberid) {
        return ResponseEntity.status(HttpStatus.OK).body(this.memberService.getMemberById(memberid));
    }
}
