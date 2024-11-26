package com.kohler.commons.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsListComponent;
import org.onehippo.cms7.essentials.components.info.EssentialsListComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.beans.Dealer;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.util.CommonUtil;

/**
 * @author Sumit.Pallakhe
 * 
 *         class provide functionality for the storelocater page. renders stores
 *         related to selected country/state/city
 *
 */
@ParametersInfo(type = EssentialsListComponentInfo.class)
public class StoreLocatorComponent extends EssentialsListComponent {

	private static final Logger LOG = LoggerFactory.getLogger(StoreLocatorComponent.class);
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {

		HstRequestContext hstRequestContext = request.getRequestContext();
		String sitelanguage = CommonUtil.getLanguage(hstRequestContext.getResolvedMount().getMount());
		String sitecountry = CommonUtil.getCountry(hstRequestContext.getResolvedMount().getMount());
		Locale locale = new Locale(sitelanguage, sitecountry);

		// featuredShowroomDealers == kec
		List<Dealer> featuredShowroomDealers = new ArrayList<Dealer>();
		List<Dealer> starDealers = new ArrayList<Dealer>();
		List<Dealer> distributors = new ArrayList<Dealer>();
		List<Dealer> dealers = new ArrayList<Dealer>();

		boolean showStoreDirectFlag = false;
		ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String storeCountry = resourceBundle.getPropertyValue(bundle, "storeCountry");
		String storeState = resourceBundle.getPropertyValue(bundle, "storeState");
		String storeCity = resourceBundle.getPropertyValue(bundle, "storeCity");

		// set flag 'true', if wants to show store directly
		if (!storeCountry.equals("NA") && !storeState.equals("NA") && !storeCity.equals("NA"))
			showStoreDirectFlag = true;

		boolean stateCitySameFlag = false;
		if (!storeCountry.equals("NA") && storeState.equals("NA") && !storeCity.equals("NA"))
			stateCitySameFlag = true;

		final EssentialsListComponentInfo paramInfo = getComponentParametersInfo(request);
		final String path = getScopePath(paramInfo);
		final HippoBean rootBean = getSearchScope(request, path);

		if (!showStoreDirectFlag) {
			FormMap map = new FormMap();
			FormUtils.populate(request, map);
			if (map.getFormMap().size() > 0) {
				getStores(request, rootBean, map, stateCitySameFlag, featuredShowroomDealers, dealers, distributors,
						starDealers);
			}
		} else {
			getStores(request, rootBean, locale, featuredShowroomDealers, dealers, distributors, starDealers);
		}
	}

	public void getStores(HstRequest request, HippoBean rootBean, FormMap map, Boolean flag,
			List<Dealer> featuredShowroomDealers, List<Dealer> dealers, List<Dealer> distributors,
			List<Dealer> starDealers) {
		HstQueryResult queryResult = null;
		String country = getFormFieldValue(map, Constants.COUNTRY);
		String state = getFormFieldValue(map, Constants.STATE);
		String city = null;
		if (!flag)
			city = getFormFieldValue(map, Constants.CITY);
		else
			city = state;
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Dealer.class);
			Filter filter = query.createFilter();
			filter.addEqualTo(Constants.NS_COUNTRY, country);
			filter.addEqualTo(Constants.NS_STATE, state);
			if (!flag)
				filter.addEqualTo(Constants.NS_CITY, city);
			query.setFilter(filter);
			query.addOrderByAscending(Constants.NS_STORENAME);
			queryResult = query.execute();

