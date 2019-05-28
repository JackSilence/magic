package magic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import net.gpedro.integrations.slack.SlackMessage;

@Service
@EnableAsync
public class AsyncExecutor {
	private final Logger log = LoggerFactory.getLogger( getClass() );

	@Autowired
	private Slack slack;

	@Async
	public void exec( IService service ) {
		try {
			service.exec();
		} catch ( RuntimeException e ) {
			log.error( "", e );

			slack.call( new SlackMessage( e.getMessage() ) );

		}
	}
}