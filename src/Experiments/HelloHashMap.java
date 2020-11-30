package Experiments;

import java.util.HashMap;
import java.util.Scanner;

public class HelloHashMap {
    public static void main(String[] args) {

       //Hash Map
        HashMap<String, String> fruitToColour = new HashMap<>();
        fruitToColour.put("banana", "yellow");
        fruitToColour.put("pumpkin", "orange");
        fruitToColour.put("apple", "red");
        fruitToColour.put("apple", "green");
        fruitToColour.put("tomato", "red");

        // prompt the user to enter a fruit
        System.out.println("Enter a Fruit or press q to get lost");
        Scanner input = new Scanner(System.in);

        //the progress
        while (input.hasNextLine()) {
            String fruit = input.nextLine();
            if (fruit.equalsIgnoreCase("q")) break;

            String colour = fruitToColour.get(fruit);

            //print colour.
            System.out.println(colour);
        }

        //Key value pairs

        //Keys - Fruit
        String[] fruit = new String[] {
                "banana",
                "pumpkin",
                "apple",
                "apple",
                "tomato",
        };

        //Values - Colours
        String[] colours = new String[] {
                "yellow",
                "green",
                "red",
                "red",
        };

        //Lookup

        String key = "apple";

        for (int i = 0; i < fruit.length; i++) {
            if (fruit[i] == key) {
                System.out.println(colours[i]);
                break;
            }
        }
    }
}

