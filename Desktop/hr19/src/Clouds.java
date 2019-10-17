import java.io.*;
import java.util.*;

public class Clouds {
    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        List<Integer> path = new ArrayList<Integer>();
        int i = 0;
        int j = 1;
        int jump = 0;
        while (j <= c.length -1 ){
            if (c[i] == 0 && c[j] == 1 ){
                path.add(i);
                jump++;
            }
            else {
                i+= 1;
                j+=1;

            }

        }


    return c.length - jump ;


    }

    public static void main(String[] args) throws IOException {
        int n = 7;
        int[] c = {0, 1, 0, 0, 1, 0}; //3
//         int[] c = {0, 0, 1, 0, 0, 1, 0}; // 4

        int result = jumpingOnClouds(c);
        System.out.println(result);


    }
}
