package com.fermin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncFunctionsExecutorPool {

	@Bean
	public TaskExecutor getAsyncFunctionsExecutorPool() {
		//return new SimpleAsyncTaskExecutor();
		ThreadPoolTaskExecutor pool= new ThreadPoolTaskExecutor();
		pool.setMaxPoolSize(10);
		return pool;
	}
}
