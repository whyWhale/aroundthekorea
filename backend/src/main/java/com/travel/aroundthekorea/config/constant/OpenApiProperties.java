package com.travel.aroundthekorea.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

	private final Info info;
	private final Security security;

	@ConstructorBinding
	public OpenApiProperties(Info info, Security security) {
		this.info = info;
		this.security = security;
	}

	public Info getInfo() {
		return info;
	}

	public Security getSecurity() {
		return security;
	}

	public record Info(String title, String version, String description) {
		@ConstructorBinding
		public Info {
		}

	}

	public record Security(String scheme, String type, String schemeName, String description) {
		@ConstructorBinding
		public Security {
		}

	}
}
