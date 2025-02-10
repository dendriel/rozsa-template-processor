package com.vrozsa;

/**
 * Deals with a single character element.
 */
public class CharacterSingle extends CharacterRange {
    public CharacterSingle(int targetChar) {
        super(targetChar, CharacterRange.EMPTY_CHAR);
    }
}
