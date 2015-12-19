package geekyfry.wordcard;

import geekyfry.wordcard.model.Word;

/**
 * Created by anangaur on 8/21/2015.
 */
public class DisplayWord {
    private Word word;

    public DisplayWord(Word word, boolean isAlphabet) {
        this.word = word;
        this.isAlphabet = isAlphabet;
    }

    public DisplayWord(String alphabet, boolean isAlphabet) {
        this.word = new Word(alphabet);
        this.isAlphabet = isAlphabet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof DisplayWord) {
            return this.getWord().equals(((DisplayWord) obj).getWord()) && this.isAlphabet() == ((DisplayWord) obj).isAlphabet();
        } else {
            return false;
        }
    }

    public boolean isAlphabet() {
        return isAlphabet;
    }

    public void setIsAlphabet(boolean isAlphabet) {
        this.isAlphabet = isAlphabet;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    private boolean isAlphabet;
}
