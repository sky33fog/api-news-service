package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.Role;
import com.example.apinewsservice.web.model.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleResponse roleToResponse(Role role);
}
