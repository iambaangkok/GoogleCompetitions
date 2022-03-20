import java.util.*;
import java.io.*;

public class Solution{

    private static final boolean DEBUG = true;

    public static Set<Long> interestingIntegers;
    public static List<Long> qsIndex;
    public static List<Long> qs;
    public static long N = 100000+10;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int T = in.nextInt();
        qsIndex = new ArrayList<>();
        qs = new ArrayList<>();
        interestingIntegers = generateII(N);
        
        for(int testcase = 1 ; testcase <= T; ++testcase){
            long A = in.nextLong();
            long B = in.nextLong();
            
            long result = solveInterestingInteger(A, B);
        
            System.out.println("Case #" + testcase + ": " + result);
        }

        in.close();
    }

    public static long solveInterestingInteger(long A, long B){
        long count = 0;
        int indexA = 0;
        int indexB = 0;
        indexA = Math.max(Arrays.binarySearch(qsIndex.toArray(), A),0);
        indexB = Math.max(Arrays.binarySearch(qsIndex.toArray(), B),0);
        if(indexA > 0) indexA -= 1;
        if(DEBUG) System.out.println(indexA + " " + indexB + " | " + qsIndex.get(indexA) + " " +  qsIndex.get(indexB) + " | " + qs.get(indexA) + " " +  qs.get(indexB));
        count = qs.get(indexB) - qs.get(indexA);
        

        return count;
    }

    public static Set<Long> generateII(long upperBound){
        Set<Long> IIs = new HashSet<>();
        long count = 0;
        qsIndex.add((long)0);
        qs.add(count);
        for(long i = 1 ; i <= upperBound; ++i){
            if((i+"").contains("0")){
                // IIs.add(i);
                count++;
                qsIndex.add(i);
                qs.add(count);
                continue;
            }
            long sum = sumDigit(i);
            long prod = productDigit(i);
            if(prod%sum == 0 && sum != 0){
                // IIs.add(i);
                count++;
                qsIndex.add(i);
                qs.add(count);
            }
        }

        return IIs;
    }

    public static void printInterestingInteger(long upperBound){
        for(int i = 1 ; i <= upperBound; ++i){
            long sum = sumDigit(i);
            long prod = productDigit(i);
            //System.out.println("i = " + i + " | " + prod + ", " + sum + " -> " + ((prod%sum == 0 && sum != 0)? "Interesting" : " "));
            System.out.print((prod%sum == 0 && sum != 0)? i+", " : "");
        }
    }

    public static long sumDigit(long num){
        long result = 0;
        while(num > 0){
            result += num%10;
            num = num/10;
        }
        return result;
    }

    public static long productDigit(long num){
        long result = 1;
        while(num > 0){
            result *= num%10;
            num = num/10;
        }
        return result;
    }
}