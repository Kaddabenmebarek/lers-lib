package org.research.kadda.labinventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.SynthesisLibraryOrderDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author: Kadda
 */

public class SynthesisService extends AbstractService {

	private static String responseMessage = "";

	/**
	 * @return all csorders
	 */
	public static List<SynthesisLibraryOrderDto> getAllSynthesisOrders() {
		List<SynthesisLibraryOrderDto> orders = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/synthesisorder/all");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "synthesisorders");
				if (jsonNode != null) {
					String csordersData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					orders = objectMapper.readValue(csordersData, new TypeReference<List<SynthesisLibraryOrderDto>>() {
					});
				}
			} else {
				System.out.println("No Response.");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	
	public static List<SynthesisLibraryOrderDto> getAllSynthesisOrdersInRange(Date startDate, Date endDate) {
		List<SynthesisLibraryOrderDto> synthesisorders = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		startDate = DateUtils.addMinutes(startDate, 1);
		startDate = new Timestamp(startDate.getTime());
		endDate = DateUtils.addMinutes(endDate, -1);
		endDate = new Timestamp(endDate.getTime());
		StringBuffer jsonData = new StringBuffer("{");

		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/synthesisorder/all/inrange");

		try {
			jsonData.append("\"startDate\":");
			jsonData.append("\"" + startDate.toString() + "\"");
			jsonData.append(",\"endDate\":");
			jsonData.append("\"" + endDate.toString() + "\"}");

			HttpEntity stringEntity = new StringEntity(jsonData.toString(), ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getEntity() != null) {
				String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
						.lines().collect(Collectors.joining("\n"));

				if (jsonResponse != null && !jsonResponse.isEmpty()) {
					System.out.println("Synthesis orders list received.");
				}

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "synthesisorders");
				if (jsonNode != null) {
					String synthesisordersData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					synthesisorders = objectMapper.readValue(synthesisordersData, new TypeReference<List<SynthesisLibraryOrderDto>>() {
					});
				}
			} else {
				System.out.println("No Response.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return synthesisorders;
	}

	/**
	 * @param csorder id
	 * @return order
	 */
	public static SynthesisLibraryOrderDto getSynthesisOrderById(Integer id) {
		SynthesisLibraryOrderDto cSOrder = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/synthesisorder/" + id);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String csorderData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
						.lines().collect(Collectors.joining("\n"));
				if (csorderData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					cSOrder = objectMapper.readValue(csorderData, new TypeReference<SynthesisLibraryOrderDto>() {
					});
				}
			} else {
				System.out.println("No Response.");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cSOrder;
	}

	/**
	 * @param SynthesisLibraryOrderDto cSOrderDto
	 * @return true if order saved
	 */
	public static boolean addSynthesisOrder(SynthesisLibraryOrderDto cSOrderDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/synthesisorder/add");
		try {
			String jsonData = JsonUtils.mapToJson(cSOrderDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("Order successfuly added.");
					}
				} else {
					System.out.println("No Response.");
				}
				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param String cSOrderId
	 * @return true if order removed
	 */
	public static boolean deleteSynthesisOrder(String synthesisOrderId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/synthesisorder/delete/" + synthesisOrderId);
		try {
			CloseableHttpResponse response = httpClient.execute(httpDelete);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "message");
						if (jsonNode != null) {
							responseMessage = jsonNode.toString();
						}
					}
				}
				return false;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}


	public static boolean updateSynthesisOrder(SynthesisLibraryOrderDto synthesisOrderDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(getLabinventoryServiceUri() + "/synthesisorder/update");

		try {
			String jsonData = JsonUtils.mapToJson(synthesisOrderDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPut.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPut);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "id");
						synthesisOrderDto.setId(jsonNode.asInt());
						System.out.println("Order successfuly updated.");
					}
				} else {
					System.out.println("No Response.");
				}

				return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getResponseMessage() {
		String ret = responseMessage;
		responseMessage = "";
		return ret;
	}

}