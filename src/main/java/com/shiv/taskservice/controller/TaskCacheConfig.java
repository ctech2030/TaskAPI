package com.shiv.taskservice.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;

@Component
public class TaskCacheConfig {

	public static final String TASK_CACHE = "task-cache";

	@Bean
	public Config cacheConfig() {
		return new Config().setInstanceName("hazelcast-instance")
				.addMapConfig(new MapConfig().setName(TASK_CACHE).setTimeToLiveSeconds(3000)
						.setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)
								.setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE).setSize(200)));

	}

}
