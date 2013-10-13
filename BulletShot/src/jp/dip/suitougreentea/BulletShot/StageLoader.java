package jp.dip.suitougreentea.BulletShot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StageLoader {
    private FileInputStream stream;
    
    public StageLoader(String path) {
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public Stage load(FileInputStream stream){
        return null;
        //return process(stream);
    }
    
    //private Stage 
}
