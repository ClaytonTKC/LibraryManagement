package org.isaacwallace.librarymanagement.Member.Business;

import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberRepository;
import org.isaacwallace.librarymanagement.Member.Mapper.MemberRequestMapper;
import org.isaacwallace.librarymanagement.Member.Mapper.MemberResponseMapper;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberRequestModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InUseException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberResponseMapper memberResponseMapper;
    private final MemberRequestMapper memberRequestMapper;

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

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
            throw new NotFoundException("Unknown memberid: " + memberid);
        }

        return this.memberResponseMapper.entityToResponseModel(member);
    }

    public MemberResponseModel addMember(MemberRequestModel memberRequestModel) {
        Member member = this.memberRequestMapper.requestModelToEntity(memberRequestModel, new MemberIdentifier());

        return this.memberResponseMapper.entityToResponseModel(this.memberRepository.save(member));
    }

    public MemberResponseModel updateMember(String memberid, MemberRequestModel memberRequestModel) {
        Member member = this.memberRepository.findMemberByMemberIdentifier_Memberid(memberid);

        if (member == null) {
            throw new NotFoundException("Unknown memberid: " + memberid);
        }

        this.memberRequestMapper.updateEntityFromRequest(memberRequestModel, member);

        Member updatedMember = this.memberRepository.save(member);

        logger.info("Updated member with memberid: " + memberid);

        return this.memberResponseMapper.entityToResponseModel(updatedMember);
    }

    public void deleteMember(String memberid) {
        Member member = this.memberRepository.findMemberByMemberIdentifier_Memberid(memberid);

        if (member == null) {
            throw new NotFoundException("Unknown memberid: " + memberid);
        }

        try {
            this.memberRepository.delete(member);
            logger.info("Member with id: " + memberid + " has been deleted");
        } catch (DataIntegrityViolationException exception) {
            logger.error("Failed to delete member with id: " + memberid, exception.getMessage());
            throw new InUseException("Member with id: " + memberid + " is already in use by another entity, currently cannot delete.");
        }
    }
}
