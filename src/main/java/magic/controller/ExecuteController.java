package magic.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import magic.service.AsyncExecutor;
import magic.service.IService;
import magic.service.Slack;

@RestController
public class ExecuteController {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private ApplicationContext context;

	@Autowired
	private AsyncExecutor executor;

	@Autowired
	private Slack slack;

	@PostMapping( "/execute/{name}" )
	public Map<String, String> execute( @PathVariable String name, String command, String text ) {
		try {
			Object bean = context.getBean( name );

			Assert.isInstanceOf( IService.class, bean );

			String message = "Execute task manually: " + name;

			if ( StringUtils.isNotEmpty( command ) ) {
				message += String.format( "\n%s %s", command, StringUtils.defaultString( text ) );

			}

			log.error( message );

			executor.exec( ( IService ) bean );

			return slack.text( message );

		} catch ( Exception e ) {
			log.error( "", e );

			return slack.text( e.getMessage() );

		}
	}
}