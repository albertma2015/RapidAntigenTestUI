package ntou.cs.rat.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.simpleflatmapper.csv.CsvParser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

// References: https://gist.github.com/arnaudroger/7cbb9ca1acda66341fc10bf54ab01439

public class RatHandler {
	private static final String dataURL = "https://data.nhi.gov.tw/Datasets/Download.ashx?rid=A21030000I-D03001-001&l=https://data.nhi.gov.tw/resource/Nhi_Fst/Fstdata.csv";
	private static final String fileName = "Fstdata.csv"; // if the link is unavailable
		
	private static final Map<String, String> fieldNameTranslationMap = constructFieldNameTranslationMap();

	private List<Pharmacy> pharmacyList;
	
	private static final Map<String, String> constructFieldNameTranslationMap() {
		Map<String, String> fieldNameTranslationMap = new HashMap<String, String>();
		fieldNameTranslationMap.put("﻿醫事機構代碼", "id");
		fieldNameTranslationMap.put("醫事機構名稱", "name");
		fieldNameTranslationMap.put("醫事機構地址", "address");
		fieldNameTranslationMap.put("經度", "longitude");
		fieldNameTranslationMap.put("緯度", "latitude");		
		fieldNameTranslationMap.put("醫事機構電話", "phone");
		fieldNameTranslationMap.put("廠牌項目", "brandOfRapidAntigenTests");
		fieldNameTranslationMap.put("快篩試劑截至目前結餘存貨數量", "numberOfRapidAntigenTests");
		fieldNameTranslationMap.put("來源資料時間", "updatedTime");
		fieldNameTranslationMap.put("備註", "note");

		return fieldNameTranslationMap;
	}

	public String produceStringFromURL(String requestURL) throws IOException {
		try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
	}

	public String produceStringFromFile(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		buf.close();
		return sb.toString();
	}

	public String produceDataJson(String csvContent) throws IOException, URISyntaxException {

		//Map<String, String> fieldNameTranslationMap = constructFieldNameTranslationMap();
		org.simpleflatmapper.lightningcsv.CsvReader reader = CsvParser.reader(csvContent);

		JsonFactory jsonFactory = new JsonFactory();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Iterator<String[]> iterator = reader.iterator();
		String[] headers = iterator.next();

		try (JsonGenerator jsonGenerator = jsonFactory
				.createGenerator(new PrintStream(output, true, StandardCharsets.UTF_8))) {

			jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
			jsonGenerator.writeStartArray();

			while (iterator.hasNext()) {
				jsonGenerator.writeStartObject();
				String[] values = iterator.next();
				int nbCells = Math.min(values.length, headers.length);
				for (int i = 0; i < nbCells; i++) {
					jsonGenerator.writeFieldName(fieldNameTranslationMap.get(headers[i]));
					jsonGenerator.writeString(values[i]);
				}
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();
		}

		return output.toString(StandardCharsets.UTF_8);
	}

	public List<Pharmacy> convertToObjects(String jsonData) {

		Gson gson = new Gson();
		ArrayList<Pharmacy> clinicList = new ArrayList<Pharmacy>();

		try {
			Type listType = new TypeToken<List<Pharmacy>>() {
			}.getType();
			clinicList = gson.fromJson(jsonData, listType);
		} catch (Exception e) {
			System.err.println("Exception: " + e);
		}
		
		return clinicList;
	}

	public List<Pharmacy> findPharmacies(String queryName, String queryAddress) {
		List<Pharmacy> matchingElements = pharmacyList.stream().filter(
				str -> str.getName().trim().contains(queryName) && str.getAddress().trim().contains(queryAddress))
				.collect(Collectors.toList());

		return matchingElements;
	}

	public void initialize() throws IOException, URISyntaxException {

		// String maskData = produceStringFromURL(dataURL);	// link remote URL
		String maskData = produceStringFromFile(fileName); // link local file
		String maskDataJson = produceDataJson(maskData);
		pharmacyList = convertToObjects(maskDataJson);
	}

}
