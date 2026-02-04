package Week_4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
public class FinancialTransaction {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> transaction = new ArrayList<>();


        while (true) {
            System.out.println("Financial Transaction Tracker");
            System.out.println("Choose 1 for alternate transaction");
            System.out.println("Choose 2 for yesterday transaction");
            System.out.println("Choose 3 for calculating total income and expenses");
            System.out.println("Choose 4 for exit");
            System.out.println("Choose 5 for print all");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("please enter transaction type: ");
                    String type = sc.nextLine();
                    System.out.println("please enter amount: ");
                    double amount = sc.nextDouble();
                    LocalDate date = LocalDate.now();
                    transaction.add(type + ", " + amount + ", " + date);
                    System.out.println("Transaction added successfully!");
                    break;
                case 2:
                    int count = 0;
                    LocalDate yesterday = LocalDate.now().minusDays(1);
                    for(String transaction1 : transaction){
                        String [] parts = transaction1.split(", ");
                        LocalDate date1 = LocalDate.parse(parts[0]);
                        if (parts[2].equals(yesterday.toString())){
                            count++;
                        }
                    }
                    System.out.println("Number of transactions: " + count);
                case 3:
                    double expenses = 0;
                    double income = 0;
                    for(String transaction1 : transaction){
                        String [] parts = transaction1.split(", ");
                        String type1 = parts[0];
                        double amount1 = Double.parseDouble(parts[1]);
                        if (type1.equals("sale")){
                            income+=amount1;
                        }
                        else if (type1.equals("expense")){
                            expenses+=amount1;
                        }
                        }System.out.println("Your income: "+ income + "," + "Your expenses: " + expenses);

                case 4:
                    System.out.println("Exiting tracker. Goodbye!");
                    sc.close();
                    return;
                case 5: // Print all
                    System.out.println("Print all:");
                    System.out.println(transaction);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");




            }

        }




    }
}
