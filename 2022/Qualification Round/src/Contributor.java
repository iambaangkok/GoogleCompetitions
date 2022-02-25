import java.util.HashMap;
import java.util.Map;

public class Contributor{
    public String name;
    public Map<String, Integer> skills;
    public int meanSkillLevel = -1;

    public int nextFreeDay = 0;

    public Contributor(String name){
        this.name = name;
        skills = new HashMap<>();
    }

    public void addSkill(String name, int level){
        if(!skills.containsKey(name)){
            skills.put(name, level);
        }else{
            System.out.println("already have skill " + name + " at lvl"  + level);
        }
    }

    public void levelUpSkill(String skill){
        if(!skills.containsKey(skill)){
            addSkill(skill, 0);
        }
        int pre = skills.get(skill);
        skills.put(skill, pre+1);

        // System.out.println("  " + name + " levelup " + skill + "  " + pre + "->" + skills.get(skill));
    }

    public Integer getSkill(String skill){
        if(!skills.containsKey(skill)){
            addSkill(skill, 0);
        }
        return skills.get(skill);
    }

    public Integer numbersOfSkills(){
        return skills.size();
    }

    public void computeMeanSkillLevel(){
        meanSkillLevel = 0;
        for(String skill : skills.keySet()){
            meanSkillLevel += skills.get(skill);
        }
        meanSkillLevel /= numbersOfSkills();
    }

}
