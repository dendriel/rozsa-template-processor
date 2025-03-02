package com.vrozsa;

// We could use object values and reuse checkers with the same data, but too much effort to little benefit in this case.
public class CharacterRange {
    protected static final int EMPTY_CHAR = -1;
    protected final int startChar;

    private final int endChar;

    public CharacterRange(int startChar, int endChar) {
        this.startChar = startChar;
        this.endChar = endChar;
    }

    /**
     * Checks if the targetChar is valid for the configured range in this checker.
     * @param targetChar character to check.
     * @return true if the char is valid; false otherwise.
     */
    boolean isValid(int targetChar) {
        // range checking
        if (endChar != EMPTY_CHAR &&
                targetChar >= startChar && targetChar <= endChar) {
            return true;
        }

        // exact checking
        return targetChar == startChar;
    }


    @Override
    public String toString() {
        return "CharacterRange='" + (char)startChar + "', " +
                + (char)endChar + "'";
    }
}
