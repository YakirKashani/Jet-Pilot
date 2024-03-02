package Logic;

import com.google.gson.Gson;

import java.util.ArrayList;

import Utilities.SharedPreferencesManagerV3;


public class HallOfFame {
    public static Records records;
    public static final String SCORES = "SCORES";
    Gson gson;
    SharedPreferencesManagerV3 spm;

    public HallOfFame(){

        gson = new Gson();
        spm = SharedPreferencesManagerV3.getInstance();
        Records fromSP = new Gson().fromJson(SharedPreferencesManagerV3.getInstance().getString(SCORES, ""), Records.class);
        if(fromSP!=null) {
            records = fromSP;
        }
        else
            records = new Records();
    }
    public ArrayList<Record> getRecords() {

        return records.getRecords();
    }
    public void addRecord(Record record) {
        records.addRecord(record);
        updateJson();
    }
    private void updateJson(){
        String HOFAsJson;
        HOFAsJson = gson.toJson(HallOfFame.records);  //Problem is here - pass Records but try to read HallOfFame
        spm.putString(SCORES, HOFAsJson);
    }

}
