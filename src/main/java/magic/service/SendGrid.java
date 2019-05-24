package magic.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;

@Service
public class SendGrid implements IMailService {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private Slack slack;

	@Value( "${sendgrid.api.key:}" )
	private String key;

	@Value( "${sendgrid.mail.to:}" )
	private String mail;

	@Override
	public void send( String subject, String content ) {
		try {
			Email from = new Email( "notice@heroku.com" ), to = new Email( mail );

			Request request = new Request();

			request.setMethod( Method.POST );
			request.setEndpoint( "mail/send" );
			request.setBody( new Mail( from, subject, to, new Content( "text/html", content ) ).build() );

			Response response = new com.sendgrid.SendGrid( key ).api( request );

			log.info( "Subject: {}, status: {}", subject, response.getStatusCode() );

			slack.message( String.format( "[%s] -> OK!", subject ) );

		} catch ( IOException e ) {
			throw new RuntimeException( "Failed to send (SendGrid): " + subject, e );

		}
	}
}