import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;


public class StockParser {
	static HashMap<String, Company> Companies;
	static List<String> tickers;
	public StockParser() {
		Companies = new HashMap<String, Company>();
		tickers = new ArrayList<String>();
		String fileName = "Stockmarket-1990-2015.txt";
		File stockData = new File(fileName);
		try {
			Scanner stockScanner = new Scanner(stockData);
			String delimiter = "\\s+"; //Will split the string on ANY whitespace char
			Company currentCompany = null;
			while (stockScanner.hasNextLine()) {
				String currentDateLine = stockScanner.nextLine();
				String[] currentDateData = currentDateLine.split(delimiter);
				String previousDateLine = stockScanner.nextLine();
				String[] previousDateData = previousDateLine.split(delimiter);
				String tickerSymbol = currentDateData[0];
				if (!Companies.containsKey(tickerSymbol)) {
					Company newCompany = new Company(tickerSymbol);
					Companies.put(tickerSymbol, newCompany);
					tickers.add(tickerSymbol);
					currentCompany = newCompany;
				}
//				String prevTicker = previousDateData[0];
//				if (!Companies.containsKey(prevTicker)) {
//					Company newCompany = new Company(prevTicker);
//					Companies.put(prevTicker, newCompany);
//					currentCompany = newCompany;
//				}
				String currentDateID = currentDateData[1];
				double currentOpening = Double.parseDouble(currentDateData[2]);
				double currentHigh = Double.parseDouble(currentDateData[3]);
				double currentLow = Double.parseDouble(currentDateData[4]);
				double currentClose = Double.parseDouble(currentDateData[5]);
				int	   currentNumShares = Integer.parseInt(currentDateData[6]);
				double currentAdjClose = Double.parseDouble(currentDateData[7]);
				
				String previousDateID = previousDateData[1];
				double previousOpening = Double.parseDouble(previousDateData[2]);
				double previousHigh = Double.parseDouble(previousDateData[3]);
				double previousLow = Double.parseDouble(previousDateData[4]);
				double previousClose = Double.parseDouble(previousDateData[5]);
				int	   previousNumShares = Integer.parseInt(previousDateData[6]);
				double previousAdjClose = Double.parseDouble(previousDateData[7]);
				//public void addDate(String dateId, double opening, double closing, double high, double low, int shares, double adjClose, Date next)
				Date currentDate = new Date(currentDateID, currentOpening, currentClose, currentHigh, currentLow, currentNumShares, currentAdjClose);
				Companies.get(tickerSymbol).addDate(previousDateID, previousOpening, previousClose, previousHigh, previousLow, previousNumShares, previousAdjClose, currentDate);
			}
			stockScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File: " + fileName + " was not found.");
		}
	}
	
	private void printDivider(int numChars, String divider) {
		for (int i = 0; i < numChars; i++) {
			System.out.print(divider);
		}
		System.out.print("\n");
	}
	
	public static void main(String args[]) {
		int dividerLength = 22;
		StockParser parser = new StockParser();
		Collections.sort(parser.tickers);
		for (String ticker: parser.tickers) {
			System.out.print("\nProcessing " + ticker + "\n");
			parser.printDivider(dividerLength, "=");
			Company companyToDisplay = Companies.get(ticker);
			companyToDisplay.printCrazyDays();
			System.out.print("\n");
			companyToDisplay.printStockSplits();
		}
	}
}