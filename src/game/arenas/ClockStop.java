package game.arenas;

import java.math.BigDecimal;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class ClockStop {
	
	// PRIVATE
	 private long fStart;
	 private long fStop;
	 private boolean fIsRun;
	 private boolean fHasBeenUsed;
		 
		 
	public void start(){
	   System.out.println("Game start and the time is on!");
	   
	   if (fIsRun) {
	     throw new IllegalStateException("Before the game is on again , stop!");
	   }
	   //reset both start and stop
	   fStart = System.nanoTime();
	   fStop = 0;
	   fIsRun = true;
	   fHasBeenUsed = true;
	 }
	
	 public String getTime() {
		 long temp = fStart;
		 stop(false);
		 String time = toString();
		 fStart = temp;
		 fIsRun = true;
		 fHasBeenUsed = true;
		 return time;
	 }
	
	 public void stop(boolean finished) {
	   if (!fIsRun) {
	     throw new IllegalStateException("If running can't stop!");
	   }
	   fStop = System.nanoTime();
	   fIsRun = false;
	   if(finished) System.out.println("The game is finished the time is stop!");
	 }
	
	 
	 @Override public String toString() {
		 /*
		  *return result.toString 
		  */
	   validateIsReadable();
	   StringBuilder result = new StringBuilder();
	   BigDecimal value = new BigDecimal(toValue());
	   value = value.divide(MILLION, 3, BigDecimal.ROUND_HALF_EVEN);
	   result.append(value);
	   result.append(" ms");
	   return result.toString();
	 }
	
	 public long toValue() {
		 /*
		  * return  fStop - fStart
		  */
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
	   if (fIsRun) {
	     String message = "Cannot read a stopwatch which is still running.";
	     throw new IllegalStateException(message);
	   }
	   if (!fHasBeenUsed) {
	     String message = "Cannot read a stopwatch which has never been started.";
	     throw new IllegalStateException(message);
	   }
	 } 
}