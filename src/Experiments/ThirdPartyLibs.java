package Experiments;

import org.apache.commons.math3.random.Well512a;

public class ThirdPartyLibs {

    public static void main(String[] args) {

        Well512a rng = new Well512a(10);
        for (int i = 0; i < 10; i++) {
            System.out.println(rng.nextInt());
        }
    }
}
