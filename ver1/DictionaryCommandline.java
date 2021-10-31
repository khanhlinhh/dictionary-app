package ver1;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement mgmt = new DictionaryManagement();
    Dictionary dictionary = mgmt.dictionary;

    /**
     * dictionary basic.
     */
    public void dictionaryBasic() {
        mgmt.insertFromCommandline();
        showAllWords();
    }

    /**
     * dictionary advance.
     */
    public void dictionaryAdvanced() throws IOException {
        mgmt.insertFromFile();
        showAllWords();
        mgmt.insertFromCommandline();
        showAllWords();
        mgmt.dictionaryExportToFile();
    }

    /**
     * show all word.
     */
    public void showAllWords() {
        System.out.println("No\t\tEnglish\t\t\tVietnamese");
        int index = 1;
        for (Map.Entry<String, String> e : dictionary.wordList.entrySet())
            System.out.println(index++ + "\t\t" + e.getKey() + "\t\t\t" + e.getValue());
    }

    /**
     * dictionarySearcher.
     */
    public void dictionarySearcher(){
        Scanner myvar = new Scanner(System.in);
        System.out.print("Từ bạn muốn tra cứu: ");
        String target = myvar.nextLine();
        int num = target.length();
        System.out.println("No\t\tEnglish\t\t\tVietnamese");
        int index = 1;
        for (Map.Entry<String, String> e : dictionary.wordList.entrySet()){
            if(e.getKey().substring(0,num).equals(target)) {
                System.out.println(index++ + "\t\t" + e.getKey() + "\t\t\t" + e.getValue());
            }
        }
    }
}
