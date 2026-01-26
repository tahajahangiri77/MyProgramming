package training;

public class Lasagna {
    // TODO: define the 'expectedMinutesInOven()' method
    public int expectedMinutesInOven(){
        return 40;
    }
    // TODO: define the 'remainingMinutesInOven()' method
    public int remainingMinutesInOven(int actueltime){
        return expectedMinutesInOven() - actueltime;
    }
    // TODO: define the 'preparationTimeInMinutes()' method
    public int preparationTimeInMinutes(int layer){
        return layer * 2;
    }

    // TODO: define the 'totalTimeInMinutes()' method
    public int totalTimeInMinutes(int x, int y){
        return preparationTimeInMinutes(x) + y;
    }
    public static void main(String[] args){
        Lasagna lasagna = new Lasagna();
        lasagna.expectedMinutesInOven();
        System.out.println(lasagna.remainingMinutesInOven(30));

        System.out.println(lasagna.preparationTimeInMinutes(2));

        System.out.println(lasagna.totalTimeInMinutes(2, 20));
// => 10
    }
}
