package statistic.event;

import java.util.Date;

public class NoAvailableVideoEventDataRow implements EventDataRow{
    private final int totalDuration;
    private final Date currentDate = new Date();

    public NoAvailableVideoEventDataRow(int totalDuration) {
        this.totalDuration = totalDuration;
    }
    public EventType getType(){
        return EventType.NO_AVAILABLE_VIDEO;
    }
    public Date getDate(){
        return currentDate;
    }
    public int getTime(){
        return totalDuration;
    }
}
