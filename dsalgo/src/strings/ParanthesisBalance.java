import java.util.*;
import java.lang.*;
import java.io.*;
public class ParanthesisBalance {
	public static void main (String[] args) {
	  Scanner sc = new Scanner(System.in);
	  int testCases = Integer.parseInt(sc.nextLine());
	  if(testCases > 0)
	  while(sc.hasNext()){
	    String inputStr = sc.nextLine();
	    checkParanthesis(inputStr.trim());
	  }
	  
    }
    
    
    public static void checkParanthesis(String inputStr){
        if(inputStr.isEmpty())
        System.out.println("not balanced");

        Stack<Character> charStack = new Stack<>();
        for(int i=0;i<inputStr.length(); i++){
          char chAt = inputStr.charAt(i);
        
          if(chAt == '(' || chAt == '{' || chAt == '[' ){
              charStack.push(chAt);
          }
        
          if(chAt == ')' || chAt == '}' || chAt == ']' ){
        
            if (charStack.isEmpty()){
                System.out.println("not balanced");
                return;
            }
        
            
            char lastChar =  charStack.peek();
        
            if( (chAt == ')' && lastChar == '(') || (chAt == '}' && lastChar == '{') || (chAt == ']' && lastChar == '[')){
                charStack.pop();
            }else{
                System.out.println("not balanced");
                return;

            }
            
         }
        }
        
        if(charStack.isEmpty()){
            System.out.println("balanced");
        }else{
           	System.out.println("not balanced");
        }
    }
}
