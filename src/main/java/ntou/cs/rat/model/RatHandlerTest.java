package ntou.cs.rat.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class RatHandlerTest {
	public static void main(String[] args) {
		try {
			RatHandler handler = new RatHandler();
			handler.initialize();
			List<Pharmacy> filteredClinicList = handler.findPharmacies("海大", "基隆市中正區");
			System.out.println(filteredClinicList);
			List<Pharmacy> filteredClinicList2 = handler.findPharmacies("中正", "基隆市中正區");
			System.out.println(filteredClinicList2);
			List<Pharmacy> filteredClinicList3 = handler.findPharmacies("", "基隆市信義區");
			System.out.println(filteredClinicList3);
		} catch (IOException | URISyntaxException e) {			
			System.err.println(e);
		}
	}
}
