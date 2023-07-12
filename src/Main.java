import java.util.List;

public class Main {

  /**
   *     0 1 2  3 4 5
   * A = 3 5 7 11 5 8
   * B = 5 7 11 10 5 8
   *

   *
   * BS = [(1,0)(1,4)(2,1)(3,2)(4,0)(4,4)(5,5)]
   * Problematic = [(1,0)(1,4)(4,0)(4,4)] => B = [0, 4]
   * Disjoint = [(2,1)(3,2)(5,5)]
   *     0 1 2 3  4 5
   * A = 3 5 7 11 5 8
   * B = 1 7 11 10 5 8
   * BS = [(1,4)(2,1)(3,2)(4,4)(5,5)]
   * Problematic = [(1,0)(1,4)(4,0)(4,4)] => B = [0, 4]
   * Disjoint = [(2,1)(3,2)(5,5)]
   */
  public static int beautifulPairs(List<Integer> A, List<Integer> B) {
    // Write your code here
    int result = 0;


    return result;
  }



  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}
