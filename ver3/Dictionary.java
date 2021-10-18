package ver3;

import java.util.*;

public class Dictionary {
    Hashtable<String, String> words = new Hashtable<>();

    public void add(Word word) {
        words.put(word.getWord_target(), word.getWord_explain());
    }
}
