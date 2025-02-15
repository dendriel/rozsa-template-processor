package com.vrozsa.tokens;

import com.vrozsa.ContextHolder;

public interface Token {

    /**
     * Read the content as expected by this token.
     */
    void read();

    /**
     * Get the last valid index for the whole expression related to this token.
     * <p>
     *     For instance, if this token represents an IF-ELSE-THEN condition, the endIdx will be the index of the last
     *     character of the THEN token companion value.
     * </p>
     * @return the ending index of this token.
     */
    int endIdx();

    /**
     * Read the input of this token.
     * @return the token input.
     */
    TokenInput input();

    /**
     * Evaluate the read token expression.
     * @param context the context to be used in the evaluation.
     * @return the evaluation result.
     */
    Object evaluate(ContextHolder context);

    /**
     * The keyword used to reference this token.
     * <p>
     *     If it is a variable, will return the name of the variable.
     * </p>
     * @return the token instance keyword.
     */
    String keyword();
}
