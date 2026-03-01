package com.expense;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=====Expense Tracker=====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();  
//============Registation========================================================
        if (choice == 1) {

            System.out.println("Enter Name:");
            String name = sc.nextLine();

            System.out.println("Enter Email:");
            String email = sc.nextLine();
            if(!email.contains("@")){
                System.out.println("Invalid Email Format!");
                continue;
            }

            System.out.println("Enter Password:");
            String password = sc.nextLine();
            if(password.length() < 6) {
                System.out.println("Password must be at least 6 characters!");
                continue;
            }

            User user = new User(name, email, password);
            ExpenseService.registerUser(user);
//==============================Login========================================================
        } else if (choice == 2) {

            System.out.println("Enter Email:");
            String email = sc.nextLine();
            if(!email.contains("@")){
                System.out.println("Invalid Email Format!");
                continue;
            }

            System.out.println("Enter Password:");
            String password = sc.nextLine();
            if(password.length() < 6) {
                System.out.println("Password must be at least 6 characters!");
                continue;
            }

            boolean success = ExpenseService.loginUser(email, password);

            if (success) {
                System.out.println("Login Successful!");
                
//==============================Dashboard========================================================
       while (true) {
        System.out.println("\n=====Dashboard=====");     
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Total Report");
        System.out.println("4. Highest Spending Category");
        System.out.println("5. Set Budget");
        System.out.println("6. check Budget");
        System.out.println("7. Monthly Comparison");
        System.out.println("8. Logout");
        System.out.print("Choose option: ");

        int option = sc.nextInt();
        sc.nextLine();
        //Add Income or Expense
        if (option == 1 || option == 2) {
        System.out.println("Enter Amount:");
        double amount = sc.nextDouble();
        if(amount <= 0) {
            System.out.println("Amount must be greater than 0!");
            continue;
        }
        sc.nextLine();

        System.out.println("Enter Category:");
        String category = sc.nextLine();

        if (option == 1) {
            ExpenseService.addTransaction(email, amount, "Income", category);
        }
         else   {
            ExpenseService.addTransaction(email, amount, "Expense", category);
        } 
    
        } 
        //View Report
        else if (option == 3) {
            ExpenseService.viewReport(email);
        } 
        //Highest Category
        else if (option == 4) {
            ExpenseService.highestSpendingCategory(email);
        }
        //Set Budget
        else if(option == 5) {
            System.out.println("Enter Your Monthly Budget:");
            double budget = sc.nextDouble();
            sc.nextLine();

            ExpenseService.setBudget(email, budget);
        }
        //Check Budget
        else if (option == 6) {
            ExpenseService.checkBudget(email);
        }
        //Monthly Comparison
        else if(option ==7){
            ExpenseService.monthlyComparison(email);
        }
        //Logout
        else if (option == 8) {
            System.out.println("Logged Out Successfully!");
            break;
        }
        else {
            System.out.println("Invalid Option");
        }

    } 
}
else{
    System.out.println("Invalid Email or Password!");
   }
}
//==============================Exit========================================================
        else if (choice == 3) {
            System.out.println("Exiting Program...");
            break;
        } else {
            System.out.println("Invalid Choice");
        }
    }
        sc.close();
    }
}
