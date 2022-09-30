package hw1;

public class UberDriver {
	
	 /**
	 * Maximum number of passengers allowed in the vehicle at one time.
	 */
	 public static final int MAX_PASSENGERS = 4;

	 /**
	 * Cost to operate the vehicle per mile.
	 */
	 public static final double OPERATING_COST = 0.5;
	 
	 private double mileRate;
	 private double minuteRate;
	 private int totalMiles;
	 private int totalMinutes;
	 private int passengers;
	 private double totalCredits;
	 private double operatingCost;
	 private double profit;
	 private double avgProfitPerHour;
	 
	 /**
	   * Constructs an UberDriver with the given per-mile rate and per-minute rate.
	   * @param givenPerMileRate
	   * @param givenPerMinuteRate
	   */
	 public UberDriver(double givenPerMileRate, double givenPerMinuteRate) {
		 mileRate = givenPerMileRate;
		 minuteRate = givenPerMinuteRate;
	 }
	
	 /**
	  * Returns the total miles driven since this UberDriver was constructed.
	  * @return
	  */
	 public int getTotalMiles() {
		 return totalMiles;
	 }
	
	 /**
	  * Returns the total minutes driven since this UberDriver was constructed.
	  * @return
	  */
	 public int getTotalMinutes() {
		 return totalMinutes;
	 }
	 
	 /**
	  * Drives the vehicle for the given number of miles over the given number of minutes.
	  * @param miles
	  * @param minutes
	  */
	 public void drive(int miles, int minutes) {
		 totalMiles += miles;
		 totalMinutes += minutes;
		 operatingCost  = OPERATING_COST * totalMiles;
		 totalCredits += (passengers * (miles * mileRate + minutes * minuteRate));
		 profit = totalCredits - operatingCost;
		 avgProfitPerHour = 60 * profit / totalMinutes;
	 }
	 
	 /**
	  * Simulates sitting in the vehicle without moving for the given number of minutes.
	  * Equivalent to drive(0, minutes).
	  * @param minutes
	  */
	 public void waitAround(int minutes) {
		 //totalMinutes += minutes;
		drive(0, minutes);
	 }
	 
	 /**
	  * Drives the vehicle for the given number of miles at the given speed. Equivalent to
	  * drive(miles, m), where m is the actual number of minutes required, rounded to the
	  * nearest integer.
	  * @param miles
	  * @param averageSpeed
	  * @return false
	  */
	 public void driveAtSpeed(int miles, double avgSpeed) {
		 //totalMiles += miles;
		 //totalMinutes += Math.round(miles / avgSpeed * 60);
		 drive(miles, (int)Math.round(miles / avgSpeed * 60));
	 }
	 
	 /**
	  * Returns the number of passengers currently in the vehicle.
	  * @return
	  */
	 public int getPassengerCount() {
		 //return Math.min(MAX_PASSENGERS, passengers);
		 return Math.max(0, Math.min(MAX_PASSENGERS, passengers));
	 }
	 
	 /**
	  * Increases the passenger count by 1, not exceeding MAX_PASSENGERS.
	  * @return false
	  */
	 public void pickUp() {
		 passengers++;
	 }
	 
	 /**
	  * Decreases the passenger count by 1, not going below zero.
	  * @return false
	  */
	 public void dropOff() {
		 passengers--;
		 
	 }
	 
	 /**
	  * Returns this UberDriver's total credits (money earned before deducting operating costs).
	  * @return
	  */
	 public double getTotalCredits() {
		 return totalCredits;
	 }
	 
	 /**
	  * Returns this UberDriver's profit (total credits, less operating costs).
	  * @return
	  */
	 public double getProfit() {
		 return profit;
	 }
	 
	 /**
	  * Returns this UberDriver's average profit per hour worked.
	  * @return
	  */
	 public double getAverageProfitPerHour() {
		 return avgProfitPerHour;
	 }
	 
//	 public static void main(String[] args) {
//		 UberDriver d = new UberDriver(1.00, 0.20);
//		 d.drive(10, 25);
//		    d.drive(7,  35);
//		    System.out.println(d.getTotalMiles()); // expected 17
//	 }
	 
}