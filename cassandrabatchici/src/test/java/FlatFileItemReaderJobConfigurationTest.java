import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({ "classpath*:test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FlatFileItemReaderJobConfigurationTest {

	/** Logger. */
	/** Lines count from input file. */
	private static final int COUNT = 20;
	/** JobLauncherTestUtils Bean. */
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	/** Launch Test. */
	@Test
	public void launchJob() throws Exception {
		// Job parameters
		Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
		//jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
/*		 jobParametersMap.put("input.file", new
		 JobParameter("file:D:/chipnpin/flow/output/6feb.txt"));
		jobParametersMap.put("output.file", new
	     JobParameter("file:D:/chipnpin/flowcsv/6feb.csv"));*/

		// launch the job
		 JobParameters jobParameters = new JobParameters(jobParametersMap);
		 JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		//JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		// JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1", jobParameters);
		// assert job run status
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

		// output step summaries
		/*for (StepExecution step : jobExecution.getStepExecutions()) {
			LOG.debug(step.getSummary());
			assertEquals("Read Count mismatch, changed input?", COUNT, step.getReadCount());
		}*/
	}
}
