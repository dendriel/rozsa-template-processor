package com.vrozsa;

import org.junit.jupiter.api.Test;

import static com.vrozsa.TemplateProcessorIntegrationTest.assertScenario;

class PlaygroundTest {


    @Test
    void playgroundTest() {
        var context = ContextHolder.create()
                .add("numbers", new String[]{"5", "3", "4", "9", "2", "1", "11"});

        assertScenario(context, "playground_scenario.txt", "playground_result.txt");
    }
}
