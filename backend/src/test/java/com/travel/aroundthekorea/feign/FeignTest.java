package com.travel.aroundthekorea.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.travel.aroundthekorea.batch.spot.api.SpotClient;
import com.travel.aroundthekorea.batch.spot.api.dto.response.PublicDataResponse;

@SpringBootTest
public class FeignTest {

	private final String PREFIX_PATH = "/areaBasedSyncList1";

	@Autowired
	private SpotClient spotClient;
	private WireMockServer wireMockServer;

	@BeforeEach
	public void setUp() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
		wireMockServer.start();
		WireMock.configureFor("localhost", 8089);
	}

	@AfterEach
	public void tearDown() {
		wireMockServer.stop();
	}

	@DisplayName("지정 예외가 아닌 상태코드이면, RetryableException 예외를 발생시킨다.")
	@Test
	public void failIncorrectStatusCode() {
		/// given
		stubFor(buildMapping("")
			.inScenario("RetryableException 시나리오")
			.whenScenarioStateIs(STARTED)
			.willReturn(aResponse().withStatus(513))
			.willSetStateTo("Failure"));
		// when
		// then
		Assertions.assertThatThrownBy(() -> {
			send();
		}).isInstanceOf(RuntimeException.class);
	}

	@DisplayName("잘못된 경로로 요청하면 , InvalidRequestException 예외를 발생시킨다.")
	@Test
	public void failInvalidPath() {
		//given
		String invalidPath = "invalid_path";
		buildMapping(invalidPath);
		stubFor(buildMapping(invalidPath)
			.inScenario("InvalidRequestException 시나리오")
			.whenScenarioStateIs("Failure")
			.willReturn(aResponse().withStatus(404))
			.willSetStateTo("Failure"));
		// when
		// then
		Assertions.assertThatThrownBy(() -> {
			send();
		}).isInstanceOf(RuntimeException.class);
	}

	private MappingBuilder buildMapping(String path) {
		return get(urlPathEqualTo(PREFIX_PATH + path))
			.withQueryParam("numOfRows", equalTo("100"))
			.withQueryParam("pageNo", equalTo("1"))
			.withQueryParam("MobileOS", equalTo("ETC"))
			.withQueryParam("MobileApp", equalTo("atk"))
			.withQueryParam("_type", equalTo("JSON"))
			.withQueryParam("serviceKey", equalTo("serviceKey"))
			.withQueryParam("arrange", equalTo("D"));
	}

	private PublicDataResponse send() {
		return spotClient.getSpots(100, 1, "ETC", "atk", "JSON", "serviceKey", "D");
	}
}
