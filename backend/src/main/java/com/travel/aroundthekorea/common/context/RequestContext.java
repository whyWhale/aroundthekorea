package com.travel.aroundthekorea.common.context;

import org.springframework.stereotype.Component;

@Component
public class RequestContext<T> {
	private final ThreadLocal<T> contextData = new ThreadLocal<>();

	public void set(T data) {
		contextData.set(data);
	}

	public T get() {
		return contextData.get();
	}

	public void clear() {
		contextData.remove();
	}
}
