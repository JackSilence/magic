package magic.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@WebFilter
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
	private final Logger log = LoggerFactory.getLogger( this.getClass() );

	private static final String SESSION_KEY = "SID";

	public RequestLoggingFilter() {
		super.setBeforeMessagePrefix( "I [" );
		super.setAfterMessagePrefix( "O [" );

		super.setIncludeQueryString( true );
		super.setIncludeClientInfo( true );
	}

	@Override
	protected void beforeRequest( HttpServletRequest request, String message ) {
		HttpSession session = request.getSession( false );

		if ( session != null ) {
			MDC.put( SESSION_KEY, session.getId() );

		}

		log.trace( message );
	}

	@Override
	protected void afterRequest( HttpServletRequest request, String message ) {
		log.trace( message );

		MDC.remove( SESSION_KEY );
	}
}