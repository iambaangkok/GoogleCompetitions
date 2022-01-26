import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Simulation{


    public final String inputPath = "tests/b.txt";

    public final boolean DEBUG = true;

    public int SimulationTime;
    public int nIntersections;
    public int nStreets;
    public int nCars;
    public int pointsPerEarlyArrival;

    public List<Intersection> AllIntersections;
    public TreeMap<String, Street> AllStreets;
    public List<Car> AllCars;

    Simulation(){
        AllIntersections = new ArrayList<>();
        AllStreets = new TreeMap<>();
        AllCars = new ArrayList<>();
    }

    /** Generate traffic light schedule 
     * @effects AllIntersections's TrafficLight's LightSchedule will be filled with proper LightSchedule
     * @param greenLightTimePerLoop Each Intersection's green light time per loop
     * @implNote LightSchedule's green light time will be proportional to the number of car that will 
     * pass that TrafficLight compared to the total number of car that will pass that Intersection. 
     */
    public void GenerateTrafficLightSchedule(int greenLightTimePerLoop){

        // Proportional
        for(Intersection inter: AllIntersections){
            TrafficLight tf = inter.trafficLight;

            int nCarMustPassIntersection = 0;
            for(Street inSt : inter.IncomingStreets){
                nCarMustPassIntersection += inSt.nCarMustPass;
            }
            for(Street inSt : inter.IncomingStreets){
                double proportion = inSt.nCarMustPass/((double)nCarMustPassIntersection);
                int greenLightTime = (int)Math.ceil(proportion*greenLightTimePerLoop);
                tf.LightSchedule.put(inSt, greenLightTime);

                if(DEBUG) System.out.println("   " + inSt.name + ": " + proportion + " " + tf.LightSchedule.get(inSt));
            }
        }
    }
     /** Generate traffic light schedule 
     * @effects AllIntersections's TrafficLight's LightSchedule will be filled with proper LightSchedule
     * @param greenLightTimePerLoop Each Intersection's green light time per loop
     * @implNote LightSchedule's green light time will be proportional to the number of car that will 
     * pass that TrafficLight compared to the total number of car that will pass that Intersection. 
     */
    public void GenerateTrafficLightSchedule(){
        // HOW TO CALCULATE greenLightTimePerLoop ??????????????????
        // Proportional
        for(Intersection inter: AllIntersections){
            TrafficLight tf = inter.trafficLight;
            int greenLightTimePerLoop = 2;

            int nCarMustPassIntersection = 0;
            for(Street inSt : inter.IncomingStreets){
                nCarMustPassIntersection += inSt.nCarMustPass;
            }
            if(nCarMustPassIntersection < 50){

            }
            for(Street inSt : inter.IncomingStreets){
                double proportion = inSt.nCarMustPass/((double)nCarMustPassIntersection);
                int greenLightTime = (int)Math.ceil(proportion*greenLightTimePerLoop);
                tf.LightSchedule.put(inSt, greenLightTime);

                if(DEBUG) System.out.println("   " + inSt.name + ": " + proportion + " " + tf.LightSchedule.get(inSt));
            }
        }
    }

    /** Display all traffic light schedule 
     * @effects display all traffic light schedule, grouped by Intersection
     */
    public void DisplayTrafficLightSchedule(){
        for(Intersection inter: AllIntersections){
            TrafficLight tf = inter.trafficLight;
            System.out.println("INTER#" + inter.label);
            for(Street inSt : inter.IncomingStreets){
                System.out.println("   " + inSt.name + ": " + tf.LightSchedule.get(inSt));
            }
        }
    }

    public void ReadInput(){
        File inputFile = new File(inputPath);
        try(Scanner sc = new Scanner(inputFile);){
            // INPUT: first line
            SimulationTime = sc.nextInt();
            nIntersections = sc.nextInt();
            nStreets = sc.nextInt();
            nCars = sc.nextInt();
            pointsPerEarlyArrival = sc.nextInt();

            // generate intersections
            for(int i = 0 ; i < nIntersections; ++i){
                AllIntersections.add(new Intersection(i));
            }

            // INPUT: streets
            for(int i = 0 ; i < nStreets; ++i){
                int i1 = sc.nextInt();
                int i2 = sc.nextInt();
                String sname = sc.next();
                int length = sc.nextInt();

                Street s = new Street(AllIntersections.get(i1), AllIntersections.get(i2), sname, length, i);
                AllStreets.put(s.name, s);
                AllIntersections.get(i1).addOutgoingStreet(s);
                AllIntersections.get(i2).addIncomingStreet(s);
            }

            // INPUT: cars
            for(int i = 0 ; i < nCars; ++i){
                int nS = sc.nextInt();
                Car car = new Car(i+1);

                // first street
                String streetName = sc.next();
                Street street = AllStreets.get(streetName);
                street.Cars.add(car);
                street.CarsAtEnd.add(car);
                street.nCarMustPass++;
                car.AddStreet(street);
                car.PositionInStreet = street.length;
                // the rest
                for(int j = 1 ; j < nS; ++j){
                    streetName = sc.next();
                    street = AllStreets.get(streetName);
                    street.nCarMustPass++;
                    car.AddStreet(street);
                }
            }
            System.out.println("Read Successfully");
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }


    public static void main(String[] args) {

        Simulation sim = new Simulation();
        sim.ReadInput();
        sim.GenerateTrafficLightSchedule(2);
        sim.DisplayTrafficLightSchedule();

    }
}