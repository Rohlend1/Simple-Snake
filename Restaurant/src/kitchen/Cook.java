package kitchen;

import restaurant.ConsoleHelper;
import statistic.StatisticManager;
import statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable{
    private final String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public void setQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.queue = orderQueue;
    }

    @Override
    public void run() {
        while (true){
            try{
                if(queue.isEmpty());
                else{
                    if(!this.isBusy()) this.startCookingOrder(queue.take());
                }
                Thread.sleep(10);
            }
            catch (InterruptedException ignored){

            }
        }
    }

    public boolean isBusy() {
        return busy;
    }

    public void startCookingOrder(Order order){
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - "+order);

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),name,order.getTotalCookingTime()*60,order.getDishes()));
        try {
            Thread.sleep(order.getTotalCookingTime()* 10L);
        }
        catch (InterruptedException ignored){

        }
        setChanged();
        notifyObservers(order);
        busy=false;
    }


    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
