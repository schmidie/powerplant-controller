package power_plant;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.LinkedList;

/*
 * PowerProfile representing a Profile with:
 * 	start, end and a series of <time|power> tuples
 *  A valid PowerProfile needs to satisfy the following constrains:
 *  	* valid time-interval (start <= end)
 * 		* valid series of timestamp/power tuples within given time and power range
 * 			* E.g.: <time1|power1>,<time2|power2> => 
 * 				start <= time1 <= time2 <= end
 * 				-6000 <= power1 <= 6000 and -6000 <= power2 <= 6000  
 */
public class PowerProfile {
	
	private ZonedDateTime start = null;
	private ZonedDateTime end = null;
	// We use LinkedList for the power/time tuples as we need to keep the order
	private LinkedList<PowerTimeTuple> plan = new LinkedList<PowerTimeTuple>();
	
	
	/*
	 * PowerProfile Validations
	 */
	
	/*
	 * validateTuple(Integer power, ZonedDateTime dateTime)
	 * returns: validateTimeRange(dateTime) and validatePowerRange(power)
	 */
	private boolean validateTuple(Integer power, ZonedDateTime dateTime){
		if(!validatePowerRange(power) || !validateTimeRange(dateTime)){
			return false;
		}
		return true;
	}
	/*
	 * validateTimeRange(ZonedDateTime dateTime)
	 * returns: start <= dateTime <= end and last-dateTime <= dateTime
	 */
	private boolean validateTimeRange(ZonedDateTime dateTime){
		boolean retval = true;
		Instant dateTime_inst = dateTime.toInstant();
		if (start != null && end != null ){
			if (dateTime_inst.isBefore(start.toInstant()) || dateTime_inst.isAfter(end.toInstant())){
					retval = false;
			}
			else if(!plan.isEmpty() && (dateTime_inst.isBefore(plan.getLast().getTime().toInstant()))){
				retval = false;
			}
		}
		else{
			retval = false;
		}
		return retval;
	}
	/*
	 * validatePowerRange(Integer power)
	 * returns: -6000 <= power <= 6000
	 */
	private boolean validatePowerRange(Integer power){
		if(Math.abs(power) > 6000 ){
			return false;
		}
		return true;
	}
		
	/*
	 * PowerProfile Setter and Getter
	 * Note:
	 * 	We create only valid PowerProfiles! 
	 * 	So always check the given constrains setting start, end and the <time|power> tuples
	 */
	
	// Setter
	
	public boolean setStart(ZonedDateTime start) {
		// We can set a start if no end-date is set.
		if(this.end == null || start.toInstant().isBefore(this.end.toInstant())){
			this.start = start;
			return true;
		}
		return false;
	}
	public boolean setEnd(ZonedDateTime end) {
		// We can set a end if no start-date is set.
		if(this.start == null || end.toInstant().isAfter(this.start.toInstant())){
			this.end = end;
			return true;
		}
		return false;
	}
	public boolean pushNextPlanEntry(Integer power, ZonedDateTime dateTime) {
		if(validateTuple(power,dateTime)){
			PowerTimeTuple ptt = new PowerTimeTuple(power, dateTime);
			plan.add(ptt);
			return true;
		}
		return false;
	}
	
	// Getter
	
	public ZonedDateTime getStart() {
		return start;
	}
	public ZonedDateTime getEnd() {
		return end;
	}
	public PowerTimeTuple pullAndRemoveNextPlanEntry() {
		return plan.poll();
	}
	public LinkedList<PowerTimeTuple> getPlan(){
		return plan;
	}
	
}
