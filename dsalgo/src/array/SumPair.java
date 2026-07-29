import java.util.*;
import java.lang.*;
import java.io.*;
public class SumPair {
	public static void main (String[] args) {
	    Scanner sc = new Scanner(System.in);
        int testCases = Integer.parseInt(sc.nextLine().trim());
	    int testCaseGroup = 3;
        // System.out.println("testCases "+testCases);

        for(int i=0; i<testCases; i++){
            int arrLen = Integer.parseInt(sc.nextLine().trim());
            // System.out.println("arrLen "+arrLen);
            int[] eachNum = Arrays
	                    .stream(sc.nextLine().split(" "))
	                    .mapToInt(Integer::parseInt)
	                    .toArray();
	                    
            int targetSum = Integer.parseInt(sc.nextLine().trim());
            // System.out.println("targetSum "+targetSum);
            
            testHere(eachNum, targetSum);
        }
	 }
	 
	 public static void testHere(int[] givenArray, int givenSum){
	     
        int arrLen = givenArray.length;
        boolean hasSum = false;
        int low=0,high=givenArray.length-1;
        while(low<high) {
            if(givenArray[low]+givenArray[high]>givenSum)
                high--;
            else if(givenArray[low]+givenArray[high]<givenSum)
                low++;
            else {
            	System.out.println(givenArray[low] +" "+givenArray[high]+" "+givenSum);
            	hasSum=true;
            	low++;
            }
        }
	     
	     if(!hasSum){
	         System.out.println("-1");

	     }
	     
	 }
}
