import java.util.ArrayList;
import java.util.List;

public class Car {

    public int label; // car number: 1, 2, 3, ... (first, second, ...)

    public List<Street> Carpath;
    public int CurrentStreetIndex;
    public int PositionInStreet;

    Car(int label){
        this.label = label;

        Carpath = new ArrayList<>();
        CurrentStreetIndex = 0;
        PositionInStreet = 0;
    }

    public void SetCurrentStreet(int index){
        CurrentStreetIndex = index;
    }

    public void AddStreet(Street s){
        Carpath.add(s);
    }
}
