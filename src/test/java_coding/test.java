package test.java_coding;

import java.io.*;
import java.nio.file.Files;
import java.util.*;


public class test {
    public static void main(String asrgs[]) {
        try {
            ArrayList<QuizData> quizDataList = new ArrayList<>();
            String filePath = "D:\\code\\dog.txt";
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int lineCount = 0;
            while (reader.readLine() != null) {
                lineCount++;
            }
            reader = new BufferedReader(new FileReader(file));
            for(int i=0; i<lineCount/3; i++){ //9/3
                QuizData temp = new QuizData(reader.readLine(), reader.readLine(), Integer.parseInt(reader.readLine()));
                quizDataList.add(temp);
            }

            int totalScore=0;
           for(int i=0; i<5; i++) {

               Random random = new Random();
               int randomIndex = random.nextInt(quizDataList.size());
               System.out.println(quizDataList.get(randomIndex));

               Scanner scanner = new Scanner(System.in);
               System.out.print("정답을 입력해");
               String answer = scanner.nextLine();


               if (quizDataList.get(randomIndex).answer.equals(answer)) {
                   System.out.println("정답입니다!");
                   totalScore+=quizDataList.get(randomIndex).score;
               } else {
                   System.out.print("기니");
               }
           }
            System.out.println("총점수는" + totalScore);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFile(ArrayList<QuizData> quizDataList, File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Optional<String> firstLine = br.lines()
                    .filter(line -> line.contains("문제"))
                    .findFirst();

            firstLine.ifPresent(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}