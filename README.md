# Stock Optimization Algorithm

This project implements an algorithm for stock portfolio optimization based on several financial indicators. The goal is to select a set of stocks that maximizes a weighted score (based on EBITDA, Revenue Growth, Market Cap, and Price) while staying within a given budget.

## Features

- **Stock Selection**: The algorithm calculates a weighted score for each stock based on provided weights for key financial indicators such as EBITDA, Revenue Growth, Market Cap, and Price.
- **Budget Constraint**: The algorithm selects stocks such that their total cost does not exceed a given budget.
- **CSV Output**: The selected stocks and their respective costs are saved to a CSV file for further analysis.

## How It Works

1. **Input**:
   - Weights for EBITDA, Revenue Growth, Market Cap, and Price (each between 0 and 1).
   - Budget for the total cost of the stock portfolio.
   
2. **Processing**:
   - Reads stock data from a CSV file (`stocks_cleaned.csv`).
   - Calculates a weighted score for each stock based on the specified weights.
   - Sorts the stocks in descending order of their weighted score.
   - Selects stocks within the budget constraint, starting with those with the highest weighted scores.

3. **Output**:
   - Displays the selected stocks with their costs in a formatted table.
   - Saves the selected stocks and total cost to a CSV file (`selected_stocks.csv`).

## Prerequisites

Before running the algorithm, ensure that you have the following:
- **Java**: Version 8 or later is required to compile and run the program.
- **CSV File**: The [stocks_cleaned.csv](https://github.com/SavyaSanchi-Sharma/OOPs-Project/blob/main/stocks_cleaned.csv) file containing the stock data. You can replace the file with your dataset.

### Sample Format of [stocks_cleaned.csv](https://github.com/SavyaSanchi-Sharma/OOPs-Project/blob/main/stocks_cleaned.csv)

### Code 

[Click Here for StockOptimization.java](https://github.com/SavyaSanchi-Sharma/OOPs-Project/blob/main/StockOptimization.java)

## Acknowledgment 

**Author:** Savya Sanchi Sharma  
**Registration Number:** 23BDS052
