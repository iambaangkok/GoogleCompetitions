public class Skill {
    public String name;
    public int level;


    public Skill(String name, int level) {
        this.name = name;
        this.level = level;
    }
    
    public void levelUp(){
        level++;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof Skill)) {
            return false;
        }
        
        Skill s = (Skill) o;
        
        return name.equals(s.name) && Integer.compare(level, s.level) == 0;
    }

}
