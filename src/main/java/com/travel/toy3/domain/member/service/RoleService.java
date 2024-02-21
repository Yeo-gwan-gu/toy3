package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}