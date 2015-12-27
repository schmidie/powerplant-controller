package power_plant;

import java.time.ZonedDateTime;

/*
 * PowerTimeTuple representing a tuple of <power(Integer)|time(Date)> 
 * Note: This class is not responsible for the validation of the correctness of this tuple.
 * 		(For the validation see PowerProfile)
 */
public class PowerTimeTuple {

	private Integer power;
	private ZonedDateTime time;
	
	public PowerTimeTuple(){
	}
	public PowerTimeTuple(Integer power, ZonedDateTime time) {
		this.setPower(power);
		this.setTime(time);
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public ZonedDateTime getTime() {
		return time;
	}
	public void setTime(ZonedDateTime time) {
		this.time = time;
	}
}
