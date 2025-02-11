package com.vrozsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateProcessorIntegrationTest {

    @Test
    void testIfThenElseTokenScenarios() {

        var context = ContextHolder.create()
                .add("valid_user", false)
                .add("user.type", "GUEST")
                .add("invalid_user", true)
                .add("xpto_value", 123)
                .add("foo", 123)
                .add("bar", "xxx")
                .add("equality_result", "values are equal")
                .add("inequality_result", "this value wont show")
                .add("fallback_result", "fallback result from else")
                .add("result_then", "result when then value is used")
                .add("user.type2", "ADMIN");

        var scenarioContent = FileReader.readFile("if_then_else_scenarios.yml");
        var expectedResult = FileReader.readFile("if_then_else_scenarios_result.yml");

        var actualResult = new TemplateProcessor().process(scenarioContent, context);

        assertEquals(expectedResult, actualResult);
    }
}
