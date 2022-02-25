import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project{
    
    // INPUT fields
    public String name;
    public int nDays;
    public int score;
    public int bestBefore;
    
    public List<Pair<String, Integer>> skills;
    public int nSkills = 0;
    public int meanSkillLevel = -1;

    public List<Contributor> cons;

    public Project(String name, int nDays, int score, int bestBefore) {
        this.name = name;
        this.nDays = nDays;
        this.score = score;
        this.bestBefore = bestBefore;
        skills = new ArrayList<>();
        cons = new ArrayList<>();
    }

    public void addContributor(Contributor con){
        cons.add(con);
    }

    public void addSkill(String skill, int level){
        skills.add(new Pair<String,Integer>(skill,level));
    }

    public boolean isPlanned(){
        return cons.size() == skills.size();
    }

    public Integer computeNumbersOfSkills(){
        // for (String name : skills.keySet()) {
        //     nSkills += skills.get(name).size();
        // }
        nSkills = skills.size();
        return nSkills;
    }

    public void computeMeanSkillLevel(){
        meanSkillLevel = 0;
        // for(String skill : skills.keySet()){
        //     for(int lvl : skills.get(skill)){
        //         meanSkillLevel += lvl;
        //     }
        // }
        for(Pair<String,Integer> p : skills){
            meanSkillLevel += p.level();
        }
        meanSkillLevel /= nSkills;
    }
    

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");

        sb.append(name); sb.append(" "); 
        sb.append(nDays); sb.append(" ");
        sb.append(score); sb.append(" "); 
        sb.append(bestBefore); sb.append(" | "); 
        for (Pair<String, Integer> skill : skills) {
            sb.append("[");
            sb.append(skill.name()); sb.append(" - ");
            sb.append(skill.level());
            sb.append("], ");
        }
        

        return sb.toString();
    }



    
    
}

