package Logic;


public class Record {
    private long points;
    private double lat;
    private double lon;



    public Record(long points,double lat,double lon){
        this.points = points;
        this.lat = lat;
        this.lon=lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public long getPoints() {
        return points;
    }
    public void setPoints(long points) {
        this.points = points;
    }

    public int compareTo(Record o1) {
        if (this.getPoints() == o1.getPoints())
            return 0;
        else if (this.getPoints() > o1.getPoints())
            return 1;
        else
            return -1;
    }
}
