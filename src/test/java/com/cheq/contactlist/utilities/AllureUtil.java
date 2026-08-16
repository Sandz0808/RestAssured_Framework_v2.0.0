package com.cheq.contactlist.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * ============================================================
 * API ALLURE UTILITY
 * ============================================================

 * Enterprise utility class used for enriching Allure Reports.

 * Supported Features:

 * ✔ Test Steps
 * ✔ Step + Dynamic Attachment
 * ✔ Plain Text
 * ✔ Request Body
 * ✔ Response Body
 * ✔ Endpoint
 * ✔ Headers
 * ✔ Exceptions
 * ✔ Environment Properties
 * ✔ Executor Information

 * ============================================================
 */
public final class AllureUtil {

	private static final Logger log =
			LoggerUtil.getLogger(AllureUtil.class);

	private AllureUtil() {
	}

	// ==========================================================
	// STEP ONLY
	//
	// Usage:
	//
	// ApiAllureUtil.stepAndValidation(
	//      "Verify Status Code");
	//
	// ==========================================================

	public static void steps(String message) {

		Allure.step(message);

	}



	// ==========================================================
	// TEXT
	//
	// Usage:
	//
	// ApiAllureUtil.attachText(
	//      "Status Code",
	//      "Expected : 200");
	//
	// ==========================================================

	public static void attachText(
			String title,
			String message) {

		Allure.addAttachment(
				title,
				"text/plain",
				new ByteArrayInputStream(
						message.getBytes(StandardCharsets.UTF_8)),
				".txt");

	}

	// ==========================================================
	// REQUEST BODY
	//
	// Usage:
	//
	// ApiAllureUtil.attachRequest(payload);
	//
	// ==========================================================

	public static void attachRequest(Object request) {

		if (request == null) {
			return;
		}

		Allure.addAttachment(
				"Request Body",
				"application/json",
				request.toString());

	}

	// ==========================================================
	// RESPONSE BODY
	//
	// Usage:
	//
	// ApiAllureUtil.attachResponse(response);
	//
	// ==========================================================

	public static void attachResponse(Response response) {

		if (response == null) {
			return;
		}

		Allure.addAttachment(
				"Response Body",
				"application/json",
				response.getBody().prettyPrint());

	}

	// ==========================================================
	// ENDPOINT
	//
	// Usage:
	//
	// ApiAllureUtil.attachEndpoint(CREATE_USER);
	//
	// ==========================================================

	public static void attachEndpoint(String endpoint) {

		attachText(
				"Endpoint",
				endpoint);

	}

	// ==========================================================
	// HEADERS
	//
	// Usage:
	//
	// ApiAllureUtil.attachHeaders(headers);
	//
	// ==========================================================

	public static void attachHeaders(
			Map<String, String> headers) {

		StringBuilder builder = new StringBuilder();

		headers.forEach((k, v) ->
				builder.append(k)
						.append(" : ")
						.append(v)
						.append("\n"));

		attachText(
				"Headers",
				builder.toString());

	}

	// ==========================================================
	// EXCEPTION
	//
	// Usage:
	//
	// ApiAllureUtil.attachException(exception);
	//
	// ==========================================================

	public static void attachException(Throwable throwable) {

		if (throwable == null) {
			return;
		}

		attachText(
				"Exception",
				throwable.toString());

	}

	// ==========================================================
	// ENVIRONMENT
	//
	// Usage:
	//
	// ApiAllureUtil.writeAllureEnvironment(env);
	//
	// ==========================================================

	public static void writeAllureEnvironment(
			Map<String, String> env) {

		File envFile =
				new File("target/allure-results/environment.properties");

		envFile.getParentFile().mkdirs();

		try (FileWriter writer = new FileWriter(envFile)) {

			for (Map.Entry<String, String> entry : env.entrySet()) {

				writer.write(
						entry.getKey()
								+ "="
								+ entry.getValue()
								+ System.lineSeparator());

			}

			log.info("Allure environment.properties created.");

		} catch (IOException e) {

			log.error(
					"Failed to create Allure environment file.",
					e);

			org.testng.Assert.fail(
					"Failed to create Allure environment file.");

		}

	}

