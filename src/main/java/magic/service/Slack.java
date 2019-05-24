package magic.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import magic.util.Utils;

@Service
public class Slack {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Value( "${slack.webhook.url:}" )
	private String url;

	public void message( String text ) {
		log.info( "Status: {}", Utils.getEntityAsString( Request.Post( url ).bodyString( new Gson().toJson( text( text ) ), ContentType.APPLICATION_JSON ) ) );
	}

	public Map<String, String> text( String text ) {
		Map<String, String> map = new HashMap<>();

		map.put( "text", text );

		return map;
	}
}