import java.util.*;
import java.io.*;



public class Solution{

    public static void print(String str){
        System.out.println(str);
    }

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int T = in.nextInt();
        for(int testcase = 1 ; testcase <= T; ++testcase){
            int r = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            
            double result = solveInfinityArea(r,a,b);
        
            print("Case #" + testcase + ": " + result);
        }

        in.close();
    }

    public static double solveInfinityArea(int r, int a, int b){
        double ans = 0;
        
        double area = circArea(r);
        int i = 0;
        while(area > 0){
            if (DEBUG) print(r + " " + area + " " + ans);
            if(i == 0){
                
            }else if(i%2 == 1){
                r = r*a;
            }else if(i%2 == 0){
                r = r/b;
            }
            i++;
            area = circArea(r);
            ans += area;
        }


        return ans;
    }

    public static double circArea(double r){
        return Math.PI*r*r;
    }

    
}