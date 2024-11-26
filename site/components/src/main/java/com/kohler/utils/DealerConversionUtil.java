package com.kohler.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;

import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Dealer;
import com.kohler.commons.util.CommonUtil;

public class DealerConversionUtil {


	public List<String> decideFields() {

		HstRequestContext requestContext = RequestContextProvider.get();
		Mount mount = requestContext.getResolvedMount().getMount();
		String language = CommonUtil.getLanguage(mount);
		String country = CommonUtil.getCountry(mount);
		Locale locale = new Locale(language, country);
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundleApacLabel = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		List<String> englishFieldsList = Arrays.asList(resourceBundle.getPropertyValue(bundleApacLabel,"dealer-excel-column-names").split(","));
		List<String> languageFieldsList = Arrays.asList(resourceBundle.getPropertyValue(bundleApacLabel,"dealer-excel-column-names-suffix").split(","));
		List<String> finalFieldsList = new ArrayList<>();

		if (language.equalsIgnoreCase(Constants.LOCALE_EN)) {
			finalFieldsList.addAll(englishFieldsList);
		} else {
			englishFieldsList.forEach(fieldName -> {
				String altFieldName = fieldName + Constants.ALTERNATE;
				if (!languageFieldsList.contains(altFieldName)) {
					finalFieldsList.add(fieldName);
				} else {
					finalFieldsList.add(altFieldName);
				}
			});
		}

		return finalFieldsList;
	}

	public Dealer prepareObjectFromRow(Row row, Map<String, Integer> columnMapping) {
		Dealer dealer = new Dealer();
		dealer.setStoreId(getStringCellValue(row, columnMapping.get(Constants.ENTITY_ID)).trim());
		dealer.setStoreName(getStringCellValue(row, columnMapping.get(Constants.LOCATION_NAME)).trim());
		dealer.setPrimaryContactName(getStringCellValue(row, columnMapping.get(Constants.PRIMARY_CONTACT_NAME)).trim());
		dealer.setAddressLineOne(getStringCellValue(row, columnMapping.get(Constants.ADDRESSLINE_1)).trim());
		dealer.setAddressLineTwo(getStringCellValue(row, columnMapping.get(Constants.ADDRESSLINE_2)).trim());
		dealer.setAddressLineThree(getStringCellValue(row, columnMapping.get(Constants.ADDRESSLINE_3)).trim());
		dealer.setCity(getStringCellValue(row, columnMapping.get(Constants.LOCALITY)).trim());
		dealer.setDealerState(getStringCellValue(row, columnMapping.get(Constants.ADMIN_DISTRICT)).trim());
		dealer.setZipCode(getStringCellValue(row, columnMapping.get(Constants.POSTAL_CODE)).trim());
		dealer.setCountry(getStringCellValue(row, columnMapping.get(Constants.COUNTRY_REGION)).trim());
		dealer.setLatitude(getStringCellValue(row, columnMapping.get(Constants.LATITUDE)).trim());
		dealer.setLongitude(getStringCellValue(row, columnMapping.get(Constants.LONGITUDE)).trim());
		String phoneNumbersVal = getStringCellValue(row, columnMapping.get(Constants.PHONE)).trim();
		if (phoneNumbersVal != null) {
			String[] phoneNumbers = getCommmaSeperatedValues(phoneNumbersVal);
			dealer.setPhone(phoneNumbers);
		} else {
			dealer.setPhone(null);
		}
		dealer.setMobile(getStringCellValue(row, columnMapping.get(Constants.MOBILE)).trim());
		dealer.setFax(getStringCellValue(row, columnMapping.get(Constants.FAX)).trim());
		dealer.setGeneralEmail(getStringCellValue(row, columnMapping.get(Constants.GENERAL_EMAIL)).trim());
		dealer.setPrimaryContactEmail(getStringCellValue(row, columnMapping.get(Constants.PRIMARY_CONTACT_EMAIL)).trim());
		dealer.setWebsite(getStringCellValue(row, columnMapping.get(Constants.WEB_URL)).trim());
		dealer.setFacebookLink(getStringCellValue(row, columnMapping.get(Constants.FACEBOOK_LINK)).trim());
		dealer.setTwitterLink(getStringCellValue(row, columnMapping.get(Constants.TWITTER_LINK)).trim());
		dealer.setInstagramLink(getStringCellValue(row, columnMapping.get(Constants.INSTAGRAM_LINK)).trim());
		dealer.setLineLink(getStringCellValue(row, columnMapping.get(Constants.LINE_LINK)).trim());
		dealer.setDealerType(getStringCellValue(row, columnMapping.get(Constants.DEALER_TYPE)).trim());
		dealer.setShowroomType(getStringCellValue(row, columnMapping.get(Constants.SHOWROOM_TYPE)).trim());
		dealer.setDescription(getStringCellValue(row, columnMapping.get(Constants.DEALER_DESCRIPTION)).trim());
		String openingHoursVal = getStringCellValue(row, columnMapping.get(Constants.OPENING_HOURS));
		if (openingHoursVal != null) {
			String[] openingHours = getCommmaSeperatedValues(openingHoursVal);
			dealer.setOpeningHours(openingHours);
		} else {
			dealer.setOpeningHours(null);
		}
		dealer.setStoreLogo(getStringCellValue(row, columnMapping.get(Constants.STORE_LOGO)).trim());
		Boolean val = false;
		if ("yes".equalsIgnoreCase(getStringCellValue(row, columnMapping.get(Constants.DETAIL_VIEW)).trim())) {
			val = true;
		}
		dealer.setDetailView(val);
		return dealer;
	}

	public Map<String, Integer> checkColumnHeader(Row titleRow, List<String> finalFieldsList) {
		Map<String, Integer> columnMapping = new LinkedHashMap<String, Integer>();
		Iterator<Cell> cellItr = titleRow.cellIterator();
		while (cellItr.hasNext()) {
			Cell cell = cellItr.next();
			if (finalFieldsList.contains(cell.getStringCellValue())) {
				columnMapping.put(cell.getStringCellValue().replace(Constants.ALTERNATE, ""), cell.getColumnIndex());
			}
		}
		return columnMapping;
	}

	public String getStringCellValue(Row row, int columnNum) {
		Cell cellValue = row.getCell(columnNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		String val = "";
		if (cellValue != null) {
			try {
				val = cellValue.getStringCellValue();
			} catch (Exception ex) {
				val = "" + cellValue.getNumericCellValue();
			}
		}
		return val;
	}

	public Double getNUmericCellValue(Row row, int rowNum) {
		Cell cellValue = row.getCell(rowNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
		Double val = null;
		if (cellValue != null) {
			try {
				val = cellValue.getNumericCellValue();
			} catch (Exception ex) {
				val = Double.parseDouble(row.getCell(rowNum).getStringCellValue());
			}
		}
		return val;
	}

	public String[] getCommmaSeperatedValues(String string) {
		if (string.isEmpty())
			return null;
		String values[] = string.split(",");
		return values;
	}

}
