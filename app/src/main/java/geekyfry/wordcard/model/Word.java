package geekyfry.wordcard.model;

/**
 * Created by anangaur on 8/1/2015.
 */
public class Word {

    //0=ID,1=LETTER,2=WORD,3=POS1,4=POS2,5=DEFINITION,6=LEVEL,7=USAGE,8=Synonym,9=Antonym
    public Word(String word, String pos, String pos2, String meaning, String level, String usage, String synonym, String antonym) {
        this.word = word;
        this.meaning = meaning;
        this.pos = pos;
        this.usage = usage;
        this.level = 0;
        this.pos2 = pos2;
        this.synonym = synonym;
        this.antonym = antonym;

        this.startLetter = String.valueOf(word.charAt(0)).toLowerCase();
    }

    public Word(String[] words) {
        this(words[2], words[3], words[4], words[5], words[6], "","","");
    }

    public Word(String word) {
        this.word = word;
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
    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public String getPos2() {
        return pos2;
    }

    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Word) {
            return ((Word) obj).getWord().equals(this.getWord());

        } else {
            return false;
        }
    }

    private String word;
    private String meaning;
    private String pos;
    private String usage;
    private int level;
    String startLetter;
    private String synonym;
    private String antonym;
    private String pos2;

    public String getStartingAlphabet() {
        return word.substring(0, 1).toUpperCase();
    }
}
