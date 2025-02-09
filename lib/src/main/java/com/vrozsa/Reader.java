package com.vrozsa;

import com.vrozsa.exceptions.IncompleteTokenException;

public class Reader {
    private static final CharacterChecker separatorCharsChecker = CharacterChecker.of(
            new CharacterSingle(32), // space
            new CharacterSingle(9) // tab
    );

    /**
     * Read until the next valid character.
     * @param startIdx starting index in the content.
     * @param content content to read.
     * @param skipEscaped skip escaped characters. Useful when reading the whole documment. Inside expressions we don't
     *                    expect escaped characters, so we don't skip to detect them.
     * @return the next valid character index.
     */
    public static int nextValidCharIndex(final int startIdx, final char[] content) {
        var idx = startIdx;

        while(idx < content.length) {

            var nextChar = content[idx];

            // skip blank characters
            if (separatorCharsChecker.match(nextChar)) {
                idx++;
                continue;
            }

            break;
        }

        // TODO: return wildcard value if we reach the end of the content
        return idx;
    }

    public static void assertValidIndex(final int idx, final char[] content) {
        if (!validIndex(idx, content)) {
            throw new IncompleteTokenException(idx);
        }
    }

    public static boolean validIndex(final int idx, final char[] content) {
        return idx < content.length;
    }
}
