import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public List<SnakeSection> getSections() {

        return sections;
    }

    public void move(){
        if(isAlive()){
            switch (direction) {
                case UP -> move(0, -1);
                case DOWN -> move(0, 1);
                case LEFT -> move(-1, 0);
                case RIGHT -> move(1, 0);
            }
        }
    }
    public void move(int x, int y){
        SnakeSection head = new SnakeSection(sections.get(0).getX()+x,sections.get(0).getY()+y);
        checkBorders(head);
        checkBody(head);
        if(isAlive){
            if(Room.game.getMouse().getX() == head.getX() && Room.game.getMouse().getY() == head.getY()){
                Room.game.eatMouse();
                sections.add(0,head);
            }
            else {
                sections.add(0, head);
                sections.remove(sections.size() - 1);
            }
        }
    }


    public void checkBorders(SnakeSection head){
        if(head.getX() < 0 || head.getY() < 0 ||
                head.getX() > Room.game.getHeight() || Room.game.getWidth() < head.getY()){
            isAlive = false;
        }

    }
    public void checkBody(SnakeSection head){
        if(sections.contains(head)){
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }


}