package geekyfry.wordcard.model;

/**
 * Created by anangaur on 8/1/2015.
 */
public class Word {

    public Word(String word, String meaning, String pos, String usage, int level) {
        this.word = word;
        this.meaning = meaning;
        this.pos = pos;
        this.usage = usage;
        this.level = level;

        this.startLetter = String.valueOf(word.charAt(0)).toLowerCase();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getStartLetter() {
        return startLetter;
    }

    public void setStartLetter(String startLetter) {
        this.startLetter = startLetter;
    }

    String word;
    String meaning;
    String pos;
    String usage;
    int level;
    String startLetter;
}
