import Parser.ConstructorParser;
import Parser.Parser;
import Tokenizer.ConstructorTokenizer;
import Tokenizer.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Exception{

    public static void main(String[] args) {
//        try {
//            List<String> lines = Files.readAllLines(Paths.get("src/test.txt"));
//            Tokenizer tokenizer = new ConstructorTokenizer(lines);
//            Parser parser = new ConstructorParser(tokenizer);
//            parser.parse();
//        } catch (Exception e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        }
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test.txt"))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            int i = 0;
            while(i < 1000000){
                Tokenizer tokenizer = new ConstructorTokenizer(lines);
                Parser parser = new ConstructorParser(tokenizer);
                parser.parse();
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        ArrayList<String> list = new ArrayList<>();
//        list.add("if");
//        list.add("(0)");
//        list.add("then");
//        list.add("shoot up 500");
//        list.add("else {}");
//        list.add("while");
//        list.add("(4+3)");
//        list.add("{done}");
//        Tokenizer tokenizer = new ConstructorTokenizer(list);
//        Parser parser = new ConstructorParser(tokenizer);
//        parser.parse();
    }
}