import java.util.*;
import java.io.*;

public class Solution{

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int T = in.nextInt();
        for(int testcase = 1 ; testcase <= T; ++testcase){
            String I = in.next();
            String P = in.next();
            int result = -1;
            if(I.length() <= P.length()){
                result = checkSpeedType(I, P);
            }
            if(result >= 0){
                System.out.println("Case #" + testcase + ": " + result);
            }else{
                System.out.println("Case #" + testcase + ": " + "IMPOSSIBLE");
            }
        }
        in.close();
    }

    public static int checkSpeedType(String I, String P){
        int missMatchCount = 0;
        int indexI = 0;
        int indexP = 0;

        while(indexI < I.length() && indexP < P.length()){
            if(DEBUG) System.out.println(P.charAt(indexP) + " " + I.charAt(indexI));
            if(P.charAt(indexP) != I.charAt(indexI)){
                missMatchCount++;
                indexP++;
            }else{
                indexI++;
                indexP++;
            }
        }

        missMatchCount += (P.length() - indexP);

        if(indexI < I.length()){
            return -1;
        }else{
            return missMatchCount;
        }
    }
}