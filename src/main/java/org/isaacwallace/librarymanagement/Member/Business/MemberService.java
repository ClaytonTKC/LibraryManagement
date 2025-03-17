package org.isaacwallace.librarymanagement.Member.Business;

import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberRequestModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;

import java.util.List;

public interface MemberService {
    public List<MemberResponseModel> getAllMembers();
    public  MemberResponseModel getMemberById(String memberid);
    public MemberResponseModel addMember(MemberRequestModel memberRequestModel);
    public MemberResponseModel updateMember(String memberid, MemberRequestModel memberRequestModel);
    public void deleteMember(String memberid);
}
