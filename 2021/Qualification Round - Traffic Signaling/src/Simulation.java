import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Simulation{


    public final String inputPath = "tests/";
    public final String outputPath = "outputs/";
    public static String inputFileName = "a.txt";
    public static String outputFileName = "out_a_v0.txt";

    public static boolean DEBUG = false;

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
    public void GenerateTrafficLightSchedule(double greenLightTimePerLoop){
        // Proportional with static greenlighttimeperloop
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

        double greenLightTimeMultiplier = 3;

        // Proportional
        for(Intersection inter: AllIntersections){
            TrafficLight tf = inter.trafficLight;
            double greenLightTimePerLoop = 0;
            

            int nCarMustPassIntersection = 0;
            for(Street inSt : inter.IncomingStreets){
                nCarMustPassIntersection += inSt.nCarMustPass;
            }
            
            if(nCarMustPassIntersection != 0){
                greenLightTimePerLoop = Math.log(nCarMustPassIntersection)*greenLightTimeMultiplier;
            }else{
                greenLightTimePerLoop = 0;
            }
            
            
            for(Street inSt : inter.IncomingStreets){
                double proportion = 0;
                if(nCarMustPassIntersection != 0){
                    proportion = inSt.nCarMustPass/((double)nCarMustPassIntersection);
                }else{
                    proportion = 0;
                }
                int greenLightTime = (int)Math.ceil(proportion*greenLightTimePerLoop);
                if(greenLightTime > 0){
                    tf.LightSchedule.put(inSt, greenLightTime);
                }

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

    public void WriteOutput(){
        try(FileWriter fw = new FileWriter(outputPath + outputFileName);) {
            int AllInterCount = 0;
            for(Intersection inter : AllIntersections){
                TrafficLight tf = inter.trafficLight;
                if(!tf.LightSchedule.isEmpty()){
                    AllInterCount++;
                }
            }
            fw.write(AllInterCount + "\n");
            for(Intersection inter : AllIntersections){
                Map<Street, Integer> ls = inter.trafficLight.LightSchedule;
                if(!ls.isEmpty()){
                    fw.write(inter.label + "\n");
                    fw.write(ls.size() + "\n");
                    for(Street st : ls.keySet()){
                        fw.write(st.name + " " + ls.get(st) + "\n");
                    }
                }
            }



            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadInput(){
        File inputFile = new File(inputPath+inputFileName);
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

        int version = 0;
        for(int i = 0 ; i < 6; ++i){
            Simulation sim = new Simulation();
            // System.out.println(Math.log(2));
            // System.out.println(Math.log(10));
            // System.out.println(Math.log(100));
            // System.out.println(Math.log(100));
            // System.out.println(Math.log(10000));
            // System.out.println(Math.log(100000));
            DEBUG = false;
            inputFileName = String.valueOf((char)('a'+i)) + ".txt";
            outputFileName = "out_" + String.valueOf((char)('a'+i)) + "v" + version + ".txt";
            sim.ReadInput();
            System.out.println(inputFileName + " " + outputFileName);
            sim.GenerateTrafficLightSchedule();
            // sim.GenerateTrafficLightSchedule(2);
            //sim.DisplayTrafficLightSchedule();
            sim.WriteOutput();
        }
    }
}