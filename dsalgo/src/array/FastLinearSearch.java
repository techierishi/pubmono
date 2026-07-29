public class FastLinearSearch{
    
    public static void main() {

        int iarr[] = {4,5,8,2,6,0,1,7};
		int searchItem = 0;
		for(int i = 0, j = iarr.length -1; i < iarr.length && j>= 0; i++, j-- ){
		    System.out.println(" Looping : "+i);
		    if(iarr[i] == searchItem  ){ 
		        System.out.println("Found att : "+ i );
		        break;
		        
		    }else if(iarr[j] == searchItem ){
		        System.out.println("Found att :  "+j);
		        break;
		    }
		}
		
	}
}
