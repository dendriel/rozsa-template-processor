package com.vrozsa;


import java.util.Arrays;
import java.util.List;

class CharacterChecker {
    private final List<CharacterRange> charRanges;

    public CharacterChecker(List<CharacterRange> charRanges) {
        this.charRanges = charRanges;
    }

    public static CharacterChecker of(CharacterRange...charRanges) {
        return new CharacterChecker(Arrays.asList(charRanges));
    }

    boolean match(int targetChar) {
        for (var range : charRanges) {
            if (range.isValid(targetChar)) {
                return true;
            }
        }
        return false;
    }
}
