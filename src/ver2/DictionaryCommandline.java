package ver2;
public class DictionaryCommandline {
    DictionaryManagement management = new DictionaryManagement();

    public void showAllWords() {
        management.show();
    }

    public void dictionaryBasic() {
        management.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced(){
        management.insertFromFile();
        showAllWords();
        management.dictionaryLookup();
    }
}
