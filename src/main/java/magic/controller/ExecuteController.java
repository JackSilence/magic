package magic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import magic.service.IService;

@RestController
public class ExecuteController {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private ApplicationContext context;

	@PostMapping( value = "/execute/{name}" )
	public void execute( @PathVariable String name ) {
		Object bean = context.getBean( name );

		Assert.isInstanceOf( IService.class, bean );

		log.error( "Execute task manually: " + name );

		( ( IService ) bean ).exec();
	}
}