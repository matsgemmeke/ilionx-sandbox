package com.github.matsgemmeke.sandbox.model;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Set<CompetitionEntry> entries;
    private String email;
    private String name;

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
        this.entries = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public Set<CompetitionEntry> getEntries() {
        return entries;
    }

    public String getName() {
        return name;
    }
}
