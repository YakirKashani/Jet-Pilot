package Logic;

import java.util.ArrayList;

public class Records {

    private ArrayList<Record> Records;

    public Records(){
        Records = new ArrayList<>();
    }

    public ArrayList<Record> getRecords() {
        return Records;
    }

    public void setRecords(ArrayList<Record> records) {
        Records = records;
    }
    public void addRecord(Record record){
        Records.add(record);
        Records.sort((o1,o2) -> o2.compareTo(o1));
        if(Records.size()==11)
            Records.remove(10);
    }


}
