import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {
    private ArrayList<String> datalist = new ArrayList<String>();
    public DataProvider(){
         this.readTextFileData();
    }
    private void readTextFileData(){
        BufferedReader br = null;
        try {
            File file = new File("C:\\Users\\Lenovo\\IdeaProjects\\uitest\\src\\ScenarioTestData.txt"); // java.io.File
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                this.datalist.add(line);
            }
        }
        catch(IOException e) { e.printStackTrace();}
    }
    public ArrayList<String> getData(){return this.datalist;}
}
