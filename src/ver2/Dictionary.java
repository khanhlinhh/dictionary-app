package ver2;

import java.util.*;

public class Dictionary {
    Hashtable<String, String> words = new Hashtable<String, String>();

    public void add(Word word) {
        words.put(word.getWord_target(), word.getWord_explain());
    }
}
