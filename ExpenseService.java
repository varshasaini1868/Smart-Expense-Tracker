package com.expense;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExpenseService {

    public static void registerUser(User user) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            ps.setString(3, hashedPassword);

            ps.executeUpdate();

            System.out.println("User Registered Successfully!");

        } catch (Exception e) {
            System.out.println("Registration Failed!");
            e.printStackTrace();
        }
    }
    public static boolean loginUser(String email, String password) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, email);
        String hashedPassword = PasswordUtil.hashPassword(password);
        ps.setString(2, hashedPassword);

        var rs = ps.executeQuery();

        if (rs.next()) {
            return true;  
        } else {
            return false; 
        }

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public static void addTransaction(String email, double amount, String type, String category) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        // get user id first
        String getUserId = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps1 = conn.prepareStatement(getUserId);
        ps1.setString(1, email);

        var rs = ps1.executeQuery();

        int userId = 0;
        if (rs.next()) {
            userId = rs.getInt("id");
        }

        String sql = "INSERT INTO transactions (user_id, amount, type, category, date) VALUES (?, ?, ?, ?, CURDATE())";
        PreparedStatement ps2 = conn.prepareStatement(sql);

        ps2.setInt(1, userId);
        ps2.setDouble(2, amount);
        ps2.setString(3, type);
        ps2.setString(4, category);

        ps2.executeUpdate();

        System.out.println("Transaction Added Successfully!");

    } catch (Exception e) {
        System.out.println("Failed to Add Transaction!");
        e.printStackTrace();
    }
}

    @SuppressWarnings({"unused", "unused"})
    static void addTransaction11(String email, double amount, String income, String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void addTransaction1(String email, double amount, String expense, String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void addTransaction111(String email, double amount, String income, String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void viewReport(String email) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        
        String getUserId = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps1 = conn.prepareStatement(getUserId);
        ps1.setString(1, email);
        var rs1 = ps1.executeQuery();

        int userId = 0;
        if (rs1.next()) {
            userId = rs1.getInt("id");
        }

        
        String incomeQuery = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'Income'";
        PreparedStatement ps2 = conn.prepareStatement(incomeQuery);
        ps2.setInt(1, userId);
        var rs2 = ps2.executeQuery();

        double totalIncome = 0;
        if (rs2.next()) {
            totalIncome = rs2.getDouble(1);
        }

        
        String expenseQuery = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'Expense'";
        PreparedStatement ps3 = conn.prepareStatement(expenseQuery);
        ps3.setInt(1, userId);
        var rs3 = ps3.executeQuery();

        double totalExpense = 0;
        if (rs3.next()) {
            totalExpense = rs3.getDouble(1);
        }

        double savings = totalIncome - totalExpense;

        System.out.println("----- Monthly Report -----");
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Net Savings: " + savings);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void highestSpendingCategory(String email) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        // get user id
        String getUserId = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps1 = conn.prepareStatement(getUserId);
        ps1.setString(1, email);
        var rs1 = ps1.executeQuery();

        int userId = 0;
        if (rs1.next()) {
            userId = rs1.getInt("id");
        }

        String sql = "SELECT category, SUM(amount) as total " +
                     "FROM transactions " +
                     "WHERE user_id = ? AND type = 'Expense' " +
                     "GROUP BY category " +
                     "ORDER BY total DESC LIMIT 1";

        PreparedStatement ps2 = conn.prepareStatement(sql);
        ps2.setInt(1, userId);

        var rs2 = ps2.executeQuery();

        if (rs2.next()) {
            System.out.println("Highest Spending Category: " + rs2.getString("category"));
            System.out.println("Total Spent: " + rs2.getDouble("total"));
        } else {
            System.out.println("No expenses found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void checkBudget(String email, double budgetLimit) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        String getUserId = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps1 = conn.prepareStatement(getUserId);
        ps1.setString(1, email);
        var rs1 = ps1.executeQuery();

        int userId = 0;
        if (rs1.next()) {
            userId = rs1.getInt("id");
        }

        String expenseQuery = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'Expense'";
        PreparedStatement ps2 = conn.prepareStatement(expenseQuery);
        ps2.setInt(1, userId);
        var rs2 = ps2.executeQuery();

        double totalExpense = 0;
        if (rs2.next()) {
            totalExpense = rs2.getDouble(1);
        }

        if (totalExpense > budgetLimit) {
            System.out.println("⚠ Warning: You have crossed your budget!");
            System.out.println("Total Expense: " + totalExpense);
        } else {
            System.out.println("You are within your budget.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void setBudget(String email, double budget) {
    
    throw new UnsupportedOperationException("Unimplemented method 'setBudget'");
}
public static void checkBudget(String email) {
    
    throw new UnsupportedOperationException("Unimplemented method 'checkBudget'");
}

    static void setBudget(String email, double budget) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void setBudget(String email, double budget) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void checkBudget(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public static void monthlyComparison(String email) {
    try {
        Connection conn = DatabaseConnection.getConnection();

        
        String getUserId = "SELECT id FROM users WHERE email = ?";
        PreparedStatement ps1 = conn.prepareStatement(getUserId);
        ps1.setString(1, email);
        var rs1 = ps1.executeQuery();

        int userId = 0;
        if (rs1.next()) {
            userId = rs1.getInt("id");
        }

        
        String currentMonthQuery =
            "SELECT SUM(amount) FROM transactions " +
            "WHERE user_id = ? AND type = 'Expense' " +
            "AND MONTH(date) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(date) = YEAR(CURRENT_DATE())";

        PreparedStatement ps2 = conn.prepareStatement(currentMonthQuery);
        ps2.setInt(1, userId);
        var rs2 = ps2.executeQuery();

        double currentMonthExpense = 0;
        if (rs2.next()) {
            currentMonthExpense = rs2.getDouble(1);
        }

        
        String lastMonthQuery =
            "SELECT SUM(amount) FROM transactions " +
            "WHERE user_id = ? AND type = 'Expense' " +
            "AND MONTH(date) = MONTH(CURRENT_DATE() - INTERVAL 1 MONTH) " +
            "AND YEAR(date) = YEAR(CURRENT_DATE() - INTERVAL 1 MONTH)";

        PreparedStatement ps3 = conn.prepareStatement(lastMonthQuery);
        ps3.setInt(1, userId);
        var rs3 = ps3.executeQuery();

        double lastMonthExpense = 0;
        if (rs3.next()) {
            lastMonthExpense = rs3.getDouble(1);
        }

        System.out.println("Current Month Expense: " + currentMonthExpense);
        System.out.println("Last Month Expense: " + lastMonthExpense);

        if (lastMonthExpense > 0) {
            double percentage =
                ((currentMonthExpense - lastMonthExpense) / lastMonthExpense) * 100;

            System.out.println("Change: " + percentage + "%");
        } else {
            System.out.println("No data for last month.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
