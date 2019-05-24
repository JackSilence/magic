package magic.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import magic.service.AsyncExecutor;
import magic.service.Slack;

@RestController
public class ExecuteController {
	@Autowired
	private AsyncExecutor executor;

	@Autowired
	private Slack slack;

	@PostMapping( "/execute/{name}" )
	public Map<String, String> execute( @PathVariable String name ) {
		executor.exec( name );

		return slack.text( "OK" );
	}
}