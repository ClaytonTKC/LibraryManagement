package org.isaacwallace.librarymanagement.Member.Mapper;

import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.Presentation.MemberController;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface MemberResponseMapper {
    @Mapping(expression = "java(member.getMemberIdentifier().getMemberid())", target = "memberid")
    MemberResponseModel entityToResponseModel(Member member);
    List<MemberResponseModel> entityToResponseModelList(List<Member> members);

    @AfterMapping
    default void addLinks(@MappingTarget MemberResponseModel memberResponseModel, Member member) {
        Link selfLink = linkTo(methodOn(MemberController.class).getMemberById(member.getMemberIdentifier().getMemberid())).withSelfRel();
        memberResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(MemberController.class).getAllMembers()).withRel("members");
        memberResponseModel.add(allLink);
    }
}
