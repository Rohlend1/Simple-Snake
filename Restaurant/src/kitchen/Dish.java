package kitchen;

public enum Dish {
    STEAK(30),SOUP(15),JUICE(5),WATER(3),FISH(25);
    private final int duration;

    Dish(int duration){
        this.duration = duration;
    }
    public int getDuration() {
        return duration;
    }
    public static String allDishesToString(){
        StringBuilder str = new StringBuilder();
        for(Dish dish: Dish.values()){
            str.append(dish.name()).append(" ");
        }
        return str.toString().trim();
    }
}
