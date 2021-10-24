package ver2;

import java.util.*;
import java.io.*;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();

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

    public void insertFromFile() {
        File file = new File("D:\\OOP\\BTL\\src\\ver2\\data.txt");
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

    public void show() {
        System.out.println("No\t\tEnglish\t\t\tVietnamese");
        int index=1;
        for (Map.Entry<String, String> e : dictionary.words.entrySet())
            System.out.println(index++ + "\t\t" + e.getKey() + "\t\t\t" + e.getValue());
        }

    public void dictionaryLookup() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Từ bạn muốn tra cứu: ");
        String word = myvar.nextLine();
        if(dictionary.words.containsKey(word)){
            System.out.print("Nghĩa của từ đó: " + dictionary.words.get(word));
        }else{
            System.out.println("Không có từ đó trong từ điển!");
        }
    }
}
