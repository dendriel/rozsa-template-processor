package com.vrozsa.tokens;

/**
 * Contains token related metadata
 * @param keyword the keyword used to match the token
 * @param startIdx start of the token (first char) in the content
 * @param endIdx end of the token (last char) in the content
 * @param content the content itself.
 */
public record TokenInput(String keyword, int startIdx, int endIdx, char[] content) {
}
