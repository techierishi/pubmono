public class ReverseArrayInGroups {
// Reverse Array In Groups
    public static void main(String args[]) {
      int[] inputArr = new int[]{10,20,30,40,50,60};
      int group = 2;
      
      reverseGroup(inputArr,group);
    }
    
    public static void reverseGroup(int[] iArray, int iGrp){
        int arrayLen = iArray.length;
        int grpMod = arrayLen / iGrp;
        
        System.out.println("grpMod "+grpMod);

        int start = 0;
        int end = iGrp -1;
        System.out.println("start end "+start+" "+end);

        for(int i=1; i<= grpMod; i++){
            System.out.println("loop -- "+i);
            System.out.println("start end -- "+start+" "+end);
            for(int j=start, k = end; j<= (start+end)/2; j++,k--){
                System.out.println("j k -- "+j+" "+k);
                int temp = iArray[k];
                iArray[k] = iArray[j];
                iArray[j] = temp;
            }
            start = end + 1;
            end = ( start + iGrp) - 1;
        }
        
        for(int ele: iArray){
            System.out.println(ele);
        }
    }
}
