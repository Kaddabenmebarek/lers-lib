package org.research.kadda.labinventory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.research.kadda.labinventory.data.FavoriteDto;
import org.research.kadda.labinventory.data.GroupInstrumentDto;
import org.research.kadda.labinventory.data.InstrumentDeputyDto;
import org.research.kadda.labinventory.data.InstrumentDto;
import org.research.kadda.labinventory.data.InstrumentEmployeeGroupDto;
import org.research.kadda.labinventory.data.InstrumentGroupDto;
import org.research.kadda.labinventory.data.InstrumentPriorityUsersDto;
import org.research.kadda.labinventory.data.JsonUtils;
import org.research.kadda.labinventory.data.ResOptionLinkDto;
import org.research.kadda.labinventory.data.ReservationDto;
import org.research.kadda.labinventory.data.ReservationHistoryDto;
import org.research.kadda.labinventory.data.ReservationUsageDto;
import org.research.kadda.labinventory.data.ResoptionDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author: Kadda
 */

public class LabInventoryService extends AbstractService {

	private static String responseMessage = "";
	
	public static List<InstrumentDto> getAllInstruments() {
		List<InstrumentDto> instruments = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrument/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(), Charset.forName("UTF8"))).lines()
								.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "instruments");
				if (jsonNode != null) {
					String instrumentsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instruments = objectMapper.readValue(instrumentsData, new TypeReference<List<InstrumentDto>>() {
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

		return instruments;
	}

	public static InstrumentDto getInstrumentById(Integer id) {
		InstrumentDto instrument = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrument/" + id);

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String instrumentData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
						.lines().collect(Collectors.joining("\n"));
				if (instrumentData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					instrument = objectMapper.readValue(instrumentData, new TypeReference<InstrumentDto>() {
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

		return instrument;
	}

	public static int addInstrument(InstrumentDto instrumentDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/instrument/add");
		int id = 0;
		try {
			String jsonData = JsonUtils.mapToJson(instrumentDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "instId");
						id = jsonNode.asInt();
						System.out.println("Instrument successfuly added.");
					}
				} else {
					System.out.println("No Response.");
				}
				return id;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static boolean addReservationOptionLink(ResOptionLinkDto resOptionLinkDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/resoptionlink/add");
		try {
			String jsonData = JsonUtils.mapToJson(resOptionLinkDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("Reservation Option Link successfuly added.");
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

	public static boolean addGroupInstrument(GroupInstrumentDto groupInstrumentDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/groupinstrument/add");
		try {
			String jsonData = JsonUtils.mapToJson(groupInstrumentDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("Group Instrument Link successfuly added.");
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
	
	public static boolean updateInstrument(InstrumentDto instrumentDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(getLabinventoryServiceUri() + "/instrument/update");
		try {
			String jsonData = JsonUtils.mapToJson(instrumentDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPut.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPut);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("Instrument successfuly updated.");
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

	public static boolean deleteInstrument(String instId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/instrument/delete/" + instId);
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

	public static List<String> getGroupNames() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> groupNames = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/group/names");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "groupNames");
				if (jsonNode != null) {
					String groupNamesData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					groupNames = objectMapper.readValue(groupNamesData, new TypeReference<List<String>>() {
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

		return groupNames;
	}

	public static List<InstrumentGroupDto> getAllInstrumentGroups() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<InstrumentGroupDto> instrumentGroups = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrumentGroup/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "instrumentGroups");
				if (jsonNode != null) {
					String instrumentGroupsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentGroups = objectMapper.readValue(instrumentGroupsData,
							new TypeReference<List<InstrumentGroupDto>>() {
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

		return instrumentGroups;
	}

	public static List<Integer> getInstrumentIdsByGroupId(Integer id) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<Integer> instrumentIds = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrumentGroup/category/" + id.toString());

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentIds = objectMapper.readValue(jsonData, new TypeReference<List<Integer>>() {
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

		return instrumentIds;
	}

	public static List<Integer> getGroupIdsByInstrumentId(Integer id) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<Integer> groupIds = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrument/" + id.toString() + "/groupids");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					groupIds = objectMapper.readValue(jsonData, new TypeReference<List<Integer>>() {
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

		return groupIds;
	}

	/**
	 * Returns a list of reservations that are there in the time range given in
	 * arguments To avoid boundary conflicts, 1 minute is added to the startDate and
	 * 1 minute is substract from the endDate
	 * 
	 * @param instrIds
	 * @param reservationId
	 * @param startDate
	 * @param endDate
	 * @param strict
	 * @return
	 */
	public static List<ReservationDto> getReservationsByInstrIdsAndDateRange(List<Integer> instrIds,
			Integer reservationId, Date startDate, Date endDate, boolean strict) {
		List<ReservationDto> reservations = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		startDate = DateUtils.addMinutes(startDate, 1);
		startDate = new Timestamp(startDate.getTime());
		endDate = DateUtils.addMinutes(endDate, -1);
		endDate = new Timestamp(endDate.getTime());
		StringBuffer jsonData = new StringBuffer("{");

		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/reservation/instruments");

		try {
			// build json payload
			jsonData.append("\"instrIds\":");
			jsonData.append(JsonUtils.mapToJson(instrIds));
			jsonData.append(",\"reservationId\":");
			jsonData.append("" + String.valueOf(reservationId) + "");
			jsonData.append(",\"strict\":");
			jsonData.append("" + Boolean.toString(strict) + "");
			jsonData.append(",\"startDate\":");
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
					System.out.println("Reservation list received.");
				}

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "reservations");
				if (jsonNode != null) {
					String reservationsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservations = objectMapper.readValue(reservationsData, new TypeReference<List<ReservationDto>>() {
					});
				}
			} else {
				System.out.println("No Response.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	public static InstrumentGroupDto getInstrumentGroupById(Integer id) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		InstrumentGroupDto instrumentGroup = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrumentGroup/" + id);

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonNode = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonNode != null) {
					String instrumentGroupData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentGroup = objectMapper.readValue(instrumentGroupData,
							new TypeReference<InstrumentGroupDto>() {
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

		return instrumentGroup;
	}

	public static List<ReservationDto> getAllReservations() {
		List<ReservationDto> reservations = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservation/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservations");
				if (jsonNode != null) {
					String reservationsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservations = objectMapper.readValue(reservationsData, new TypeReference<List<ReservationDto>>() {
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

		return reservations;
	}

	public static ReservationDto getReservationById(Integer id) {
		ReservationDto reservation = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservation/" + id);

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonNode = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonNode != null) {
					String reservationData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservation = objectMapper.readValue(reservationData, new TypeReference<ReservationDto>() {
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

		return reservation;
	}

	public static List<ReservationDto> getReservationsByInstrId(Integer id) {
		List<ReservationDto> reservations = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservation/instr/" + id);

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservations");
				if (jsonNode != null) {
					String reservationsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservations = objectMapper.readValue(reservationsData, new TypeReference<List<ReservationDto>>() {
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

		return reservations;
	}

	public static boolean addReservation(ReservationDto reservationDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/reservation/add");

		try {
			String jsonData = JsonUtils.mapToJson(reservationDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "resId");
						reservationDto.setId(jsonNode.asInt());
						System.out.println("Reservation successfuly created.");
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

	public static boolean updateReservation(ReservationDto reservationDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(getLabinventoryServiceUri() + "/reservation/update");

		try {
			String jsonData = JsonUtils.mapToJson(reservationDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPut.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPut);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "resId");
						reservationDto.setId(jsonNode.asInt());
						System.out.println("Reservation successfuly updated.");
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

	public static boolean deleteReservation(String resid) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/reservation/delete/" + resid);

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

	public static List<ResoptionDto> getAllResoptions() {
		List<ResoptionDto> resoptions = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/resoption/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "resoptions");
				if (jsonNode != null) {
					String resoptionsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					resoptions = objectMapper.readValue(resoptionsData, new TypeReference<List<ResoptionDto>>() {
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

		return resoptions;
	}

	public static ResoptionDto getResoptionById(Integer id) {
		ResoptionDto resoption = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/resoption/" + id);

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonNode = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonNode != null) {
					String resoptionData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					resoption = objectMapper.readValue(resoptionData, new TypeReference<ResoptionDto>() {
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
		return resoption;
	}

	public static List<Integer> getResoptionIdsByInstrumentId(String instrId) {
		List<Integer> resoptionIds = new ArrayList<>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrument/" + instrId + "/resoptions");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonNode = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonNode != null) {
					String resoptionData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					resoptionIds = objectMapper.readValue(resoptionData, new TypeReference<List<Integer>>() {
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
		return resoptionIds;
	}

	public static List<FavoriteDto> getAllFavorites() {
		List<FavoriteDto> favorites = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/favorite/all");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "favorites");
				if (jsonNode != null) {
					String favoritesData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					favorites = objectMapper.readValue(favoritesData, new TypeReference<List<FavoriteDto>>() {
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
		return favorites;
	}

	public static List<FavoriteDto> getUserFavorites(String user) {
		List<FavoriteDto> userFavorites = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/favorite/user/" + user);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				ObjectMapper objectMapper = new ObjectMapper();
				userFavorites = objectMapper.readValue(jsonData, new TypeReference<List<FavoriteDto>>() {
				});
			} else {
				System.out.println("No Response.");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userFavorites;
	}

	public static boolean addFavorite(FavoriteDto favoriteDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/favorite/add");
		try {
			String jsonData = JsonUtils.mapToJson(favoriteDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("Favorite successfuly added.");
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

	public static boolean deleteFavorite(String favoriteId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/favorite/delete/" + favoriteId);
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

	public static boolean deleteFavoriteByInstrument(String instId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/favorite/delete/inst/" + instId);
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
	
	public static FavoriteDto getFavoriteById(Integer id) {
		FavoriteDto favorite = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/favorite/" + id);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String favoriteData = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
						.lines().collect(Collectors.joining("\n"));
				if (favoriteData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					favorite = objectMapper.readValue(favoriteData, new TypeReference<FavoriteDto>() {
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
		return favorite;
	}

	public static List<ReservationUsageDto> getAllReservationUsages() {
		List<ReservationUsageDto> reservationUsages = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservationUsage/all");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservationUsages");
				if (jsonNode != null) {
					String reservationUsagesData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservationUsages = objectMapper.readValue(reservationUsagesData,
							new TypeReference<List<ReservationUsageDto>>() {
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
		return reservationUsages;
	}

	public static ReservationUsageDto getReservationUsage(Integer id) {
		ReservationUsageDto reservationUsage = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservationUsage/" + id);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonNode = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				if (jsonNode != null) {
					String reservationUsageData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservationUsage = objectMapper.readValue(reservationUsageData,
							new TypeReference<ReservationUsageDto>() {
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
		return reservationUsage;
	}

	public static List<ReservationUsageDto> getReservationUsageByReservationId(Integer reservationId) {
		List<ReservationUsageDto> reservationUsages = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservationUsage/reservationId/" + reservationId);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservationUsages");
				if (jsonNode != null) {
					String reservationUsagesData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservationUsages = objectMapper.readValue(reservationUsagesData,
							new TypeReference<List<ReservationUsageDto>>() {
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
		return reservationUsages;
	}

	public static boolean addReservationUsage(ReservationUsageDto reservationUsageDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/reservationUsage/add");
		try {
			String jsonData = JsonUtils.mapToJson(reservationUsageDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "resUsageId");
						reservationUsageDto.setId(jsonNode.asInt());
						System.out.println("Reservation successfuly created.");
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

	public static boolean updateReservationUsage(ReservationUsageDto reservationUsageDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(getLabinventoryServiceUri() + "/reservationUsage/update");
		try {
			String jsonData = JsonUtils.mapToJson(reservationUsageDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPut.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPut);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));
					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						JsonNode jsonNode = JsonUtils.getJsonNode(jsonResponse, "reservationUsageId");
						reservationUsageDto.setId(jsonNode.asInt());
						System.out.println("ReservationUsage successfuly updated.");
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

	public static boolean deleteReservationUsage(String reservationUsgaeId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(
				getLabinventoryServiceUri() + "/reservationUsage/delete/" + reservationUsgaeId);
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

	public static List<InstrumentDeputyDto> getAllInstrumentDeputies() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<InstrumentDeputyDto> instrumentDeputies = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrumentDeputy/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "instrumentDeputies");
				if (jsonNode != null) {
					String instrumentDeputiesData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentDeputies = objectMapper.readValue(instrumentDeputiesData,
							new TypeReference<List<InstrumentDeputyDto>>() {
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

		return instrumentDeputies;
	}

	public static List<String> getDeputiesByInstrumentId(Integer instrumentId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> deputies = null;
		HttpGet httpGet = new HttpGet(
				getLabinventoryServiceUri() + "/instrumentDeputy/instrument/" + instrumentId.toString());

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					deputies = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
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

		return deputies;
	}

	public static boolean addInstrumentDeputy(InstrumentDeputyDto instrumentDeputyDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/instrumentDeputy/add");
		try {
			String jsonData = JsonUtils.mapToJson(instrumentDeputyDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("InstrumentDeputy successfuly added.");
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

	public static boolean deleteInstrumentDeputies(String instrumentId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(
				getLabinventoryServiceUri() + "/instrumentDeputy/delete/" + instrumentId);
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

	public static List<InstrumentEmployeeGroupDto> getAllInstrumentEmployeeGroups() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<InstrumentEmployeeGroupDto> instrumentEmployeeGroups = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrument_employee_group/all");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "instrumentEmployeeGroups");
				if (jsonNode != null) {
					String instrumentEmployeeGroupsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentEmployeeGroups = objectMapper.readValue(instrumentEmployeeGroupsData,
							new TypeReference<List<InstrumentEmployeeGroupDto>>() {
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
		return instrumentEmployeeGroups;
	}

	public static List<String> getEmployeeGroupsByInstrumentId(String instrumentId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> employeeGroups = null;
		HttpGet httpGet = new HttpGet(
				getLabinventoryServiceUri() + "/instrument_employee_group/instrument/" + instrumentId.toString());
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					employeeGroups = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
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
		return employeeGroups;
	}

	public static List<Integer> getRestrictedInstrumentIdsByInstId(Integer id) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<Integer> restrictedInstrumentIds = null;
		HttpGet httpGet = new HttpGet(
				getLabinventoryServiceUri() + "/instrument/restricted_instruments/" + id.toString());
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					restrictedInstrumentIds = objectMapper.readValue(jsonData, new TypeReference<List<Integer>>() {
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
		return restrictedInstrumentIds;
	}

	public static String getResponseMessage() {
		String ret = responseMessage;
		responseMessage = "";
		return ret;
	}

	public static List<ReservationDto> getReservationsByInstrIdsAndDateRange(
			ReservationHistoryDto reservationHistoryDto) {
		List<ReservationDto> reservations = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpGet = new HttpPost(getLabinventoryServiceUri() + "/reservation/instruments");
		try {
			String resaHistJsonData = JsonUtils.mapToJson(reservationHistoryDto);
			HttpEntity stringEntity = new StringEntity(resaHistJsonData, ContentType.APPLICATION_JSON);
			httpGet.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservations");
				if (jsonNode != null) {
					String reservationsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservations = objectMapper.readValue(reservationsData, new TypeReference<List<ReservationDto>>() {
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
		return reservations;
	}
	
	public static List<ReservationDto> getReservationsByInstrIdDateRange(Integer id, Long from, Long to) {
		List<ReservationDto> reservations = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservation/instrument-in-range/" + id + "/" + from + "/" + to); 

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "reservations");
				if (jsonNode != null) {
					String reservationsData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					reservations = objectMapper.readValue(reservationsData, new TypeReference<List<ReservationDto>>() {
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

		if(reservations != null && !reservations.isEmpty()) {			
			Collections.sort(reservations, new Comparator<ReservationDto>() {
				@Override
				public int compare(ReservationDto r1, ReservationDto r2) {
					return r1.getToTime().compareTo(r2.getToTime());
				}
			});
		}
		
		return reservations;
	}

	
	public static List<InstrumentPriorityUsersDto> getAllInstrumentPriorityUsers() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<InstrumentPriorityUsersDto> instrumentPriorityUsersList = null;
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/instrumentPriorityUsers/all");

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "instrumentPriorityUsers");
				if (jsonNode != null) {
					String instrumentPriorityUsersData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instrumentPriorityUsersList = objectMapper.readValue(instrumentPriorityUsersData,
							new TypeReference<List<InstrumentPriorityUsersDto>>() {
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

		return instrumentPriorityUsersList;
	}

	public static List<String> getPriorityUsersByInstrumentId(Integer instrumentId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<String> priorityUsers = null;
		HttpGet httpGet = new HttpGet(
				getLabinventoryServiceUri() + "/instrumentPriorityUsers/instrument/" + instrumentId.toString());

		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));

				if (jsonData != null) {
					ObjectMapper objectMapper = new ObjectMapper();
					priorityUsers = objectMapper.readValue(jsonData, new TypeReference<List<String>>() {
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

		return priorityUsers;
	}

	public static boolean addInstrumentPriorityUser(InstrumentPriorityUsersDto instrumentPriorityUsersDto) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(getLabinventoryServiceUri() + "/instrumentPriorityUsers/add");
		try {
			String jsonData = JsonUtils.mapToJson(instrumentPriorityUsersDto);
			HttpEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				if (response.getEntity() != null) {
					String jsonResponse = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					if (jsonResponse != null && !jsonResponse.isEmpty()) {
						System.out.println("InstrumentPriorityUser successfuly added.");
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

	public static boolean deleteInstrumentPriorityUser(String instrumentId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(
				getLabinventoryServiceUri() + "/instrumentPriorityUsers/delete/" + instrumentId);
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
	
	public static List<GroupInstrumentDto> getGroupInstrumentByInstrId(Integer instId) {
		List<GroupInstrumentDto> groupInstruments = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/groupinstrument/instr/" + instId);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "groupInstruments");
				if (jsonNode != null) {
					String grpInstData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					groupInstruments = objectMapper.readValue(grpInstData, new TypeReference<List<GroupInstrumentDto>>() {
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
		return groupInstruments;
	}
	
	public static boolean deleteGroupInstrument(String instId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/groupinstrument/delete/" + instId);
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
	
	public static boolean deleteInstrumentGroupEmployee(String instId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/instrument_employee_group/delete/" + instId);
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
	
	public static boolean deleteReservationOptionLinks(String instId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(getLabinventoryServiceUri() + "/resoptionlink/delete/" + instId);
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
	
	
	public static List<Integer> getAllowedInstrumentForReservationUsages() {
		List<Integer> instIds = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(getLabinventoryServiceUri() + "/reservationUsage/allowed-instruments");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response.getEntity() != null) {
				String jsonData = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).lines()
						.collect(Collectors.joining("\n"));
				JsonNode jsonNode = JsonUtils.getJsonNode(jsonData, "allowedInstrumentsResaUsage");
				if (jsonNode != null) {
					String instIdData = jsonNode.toString();
					ObjectMapper objectMapper = new ObjectMapper();
					instIds = objectMapper.readValue(instIdData, new TypeReference<List<Integer>>() {
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
		return instIds;
	}
	
	
	public static void main(String[] args) {
		// System.out.println(getAllInstruments().size());
		// System.out.println(getAllInstrumentGroups().size());
		System.out.println(getReservationById(314764));

		System.out.println(getAllReservations().size());
		// System.out.println(getAllResoptions().size());

		// System.out.println(getInstrumentById(42));
		System.out.println(getInstrumentGroupById(2));
		System.out.println(getReservationById(314764));
		// System.out.println(getResoptionById(1));
	}




}