package com.vrozsa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TestUser(String name, int age, TestContact contact) {
    public static record TestContact(@JsonProperty("phone_number") String number) {

    }
}
