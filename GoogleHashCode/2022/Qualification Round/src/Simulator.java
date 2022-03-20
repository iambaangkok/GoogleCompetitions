import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Simulator {

    public final String inputPath = "input_data/";
    public final String outputPath = "outputs/";
    public static String inputFileName = "a.txt";
    public static String outputFileName = "out_a_v0.txt";

    // INPUT fields
    public int nContributors;
    public int nProjects;

    public Map<String, Contributor> AllContributors;
    public Map<String, Project> AllProjects;

    // INTERNAL fields
    public List<Project> AllProjectsList;

    public int currentDay = 0;
    public int maxDay = 0;

    public List<Project> CompletedProjects;
    public List<Project> JustCompleted;

    public static boolean DEBUG = true;

    public Simulator(){
        AllContributors = new HashMap<>();
        AllProjects = new HashMap<>();
        AllProjectsList = new ArrayList<>();
        CompletedProjects = new ArrayList<>();
    }

    public void ReadInput(){
        File inputFile = new File(inputPath+inputFileName);
        try(Scanner sc = new Scanner(inputFile);){
            // INPUT: first line
            nContributors = sc.nextInt();
            nProjects = sc.nextInt();

            // INPUT: contributors
            for(int i = 0 ; i < nContributors; ++i){
                String name = sc.next();
                int nSkills = sc.nextInt();
                Contributor con = new Contributor(name);
                AllContributors.put(name, con);
                for(int j = 0 ; j < nSkills; ++j)        {
                    String skillName = sc.next();
                    int skillLevel = sc.nextInt();
                    con.addSkill(skillName, skillLevel);
                }
            }

            // INPUT: projects
            for(int i = 0 ; i < nProjects; ++i){
                String name = sc.next();
                int nDays = sc.nextInt();
                int score = sc.nextInt();
                int bestBefore = sc.nextInt();
                int nRoles = sc.nextInt();
                Project pro = new Project(name, nDays, score, bestBefore);
                AllProjects.put(name, pro);
                for(int j = 0 ; j < nRoles; ++j)        {
                    String roleName = sc.next();
                    int skillLevel = sc.nextInt();
                    pro.addSkill(roleName, skillLevel);
                }
                pro.computeNumbersOfSkills();
                pro.computeMeanSkillLevel();

                maxDay = Math.max(maxDay, bestBefore + score); 
            }

            for(String name : AllProjects.keySet()){
                AllProjectsList.add(AllProjects.get(name));
            }
        
            System.out.println("Read Successfully");
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }

    public void WriteOutput(){
        try(FileWriter fw = new FileWriter(outputPath + outputFileName);) {
            fw.write(CompletedProjects.size() + "\n");
            
            for (Project pro : CompletedProjects) {
                fw.write(pro.name + "\n");
                StringBuilder names = new StringBuilder("");
                for (Contributor con : pro.cons) {
                    names.append(con.name); names.append(" ");
                }
                fw.write(names.toString() + "\n");
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Contributor getWithSkill(String name, int minimumLevel, List<Contributor> alreadyAssigned){
        for (Contributor con : AllContributors.values()) {
            if(con.skills.containsKey(name)){
                if(!alreadyAssigned.contains(con) 
                && con.nextFreeDay <= currentDay ){
                    if((con.getSkill(name) >= minimumLevel))
                        return con;
                    for(Contributor mentor : alreadyAssigned){
                        if(mentor.getSkill(name) >= minimumLevel){
                            if((con.getSkill(name) >= minimumLevel-1))
                                return con;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean assign_PrioritizeLevelUp(Project pro){
        boolean assigned = false;

        List<Contributor> toAssign = new ArrayList<>();

        for (Pair<String,Integer> skill : pro.skills) {
            String name = skill.name();
            int level = skill.level();

            Contributor con = getWithSkill(name, level, toAssign);

            if(con != null){ // assign this con to this pro
                toAssign.add(con);
            }else{
                break;
            }
        }
        
        if(toAssign.size() < pro.nSkills){ // not assignable
            assigned = false;
        }else{ // assignable
            assigned = true;
            for (Contributor con : toAssign) {
                pro.addContributor(con);
                con.nextFreeDay = currentDay + pro.nDays;
            }
            //System.out.println("   > " + pro.name);
            for(int i = 0; i < pro.skills.size(); ++i){
                Pair<String,Integer> skill = pro.skills.get(i);

                Contributor con = toAssign.get(i);
                //System.out.println("     " + con.name + " required " + skill + " have " + con.getSkill(skill.name()));
                if(con.getSkill(skill.name()) <= skill.level()){
                    con.levelUpSkill(skill.name());
                }
            }
            JustCompleted.add(pro);
            CompletedProjects.add(pro);

        }

        return assigned;
    }

    // sort Project by meanSkillLevel, bestBefore, score
    // assign contributors prioritize 0 -> -1 delta skill level
    public void PrioritizeLevelUp(){
        Collections.sort(AllProjectsList, new SortProject_By_MeanSkillLevel_BestBefore_Score());
        // for (Project pro : AllProjectsList) {
        //     System.out.println(pro.name + "     " + pro.meanSkillLevel + " - " + pro.nSkills);
        // }
        int limit = 1000;
        if(DEBUG) System.out.println("maxDay: " + maxDay);
        while(currentDay <= maxDay && limit > 0){
            if(DEBUG) System.out.print("currentDay: " + currentDay + "  limit: " + limit);

            int nAssigned = 0;

            JustCompleted = new ArrayList<>();
            for (Project pro : AllProjectsList){
                if(!pro.isPlanned() && currentDay + pro.nDays < pro.bestBefore + pro.score){
                    //if finishing this project is a good idea
                    if(assign_PrioritizeLevelUp(pro)){
                        nAssigned++;
                    }
                }
            }
            AllProjectsList.removeAll(JustCompleted);

            int newCurrentDay = Integer.MAX_VALUE;
            for(Contributor con : AllContributors.values()){
                if(con.nextFreeDay > currentDay){
                    newCurrentDay = Math.min(newCurrentDay, con.nextFreeDay);
                }
            }currentDay = newCurrentDay;

            if(DEBUG) System.out.println("   nAssigned: " + nAssigned);
            
            
            limit--;
        }
        

    }

    class SortProject_By_MeanSkillLevel_BestBefore_Score implements Comparator<Project>{

        @Override
        public int compare(Project p1, Project p2) {
            if(p1.meanSkillLevel != p2.meanSkillLevel){
                return (int)(p1.meanSkillLevel) - (int)(p2.meanSkillLevel);
            }else if(p1.bestBefore != p2.bestBefore){
                return p1.bestBefore - p2.bestBefore;
            }else{
                return p2.score - p1.score;
            }
        }
    
    }



    public static void main(String[] args) {

        int version = 0;
        for(int i = 'a' ; i <= 'f'; ++i){
            Simulator sim = new Simulator();
            DEBUG = true;
            inputFileName = String.valueOf((char)(i)) + ".txt";
            outputFileName = "out_" + String.valueOf((char)(i)) + "v" + version + ".txt";
            sim.ReadInput();
            sim.PrioritizeLevelUp();
            sim.WriteOutput();
            System.out.println(inputFileName + " " + outputFileName);
        }
    }
}



