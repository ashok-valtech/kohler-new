package com.kohler.commons.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.taxonomy.api.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.dto.SeoRequest;
import com.kohler.commons.taxonomies.TaxonomyService;

/**
 * Service class to update SEO Plugin 
 * @author dhanwan.r
 * Date: 10/07/2019
 * @version 1.0
 */
public class SeoService {
	
	private static final String LIVE = "live";
	private static final String HIPPO_AVAILABILITY = "hippo:availability";
	private static final String HST_COMPONENTCLASS = "hst:componentclassname";
	private static final String HST_XTYPE = "hst:xtype";
	private static final String HST_LABEL = "hst:label";
	private static final String HST_LASTMODIFIED = "hst:lastmodified";
	private static final String HST_ICONPATH = "hst:iconpath";
	private static final String HST_TEMPLATE = "hst:template";
	private static final String HST_CONTCOMFOLDER = "hst:containercomponentfolder";
	private static final String HST_CONTCOMPONENT = "hst:containercomponent";
	private static final String HST_CONTITEMCOMPONENT = "hst:containeritemcomponent";
	private static final String HST_ITEM = "hst.item";
	private static final String HST_NOMARKUP = "hst.nomarkup";
	private static final String MAIN = "main";
	private static final String FORWARD_SLASH = "/";


