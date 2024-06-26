package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}