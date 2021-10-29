package ver1;

import java.util.*;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();
    int num;

    public void insertFromCommandline() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Số từ bạn muốn nhập: ");
        num = myvar.nextInt();
        dictionary.words = new Word[num];

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            dictionary.words[i] = new Word();
            System.out.print("Từ mới: ");
            dictionary.words[i].setWord_target(sc.nextLine());
            System.out.print("Nghĩa của từ: ");
            dictionary.words[i].setWord_explain(sc.nextLine());
        }
    }

    public void show() {
        System.out.println(dictionary.words[0].getWord_target());
        System.out.println("No \t English \t Vietnamese");
        for (int i = 0; i < num; i++) {
            System.out.println(i + 1 + " \t " + dictionary.words[i].getWord_target() + "\t\t " + dictionary.words[i].getWord_explain());
        }
    }
}
