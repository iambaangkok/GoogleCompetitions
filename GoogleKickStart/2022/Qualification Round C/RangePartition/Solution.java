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
            int n = in.nextInt();
            String oldPassword = in.next();
            
            String result = solveNewPassword(n,oldPassword);
        
            print("Case #" + testcase + ": " + result);
        }

        in.close();
    }

    public static String solveNewPassword(int n, String oldPassword){

        String ans = oldPassword;
        
        boolean hasLength = (n >= 7);
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        

        for(int i = 0 ; i < oldPassword.length(); ++i){
            char ch = oldPassword.charAt(i);
            if (!hasUpper && Character.isUpperCase(ch)) hasUpper = true;
            if (!hasLower && Character.isLowerCase(ch)) hasLower = true;
            if (!hasDigit && Character.isDigit(ch)) hasDigit = true;
            if (!hasSpecial && ( ch == '#' || ch == '@' || ch == '*' || ch == '&')) hasSpecial = true;
        }

        if(!hasUpper) ans+="A";
        if(!hasLower) ans+="a";
        if(!hasDigit) ans+="0";
        if(!hasSpecial) ans+="#";

        while(ans.length() < 7){
            ans += "0";
        }
        

        return ans;

    }

}