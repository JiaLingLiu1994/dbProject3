import java.lang.Object;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* TEAM3
 
 Write a program that can input from an input file (named "FDs") that contains a set of functional dependencies (F) and a set of all attributes (R) for a relation, and output all possible superkeys for the relation to an output file (named "superkeys").
 
 The algorithm you may use was discussed in the class.

 
 Use java7 */

public class project3_Berezinski_Liu_Sterrenberg {
    
    final static Charset ENCODING = StandardCharsets.UTF_8;
    private final static String INPUT_FILE_NAME = "./FDs.txt/";
    private final static String OUTPUT_FILE_NAME = "./superkeys.txt/";
    
    public static void main (String[] args) throws IOException {
    
        project3_Berezinski_Liu_Sterrenberg writeText = new project3_Berezinski_Liu_Sterrenberg();
        
        BufferedReader input_file = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        FileWriter output_file = new FileWriter(OUTPUT_FILE_NAME);
        String str;
        int line_num = 0;
        
        List<String> FileContentsList = new ArrayList<String>();
//        Set<char> attributes = new HashSet<char>();
        char[] allAttributeInArray = new char[0];
        int columns = 0;
        int rows = 0;
        boolean[][] binary_matrix = new boolean[0][0];
        String[] powerSet = new String[0];
        
        while((str = input_file.readLine()) != null) {
            line_num++;
            
            if (line_num == 1) {
                allAttributeInArray = str.toCharArray();
                columns = allAttributeInArray.length;
                rows = (int) Math.pow(2, columns);
                binary_matrix = new boolean[rows][columns];
                powerSet = new String[rows-1];
                
                for (char temp : allAttributeInArray) {
                    System.out.println(temp);
                }
            }
            
            FileContentsList.add(str);
        }
        
        for(int i = 0 ; i< powerSet.length ; i++){
            powerSet[i] = "";
        }
        
        for(int i = 1 ; i < binary_matrix.length; i++){
            for(int j = 0; j < binary_matrix[i].length; j++){
                int val = binary_matrix.length * j + i;
                int ret = (1 & (val >>> j));
                
                binary_matrix[i][j] = ret != 0;
                
                if(binary_matrix[i][j]==true){
                    powerSet[i-1] += "," + allAttributeInArray[j];
                }
                
            }
            
        }
        
        Arrays.sort(powerSet,new comp());
        
        for(int i = 0 ; i< powerSet.length ; i++){
            System.out.println("{" + powerSet[i].substring(1, powerSet[i].length()) + "}");
        }


        writeText.writeLargerTextFile(OUTPUT_FILE_NAME, FileContentsList);
    
    }
    
    void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException {
        Path path = Paths.get(aFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
            for(String line : aLines){
                writer.write(line);
                writer.newLine();
            }
        }
    }
}

class comp implements Comparator<String> {
    public int compare(String o1, String o2) {
        return Integer.compare(o1.length(), o2.length());
    }
}

