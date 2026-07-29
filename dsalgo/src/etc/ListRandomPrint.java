import java.util.*;

class ListRandomPrint {
  public static void main(String[] args) {

    List<Integer> myList = new ArrayList<>();
    myList.add(1);
    myList.add(2);
    myList.add(3);
    myList.add(4);
    myList.add(5);
    while(myList.size() > 0){
      Random rnd = new Random();
      int rndInt = rnd.nextInt(myList.size());
      System.out.println(myList.get(rndInt));
      myList.remove(rndInt);
    }

  }
}
