package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileParser {

    
    public static Map<Integer, Character> parse(String URL) {
        Map<Integer, Character> res = new LinkedHashMap<Integer, Character>();
        
        BufferedReader br = null;
        
        try {
            
            br = new BufferedReader(new FileReader(URL));
            String curr;
            String next;
            
            while ((curr = br.readLine()) != null && (next = br.readLine()) != null) {
                res.put(Integer.parseInt(curr), next.charAt(0));
            }
            
        } catch (IOException e) {
            System.out.println("Couldn't load file: " + URL);
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                System.out.println("Couldn't close file: " + URL);
                ex.printStackTrace();
            }
        }
         
        return res;
    }
}
