package com.vrozsa;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        var useObjData = new TestUser("Jane Doe", 21, new TestUser.TestContact("55 20 445687966"));
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
                .add("user.type2", "ADMIN")
                .add("user", useObjData);

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

    @Test
    void testInnerExpressionScenarios() {
        var context = ContextHolder.create()
                .add("true_var", true)
                .add("inner_then_val", "inner then result")
                .add("inner_else_val", "inner else result")
                .add("target_val", "inner then result")
                .add("success_val", "SUCCESS")
                .add("failure_val", "FAILURE");

        assertScenario(context, "inner_expression_scenarios.txt", "inner_expression_result.txt");

    }

    @Test
    void testNavigationalVariables() {
        var userData = new HashMap<>();
        userData.put("contact_info", null);
        userData.put("name", "John Doe");
        userData.put("age", 25);
        userData.put("contact", Map.of("phone_number", "55 10 333442233"));

        var useObjData = new TestUser("Jane Doe", 21, new TestUser.TestContact("55 20 445687966"));

        var context = ContextHolder.create()
                .add("user", userData)
                .add("user_obj", useObjData);

        assertScenario(context, "navigational_variable_scenarios.properties", "navigational_variable_result.properties");
    }

    @Test
    void testSortScenarios() {
        var userList = List.of(
                new TestUser("John Doe", 25, null),
                new TestUser("Jane Doe", 21, null),
                new TestUser("Bill Doe", 7, null),
                new TestUser("Holliday Doe", 9, null),
                new TestUser("Earp Doe", 6, null)
        );

        var context = ContextHolder.create()
                .add("numberList", List.of(5, 6, 3, 2, 4, 1, 9, 7, 8))
                .add("numberArray", new int[]{5, 6, 3, 2, 4, 1, 9, 7, 8})
                .add("charArray", new int[]{'g', 'h', 'e', 'r', 't', 'c', 'v', 'b', 'c', 'a'})
                .add("userList", userList)
                .add("userArray", userList.toArray());

        assertScenario(context, "sort_scenarios.txt", "sort_result.txt");
    }

    @Test
    void testSetVariablesScenarios() {
        var context = ContextHolder.create()
                .add("numberList", List.of(5, 6, 3, 2, 4, 1, 9, 7, 8))
                .add("userName", "John Doe")
                .add("user", testUser01());

        assertScenario(context, "set_var_scenarios.txt", "set_var_result.txt");
    }

    @Test
    void testEscapeCharacterScenarios() {
        ContextHolder context = ContextHolder.create().add("value", "evaluation result");

        assertScenario(context, "escape_character_scenarios.txt", "escape_character_result.txt");
    }

    @Test
    void testLiteralsScenarios() {
        var context = ContextHolder.create()
                .add("value01", 1234)
                .add("varFoo", "foo")
                .add("trueVar", true);

        assertScenario(context, "literals_scenarios.properties", "literals_result.properties");
    }

    @Test
    void testArrayAccessScenarios() {
        var context = ContextHolder.create()
                .add("dwarfs", new String[]{"Doc", "Grumpy", "Happy", "Sleepy", "Bashful", "Sneezy", "Dopey"})
                .add("pet-owner", testPetOwner())
                .add("owners", petOwners());

        assertScenario(context, "array_access_scenarios.txt", "array_access_result.txt");
    }

    private TestUser testUser01() {
        return new TestUser("Jane Doe", 21, new TestUser.TestContact("55 20 445687966"));
    }

    public static TestPetOwner testPetOwner() {
        var pets = List.of(
                new TestPetOwner.TestPet("Dutch", List.of()),
                new TestPetOwner.TestPet("Fifi", List.of()),
                new TestPetOwner.TestPet("Toby", List.of())

        );

        return new TestPetOwner("John", pets);
    }

    private List<TestPetOwner> petOwners() {
        return List.of(
                new TestPetOwner("John First", List.of(createPet(1), createPet(2), createPet(3))),
                new TestPetOwner("Janne Second", List.of(createPet(4), createPet(5), createPet(6))),
                new TestPetOwner("Tobias Third", List.of(createPet(7), createPet(8), createPet(9)))
        );
    }

    private TestPetOwner.TestPet createPet(int suffix) {
        return new TestPetOwner.TestPet("Pet #" + suffix, List.of("Food 1 from pet #" + suffix, "Food 2 from pet #" + suffix, "Food 3 from pet #" + suffix));
    }
}
