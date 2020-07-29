package game.arenas;

import java.math.BigDecimal;

public class Stopper {
	
	// PRIVATE
	 private long fStart;
	 private long fStop;
	
	 private boolean fIsRunning;
	 private boolean fHasBeenUsedOnce;
		 
		 
	public void start(){
	   System.out.println("timer started! Game start");
	   
	   if (fIsRunning) {
	     throw new IllegalStateException("Must stop before calling start again.");
	   }
	   //reset both start and stop
	   fStart = System.nanoTime();
	   fStop = 0;
	   fIsRunning = true;
	   fHasBeenUsedOnce = true;
	 }
	
	 public String getTime() {
		 long temp = fStart;
		 stop(false);
		 String time = toString();
		 fStart = temp;
		 fIsRunning = true;
		 fHasBeenUsedOnce = true;
		 return time;
	 }
	
	 public void stop(boolean finished) {
	   if (!fIsRunning) {
	     throw new IllegalStateException("Cannot stop if not currently running.");
	   }
	   fStop = System.nanoTime();
	   fIsRunning = false;
	   if(finished) System.out.println("timer stopped! the finished - GameOver");
	 }
	
	 
	 @Override public String toString() {
	   validateIsReadable();
	   StringBuilder result = new StringBuilder();
	   BigDecimal value = new BigDecimal(toValue());//scale is zero
	   //millis, with 3 decimals:
	   value = value.divide(MILLION, 3, BigDecimal.ROUND_HALF_EVEN);
	   result.append(value);
	   result.append(" ms");
	   return result.toString();
	 }
	
	 public long toValue() {
	   validateIsReadable();
	   return  fStop - fStart;
	 }
	 
	 
	 
	 /** Converts from nanos to millis. */
	 private static final BigDecimal MILLION = new BigDecimal("1000000");
	 
	 /**
	  Throws IllegalStateException if the watch has never been started,
	  or if the watch is still running.
	 */
	 private void validateIsReadable() {
	   if (fIsRunning) {
	     String message = "Cannot read a stopwatch which is still running.";
	     throw new IllegalStateException(message);
	   }
	   if (!fHasBeenUsedOnce) {
	     String message = "Cannot read a stopwatch which has never been started.";
	     throw new IllegalStateException(message);
	   }
	 } 
}
