package ver1;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();

    /**
     * insert new words from commandline.
     */
    public void insertFromCommandline() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Số từ bạn muốn nhập: ");
        int num = myvar.nextInt();

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            Word newWord = new Word();
            System.out.print("Từ mới: ");
            newWord.setWord_target(sc.nextLine());
            System.out.print("Nghĩa của từ: ");
            newWord.setWord_explain(sc.nextLine());
            dictionary.add(newWord);
        }
    }

    /**
     * insert new words from file.
     */
    public void insertFromFile() {
        File file = new File("./src/ver1/data.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String[] data = sc.nextLine().split("\t");
                Word newWord = new Word();
                newWord.setWord_target(data[0]);
                newWord.setWord_explain(data[1]);
                dictionary.add(newWord);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
    }

    /**
     * look up word.
     */
    public void dictionaryLookup() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Từ bạn muốn tra cứu: ");
        String word = myvar.nextLine();
        while (!dictionary.wordList.containsKey(word)) {
            System.out.print("Không có từ đó trong từ điển! Hãy tìm từ khác: ");
            word = myvar.nextLine();
        }
        System.out.println("Nghĩa của từ đó: " + dictionary.wordList.get(word));
    }

    /**
     * Xóa từ
     */
    public void deleteWord() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Từ bạn muốn xóa: ");
        String target = myvar.nextLine();
        while (!dictionary.wordList.containsKey(target)){
            System.out.print("Không có từ đó trong từ điển! Hãy xóa từ khác: ");
            target = myvar.nextLine();
        }
        dictionary.wordList.remove(target);
        System.out.println("Đã xóa '" + target + "'!");
    }

    /**
     * Sửa từ
     */
    public void updateWord(){
        Scanner myvar = new Scanner(System.in);
        System.out.print("Từ bạn muốn sửa: ");
        String target = myvar.nextLine();
        while (!dictionary.wordList.containsKey(target)){
            System.out.print("Không có từ đó trong từ điển! Hãy sửa từ khác: ");
            target = myvar.nextLine();
        }
        System.out.print("Nghĩa mới: ");
        String newMeaning = myvar.nextLine();
        dictionary.wordList.replace(target, newMeaning);
        System.out.println("Đã sửa '" + target + "'!");
    }

    /**
     * Xuất dữ liệu.
     */
    public void dictionaryExportToFile() throws IOException {
        File file = new File("./src/ver1/data.txt");
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        for (Map.Entry<String, String> e : dictionary.wordList.entrySet()){
            for (String s : Arrays.asList(e.getKey() + "\t" + e.getValue(), "\n")) {
                outputStreamWriter.write(s);
            }
        }
        outputStreamWriter.flush();
    }
}
