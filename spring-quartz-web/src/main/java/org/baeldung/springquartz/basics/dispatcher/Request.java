package org.baeldung.springquartz.basics.dispatcher;

import java.time.LocalDateTime;
import java.time.ZoneId;



import org.springframework.lang.NonNull;



public class Request {

	@NonNull
	private String  jobName;
	
	/*@NotNull
    private LocalDateTime dateTime;*/

    /*@NotNull
    private ZoneId timeZone;*/
	
    public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
/*
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}*/

/*	public ZoneId getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(ZoneId timeZone) {
		this.timeZone = timeZone;
	}
*/    
}
