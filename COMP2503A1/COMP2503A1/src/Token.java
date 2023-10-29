/**
 * The Token class represents a word and its frequency in a text document.
 */
class Token {
    private String word; // The word itself
    private int frequency; // The frequency of the word in the document

    /**
     * Constructor to create a Token object with an initial frequency of 1.
     *
     * @param word The word to be stored as a token.
     */
    public Token(String word) {
        this.word = word;
        this.frequency = 1;
    }

    /**
     * Get the word associated with this token.
     *
     * @return The word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Get the frequency of the word in the document.
     *
     * @return The frequency.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Set the frequency of the word in the document.
     *
     * @param frequency The new frequency value to set.
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Override the equals method to compare two Token objects based on their words.
     *
     * @param obj The object to compare to this Token.
     * @return True if the objects are equal (have the same word), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token token = (Token) obj;
        return word.equals(token.word);
    }
}