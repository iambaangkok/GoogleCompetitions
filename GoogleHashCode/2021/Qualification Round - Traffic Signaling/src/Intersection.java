import java.util.ArrayList;
import java.util.List;

public class Intersection {
    
    public int label; // intersection number: 0, 1, 2, ...

    public List<Street> IncomingStreets;
    public List<Street> OutgoingStreets;
    public TrafficLight trafficLight;

    Intersection(int label){
        this.label = label;
        IncomingStreets = new ArrayList<>();
        OutgoingStreets = new ArrayList<>();
        trafficLight = new TrafficLight();
    }

    public void addIncomingStreet(Street s){
        IncomingStreets.add(s);
    }

    public void addOutgoingStreet(Street s){
        OutgoingStreets.add(s);
    }
    
}