	private ValueFactory valueFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SeoService.class);
	
	public Boolean updateSeoDetails (String seoTitle, String seoDescription, String seoPath, Boolean noindex, Boolean noFollow, String canonicalUrl, Session session)
	{
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = getNodeIfExist(seoPath, session);
			if (rootTaxonomy != null) {
				Property parameterNames = rootTaxonomy.getProperty(Constants.HSTPARAMETERNAMES);
				Property parameterValues = rootTaxonomy.getProperty(Constants.HSTPARAMETERVALUES);
				Value[] values = parameterNames.getValues();
				List<String> valueList = valuesToList(values);
				int titleIndex = valueList.indexOf(Constants.SITETITLE);
				int descriptionIndex = valueList.indexOf(Constants.DEFAULTMETADESC);
				int noIndexIndex = valueList.indexOf(Constants.NOINDEXPARAMETER);
				int noFollowIndex = valueList.indexOf(Constants.NOFOLLOWPARAMETER);
				int canonicalIndex = valueList.indexOf(Constants.CANONICALPARAMETER);
				Value[] values1 = parameterValues.getValues();
				List<String> valueList1 = valuesToList(values1);
				if (titleIndex >= 0) {
					valueList1.set(titleIndex, seoTitle);
				}
				if (descriptionIndex >= 0) {
					valueList1.set(descriptionIndex, seoDescription);
				}
				if (canonicalIndex >= 0) {
					valueList1.set(canonicalIndex, canonicalUrl);
				}
				setValueFlag(valueList1, noIndexIndex, noindex);
				setValueFlag(valueList1, noFollowIndex, noFollow);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("pirName--> %s --- %s  size--> %s", parameterValues.getName(), valueList1,
							valueList1.size()));
				}
				Value[] valueArray = setValueArray(values1, valueList1);
				rootTaxonomy.setProperty(Constants.HSTPARAMETERVALUES, valueArray);
				return true;
			} 
			
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return false;
	}

	private Value[] setValueArray(Value[] values1, List<String> valueList1) {
		Value[] valueArray = new Value[values1.length];
		for (int i = 0; i < values1.length; i++) {
			String value = valueList1.get(i);
			valueArray[i] = valueFactory.createValue(value);
		}
		return valueArray;
	}
	
	private void setValueFlag (List<String> valueList1, int index, Boolean flag) {
		if (index >= 0) {
			if (flag) {
				valueList1.set(index, "on");
			}
			else {
				valueList1.set(index, "off");
			}
		}
	}

	public List<String> getSites(Session session)
	{
		List<String> returnValue = new ArrayList<>();
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = getNodeIfExist(FORWARD_SLASH+Constants.HSTSITESPATH, session);
			if (rootTaxonomy != null) {
				NodeIterator nodeIterator = rootTaxonomy.getNodes();
				while (nodeIterator.hasNext()) {
					Node node = nodeIterator.nextNode();
					String name = node.getName();
					returnValue.add(name);
				}
			}
			return returnValue;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return returnValue;
		}
	}
	
 private Long getSEOTemplateSize(String rootpath,Session session)
	{
		long size=0;
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = getNodeIfExist(rootpath, session);
			if (rootTaxonomy != null) {
				size=rootTaxonomy.getNodes().getSize();
			}
			return size;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return 0l;
		}
	}
	
	public String getPropertyFromNode (Node node, String propertyName) {
		try {
			if(propertyName.equalsIgnoreCase(Constants.SEOSUPPORTNOINDEX) || propertyName.equalsIgnoreCase(Constants.SEOSUPPORTNOFOLLOW) || propertyName.equalsIgnoreCase(Constants.SEONOINDEX) || propertyName.equalsIgnoreCase(Constants.SEONOFOLLOW)){
			   Boolean value=node.getProperty(propertyName).getValue().getBoolean();
			   if(value)
				   return "checked";
			   else
				   return "";
			}
			else
			{
				return node.getProperty(propertyName).getValue().getString();
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("There is No Such Element");
				LOGGER.error(e.getMessage());
			}
			return "";
		}
	}

	protected Boolean checkLive (String documentPath3, Session session) {
		try {
			Node isPublishedNode = getNodeIfExist(documentPath3, session);
			Value[] liveValue = isPublishedNode.getProperty(HIPPO_AVAILABILITY).getValues();
			if ((liveValue.length > 0) && (liveValue[0].getString().equalsIgnoreCase(LIVE))) {
				return true;
			} 
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("There is No Live Node");
				LOGGER.error(e.getMessage());
			}
		}
		return false;
	}
	
	private Map<String, String> getSeoPageName(String siteContentBasePath, Node rootTaxonomy, String siteName, Session session, ResourceBundle bundle)
	{
		Map<String, String> seoPageValuesMap = new HashMap<>();
		if (rootTaxonomy != null) {
			try {
				String relativePath = Constants.BLANK;
				Property parameterNames = rootTaxonomy.getProperty(Constants.HSTPARAMETERNAMES);
				Property parameterValues = rootTaxonomy.getProperty(Constants.HSTPARAMETERVALUES);
				Map <String, String> valueListMap = valuesToMap (parameterNames.getValues(), parameterValues.getValues());
				String seoPageName = valueListMap.get(Constants.SEOPAGEDISPLAYNAME);
				if (Strings.isNullOrEmpty(seoPageName)) {
					seoPageName	= valueListMap.get(Constants.SEOSITEMAPITEM);
				}
				seoPageValuesMap.put(Constants.REQUEST_DISPLAY_NAME, seoPageName);
				Long size = 0l;
				Node siteMapNode = null;
				if (valueListMap.containsKey(Constants.SEOCLASSNAME) && !Strings.isNullOrEmpty(valueListMap.get(Constants.SEOCLASSNAME)) && valueListMap.containsKey(Constants.SEOSITEMAPITEM)) {
					String seoSitemapitem = valueListMap.get(Constants.SEOSITEMAPITEM);
					String categoriesSiteMapItemList = bundle.getString(Constants.CATEGORY_SITEMAP_ITEM_LIST);
					if (Arrays.asList(categoriesSiteMapItemList.split(",")).contains(seoSitemapitem)) {
						relativePath = bundle.getString(Constants.CATEGORY_RELATIVE_BASE_PATH) + "/" + seoSitemapitem;
					} else {	
						StringBuilder siteMap = new StringBuilder (Constants.HSTSHST);
						siteMap.append(FORWARD_SLASH + Constants.HSTCONFIGURATION);
						siteMap.append(FORWARD_SLASH + siteName);
						siteMap.append(FORWARD_SLASH + Constants.HSTSITEMAP);
						siteMap.append(FORWARD_SLASH + seoSitemapitem);
						siteMapNode = getNodeIfExist(siteMap.toString(), session);
					}
				}	
				if (siteMapNode != null) {
					relativePath = getPropertyFromNode(siteMapNode, Constants.HSTRELATIVECONTENTPATH);
				}
				if (StringUtils.isNotEmpty(relativePath)) {
					StringBuilder documentPath = new StringBuilder (FORWARD_SLASH + siteContentBasePath);
					documentPath.append(FORWARD_SLASH + relativePath);
				    size = getSEOTemplateSize(documentPath.toString(), session);
				}
				seoPageValuesMap.put(Constants.REQUEST_RELATIVE_PATH, relativePath);
				seoPageValuesMap.put(Constants.REQUEST_CLASS_NAME, valueListMap.get(Constants.SEOCLASSNAME));
				seoPageValuesMap.put(Constants.REQUEST_LENGTH, String.valueOf(size));
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}	
		} 
		return seoPageValuesMap;
	}
	
	public Map<String, Map<String, String>> getProductCategories(HstRequest request,SeoRequest seoRequest, String siteContentBasePath,
			Session session, Mount mount, ResourceBundle bundle) {
		Map<String, Map<String, String>> returnValue = new LinkedHashMap<>();
		String containerPath = bundle.getString(Constants.HSTCONTAINERPATH);
		String seoPath = containerPath + FORWARD_SLASH + Constants.PRODUCTS_DETAILS_CPATH + FORWARD_SLASH
				+ Constants.MAINSEO;
		Node seoNode = getNodeIfExist(seoPath, session);
		if (seoNode != null) {
			try {
			Property parameterNames = seoNode.getProperty(Constants.HSTPARAMETERNAMES);
			Property parameterValues = seoNode.getProperty(Constants.HSTPARAMETERVALUES);
			Map <String, String> valueListMap = valuesToMap (parameterNames.getValues(), parameterValues.getValues());
			String seoPageName = valueListMap.get(Constants.SEOPAGEDISPLAYNAME);
			request.setAttribute(Constants.PRODUCTSDISPLAYNAME, seoPageName);
			request.setAttribute(Constants.RELATIVEPATHATTRIBUTE,Constants.PRODUCT_FOLDER_NODE_NAME);
			request.setAttribute(Constants.CLASSNAMEATTRIBUTE, valueListMap.get(Constants.SEOCLASSNAME));
			request.setAttribute(Constants.SEOSITEMAPITEM, valueListMap.get(Constants.SEOSITEMAPITEM));
			}catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			TaxonomyService taxonomyService = new TaxonomyService();
			List<? extends Category> allCategories = taxonomyService.getParentCategories();
			String query = seoRequest.getQuery();
			if (Strings.isNullOrEmpty(query)) {
				for (Category category : allCategories) {
					List<? extends Category> childrens = category.getChildren();
					for (Category childrenCategory : childrens) {
						String categoryKey = childrenCategory.getKey();
						Locale locale =  LocaleUtils.toLocale(Constants.LOCALE_EN + "_" + Constants.COUNTRY_CODE_APAC);
						String categoryName = childrenCategory
								.getInfo(locale).getName();
						Map<String, String> seoPageValuesMap = getSeoPageNameForProducts(siteContentBasePath,
								categoryName, mount, session, bundle);
						returnValue.put(categoryKey, seoPageValuesMap);
					}
				}
			} else {
				returnValue.put(Constants.PRODUCTRESULTS, getSeoPageNameForProducts(siteContentBasePath,
						Constants.PRODUCTRESULTS, mount, session, bundle));
			}
		}
		return returnValue;
	}
	
	public Map<String, String> getSeoPageNameForProducts(String siteContentBasePath, String categoryName, Mount mount, Session session, ResourceBundle bundle)
	{
		Map<String, String> seoPageValuesMap = new HashMap<>();
		String siteName = mount.getHstSite().getName();
			try {
				String containerPath = bundle.getString(Constants.HSTCONTAINERPATH);
				String seoPath = containerPath + FORWARD_SLASH+Constants.PRODUCTS_DETAILS_CPATH+ FORWARD_SLASH + Constants.MAINSEO;
				Node seoNode = getNodeIfExist(seoPath, session);
				if (seoNode != null) {
			    Map<String, String> values = getSeoPageName(siteContentBasePath, seoNode, siteName, session, bundle);
				seoPageValuesMap.put(Constants.REQUEST_DISPLAY_NAME, categoryName);
				seoPageValuesMap.put(Constants.REQUEST_RELATIVE_PATH, Constants.PRODUCT_FOLDER_NODE_NAME);
				seoPageValuesMap.put(Constants.REQUEST_CLASS_NAME, values.get(Constants.REQUEST_CLASS_NAME));
				seoPageValuesMap.put(Constants.REQUEST_LENGTH, "1");
				}	
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}	
		return seoPageValuesMap;
	}
	
	public Map<String,Map<String, String>> getSitePagesValues(String siteContentBasePath, Session session, Mount mount, ResourceBundle bundle)
	{
		String siteName = mount.getHstSite().getName();
		Map<String,Map<String, String>> returnValue = new LinkedHashMap<>();
		try {
			String containerPath = bundle.getString(Constants.HSTCONTAINERPATH);
			String ignoreList = bundle.getString(Constants.IGNORELIST);
			Node rootTaxonomy = getNodeIfExist(containerPath, session);
			if (rootTaxonomy != null) {
				NodeIterator nodeIterator = rootTaxonomy.getNodes();
				while (nodeIterator.hasNext()) {
					Node node = nodeIterator.nextNode();
					String name = node.getName();
					if (!Arrays.asList(ignoreList.split(",")).contains(name)) {
						String seoPath = containerPath + FORWARD_SLASH + name+ FORWARD_SLASH + Constants.MAINSEO;
						Node seoNode = getNodeIfExist(seoPath, session);
						if (seoNode != null) {
							Map<String, String> seoPageValuesMap = getSeoPageName(siteContentBasePath, seoNode, siteName, session, bundle);
							returnValue.put(name, seoPageValuesMap);	
						}
					}
				}
			}
			return returnValue;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return returnValue;
		}
	}
	
	private Node getNodeIfExist (String siteMap, Session session) {
		try {
			return JcrUtils.getNodeIfExists(siteMap, session);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return null;
		}
	}
	
	private Map <String, String> valuesToMap (Value[] values, Value[] values1) {
		Map <String, String> valueList = new LinkedHashMap<>();
		for (Integer i = 0; i < values.length; i++) {
			try {
				valueList.put(values[i].getString() ,values1[i].getString());
			} catch (IllegalStateException | RepositoryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return valueList;
	}
	
	private List <String> valuesToList (Value[] values) {
		List <String> valueList = new ArrayList<>();
		for (Integer i = 0; i < values.length; i++) {
			try {
				valueList.add(values[i].getString());
			} catch (IllegalStateException | RepositoryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return valueList;
	}
	
	public Boolean updateSeoTemplate(LinkedHashMap<String, String> params, String seoPath, Session session)
	{
		Boolean update=false;
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = getNodeIfExist(seoPath, session);
			if (rootTaxonomy == null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String[] path = seoPath.split(FORWARD_SLASH + Constants.MAINSEO);
				JcrUtils.getOrCreateByPath(path[0], HST_CONTCOMFOLDER, session);
				Node mainNode = JcrUtils.getOrCreateByPath(path[0] + FORWARD_SLASH + MAIN, HST_CONTCOMPONENT, session);
				mainNode.setProperty(HST_XTYPE, HST_NOMARKUP);
				mainNode.setProperty(HST_LABEL, Constants.SEO);
				mainNode.setProperty(HST_LASTMODIFIED, cal);
				session.save();
				try {
					rootTaxonomy = JcrUtils.getOrCreateByPath(seoPath, HST_CONTITEMCOMPONENT, session);
					rootTaxonomy.setProperty(HST_COMPONENTCLASS, Constants.HIPPO_COMPONENTCLASSVALUE);
					rootTaxonomy.setProperty(HST_ICONPATH, Constants.SEOICONPATH);
					rootTaxonomy.setProperty(HST_LABEL, Constants.SEO);
					rootTaxonomy.setProperty(HST_TEMPLATE, Constants.SEO);
					rootTaxonomy.setProperty(HST_XTYPE, HST_ITEM);
					session.save();
				} catch (Exception e) {
					LOGGER.info("Exception occurs in sync Seo ");
				}
			}
			if (rootTaxonomy != null) {
				Value[] valuePNameList = new Value[params.size()];
				Value[] valuePValuesList = new Value[params.size()];
				int i = 0;
				for (Map.Entry<String, String> entry : params.entrySet()) {
					valuePNameList[i] = valueFactory.createValue(entry.getKey());
					if (StringUtils.isNotEmpty(entry.getValue())) {
						valuePValuesList[i] = valueFactory.createValue(entry.getValue());
					} else {
						valuePValuesList[i] = valueFactory.createValue("");
					}
					i++;
				}
				rootTaxonomy.setProperty(HST_COMPONENTCLASS, Constants.HIPPO_COMPONENTCLASSVALUE);
				rootTaxonomy.setProperty(Constants.HSTPARAMETERNAMES, valuePNameList);
				rootTaxonomy.setProperty(Constants.HSTPARAMETERVALUES, valuePValuesList);
				update=true;
			}
		} catch (Exception e) {
			LOGGER.info("Exception occurs in sync Seo ");
		}
		return update;
	}
	
	public LinkedHashMap<String, String> getSeoDetails(String seoPath, Session session)
	{
		LinkedHashMap<String, String> seoDetails=new LinkedHashMap<>();
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = getNodeIfExist(seoPath, session);
			if(rootTaxonomy!=null){
			Property parameterNames = rootTaxonomy.getProperty(Constants.HSTPARAMETERNAMES);
			Property parameterValues = rootTaxonomy.getProperty(Constants.HSTPARAMETERVALUES);
			seoDetails = valuesToLinkedHashMap(parameterNames.getValues(), parameterValues.getValues());
			}	
			return seoDetails;
		} catch (Exception e) {
			LOGGER.info("Exception occurs in getting Seo ");
		}
		return seoDetails;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String, String>> getAllSeoValues(Session session,String containerPath)
	{
		LinkedHashMap<String,LinkedHashMap<String, String>> returnValue = new LinkedHashMap<>();
		try {
			valueFactory = session.getValueFactory();
			Node rootTaxonomy = JcrUtils.getNodeIfExists(containerPath, session);
			if (rootTaxonomy != null) {
				NodeIterator nodeIterator = rootTaxonomy.getNodes();
				while (nodeIterator.hasNext()) {
					Node node = nodeIterator.nextNode();
					String name = node.getName();
						String seoPath = containerPath + FORWARD_SLASH + name + FORWARD_SLASH + Constants.MAINSEO;
						Node seoNode = JcrUtils.getNodeIfExists(seoPath, session);
						if (seoNode != null) {
							LinkedHashMap<String, String> seoPageValues = getSeoDetails(seoPath, session);
							returnValue.put(name, seoPageValues);
						}
				}
			}
			return returnValue;
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return returnValue;
		}
	}
	
	private LinkedHashMap <String, String> valuesToLinkedHashMap (Value[] values, Value[] values1) {
		LinkedHashMap <String, String> valueList = new LinkedHashMap <>();
		for (Integer i = 0; i < values.length; i++) {
			try {
				valueList.put(values[i].getString() ,values1[i].getString());
			} catch (IllegalStateException | RepositoryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return valueList;
	}
	
}
