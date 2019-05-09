import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.Collections;

public class Company {
	public String tickerSymbol;
	
	private HashMap<String, Date> dateMap;
	private HashMap<Date, String> splitDays; //Maps date object to split string ex: "3:1"
	private List<Date> crazyDays;
	
	public Company(String ticker) {
		tickerSymbol = ticker;
		dateMap = new HashMap<String, Date>();
		splitDays = new HashMap<Date, String>();
		crazyDays = new ArrayList<Date>();
	}
	
	
	public void addDate(String dateId, double opening, double high, double low, double closing, int numShares, double adjClosing) {
		// Date(String dateID, double opening, double high, double low, double closing,
		// int numShares, double adjClosing)
		Date newDate = new Date(dateId, opening, high, low, closing, numShares, adjClosing);
		dateMap.put(dateId, newDate);
		if (newDate.isCrazyDay) {
			crazyDays.add(newDate);
		}
	}
	
	public String getDateId(Date day) {
		return day.dateId;
	}
	
	public void addDate(String dateId, double opening, double closing, double high, double low, int shares, double adjClose, Date next) {
		Date newDay = new Date(dateId, opening, closing, high, low, shares, adjClose, next);
		dateMap.put(dateId, newDay);
		
		if (newDay.isCrazyDay) {
			crazyDays.add(newDay);
		}
		
		if (next != null) {
			dateMap.put(next.dateId, next);
			if (next.isCrazyDay) {
				crazyDays.add(next);
			}
			
			double closeToOpen = (closing / next.openingPrice);
			double stockSplitThreshold = 0.15;
			if (Math.abs(closeToOpen - 3.0) < stockSplitThreshold) {
				if (next.closingPrice > newDay.closingPrice) {
					splitDays.put(next, "3:1");
				} else {
					splitDays.put(newDay, "3:1");
				}
			}
			if (Math.abs(closeToOpen - 1.5) < stockSplitThreshold) {
				if (next.closingPrice > newDay.closingPrice) {
					splitDays.put(next, "3:2");
				} else {
					splitDays.put(newDay, "3:2");
				}
			}
			if (Math.abs(closeToOpen - 2.0) < stockSplitThreshold) {
				if (next.closingPrice > newDay.closingPrice) {
					splitDays.put(next, "2:1");
				} else {
					splitDays.put(newDay, "2:1");
				}
			}
			
		}
		
	}
	
	public void printCrazyDays() { //Prints the crazyDays array list
		int totalCrazyDays = crazyDays.size();
		if (!crazyDays.isEmpty()) {
			Date craziestDay = crazyDays.get(0);
			for (Date day : crazyDays) {
				double stockPercentage = day.getStockVariancePercent();
				if (stockPercentage > craziestDay.getStockVariancePercent()) {
					craziestDay = day;
				}
				System.out.printf("Crazy Day = %s\t%.2f\n",day.dateId, stockPercentage);
			}
			System.out.printf("The craziest day: %s  %.2f\n",craziestDay.dateId, craziestDay.getStockVariancePercent());
		} 
		System.out.print("Total crazy days = " + totalCrazyDays + "\n");
	}
	
	public Date getDate(String dateId) {
		if (dateMap.containsKey(dateId)) {
			return dateMap.get(dateId);
		} 
		return null;
	}
	
	public void printStockSplits() { //Prints the SplitDays array list
		if (!splitDays.isEmpty()) {
			Set<Date> keys = splitDays.keySet();
			for (Date day: keys) {
				Date nextDay = day.nextDay;
				if (nextDay != null) {
					System.out.printf("%s split on %s %.2f ---> %.2f\n", splitDays.get(day),day.dateId,day.closingPrice, nextDay.openingPrice);
				}
			}
		}
		System.out.print("Total number of splits = " + splitDays.size() + "\n");
	}
	
	
}







