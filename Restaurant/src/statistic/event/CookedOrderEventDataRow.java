package statistic.event;

import kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow{
    private final String cookName;
    private final int cookingTimeSeconds;
    private final Date currentDate = new Date();

    public String getCookName() {
        return cookName;
    }

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
    }
    public EventType getType(){
        return EventType.COOKED_ORDER;
    }
    public Date getDate(){
        return currentDate;
    }
    public int getTime(){
        return cookingTimeSeconds;
    }
}
