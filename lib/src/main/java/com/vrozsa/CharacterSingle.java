package com.vrozsa;

/**
 * Deals with a single character element.
 */
public class CharacterSingle extends CharacterRange {
    CharacterSingle(int startChar) {
        super(startChar, CharacterRange.EMPTY_CHAR);
    }
}
