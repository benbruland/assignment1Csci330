import java.util.Comparator;

public class Date {
		String dateId;
		double openingPrice;
		double closingPrice;
		double highPrice;
		double lowPrice;
		int	   numShares;
		double adjClosing;
		boolean isCrazyDay;
		Date nextDay;
		public Date(String dateString, double opening, double closing, double high, double low, int shares, double adjClose) {
			dateId = dateString;
			openingPrice = opening;
			closingPrice = closing;
			highPrice = high;
			lowPrice = low;
			adjClosing = adjClose;
			numShares = shares;
			isCrazyDay = ((highPrice - lowPrice)/highPrice) >= 0.15;
		}
		
		public Date(String dateString, double opening, double closing, double high, double low, int shares, double adjClose, Date next) {
			dateId = dateString;
			openingPrice = opening;
			closingPrice = closing;
			highPrice = high;
			lowPrice = low;
			adjClosing = adjClose;
			numShares = shares;
			nextDay = next;
			isCrazyDay = ((highPrice - lowPrice)/highPrice) >= 0.15;
		} 
		public double getStockVariancePercent() {
			double variance = ((this.highPrice - this.lowPrice) / this.highPrice) * 100;
			return variance;
		}
		
		boolean equalTo(Date otherDay) {
			return (this.dateId.compareTo(otherDay.dateId) == 0);
		}
		
		String getDateId() {
			return dateId;
		}
	}