	// ==========================================================
	// EXECUTOR
	//
	// Usage:
	//
	// ApiAllureUtil.writeAllureExecutor();
	//
	// ==========================================================

	public static void writeAllureExecutor() {

		File executor =
				new File("target/allure-results/executor.json");

		executor.getParentFile().mkdirs();

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode json = mapper.createObjectNode();

		json.put(
				"name",
				System.getProperty("user.name") + " (Local Machine)");

		json.put("type", "Maven");
		json.put("url", "");
		json.put("buildName", "Rest Assured API Framework");
		json.put("buildUrl", "");
		json.put("reportUrl", "");

		try {

			mapper.writerWithDefaultPrettyPrinter()
					.writeValue(executor, json);

			log.info("Allure executor.json created.");

		} catch (IOException e) {

			log.error(
					"Unable to create executor.json",
					e);

		}

	}


	/**
	 * ============================================================
	 * ALLURE ATTACHMENT TYPES
	 * ============================================================

	 * Defines the supported attachment types that can be used
	 * together with ApiAllureUtil.stepAndValidation().

	 * ============================================================
	 */
	public enum AttachmentType {

		TEXT,
		REQUEST,
		RESPONSE,
		ENDPOINT,
		HEADERS,
		EXCEPTION

	}

	// ==========================================================
	// STEP WITH ATTACHMENT
	//
	// Usage:
	//
	// ApiAllureUtil.stepAndValidation(
	//      "Verify Response",
	//      AttachmentType.RESPONSE,
	//      response);
	//
	// ApiAllureUtil.stepAndValidation(
	//      "Verify Request",
	//      AttachmentType.REQUEST,
	//      payload);
	//
	// ApiAllureUtil.stepAndValidation(
	//      "Verify Endpoint",
	//      AttachmentType.ENDPOINT,
	//      CREATE_USER);
	//
	// ==========================================================

	@SuppressWarnings("unchecked")
	public static void stepAndValidation(
			String message,
			AttachmentType attachmentType,
			Object data) {

		Allure.step(message, () -> {

			if (attachmentType == null || data == null) {
				return;
			}

			switch (attachmentType) {

				case TEXT ->
						attachText(
								"Information",
								String.valueOf(data));

				case REQUEST ->
						attachRequest(data);

				case RESPONSE ->
						attachResponse((Response) data);

				case ENDPOINT ->
						attachEndpoint(String.valueOf(data));

				case HEADERS ->
						attachHeaders((Map<String, String>) data);

				case EXCEPTION ->
						attachException((Throwable) data);

				default ->
						attachText(
								"Unsupported Attachment",
								String.valueOf(data));

			}

		});

	}

	/**
	 * ==========================================================
	 * SANITIZED RESPONSE
	 * ==========================================================
	 */
	public static void attachSanitizedResponse(Response response) {

		if (response == null) {
			return;
		}

		Allure.addAttachment(
				"Response Body",
				"application/json",
				LogSanitizerUtil.sanitize(
						response.getBody().prettyPrint()));
	}

	/**
	 * ==========================================================
	 * SANITIZED HEADER
	 * ==========================================================
	 */
	public static void attachSanitizedHeader(
			String header,
			String value) {

		if (value == null) {
			return;
		}

		if (header.equalsIgnoreCase("Authorization")
				|| header.equalsIgnoreCase("Authorization Token")
				|| header.equalsIgnoreCase("Authentication Token")
				|| header.equalsIgnoreCase("JWT Token")) {

			value = "🙈🙃🛡️✨👻🥷";
		}

		Allure.addAttachment(
				header,
				"text/plain",
				value);
	}


}