package runner;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.matcher.ResponseAwareMatcher;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

import utils.HelperUtil;

public class Driver {

	private String uri;
	private ValidatableResponse response;
	private String body;
	private String contentType;
	private String clientId;
	private String clientSecret;
	private String correlationId;
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";
	private static final String RANDOM = "random";

	public void sendRequest(String reqestMethod) throws FileNotFoundException {
		if (GET.equalsIgnoreCase(reqestMethod)) {
			//RequestSpecification request = RestAssured.given();
			RequestSpecification request = setSSLConfig();
			request.header("Content-Type", "application/json");
			request.header("x-correlation-id", "1234");
			request.header("client_secret", "1234");
			request.header("client_id", "1234");
			// response = given().when().delete(uri).then();
			response = request.get(uri).then();
		} else if (POST.equalsIgnoreCase(reqestMethod)) {
			//RequestSpecification request = RestAssured.given();
			RequestSpecification request = setSSLConfig();
			request.header("Content-Type", this.contentType);
			request.header("x-correlation-id", "abcd123");
			request.header("client_secret", "1234");
			request.header("client_id", "1234");
			request.body(this.body);
			response = request.post(uri).then();
		} else if (PUT.equalsIgnoreCase(reqestMethod)) {
			//RequestSpecification request = RestAssured.given();
			RequestSpecification request = setSSLConfig();
			request.header("Content-Type", this.contentType);
			request.header("x-correlation-id", "abcd123");
			request.header("client_secret", "1234");
			request.header("client_id", "1234");
			request.body(this.body);
			response = request.put(uri).then();
		} else if (DELETE.equalsIgnoreCase(reqestMethod)) {
			//RequestSpecification request = RestAssured.given();
			RequestSpecification request = setSSLConfig();
			request.header("Content-Type", "application/json");
			request.header("x-correlation-id", "1234");
			request.header("client_secret", "1234");
			request.header("client_id", "1234");
			// response = given().when().delete(uri).then();
			response = request.delete(uri).then();
		}
	}

	public void createURI(String serviceName) {
		this.uri = serviceName;
	}

	public void addEndpoint(String endpoint) {
		this.uri = uri.concat(endpoint);
	}

	public void expectedResponse(int responseCode) {
		response.statusCode(responseCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void validateResponse(String filePath) throws IOException {
		ObjectMapper objectmapper = new ObjectMapper();
		Map<String, String> map = objectmapper.readValue(HelperUtil.getJsonStringFromPath(filePath), Map.class);

		for (Map.Entry<String, String> entrySet : map.entrySet()) {
			String key = entrySet.getKey();
			final String value = entrySet.getValue();
			response.body(key, new ResponseAwareMatcher() {
				public Matcher matcher(ResponseBody response) throws Exception {
					return equalTo(value);
				}
			});
		}
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setclientId(String clientId) {
		this.clientId = clientId;
	}
	public void setclientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public void setcorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	

	public void setRequestBody(String filePath) throws IOException {
		String jsonString = HelperUtil.getJsonStringFromPath(filePath);
		if (jsonString != null && jsonString.contains(RANDOM)) {
			jsonString = getManipulatedString(jsonString);
		}
		this.body = jsonString;
	}

	@SuppressWarnings("unchecked")
	private String getManipulatedString(String jsonString) throws IOException {
		JSONObject jsonObject = new JSONObject();
		ObjectMapper objectmapper = new ObjectMapper();

		Map<String, String> map = objectmapper.readValue(jsonString, Map.class);
		for (Map.Entry<String, String> entrySet : map.entrySet()) {
			String value = entrySet.getValue();
			if (value.contains(RANDOM)) {
				entrySet.setValue(HelperUtil.getRandomValue(value));
			}
			jsonObject.put(entrySet.getKey(), entrySet.getValue());
		}
		return jsonObject.toJSONString();
	}
	private RequestSpecification setSSLConfig() throws FileNotFoundException {
		RequestSpecification request = RestAssured.given().config(
				RestAssured
				  .config()
				  .sslConfig(
				    new SSLConfig().with().trustStoreType("JKS").and()
				      .trustStore(ResourceUtils.getFile("classpath:certs/apix-servicenow-sapi.jks"), "njclabs"))
				);
		return request;
	}

}
