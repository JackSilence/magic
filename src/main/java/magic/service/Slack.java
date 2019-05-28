package magic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;

@Service
public class Slack {
	@Value( "${slack.webhook.url:}" )
	private String url;

	public void call( SlackMessage message ) {
		new SlackApi( url ).call( message );
	}
}