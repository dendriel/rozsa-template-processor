package com.vrozsa;

import java.util.List;

public record TestPetOwner(String name, List<TestPet> pets) {



    public record TestPet(String name, List<String> foods) {

    }
}
