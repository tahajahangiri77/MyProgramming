package training;

public class Lasagna {
    public int expectedMinutesInOven(){

        return 40;
    }
    public int remainingMinutesInOven(int actueltime){

        return expectedMinutesInOven() - actueltime;
    }
    public int preparationTimeInMinutes(int layer){

        return layer * 2;
    }


    public int totalTimeInMinutes(int x, int y){
        return preparationTimeInMinutes(x) + y;
    }
    public static void main(String[] args){
        Lasagna lasagna = new Lasagna();
        lasagna.expectedMinutesInOven();
        System.out.println(lasagna.remainingMinutesInOven(30));

        System.out.println(lasagna.preparationTimeInMinutes(2));

        System.out.println(lasagna.totalTimeInMinutes(2, 20));
    }
}
