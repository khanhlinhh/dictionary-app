package ver1;

import java.util.Hashtable;

public class Dictionary {
    Hashtable<String, String> wordList = new Hashtable<String, String>();

    /**
     * add word.
     */
    public void add(Word word) {
        wordList.put(word.getWord_target(), word.getWord_explain());
    }
}
