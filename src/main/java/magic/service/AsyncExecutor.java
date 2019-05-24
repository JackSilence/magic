package magic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@EnableAsync
public class AsyncExecutor {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	@Autowired
	private ApplicationContext context;

	@Async
	public void exec( String name ) {
		Object bean = context.getBean( name );

		Assert.isInstanceOf( IService.class, bean );

		log.error( "Execute task manually: " + name );

		( ( IService ) bean ).exec();
	}
}