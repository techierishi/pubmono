import java.util.*;
import java.lang.*;
import java.io.*;

class CheckAnagram {
	public static void main (String[] args){
	 Scanner sc = new Scanner(System.in);
	  int testCases = Integer.parseInt(sc.nextLine());
	  if(testCases > 0)
	  while(sc.hasNext()){
	    String inputStr = sc.nextLine();
	    checkAnagram(inputStr.trim());
	  }
	}
	
	public static void checkAnagram(String inputStr){
	    String[] splitedStr = inputStr.split("\\s+");
	    char tempArray1[] = splitedStr[0].toCharArray(); 
	    char tempArray2[] = splitedStr[1].toCharArray(); 
	    Arrays.sort(tempArray1);
	    Arrays.sort(tempArray2);
	    String str1 = String.valueOf(tempArray1);
	    String str2 = String.valueOf(tempArray2);
	    if(str1.equals(str2)){
	        System.out.println("YES");
	    }else{
	        System.out.println("NO");
	    }

	}
}
