# Rozsa Template Processor

Enhances configuration-based solutions by enabling the use of **dynamic configurations** instead of relying solely on
static data.

The Template Processor can function as a **template engine**, capable of parsing entire files and evaluating language
expressions where needed, or as a stand-alone expression's processor, when you need to provide dynamic values for
specific configuration fields.

Additionally, the Template Processor can also be extended with **custom functions** which will trigger the registered
java methods and with an entire new **vocabulary** to be used in the language expressions.

# Features

- Expressions
- Inner Expressions (aka Nested Expressions)
- Conditionals
- Operators
- Functions
- Transformations
- Context Variables
- Arrays and List elements Access
- Literals
- Context Enrichment
- Escape Characters
- Flexible Formatting

## Expressions

Expressions are the way we declare that a part of a template is dynamic and where the language tokens can be used.

Expressions are declared inside the `${}` guards. For instance, a dynamic properties .yml can be declared as:

```yml
data:
  "propertyA": "${IF UPPERCASE(foo) EQUALS bar THEN xpto}"
  "propertyB": "satic value"
  "propertyC": 123
```

Given a context containing the following values (foo=abc, bar=ABC, xpto=dummy_value), after processing the resulting
properties content will be:

```yml
data:
  "propertyA": "dummy_value"
  "propertyB": "satic value"
  "propertyC": 123
```

### Inner Expressions

Inner Expressions empowers the language by allowing nesting expressions.

You can use it to carry more complex transformations to provide a value to an outer expression. For instance:

- `${IF ${IF true_var THEN inner_then_val ELSE inner_else_val} == target_val THEN success_val ELSE failure_val}`

In the above example, the left side value of the outer IF condition is an inner expression.
- `${IF true_var THEN inner_then_val ELSE inner_else_val}`

It will be evaluated first to provide a value to be used in the outer if evaluation.

- `${IF inner_expression_result == target_val THEN success_val ELSE failure_val}`

## Conditionals

- IF - ELSE - THEN

## Operators

Operators are used to express conditionals.

- EQUALS, ==
- NOT EQUALS, !=
- GREATER_THAN, >
- GREATER_THAN_EQUALS, >=
- LESS_THAN, <
- LESS_THAN_EQUALS, <=

## Functions

Functions are transformations over the context variables.

- UPPERCASE
- LOWERCASE
- IS_PRESENT - if value is present, return true
- IS_ABSENT - if value is absent, return false

## Transformations

- SORT
  - SORT {set} [ASC|DESC]
  - SORT {set} AS {key} ON {key.prop} [ASC|DESC]


## Context Enrichment

It is possible to use the `SET` transformation to create custom context variables directly from the template
specification.

This is useful because it allows reducing the template pollution with the expression and duplicated entries.

Instead, one can define beforehand, at the start of the template, all necessary transformations or expressions and save
it to new variables which will be available in the context.

```
SET {some-expression} AS {new_var_name}

${SET UPPERCASE(userName) AS upperUserName}
```

Examples available at the `set_var_scenarios.txt` testing scenarios.

## Context Variables

Context variables allow the template to refer to dynamic context passed to the processor in runtime.

The context variables are the main source of dynamization in the template, and you can refer to them stand-alone or use
inside expressions.

> If a variable results in 'null' and it is the expression result, an empty string value will be used instead of null.

### Arrays and List elements Access

If you have a type which is a list or array type, you can access their elements using the brackets notation `[]`:

- Stand-alone variable: `$myList[0]`
- Inside expressions: `${IF myList[0] == true THEN "xpto"}`
- Lists inside lists: `$first[1].second[2].third[1].prop`

> Check out the valid usages in the array_access_scenarios.txt file.


### Stand-alone usage

```
$variable
```

### Inside expressions

```
${variable}
${IF condition_var THEN resulting_var ELSE another_var}
```

### Variable Navigation

It is also to use variables to navigate inside complex objects. In this case, chain the variable properties using a
dot `.`:

```
$variable.propA
${variable.propA}
$variable.propA.innerProp
${variable.propA.innerProp}
```

## Escape Characters

Some characters as `$`, `.`, `-` and `_` are used in the language. Sometimes it is necessary to use them in the template
but to do that it is necessary to use the escape notation `\`.

For instance `This library is $key.` ends with a `.`. So the processor will try to find a variable named `key.` in
the context and will fail. To make this work, you have to escape the dot `This is library $key\.`.

The same stands for expression starts `$`.

If it is necessary to use it for something else, just escape the character as
in `I own you \$US 10.00.`

Note that the processor removes the escape token `\`. So the previous template would be rendered as 
`I own you $US 10.00.`.

For convenience, escape the `.`, `-` and `_` characters only when conflicting with the template. Otherwise, you can use
these characters freely.

The '$' is the only character that has to be always escaped when not trying to write an expression. 

## Literals

The Literals feature allows using literal values inside expressions. The following is supported:

- **Text** - declare inside expressions between quotes.
  - eg. `${IF varX == "myLiteral" THEN 100 ELSE 200}`
  - Inside text literals, if you have to use quotes " or backslash \\, it is necessary to escape them as `\"` and `\\`.
- **Numbers** - declare inside expressions without quotes.
  - eg. `${IF varY == 123 THEN 4321}`
- **Booleans** - declare inside expressions without quotes using `true` or `false`.
  - eg. `IF varZ == true THEN true ELSE false`

## Flexible Formatting

Flexible Formatting is just a quality of life feature that allows you to use spaces, tabs, and break-lines to format
expressions as you will.

For instance, the expression inside this phrase `I have ${IF UPPERCASE(foo) EQUALS bar THEN xpto} apples.` which results
to `I have 5 apples.` can also be written in the following forms and keep yielding the same result:

```
I have ${ IF UPPERCASE(foo) EQUALS bar THEN xpto } apples.
```
```
I have ${ 	IF UPPERCASE(foo) EQUALS bar THEN xpto		} apples.
```
```
I have ${IF UPPERCASE(foo)
	EQUALS bar
	THEN xpto} apples.
```
```
I have ${
          IF
              UPPERCASE(foo)
          EQUALS
              bar
          THEN
              xpto
} apples.
```

# Examples

Check out the `resources` package from the testing module. There are all valid usage of the language inside templates
and the expected result after processing.

# TODO

- Handle conditions with combined expressions such as
  - IF x AND y AND z THEN ...
  - IF x OR y OR z THEN ...
  - IF ((x >= y) AND (x <= z)) OR k THEN ...
  - Use the () as grouping tool, otherwise the expression will evaluate from left to right (not the ANDs first then the ORs)
- Handle stand-alone expressions and provide examples
  - Allows processing a single expression inside the specified string without the need for expression tokens `$` or `${}`
- Add missing language tokens
  - SWITCH CASE THEN ELSE
  - FILTER AS ON
  - MAP AS ON SEPARATOR
  - COMBINE AS AND AS ON EQUALS
  - MAP REDUCE
  - EMPTY TEXT/MAP/LIST
  - RENDER AS ON SEPARATOR
  - LIST OF
  - NOT (for reversing conditions result)
  - CONCAT
  - SUBSTRING/ARRAY (todo)
- Reuse char checkers
- Add a testing feature out of the box
  - void assertProcess(String template, String expectedResult) throws UnexpectedProcessingResultException
  - new TemplateProcessor().assertProcess("template.json", "expected_result.json")
- Create the functional documentation
- Handle comment expressions
  - Expressions just to add comments in the template
  - The whole expression is erased during processing
  - eg. ${// this is a comment and won't appear in the final result}
- Select a license type
- Release