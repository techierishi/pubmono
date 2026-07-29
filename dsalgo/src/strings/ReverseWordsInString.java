import java.util.*;
import java.lang.*;
import java.io.*;
public class ReverseWordsInString {
	public static void main (String[] args) {
	 Scanner sc = new Scanner(System.in);
	  int testCases = Integer.parseInt(sc.nextLine());
	  if(testCases > 0)
	  while(sc.hasNext()){
	    String inputStr = sc.nextLine();
	    reverseStr(inputStr.trim());
	  }
    }
    
    public static void reverseStr(String inputStr){
        String[] strArr = inputStr.split("\\.");
        int len = strArr.length;
        int i,j;
        for(i=0,j=len-1; i<=(len/2)-1;i++,j--){
            String tempStr = strArr[i];
            strArr[i] = strArr[j];
            strArr[j] = tempStr;
        }
        
        String joinedStr = String.join(".", strArr);
        System.out.println(joinedStr);
    }
}
