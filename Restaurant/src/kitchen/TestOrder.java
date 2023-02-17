package kitchen;

import restaurant.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestOrder extends Order{
    protected void initDishes(){
        dishes = new ArrayList<>();
        dishes.addAll(Arrays.asList(Dish.values()));
    }

    public TestOrder(Tablet tablet) throws IOException{
        super(tablet);
    }
}
