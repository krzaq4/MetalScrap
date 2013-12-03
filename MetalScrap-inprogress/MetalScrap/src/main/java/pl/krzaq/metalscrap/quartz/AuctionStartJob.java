package pl.krzaq.metalscrap.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import pl.krzaq.metalscrap.quartz.jobs.CheckStartedAuctions;



public class AuctionStartJob extends QuartzJobBean {


	
	CheckStartedAuctions checkStartedAuctions ;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx)
			throws JobExecutionException {
		
		checkStartedAuctions.checkIfStarted();
		
	}

	public CheckStartedAuctions getCheckStartedAuctions() {
		return checkStartedAuctions;
	}

	public void setCheckStartedAuctions(CheckStartedAuctions checkStartedAuctions) {
		this.checkStartedAuctions = checkStartedAuctions;
	}

	
	
	
	
}
