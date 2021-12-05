package cf.tinkerit.rosetta.generator.impl;

import java.io.Serializable;

public class Author implements Serializable {
    private String name;
    private String affiliation;
    private String email;

    public Author(String name, String affiliation, String email) {
        this.name = name;
        this.affiliation = affiliation;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
