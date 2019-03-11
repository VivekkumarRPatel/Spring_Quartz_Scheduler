package org.baeldung.springquartz.basics.dispatcher;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	
	
	  private boolean success;
	    private String jobId;
	    private String jobGroup;
	    private String message;

	    public Response(boolean success, String message) {
	        this.success = success;
	        this.message = message;
	    }

	    public Response(boolean success, String jobId, String jobGroup, String message) {
	        this.success = success;
	        this.jobId = jobId;
	        this.jobGroup = jobGroup;
	        this.message = message;
	    }

}
