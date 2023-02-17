package statistic.event;

import ad.Advertisement;

import java.util.Date;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow{

    private final long amount;
    private final int totalDuration;
    private final Date currentDate = new Date();

    public long getAmount() {
        return amount;
    }

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.amount = amount;
        this.totalDuration = totalDuration;
    }
    public EventType getType(){
        return EventType.SELECTED_VIDEOS;
    }
    public Date getDate(){
        return currentDate;
    }
    public int getTime(){
        return totalDuration;
    }

}
