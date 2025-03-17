package org.isaacwallace.librarymanagement.Member.Business;

import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberRepository;
import org.isaacwallace.librarymanagement.Member.Mapper.MemberRequestMapper;
import org.isaacwallace.librarymanagement.Member.Mapper.MemberResponseMapper;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberResponseMapper memberResponseMapper;
    private final MemberRequestMapper memberRequestMapper;

    public MemberServiceImpl(MemberRepository memberRepository, MemberResponseMapper memberResponseMapper, MemberRequestMapper memberRequestMapper) {
        this.memberRepository = memberRepository;
        this.memberResponseMapper = memberResponseMapper;
        this.memberRequestMapper = memberRequestMapper;
    }

    public List<MemberResponseModel> getAllMembers() {
        return this.memberResponseMapper.entityToResponseModelList(memberRepository.findAll());
    }

    public MemberResponseModel getMemberById(String memberid) {
        Member member = this.memberRepository.findMemberByMemberIdentifier_Memberid(memberid);

        if (member == null) {
            throw new InvalidInputException("Unknow memberid: " + memberid);
        }

        return this.memberResponseMapper.entityToResponseModel(member);
    }
}
