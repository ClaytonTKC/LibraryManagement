package org.isaacwallace.librarymanagement.Member.Mapper;

import org.isaacwallace.librarymanagement.Member.DataAccess.Member;
import org.isaacwallace.librarymanagement.Member.DataAccess.MemberIdentifier;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberRequestMapper {
    @Mapping(target = "id", ignore = true)
    Member requestModelToEntity(MemberRequestModel memberRequestModel, MemberIdentifier memberIdentifier);
}
