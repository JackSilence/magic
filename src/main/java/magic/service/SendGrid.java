package magic.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;

import magic.util.Utils;

@Service
public class SendGrid implements IMailService {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Value( "${sendgrid.api.key:}" )
	private String key;

	@Value( "${sendgrid.mail.to:}" )
	private String mail;

	@Value( "${slack.webhook.url:}" )
	private String url;

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

			slack( subject );

		} catch ( IOException e ) {
			throw new RuntimeException( "Failed to send (SendGrid): " + subject, e );

		}
	}

	private void slack( String subject ) {
		Map<String, String> map = new HashMap<>();

		map.put( "text", String.format( "[%s]發送成功!", subject ) );

		log.info( "Slack: {}", Utils.getEntityAsString( org.apache.http.client.fluent.Request.Post( url ).bodyString( new Gson().toJson( map ), ContentType.APPLICATION_JSON ) ) );
	}
}