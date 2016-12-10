/**
 * Created by Noam on 12/10/2016.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class AlgorithmTests {

    @Test
    public void  test1(){
        String s = String.join("\n"
            , "4"
            , "Jerusalem"
            , "1 2"
            , "Haifa"
            , "70 80"
            , "Beer Sheva"
            , "1000 4000"
            , "Ra'anana"
            , "33333 4444"
            , ""
        );
        System.out.println(s);
        ArrayList<Node> nodes = Main.createNodes(new Scanner(s));
        RandomRoute randomRoute = new RandomRoute(nodes);
        randomRoute.buildRoute();
        randomRoute.setRouteLength();
        Main.resetNodes(nodes);
        System.out.println("Route length: " + randomRoute.getRouteLength());
    }
}
