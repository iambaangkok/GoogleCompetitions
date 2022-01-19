import java.util.ArrayList;
import java.util.List;

public class Street {

    public int label; // street number: 0, 1, 2, ...

    public List<Car> Cars;
    public List<Car> CarsAtEnd;
    public int nCarMustPass;
    public int nCarPassed;

    public Intersection FromIntersection;
    public Intersection ToIntersection;
    public String name;
    public int length;

    Street(Intersection f, Intersection t, String name, int l, int label){
        this.label = label;
        Cars = new ArrayList<>();
        CarsAtEnd = new ArrayList<>();
        nCarMustPass = 0;
        nCarPassed = 0;
        
        FromIntersection = f;
        ToIntersection = t;
        this.name = name;
        length = l;
    }
    
}
