import java.util.*;
import java.io.*;

public class Solution{

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int T = in.nextInt();
        for(int testcase = 1 ; testcase <= T; ++testcase){
            String number = in.next();
            
            String result = solveChallengeNine(number);
        
            System.out.println("Case #" + testcase + ": " + result);
        }

        in.close();
    }

    public static String solveChallengeNine(String base){
        StringBuilder sb = new StringBuilder(base);
        
        int sumDigit = 0;
        
        // sumDigit
        int index = 0;
        while(index < base.length()){
            sumDigit += (sb.charAt(index) - '0');
            index++;
        }

        // get targetDigit that will make sum digit divisible by 9
        int targetDigit = 0;
        for(int i = 0 ; i <= 9; ++i){
            if((sumDigit + i)%9 == 0){
                targetDigit = i;
                break;
            }
        }

        // find "where" to put that digit
        // insert it before the first digit that is > targetDigit
        // if there is none, insert last
        if(targetDigit == 0){
            sb.insert(1, targetDigit);
            return sb.toString();
        }

        index = 0;
        while(index < sb.length()){
            int c = sb.charAt(index) - '0';
            if(c > targetDigit){
                sb.insert(index, targetDigit);
                return sb.toString();
            }
            index++;
        }
        sb.append(targetDigit);
        return sb.toString();
    }
}