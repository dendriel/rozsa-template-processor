package com.vrozsa;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateProcessorIntegrationTest {

    static void assertScenario(ContextHolder context, String scenarioFile, String resultFile) {
        var scenarioContent = FileReader.readFile(scenarioFile);
        var expectedResult = FileReader.readFile(resultFile);

        var actualResult = new TemplateProcessor().process(scenarioContent, context);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testContextVariableScenarios() {
        var context = ContextHolder.create()
                .add("valid_user", false)
                .add("user.type", "GUEST")
                .add("appName", "user-auth-app")
                .add("user.type2", "ADMIN");

        assertScenario(context, "context_variable_scenarios.txt", "context_variable_result.txt");
    }

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

        assertScenario(context, "if_then_else_scenarios.yml", "if_then_else_scenarios_result.yml");
    }

    @Test
    void testConditionOperatorsScenarios() {
        var context = ContextHolder.create()
                .add("int01", 100)
                .add("int02", 200)
                .add("int_true_result", Integer.MAX_VALUE)
                .add("int_false_result", Integer.MIN_VALUE)

                .add("byte01", (byte) 10)
                .add("byte02", (byte) 20)
                .add("byte_true_result", Byte.MAX_VALUE)
                .add("byte_false_result", Byte.MIN_VALUE)

                .add("short01", (short) 1000)
                .add("short02", (short) 2000)
                .add("short_true_result", Short.MAX_VALUE)
                .add("short_false_result", Short.MIN_VALUE)

                .add("long01", 10000L)
                .add("long02", 20000L)
                .add("long_true_result", Long.MAX_VALUE)
                .add("long_false_result", Long.MIN_VALUE)

                .add("float01", 10.5f)
                .add("float02", 20.5f)
                .add("float_true_result", Float.MAX_VALUE)
                .add("float_false_result", Float.MIN_VALUE)

                .add("double01", 100.5)
                .add("double02", 200.5)
                .add("double_true_result", Double.MAX_VALUE)
                .add("double_false_result", Double.MIN_VALUE)

                .add("bigInteger01", new BigInteger("100000"))
                .add("bigInteger02", new BigInteger("200000"))
                .add("bigInteger_true_result", BigInteger.TEN.pow(100))
                .add("bigInteger_false_result", BigInteger.ZERO)

                .add("bigDecimal01", new BigDecimal("10.123"))
                .add("bigDecimal02", new BigDecimal("20.456"))
                .add("bigDecimal_true_result", new BigDecimal("9999999999.9999"))
                .add("bigDecimal_false_result", BigDecimal.ZERO);

        assertScenario(context, "condition_operators_scenarios.json", "condition_operators_result.json");
    }

    @Test
    void testFunctionScenarios() {
        var context = ContextHolder.create()
                .add("foo", "abc")
                .add("bar", "ABC")
                .add("result", "result from the comparison")
                .add("else_result", "result when comparison yields false")
                .add("then_result", "result when comparison yields true")
                .add("absent_variable_val", null);

        assertScenario(context, "function_scenarios.properties", "function_result.properties");
    }

    @Test
    void testEmptyCharHandlingScenarios() {
        var context = ContextHolder.create()
                .add("foo", "abc")
                .add("bar", "ABC")
                .add("result", "variable result");

        assertScenario(context, "empty_char_handling_scenarios.properties", "empty_char_handling_result.properties");
    }
}
