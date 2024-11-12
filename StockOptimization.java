import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Stock {
    String companyName;
    double ebitda;
    double revenueGrowth;
    double marketCap;
    double price;
    double cost;
    double weightedScore;

    public Stock(String companyName, double ebitda, double revenueGrowth, double marketCap, double price, double cost) {
        this.companyName = companyName;
        this.ebitda = ebitda;
        this.revenueGrowth = revenueGrowth;
        this.marketCap = marketCap;
        this.price = price;
        this.cost = cost;
        this.weightedScore = 0;
    }

    public void calculateWeightedScore(double w1, double w2, double w3, double w4) {
        this.weightedScore = w1 * this.ebitda + w2 * this.revenueGrowth + w3 * this.marketCap - w4 * this.price;
    }
}


public class StockOptimization {
    private static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    private static void generateCSVOutput(List<Stock> selectedStocks, double totalCost) {
        String csvFile = "selected_stocks.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Write CSV header
            writer.println("Stock #,Company Name,Cost ($)");

            // Write each selected stock to CSV
            for (int i = 0; i < selectedStocks.size(); i++) {
                Stock stock = selectedStocks.get(i);
                if (stock.cost == 0.00)
                    continue;
                writer.printf("%d,%s,%.2f%n", i + 1, stock.companyName, stock.cost);
            }

            // Write total cost at the end
            writer.println();
            writer.printf("Total Cost,,%.2f%n", totalCost);

            System.out.println("CSV file generated successfully: " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the weight for EBITDA (0-1):");
        double w1 = scanner.nextDouble();

        System.out.println("Enter the weight for Revenue Growth (0-1):");
        double w2 = scanner.nextDouble();

        System.out.println("Enter the weight for Market Cap (0-1):");
        double w3 = scanner.nextDouble();

        System.out.println("Enter the weight for minimizing Price (0-1):");
        double w4 = scanner.nextDouble();

        List<Stock> stocks = new ArrayList<>();
        String csvFile = "stocks_cleaned.csv"; // Update with your file path

        // Read data from CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) { 
                    String companyName = fields[2];
                    double ebitda = parseDouble(fields[1]);
                    double revenueGrowth = parseDouble(fields[2]);
                    double marketCap = parseDouble(fields[3]);
                    double price = parseDouble(fields[4]);
                    double cost = parseDouble(fields[5]);
                    Stock stock = new Stock(companyName, ebitda, revenueGrowth, marketCap, price, cost);
                    stock.calculateWeightedScore(w1, w2, w3, w4);
                    stocks.add(stock);
                } else {
                    System.out.println("Skipping incomplete row: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set budget constraint
       
        System.out.println("Enter Your Budget\n");
        double budget = scanner.nextDouble();
        double totalCost = 0;
        List<Stock> selectedStocks = new ArrayList<>();

        // Sort stocks by weighted score in descending order
        Collections.sort(stocks, (a, b) -> Double.compare(b.weightedScore, a.weightedScore));

        // Select stocks based on the highest weighted score, respecting the budget
        for (Stock stock : stocks) {
            if (totalCost + stock.cost <= budget) {
                selectedStocks.add(stock);
                totalCost += stock.cost;
            }
        }
        Collections.sort(selectedStocks,(a,b)->Double.compare(a.cost,b.cost));
       // Output the results
        System.out.println("Optimal Stock Selection:");
        System.out.println("+---------+----------------------+------------+");
        System.out.println("| Stock # | Company Name         | Cost ($)   |");
        System.out.println("+---------+----------------------+------------+");
        for (int i = 0; i < selectedStocks.size(); i++) {
            Stock stock = selectedStocks.get(i);
            if (stock.cost == 0.00)
                continue;
            System.out.printf("| %7d | %-20s | %10.2f |\n", i + 1, stock.companyName, stock.cost);
        }
        System.out.println("+---------+----------------------+------------+");
        System.out.println("Total Cost: $" + String.format("%.2f", totalCost));
        generateCSVOutput(selectedStocks, totalCost);
    }


}