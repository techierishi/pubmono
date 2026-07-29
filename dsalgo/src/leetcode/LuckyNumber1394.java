class LuckyNumber1394 {
    public int findLucky(int[] arr) {
        
        Map<Integer,Integer> luckyMap = new HashMap<>();
        
        for(int i=0;i< arr.length;i++){
            if(luckyMap.containsKey(arr[i]))
                luckyMap.put(arr[i], luckyMap.get(arr[i])+1);
            else
                luckyMap.put(arr[i],1);
        }
        
        int largest= Integer.MIN_VALUE;
        int luckyNumber = -1;
        for(int key: luckyMap.keySet()){
            
            if(key == luckyMap.get(key)){
                if(key > largest){
                    luckyNumber = largest = key;
                }
            }
            
        }
        
        return luckyNumber;
    }
}
