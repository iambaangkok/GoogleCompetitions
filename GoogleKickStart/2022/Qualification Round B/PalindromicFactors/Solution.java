import java.util.*;
import java.io.*;

public class Solution{

    public static void print(String str){
        System.out.println(str);
    }

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        
        List<Integer> primes = Solution.generatePrimes();

        int T = in.nextInt();
        for(int testcase = 1 ; testcase <= T; ++testcase){
            long a = in.nextInt();
            
            long result = Solution.solvePalindromicFactors(a, primes);
        
            print("Case #" + testcase + ": " + result);
        }

        in.close();
    }

    public static long solvePalindromicFactors(long a, List<Integer> primes){
        int count = 0;

        for(int i = 0 ; i < primes.size(); ++i){
            int p = primes.get(i);
            if(a%p == 0 && checkPalindrome(p+"")){
                count += 2;
            }
        }
        if(checkPalindrome(a+"")){
            count++;
        }

        return count;
    }

    public static List<Integer> generatePrimes(){
        List<Integer> primes = new ArrayList<>();//sieve((int)(10e10+10));

        for(int i = 0 ; i < 10e10+10; ++i){
            if(isPrime(i)){
                primes.add(i);
            }
        }
        
        return primes;
    }

    public static List<Integer> sieve(int n) {
        List<Boolean> table = new ArrayList(n);
        
        for (int i = 2; i * i <= n; i++) {
            if (table.get(i)) {
                for (int j = i * 2; j <= n; j += i) {
                    table.set(i, false);
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (table.get(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    public static boolean isPrime(long n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)Math.sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }

    public static boolean checkPalindrome(String str){
        for(int i = 0 ; i < str.length()/2; ++i){
            if(str.charAt(i) != str.charAt(str.length()-1-i)){
                return false;
            }
        }
        return true;
    }

    // public static List<Long> generatePalindromes(long digits){
    //     List<Long> palindromes = new ArrayList<>();
        
    //     for(int i = 1 ; i <= digits; ++i){
    //         print(i+"");
    //         if(i == 1){
    //             for(long j = 1 ; j < Math.pow(10,i); ++j){
    //                 long p = j;
    //                 palindromes.add(j);
    //                 if(DEBUG) print(palindromes.get(palindromes.size()-1) + "");
    //             }
    //         }else if(i%2 == 0){ // even
    //             for(long j = 1 ; j < Math.pow(10,i)/2; ++j){
    //                 long p = j;
    //                 p = Long.valueOf(j + "" + reverse(j));
    //                 palindromes.add(p);
    //                 if(DEBUG) print(palindromes.get(palindromes.size()-1) + "");
    //             }
    //         }else if(i%2 == 1){ // odd
    //             for(long j = 1 ; j < Math.pow(10,i)/2; ++j){
    //                 long p = j;
    //                 for(long k = 0; k <= 9; ++k){
    //                     p = Long.valueOf(j + "" + k + "" + reverse(j));
    //                     palindromes.add(p);
    //                     if(DEBUG) print(palindromes.get(palindromes.size()-1) + "");
    //                 }
    //             }
    //         }
    //     }

    //     return palindromes;
    // }

    // public static long reverse(long number){
    //     long num = number;
    //     long reversed = 0;
    
    //     while(num != 0) {
            
    //         long digit = num % 10;
    //         reversed = reversed * 10 + digit;

    //         num /= 10;
    //     }

    //     return reversed;
    // }

    
}