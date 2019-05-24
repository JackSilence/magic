package magic.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class AsyncExecutor {
	@Async
	public void exec( IService service ) {
		service.exec();
	}
}