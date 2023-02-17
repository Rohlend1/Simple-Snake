package kitchen;

import restaurant.ConsoleHelper;
import restaurant.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {

        return tablet;
    }

    protected List<Dish> dishes;

    public Order(Tablet tablet){
        this.tablet = tablet;
        initDishes();
        ConsoleHelper.writeMessage(toString());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (dishes.size() == 0) return result.toString();
        result.append("Your order: [").append(dishes.get(0));

        for (int i = 1; i < dishes.size(); i++) {
            result.append(", ").append(dishes.get(i).name());
        }
        result.append("] of ").append(tablet);
        result.append(", cooking time ").append(getTotalCookingTime()).append("min");
        return result.toString();
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public int getTotalCookingTime() {
        int cookingTime = 0;
        for (Dish dish : dishes) {
            cookingTime += dish.getDuration();
        }
        return cookingTime;
    }
    protected void initDishes(){
        try{
            dishes = ConsoleHelper.getAllDishesForOrder();
        }
        catch (IOException ignored){

        }
    }
}





























