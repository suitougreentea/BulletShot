package jp.dip.suitougreentea.BulletShot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.newdawn.slick.Color;

public class LoggerHandler extends Handler {

    ArrayList<LogRecord> list = new ArrayList<LogRecord>();
    
    @Override
    public void close() throws SecurityException {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void flush() {
        // TODO 自動生成されたメソッド・スタブ

    }

    @Override
    public void publish(LogRecord record) {
        if(isLoggable(record)){
            String formattedConsoleStr = String.format("%s [%s] %s",
                    new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(record.getMillis()),
                    record.getLevel().getName(),
                    record.getMessage());
            
            String formattedLogfileStr = String.format("");
            System.out.println(formattedConsoleStr);
            if(list.size()==30)list.remove(0);
            list.add(record);
        }
    }
    
    public void drawRecords(){
        for(int i=0;i<list.size();i++){
            LogRecord record = list.get(i);
            String formattedDisplayStr = String.format("# %s",record.getMessage());
            Color color;
            if(record.getLevel() == Level.SEVERE)color = new Color(1f,0.2f,0.2f);
            else if(record.getLevel() == Level.WARNING)color = new Color(1f,0.8f,0.2f);
            else if(record.getLevel() == Level.INFO)color = new Color(0.8f,0.8f,1f);
            else color = new Color(0.5f,0.5f,0.5f);
            Res.debugfontsmall.draw(formattedDisplayStr, 16, 440+i*8-list.size()*8,color);
        }
    }

}