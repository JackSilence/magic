package magic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import magic.service.AsyncExecutor;
import magic.service.IService;
import net.gpedro.integrations.slack.SlackMessage;

@RestController
public class ExecuteController {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private ApplicationContext context;

	@Autowired
	private AsyncExecutor executor;

	@PostMapping( "/execute/{name}" )
	public JsonObject execute( @PathVariable String name, String text ) {
		String message;

		try {
			Object bean = context.getBean( name );

			Assert.isInstanceOf( IService.class, bean );

			message = "Execute task manually: " + name;

			log.error( message );

			executor.exec( ( IService ) bean );

		} catch ( Exception e ) {
			log.error( "", e );

			message = e.getMessage();

		}

		return new SlackMessage( message ).prepare();
	}
}