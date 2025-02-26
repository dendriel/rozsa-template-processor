package com.vrozsa;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.vrozsa.TemplateProcessorIntegrationTest.assertScenario;
import static com.vrozsa.TemplateProcessorIntegrationTest.testPetOwner;

class PlaygroundTest {


    @Test
    void playgroundTest() {
        var context = ContextHolder.create()
                .add("dwarfs", new String[]{"Doc", "Grumpy", "Happy", "Sleepy", "Bashful", "Sneezy", "Dopey"})
                .add("pet-owner", testPetOwner())
                .add("owners", petOwners());

        assertScenario(context, "playground_scenario.txt", "playground_result.txt");
    }

    private List<TestPetOwner> petOwners() {
        return List.of(
                new TestPetOwner("John First", List.of(createPet(1), createPet(2), createPet(3))),
                new TestPetOwner("Janne Second", List.of(createPet(1), createPet(2), createPet(3))),
                new TestPetOwner("Tobias Third", List.of(createPet(1), createPet(2), createPet(3)))
        );
    }

    private TestPetOwner.TestPet createPet(int suffix) {
        return new TestPetOwner.TestPet("Pet #" + suffix, List.of("Food 1#" + suffix, "Food 2#" + suffix, "Food 3#" + suffix));
    }
}
