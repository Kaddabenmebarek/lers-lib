package org.research.kadda.labinventory;

/**
 * Author: Kadda
 */

public class AbstractService {
	
	private static final String LABINVENTORY_SERVICE_HOST = "ares";
	private static final String LABINVENTORY_SERVICE_HOST_TEST = "apollo";
	private static final String LABINVENTORY_SERVICE_HOST_DEV = "localhost";
	private static final String LABINVENTORY_SERVICE_PORT = "9980";
	private static final String LABINVENTORY_CONTEXT_PATH = "lers";

	public static String getLabinventoryServiceUri() {
		String mode = System.getProperty("mode");
		String production = System.getProperty("production");
		//mode = "dev";
		if ("production".equals(mode) || "true".equals(production)) {
			return "http://" + LABINVENTORY_SERVICE_HOST + ":" + LABINVENTORY_SERVICE_PORT + "/"
					+ LABINVENTORY_CONTEXT_PATH;
		} else if ("dev".equals(mode)) {
			return "http://" + LABINVENTORY_SERVICE_HOST_DEV + ":" + LABINVENTORY_SERVICE_PORT + "/"
					+ LABINVENTORY_CONTEXT_PATH;
		} else {
			return "http://" + LABINVENTORY_SERVICE_HOST_TEST + ":" + LABINVENTORY_SERVICE_PORT + "/"
					+ LABINVENTORY_CONTEXT_PATH;
		}
	}
}