			if (queryResult.getTotalSize() >= 1) {
				HippoBeanIterator iterator = queryResult.getHippoBeans();
				while (iterator.hasNext()) {
					HippoBean bean = iterator.nextHippoBean();
					if (bean != null && bean instanceof Dealer) {
						Dealer currentDealer = (Dealer) bean;
						String dealerType = currentDealer.getDealerType();
						if(dealerType.equalsIgnoreCase( Constants.DEALER_TYPE_KOHLER_EXPERIENCE_CENTER)){
							featuredShowroomDealers.add(currentDealer);
						}else if(dealerType.equalsIgnoreCase(Constants.DEALER_TYPE_DISTRIBUTOR)){
							distributors.add(currentDealer);
						}else if(dealerType.equalsIgnoreCase(Constants.DEALER_TYPE_STAR_DEALERS)){
							starDealers.add(currentDealer);
						}else{
							dealers.add(currentDealer);
						}
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute(Constants.REQUEST_ATTR_STORES, dealers);
		request.setAttribute(Constants.REQUEST_ATTR_FEATUREDSTORES, featuredShowroomDealers);
		request.setAttribute(Constants.REQUEST_ATTR_DISTRIBUTORS, distributors);
		request.setAttribute(Constants.REQUEST_ATTR_STARDEALERS, starDealers);
		request.setAttribute(Constants.REQUEST_ATTR_SELECTEDCITY, city);

		request.setAttribute(Constants.COUNTRY, country);
		request.setAttribute(Constants.STATE, state);
		request.setAttribute(Constants.CITY, city);
	}

	/**
	 * @param request
	 * @param rootBean
	 * @param locale
	 * @param featuredShowroomDealers
	 * @param dealers
	 * 
	 *            logic applied for stores which we want to show directly ex:
	 *            Singapore, Korea, etc.
	 */
	public void getStores(HstRequest request, HippoBean rootBean, Locale locale, List<Dealer> featuredShowroomDealers,
			List<Dealer> dealers, List<Dealer> distributors, List<Dealer> starDealers) {
		ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		String storeCountry = resourceBundle.getPropertyValue(bundle, "storeCountry");
		HstQueryResult queryResult = null;
		try {
			@SuppressWarnings("unchecked")
			HstQuery query = request.getRequestContext().getQueryManager().createQuery(rootBean, Dealer.class);
			Filter filter = query.createFilter();
			filter.addEqualTo(Constants.NS_COUNTRY, storeCountry);
			query.setFilter(filter);
			query.addOrderByAscending(Constants.NS_STORENAME);
			queryResult = query.execute();
			if (queryResult.getTotalSize() >= 1) {
				HippoBeanIterator iterator = queryResult.getHippoBeans();
				while (iterator.hasNext()) {
					HippoBean bean = iterator.nextHippoBean();
					if (bean != null && bean instanceof Dealer) {
						Dealer currentDealer = (Dealer) bean;
						String dealerType = currentDealer.getDealerType();
						if(dealerType.equalsIgnoreCase("kec")){
							featuredShowroomDealers.add(currentDealer);
						}else if(dealerType.equalsIgnoreCase("distributors")){
							distributors.add(currentDealer);
						}else if(dealerType.equalsIgnoreCase("stardealers")){
							starDealers.add(currentDealer);
						}else{
							dealers.add(currentDealer);
						}
					}
				}
			}
		} catch (IllegalStateException | QueryException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute(Constants.REQUEST_ATTR_STORES, dealers);
		request.setAttribute(Constants.REQUEST_ATTR_FEATUREDSTORES, featuredShowroomDealers);
		request.setAttribute(Constants.REQUEST_ATTR_DISTRIBUTORS, distributors);
		request.setAttribute(Constants.REQUEST_ATTR_STARDEALERS, starDealers);
		request.setAttribute(Constants.REQUEST_ATTR_SELECTEDCITY, storeCountry);
	}

	/**
	 * This method called after form submission
	 * 
	 * @param request
	 * @param response
	 */
	public void doAction(HstRequest request, HstResponse response) {
		FormMap map = new FormMap(request, new String[] { Constants.COUNTRY, Constants.STATE, Constants.CITY });
		FormUtils.persistFormMap(request, response, map, null);
	}

	/**
	 * Gets form field value
	 * 
	 * @param formMap
	 * @param fieldName
	 * @return
	 */

	private String getFormFieldValue(FormMap formMap, String fieldName) {
		if (formMap != null && !StringUtils.isEmpty(fieldName)) {
			if (formMap.getFormMap().containsKey(fieldName)) {
				return formMap.getField(fieldName).getValue().trim();
			}
		}
		return "";
	}
}
