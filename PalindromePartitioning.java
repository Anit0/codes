import java.util.*;

public class PalindromePartitioning 
{
    // A function to check if a substring s[left...right] is a palindrome
    private static boolean isPalindrome(String s, int left, int right) 
    {
        while (left < right) 
        {
            if (s.charAt(left) != s.charAt(right)) 
            {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Function to find the minimum palindrome partitions for a given string
    private static int minPalindromePartitions(String s) 
    {
        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        for (int i = 0; i < n; i++) 
        {
            if (isPalindrome(s, 0, i)) 
            {
                dp[i] = 1; // If s[0...i] is a palindrome, we need only one partition
            } 
            else 
            {
                for (int j = 0; j < i; j++) 
                {
                    if (isPalindrome(s, j + 1, i)) 
                    {
                        dp[i] = Math.min(dp[i], dp[j] + 1);
                    }
                }
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < T; t++) 
        {
            String s = scanner.nextLine();
            int result = minPalindromePartitions(s);
            System.out.println(result);
        }

        scanner.close();
    }
}
