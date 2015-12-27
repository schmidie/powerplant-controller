package power_plant;

import java.time.ZonedDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Tests for PowerProfile Class
 * 
 * PowerProfile represents a Profile with 
 * 	start-time, end-time and a series of time/power tuples
 * 
 * This tests validate the constrains for a valid PowerProfile
 * constrains:
 * 		* valid time-interval
 * 		* valid series of timestamp/power tuples within given time and power range
 */
public class PowerProfileTest {

	private PowerProfile profile;

	@Before
	public void setUp() throws Exception {
		profile = new PowerProfile();		
	}
	@After
	public void tearDown() throws Exception {
	}

	
	/*
	 *  Tests for valid time interval
	 *  constrains:
	 *  	start is before end ( start <= end )
	 *  	end is after start (end >= start)
	 *  NOTE: 
	 *  	* there is no constrain that the time-interval is in future!
	 *  	* if start == end nothing will happen:)
	 */
	
	// start > end => false 
	@Test
	public void setStartDateAfterEndDateShouldReturnFalse() {
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.minusNanos(1);
		
		profile.setEnd(end);
		Assert.assertEquals(false, profile.setStart(start));
		
	}
	
	// end < start => false
	@Test
	public void setEndDateBeforeStartDateShouldReturnFalse() {
		ZonedDateTime end = ZonedDateTime.now();
		ZonedDateTime start = end.plusNanos(1);
		
		profile.setStart(start);
		Assert.assertEquals(false, profile.setEnd(end));
	}
	
	// start < end => true
	@Test
	public void setEndDateAfterStartDateShouldReturnTrue() {
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusNanos(1);
		profile.setStart(start);
		Assert.assertEquals(true, profile.setEnd(end));
	}
	
	@Test
	public void setEndDateWithNoStartDateShouldReturnTrue() {
		ZonedDateTime end = ZonedDateTime.now();
		Assert.assertEquals(true, profile.setEnd(end));
	}
	@Test
	public void setStartDateWithNoEndDateShouldReturnTrue() {
		ZonedDateTime start = ZonedDateTime.now();
		Assert.assertEquals(true, profile.setEnd(start));
	}

	
	/*
	 * Tests for valid time/power tuples
	 * constrains:
	 * 		* power is between -6000 and 6000
	 * 		* time is after or at start and before or at end (start <= time <= end )
	 * 		* time is after or at last_time from last time/power-tuple
	 * 			* E.g.: <time1|power1>,<time2|power2> => start <= time1 <= time2 <= end 
	 */
	@Test
	public void IfNoStartIsSetPushPlanEntryShouldReturnFalse(){
		// set end but no start
		ZonedDateTime end = ZonedDateTime.now().plusDays(1);
		profile.setEnd(end);
		Assert.assertEquals(false, profile.pushNextPlanEntry(5000, end.minusHours(1)));
	}
	@Test
	public void IfNoEndIsSetPushPlanEntryShouldReturnFalse(){
		// set start but no end
		ZonedDateTime start = ZonedDateTime.now();
		profile.setStart(start);
		Assert.assertEquals(false, profile.pushNextPlanEntry(5000, start.plusDays(1)));
	}
	
	//  <power|time> with power > 6000 => false
	@Test
	public void AddTimePowerTupleWithTooMuchPositivePowerShouldReturnFalse(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		Assert.assertEquals(false, profile.pushNextPlanEntry(6001, start.plusDays(1)));
	}
	
	//  <power|time> with power < 6000 => false
	@Test
	public void AddTimePowerTupleWithTooMuchNegativePowerShouldReturnFalse(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		Assert.assertEquals(false, profile.pushNextPlanEntry(-6001, start.plusDays(1)));
	}
	
	// start, end <power|time> with end < time => false
	@Test
	public void AddTimePowerTupleWithTimeAfterEndShouldReturnFalse(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		Assert.assertEquals(false, profile.pushNextPlanEntry(5000, end.plusNanos(1)));
	}
	
	// start, end <power|time> with time < start => false
	@Test
	public void AddTimePowerTupleWithTimeBeforeStartShouldReturnFalse(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		Assert.assertEquals(false, profile.pushNextPlanEntry(5000, start.minusNanos(1)));
	}
	
	// start, end <power|time> with start <= time <= end and -6000 < power < 6000 => true
	@Test
	public void AddCorrectTimePowerTupleShouldReturnTrue(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		Assert.assertEquals(true, profile.pushNextPlanEntry(6000, start));
	}
	
	// <power1|time1>, <power2|time2> with time2 < time1 => false
	@Test
	public void AddSecondPowerTupleWithTimeBeforeFirstPowerTupleShouldReturnFalse(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		profile.pushNextPlanEntry(6000, start.plusNanos(1));
		Assert.assertEquals(false, profile.pushNextPlanEntry(-6000, start));
	}
	
	// <power1|time1>, <power2|time2> with time1 < time2 => true
	@Test
	public void AddSecondPowerTupleWithTimeAfterFirstPowerTupleShouldReturnTrue(){
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = start.plusDays(5);
		profile.setStart(start);
		profile.setEnd(end);
		profile.pushNextPlanEntry(6000, start);
		Assert.assertEquals(true, profile.pushNextPlanEntry(-6000, start.plusNanos(1)));
	}
	
}
