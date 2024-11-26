package com.kohler.commons.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.version.VersionManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.hippoecm.repository.api.HippoWorkspace;
import org.jcrom.Jcrom;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.exception.NoTaxonomyFoundExeption;
import com.kohler.commons.jcr.beans.CrossSelling;
import com.kohler.commons.jcr.beans.HandleNode;
import com.kohler.commons.jcr.beans.ProductAttributes;
import com.kohler.commons.jcr.beans.ProductFolder;
import com.kohler.commons.jcr.beans.ProductSubFolder;
import com.kohler.commons.json.beans.Product;
import com.kohler.commons.json.beans.Skus;
import com.kohler.commons.servicebase.product.AbstractCPDBProductAPI;
import com.kohler.commons.servicebase.product.CPDBProductAPI;
import com.kohler.commons.taxonomies.TaxonomyQueryService;

/**
 * CPDB Product Updating class for APAC
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0
 */
public class APACCPDBProductAPIImpl extends AbstractCPDBProductAPI implements CPDBProductAPI{

	private static final Logger LOG = LoggerFactory.getLogger (APACCPDBProductAPIImpl.class);

	public static Map <String, String> productFilterNamespaceMap = null;

	private ValueFactory valueFactory;

	static{
		productFilterNamespaceMap = new HashMap<String, String>();
		productFilterNamespaceMap.put(Constants.INSTALLATION_TYPE, Constants.NS_INSTALLATION_TYPE);
		productFilterNamespaceMap.put(Constants.FTR_NUMBER_OF_HANDLES, Constants.NS_NUMBER_OF_HANDLES);
		productFilterNamespaceMap.put(Constants.FTR_HANDLE_STYLE, Constants.NS_HANDLE_STYLE);
		productFilterNamespaceMap.put(Constants.DESCRIPTION_PRODUCT, Constants.NS_DESCRIPTION_PRODUCT);
		productFilterNamespaceMap.put(Constants.FTR_WATER_CONSERVATION, Constants.NS_WATER_CONSERVATION);
		productFilterNamespaceMap.put(Constants.COLLECTION, Constants.NS_COLLECTION);
		productFilterNamespaceMap.put(Constants.FTR_CONFIGURATION, Constants.NS_CONFIGURATION);
		productFilterNamespaceMap.put(Constants.FTR_LITERS_PER_FLUSH, Constants.NS_LITERS_PER_FLUSH);
		productFilterNamespaceMap.put(Constants.FTR_BOWL_SHAPE, Constants.NS_BOWL_SHAPE);
		productFilterNamespaceMap.put(Constants.FTR_TRAPWAY_TYPE, Constants.NS_TRAPWAY_TYPE);
		productFilterNamespaceMap.put(Constants.FTR_MINIMUM_ROUGH_IN, Constants.NS_MINIMUM_ROUGH_IN);
		productFilterNamespaceMap.put(Constants.FTR_PRODUCT_TYPE, Constants.NS_PRODUCT_TYPE);
		productFilterNamespaceMap.put(Constants.MATERIAL, Constants.NS_MATERIAL);
		/*productFilterNamespaceMap.put(Constants.OVERALL_LENGTH_MM, Constants.NS_OVERALL_LENGTH);
		productFilterNamespaceMap.put(Constants.OVERALL_HEIGHT_MM, Constants.NS_OVERALL_HEIGHT);
		productFilterNamespaceMap.put(Constants.OVERALL_WIDTH_MM, Constants.NS_OVERALL_WIDTH);*/
		productFilterNamespaceMap.put(Constants.FTR_LIGHTING_TYPE, Constants.NS_LIGHTINGTYPE);
		productFilterNamespaceMap.put(Constants.FTR_MIRROR_DEFOGGER, Constants.NS_MIRRORDEFOGGER);
		productFilterNamespaceMap.put(Constants.FTR_FLUSHING_SYSTEM, Constants.NS_FLUSHINGTECHNOLOGY);
		productFilterNamespaceMap.put(Constants.FTR_BOWL_STYLE, Constants.NS_BOWLSTYLE);
		//productFilterNamespaceMap.put(Constants.FTR_STYLE, Constants.NS_STYLE);
		productFilterNamespaceMap.put(Constants.FTR_LENGTH_RANGE_MM, Constants.NS_LENGTHRANGE);
		productFilterNamespaceMap.put(Constants.FTR_GLASS_THICKNESS_MM, Constants.NS_GLASSTHICKNESSMM);
		//productFilterNamespaceMap.put(Constants.FTR_FEATURE, Constants.NS_FEATURE);
		productFilterNamespaceMap.put(Constants.FTR_SHAPE, Constants.NS_SHAPE);
	}

	public APACCPDBProductAPIImpl(){

	}

	public APACCPDBProductAPIImpl(String contentBase, String productFolder, String locale, String country){
		super(contentBase, productFolder, locale, country);
	}

	@Override
	public Map<String, Object> createProducts(Session session, Map<String, Product> products) {
		List<String> productIds = new ArrayList<String> ();
		List<String> noWebUploadProductIds = new ArrayList<String> ();
		Map<String,String> failedProductIds = new HashMap<String,String> ();
		Date beginTime = null;
		Date endTime = null;
		Jcrom jcrom = new Jcrom ();
		jcrom.mapPackage (Constants.JCROM_MAP_PACKAGE);
		Map<String, Object> returnMap = new HashMap<String, Object> ();
		try {
			valueFactory = session.getValueFactory();
			Map<String,List<String>> directoriesFolders = getallDirectories(session);
			TaxonomyQueryService taxonomyQueryService = new TaxonomyQueryService (session, locale, country);
			taxonomyMap = taxonomyQueryService.getTaxonomyCategories (Constants.TAXONOMY_NODE_NAME);
			LOG.info ("Processing creation of products");
			Node parentNode = JcrUtils.getNodeIfExists ("/" + contentBase + productFolder, session);
			if (parentNode == null) {
				Node rootNode = session.getRootNode ().getNode(contentBase);
				ProductFolder productFolder = new ProductFolder ();
				productFolder.setNodeName (Constants.PRODUCT_FOLDER_NODE_NAME);
				productFolder.setName (Constants.PRODUCT_FOLDER_NAME);
				productFolder.setLocale (locale + "_" + country);
				productFolder.setTranslationId (UUID.randomUUID ().toString ());
				parentNode = jcrom.addNode (rootNode, productFolder,
						new String[] { Constants.HIPPOTRANSLATION_TRANSLATED, Constants.MIX_VERSIONABLE,
								Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE, Constants.MIX_SIMPLE_VERSIONABLE,
								Constants.PRODUCT_FOLDER_PRIMARY_TYPE });
				session.save ();

			}

			String[] mixins = {Constants.MIX_REFERENCEABLE, Constants.HIPPO_CONTAINER, Constants.HIPPO_DERIVED,
					Constants.HIPPOSTD_CONTAINER, Constants.HIPPOSTD_PUBLISHABLE,
					Constants.HIPPOSTD_PUBLISHABLE_SUMMARY, Constants.HIPPOSTD_RELAXED,
					Constants.HIPPOSTDPUBWF_DOCUMENT, Constants.HIPPOTRANSLATION_TRANSLATED, Constants.MIX_VERSIONABLE,
					Constants.MIX_SIMPLE_VERSIONABLE, Constants.HIPPOTAXONOMY_CLASSIFIABLE };
			beginTime = new Date ();
			String langugeJsonFieldSuffix = "";
			Boolean hashCheck = true;
			Locale bundleLocale = new Locale(locale, country);
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, bundleLocale);
			if (!Strings.isNullOrEmpty(bundle.getString(Constants.HASH_CHECK))) {
				hashCheck = false;
			}
			if(!locale.equalsIgnoreCase(Constants.LOCALE_EN)){
				try {
					langugeJsonFieldSuffix = bundle.getString(Constants.COUNTRY_JSON_FIELD_SUFFIX);
				} catch (Exception ex) { ex.printStackTrace(); }
			}

			for (Entry<String, Product> productEntry : products.entrySet()) {
				Product product = productEntry.getValue();
				
				//If WEB_INCLUDED = NO is present at attribute level or in all skus level then product won't be added
				Boolean isNoWebIncluded = checkNoWebIcluded(product);
				if(isNoWebIncluded){
					noWebUploadProductIds.add(productEntry.getKey());
					continue;
				}
				Long[] ancestors = product.getAncestors();
				if (product.getItemType().equalsIgnoreCase(Constants.PRODUCT_ITEM_TYPE)) {
					Boolean isAncestorExists = isAncestorExists(ancestors);
					if (!isAncestorExists) {
						if(ancestors!=null){
						failedProductIds.put(productEntry.getKey()," No Taxonomy ID " + Arrays.asList(ancestors) + " Found for Product Id " + productEntry.getKey());
						continue;
						}else
						{
							failedProductIds.put(productEntry.getKey()," No Taxonomy Found for Product Id " + productEntry.getKey());
							continue;
						}
					}
					if (!Strings.isNullOrEmpty(langugeJsonFieldSuffix)) {
						convertToLocale(product, langugeJsonFieldSuffix);
					}
					ObjectMapper mapper = new ObjectMapper();
					int productHash= mapper.writeValueAsString(product).hashCode();
					product.setProducthash(Long.valueOf(productHash));
		            Boolean isProductSameHashCode = isProductSameHashCode(session, productEntry.getKey(),productHash,directoriesFolders, Constants.NS_ITEM_NO, Constants.NS_PRODUCT_HASH);
		            if(hashCheck && isProductSameHashCode)
		            {
			            productIds.add(productEntry.getKey ());
			            continue;
		            }
		            Map<String, Object> allProductAttributes = product.getAttributes();
					String atgDefaultCategory = (String) allProductAttributes.get(Constants.ATG_DEFAULT_CATEGORY);
					if (atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_EN) || atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_TW)
							|| atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_HK) || atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_ID)
							|| atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_KR)
							|| atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_TH) || atgDefaultCategory.equals(Constants.CMMERCIAL_BATHROOM_VN)) {
						String productType = (String) allProductAttributes.get(Constants.FTR_PRODUCT_TYPE);
						if (StringUtils.isNotEmpty(productType)) {
							if (productType.equals(Constants.ACCESSORIES_EN) || productType.equals(Constants.ACCESSORIES_HK)
									|| productType.equals(Constants.ACCESSORIES_TW) || productType.equals(Constants.ACCESSORIES_KR)
									|| productType.equals(Constants.ACCESSORIES_TH) || productType.equals(Constants.ACCESSORIES_VN)) {
								allProductAttributes.put(Constants.FTR_PRODUCT_TYPE, Constants.COMMERCIAL_ACCESSORIES);
							}
						}
						product.setAttributes(allProductAttributes);
					}
		            if (product.getAncestors () != null) {
						try{
							String folderName = product.getItemNo().replaceAll(Constants.PATTERN_KEEP_ALPHANUM, "");
							String firstLevelFolder=String.valueOf(folderName.charAt(0));
							String secondLevelFolder=String.valueOf(folderName.charAt(1));
							createTwoLevelDirectories(session, firstLevelFolder, secondLevelFolder);
							Node productNode = JcrUtils.getNodeIfExists("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey (), session);
							//Checking existing Product if not exists create New Product
							if(productNode==null){
								LOG.info("Creating for "+contentBase+"  Product Id--" + productEntry.getKey () + "");
								HandleNode handleNode = new HandleNode ();
								handleNode.setPath (contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder);
								handleNode.setNodeName (productEntry.getKey ());
								handleNode.setName (product.getItemNo ());

								Node secondLevelNode = JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder, session);
								Node workFlowNode = jcrom.addNode (secondLevelNode, handleNode,
										new String[] { Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE});
								workFlowNode.getSession().save();
								buildJCRNode (productEntry.getKey (), product, jcrom, workFlowNode, mixins,false);
								session.save ();
								LOG.info ("Workflow Started for product:" + productEntry.getKey () + "");
								HippoWorkspace hippoWorkspace = (HippoWorkspace) session.getWorkspace ();
								DocumentWorkflow workflow = (DocumentWorkflow) hippoWorkspace.getWorkflowManager ()
										.getWorkflow (Constants.DEFAULT, workFlowNode);
								workflow.obtainEditableInstance ();
								workflow.commitEditableInstance ();
								if(!session.getUserID().equals("admin")){
									workflow.publish();
								}
								//deletePrvAndDraft("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey (), session);
							}
							else
							{
                              LOG.info("Updating for "+contentBase+"  Product Id--" +productEntry.getKey ()+" preview Change "+bundle.getString(Constants.PREVIEW_CHANGE));
                  			if (!Strings.isNullOrEmpty(bundle.getString(Constants.PREVIEW_CHANGE))) {
								try{
									Node productUpdateNode=JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey ()+"/"+productEntry.getKey ()+"[2]", session);
									VersionManager vm=session.getWorkspace().getVersionManager();
									vm.checkout("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey ()+"/"+productEntry.getKey ()+"[2]");
									if(productUpdateNode!=null) {
									  buildJCRNode (productEntry.getKey (), product, jcrom, productUpdateNode, mixins,true);
									}
								}catch(Exception e)
								{
									LOG.error("Exception while updating Preview node1 " +e.getMessage());
									e.printStackTrace();
								}
                  			}
								try{
									Node productUpdateNode=JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey ()+"/"+productEntry.getKey (), session);
									buildJCRNode (productEntry.getKey (), product, jcrom, productUpdateNode, mixins,true);
								}catch(Exception e)
								{
									LOG.error("Exception while updating live node "+e.getMessage());
									e.printStackTrace();
								}
								try{
									Node productUpdateNode=JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +firstLevelFolder + "/" +secondLevelFolder+"/" +productEntry.getKey ()+"/"+productEntry.getKey ()+"[3]", session);
									if(productUpdateNode!=null)
									  buildJCRNode (productEntry.getKey (), product, jcrom, productUpdateNode, mixins,true);
								}catch(Exception e)
								{
									LOG.error("Exception while updating draft node "+e.getMessage());
									e.printStackTrace();
								}
								productNode.getSession().save();
							  }
							productIds.add (productEntry.getKey ());
						}catch(Exception e)
						{
							LOG.info ("Exception in product uploading ",e.getMessage());
							failedProductIds.put(productEntry.getKey (), e.getMessage());
						}
					}
				}
			}
			
			LOG.info ("$$$$$$$$$$$ PAYLOAD: " + productIds);
			LOG.info ("$$$$$$$$$$$ WEB_UPLOAD: NO " + noWebUploadProductIds);
			LOG.info ("$$$$$$$$$$$ Failed PAYLOAD: $$$$$$$$$$");
			for(Map.Entry<String, String> entry : failedProductIds.entrySet()){
				LOG.info ("Product ID: " + entry.getKey() + " : " + entry.getValue());
			}
			endTime = new Date ();
			returnMap.put (Constants.MESSAGE, Constants.MESSAGEBODY);
			returnMap.put (Constants.PAYLOAD, productIds);
			returnMap.put (Constants.FAILED_PAYLOAD, failedProductIds);
			returnMap.put (Constants.STATUS, Constants.STATUS_VALUE);
			returnMap.put (Constants.BEGIN_TIME_STAMP, beginTime);
			returnMap.put (Constants.END_TIME_STAMP, endTime);

		} catch (Exception e) {
			try {
				if(session!=null)
				 session.refresh (false);
			} catch (RepositoryException e1) {
				LOG.error (" RepositoryException while saving products into CMS ", e1.getMessage());
			}
			catch (Exception e2) {
				LOG.error (" Exception while saving products ", e2.getMessage());
			}
			LOG.info ("Error while saving products into CMS");
			endTime = new Date ();
			returnMap.put (Constants.MESSAGE, Constants.MESSAGEBODY_FAILED);
			returnMap.put (Constants.PAYLOAD, productIds);
			returnMap.put (Constants.FAILED_PAYLOAD, failedProductIds);
			returnMap.put (Constants.STATUS, Constants.STATUS_VALUE);
			returnMap.put (Constants.BEGIN_TIME_STAMP, beginTime);
			returnMap.put (Constants.END_TIME_STAMP, endTime);
		}
		return returnMap;
	}

	@Override
	public Map<String, String> dropProductsfromJcr(Session session, List<String> productIds) {
		return super.dropProductsfromJcr(session, productIds, Constants.NS_ITEM_NO);
	}

	@Override
	public boolean deleteOtherProducts(Session session, List<String> validproductIds) {
		boolean isProductDropped = true;
		if(validproductIds!=null && validproductIds.size()==0){
			LOG.info("Return False for empty Products");
			return false;
		}
		try {
			isProductDropped =  super.deleteOtherProducts(session, validproductIds, Constants.NS_ITEM_NO);
		} catch (Exception e) {
			isProductDropped = false;
			LOG.error(isProductDropped + "===Error while dropping the product-" + e.getMessage());
		}
		return isProductDropped;
	}


	/**
	 *
	 * @param productId
	 * @param product
	 *            containing product information
	 * @param jcrom
	 *            to persist mapped java object to JCR node in repository
	 * @param parentNode
	 *            under which product node will get persist
	 * @param mixins
	 *            consist of required mixin types for product
	 */
	private Node buildJCRNode(String productId, Product product, Jcrom jcrom, Node parentNode, String[] mixins,Boolean update) throws NoTaxonomyFoundExeption, Exception {
		String[] childMixins = { Constants.HIPPO_CONTAINER, Constants.HIPPOSTD_CONTAINER,
				Constants.HIPPOSTD_RELAXED };

		Calendar cal = Calendar.getInstance ();
		cal.setTime (new Date ());
		com.kohler.commons.jcr.beans.Product productJcr = new com.kohler.commons.jcr.beans.Product();
		BeanUtils.copyProperties (product, productJcr);
		String[] taxonomyArr = new String[product.getAncestors ().length];
		for (int i = 0; i < product.getAncestors ().length; i++) {
			if (taxonomyMap != null && taxonomyMap.size () > 0) {
				String taxonomyID = String.valueOf (product.getAncestors()[i]);
				String taxonomyString = taxonomyMap.get(taxonomyID);
				if(Strings.isNullOrEmpty(taxonomyString)){
					throw new NoTaxonomyFoundExeption("No Taxonomy ID " +taxonomyID+" Found for Product Id " +productId);
				}
				else{
					taxonomyArr[i] = taxonomyString;
				}
			}
		}

		if(update){
			parentNode.setProperty(Constants.NS_PRODUCT_HASH, product.getProducthash());
			parentNode.setProperty(Constants.HIPPOTAXONOMY_KEYS, taxonomyArr);
			parentNode.setProperty(Constants.NS_DISCONTINUED,productDiscontinued (productJcr, product.getSkus ()));
			Boolean isStar=isProductStar(productJcr);
			parentNode.setProperty(Constants.NS_STARPRODUCT,isStar);
			String[] styles=buildFeatureStyleValues(product.getAttributes(), parentNode,Constants.FTR_STYLE,Constants.FTR_STYLE_1,Constants.FTR_STYLE_2,Constants.NS_STYLE);
			if(styles.length>0) {
			 parentNode.setProperty(Constants.NS_STYLE,styles);
			}
			String[] features=buildFeatureStyleValues(product.getAttributes(), parentNode,Constants.FTR_FEATURE,Constants.FTR_FEATURE_1,Constants.FTR_FEATURE_2,Constants.NS_FEATURE);
			if(features.length>0) {
			 parentNode.setProperty(Constants.NS_FEATURE,features);
			}
			buildAttributes (true, product.getAttributes (), jcrom, parentNode, childMixins,
					Constants.NS_PRODUCT_ATTRIBUTES_NODE_NAME,update);
			buildSkus (product, jcrom, parentNode, childMixins,update);
			buildCrossSelling (product.getCrossSelling (), jcrom, parentNode, childMixins,update);
			buildAddCopy (product.getAdCopy (), jcrom, parentNode, childMixins,update);
			return parentNode;
		}else
		{
			if(taxonomyArr.length > 0 && taxonomyArr[0] != null){
				productJcr.setTaxonomyKeys (taxonomyArr);
			}
			productJcr.setLastModificationDate (cal);
			productJcr.setNodeName (productId);
			productJcr.setProductId (Long.valueOf (productId));
			productJcr.setLocale (locale + "_" + country);
			productJcr.setState (Constants.PUBLISHED);
			productJcr.setLastModifiedBy (Constants.USERNAME);
			productJcr.setCreatedBy (Constants.USERNAME);
			productJcr.setCreationDate (cal);
			productJcr.setPublicationDate (cal);
			productJcr.setName (productId);
			String newTranslationId = UUID.randomUUID ().toString ();
			productJcr.setTranslationId (newTranslationId);
			productJcr.setStateSummary (Constants.STATESUMMARY);
			productJcr.setListPrice(new Double(0.0));
			productJcr.setDiscontinued(productDiscontinued (productJcr,product.getSkus ()));
			Boolean isStar=isProductStar(productJcr);
			productJcr.setStarProduct(isStar);
			String[] styles=buildFeatureStyleValues(product.getAttributes(), parentNode,Constants.FTR_STYLE,Constants.FTR_STYLE_1,Constants.FTR_STYLE_2,Constants.NS_STYLE);
			if(styles.length>0) {
				productJcr.setStyle(styles);
			}
			String[] features=buildFeatureStyleValues(product.getAttributes(), parentNode,Constants.FTR_FEATURE,Constants.FTR_FEATURE_1,Constants.FTR_FEATURE_2,Constants.NS_FEATURE);
			if(features.length>0) {
				productJcr.setFeature(features);
			}
			Node productNode = jcrom.addNode(parentNode, productJcr, mixins);
			buildAttributes (true, productJcr.getAttributes (), jcrom, productNode, childMixins,
					Constants.NS_PRODUCT_ATTRIBUTES_NODE_NAME,update);
			buildSkus (product, jcrom, productNode, childMixins,update);
			buildCrossSelling (productJcr.getCrossSelling (), jcrom, productNode, childMixins,update);
			buildAddCopy (productJcr.getAdCopy (), jcrom, productNode, childMixins,update);
			return productNode;
		}
	}



	/**
	 *
	 * @param crossSellings
	 *            consists of Product Cross Selling Details
	 * @param jcrom
	 *            to persist mapped java object to JCR node in repository
	 * @param parentNode
	 *            under which CrossSelling node will get persist
	 * @param childMixins
	 *            consist of required mixin types for crossSelling JCR Node
	 */
	private void buildCrossSelling(Map<String, Long[]> crossSellings, Jcrom jcrom, Node parentNode,
			String[] childMixins,Boolean update) throws Exception {
		if(crossSellings==null){
			crossSellings=new HashMap<String,Long[]>();
			Long a[]={0l};
			crossSellings.put(Constants.RELATED_PRODUCTS, a);
			crossSellings.put(Constants.CROSS_PROMOTIONS, a);
		}
			if (crossSellings != null) {
				if(!crossSellings.containsKey(Constants.RELATED_PRODUCTS)){
					Long a[]={0l};
					crossSellings.put(Constants.RELATED_PRODUCTS, a);
				}
				if(!crossSellings.containsKey(Constants.CROSS_PROMOTIONS)){
					Long a[]={0l};
					crossSellings.put(Constants.CROSS_PROMOTIONS, a);
				}
				if(update){
					Set <String> updatedNodeKeySet = new HashSet<String>();
					NodeIterator nodeIterator = parentNode.getNodes(Constants.NS_CROSS_SELLING_NODENAME);
					while(nodeIterator.hasNext()){
						Node crossSellingsNode = nodeIterator.nextNode();
						Property property = crossSellingsNode.getProperty(Constants.NS_KEY);
						String propertyValue = property.getString();
						if(crossSellings.containsKey(propertyValue)){
							Long updateValues[] = crossSellings.get(propertyValue);
							Value[] valueArray = new Value[updateValues.length];
							for(int i = 0; i < updateValues.length; i++){
								valueArray[i] = valueFactory.createValue(updateValues[i]);
							}
							crossSellingsNode.setProperty(Constants.NS_VALUES, valueArray);
							updatedNodeKeySet.add(propertyValue);
						}else{
							crossSellingsNode.remove();
						}
					}
					for(Map.Entry<String, Long[]> entry : crossSellings.entrySet()){
						if(!updatedNodeKeySet.contains(entry.getKey())){
							CrossSelling crossSelling = new CrossSelling (entry.getKey (), entry.getValue ());
							crossSelling.setNodeName (Constants.NS_CROSS_SELLING_NODENAME);
							jcrom.addNode (parentNode, crossSelling, childMixins);
						}
					}
				}else{
					for (Entry<String, Long[]> entry : crossSellings.entrySet ()) {
						CrossSelling crossSelling = new CrossSelling (entry.getKey (), entry.getValue ());
						crossSelling.setNodeName (Constants.NS_CROSS_SELLING_NODENAME);
						jcrom.addNode (parentNode, crossSelling, childMixins);
					}
				}
			}
	}



	/**
	 *
	 * @param skus
	 *            consists of Product SKUS and its Attribute Details
	 * @param jcrom
	 *            to persist mapped java object to JCR node in repository
	 * @param productNode
	 *            under which SKUS node will get persist
	 * @param childMixins
	 *            consist of required mixin types for SKUS JCR Node
	 */
	private void buildSkus(Product product, Jcrom jcrom, Node productNode, String[] childMixins,Boolean update) throws Exception {
		List<Skus> skus = product.getSkus();
		if (skus != null) {
			String defaultSKUId = "";
			if (product.getAttributes() != null) {
				for (Entry<String, Object> entry:product.getAttributes().entrySet()) {
					String key = entry.getKey ();
					if (key.equalsIgnoreCase(Constants.DEFAULT_SKU)) {
						Object value = entry.getValue ();
						if (value instanceof String) {
							defaultSKUId = (String) value;
						}
					}
				}
			}

			String[] colorFinish = new String[skus.size ()];
			int i = 0;
			String releaseForShipment = null;
			Set<String> countries = new HashSet<String> ();
			Set<String> priceRangeSet = new HashSet<String> ();
			Set<String> priceSegmentSet = new HashSet<String> ();
			Double listPrice = new Double(0.0);
			Set <String> updatedSKUNodeSet = new HashSet<String>();
			for (Skus sku : skus) {
				Boolean isDefaultSku = false;
				if (!Strings.isNullOrEmpty(sku.getSku()) && !Strings.isNullOrEmpty(defaultSKUId)) {
					if (defaultSKUId.equalsIgnoreCase(sku.getSku())) {
						isDefaultSku = true;
					}
				}
				if (sku.getSkuAttributes() != null) {
					if ((sku.getSkuAttributes().get(Constants.WEB_INCLUDED) != null
							&& sku.getSkuAttributes().get(Constants.WEB_INCLUDED).toString().equalsIgnoreCase("NO"))) {
						continue;
					}
					sku.setSkuAttributes(new TreeMap<String, Object>(sku.getSkuAttributes()));
					com.kohler.commons.jcr.beans.Skus jcrSku = new com.kohler.commons.jcr.beans.Skus ();
					BeanUtils.copyProperties (sku, jcrSku);
					if(update){
						NodeIterator nodeIterator = productNode.getNodes(Constants.NS_SKU_NODE_NAME);
						Boolean notUpdated = true;
						while(nodeIterator.hasNext()){
							Node skusNode = nodeIterator.nextNode();
							Property property = skusNode.getProperty(Constants.NS_SKU);
							String skuValue = property.getString();
							if(skuValue.equalsIgnoreCase(sku.getSku())){
								buildAttributes (false, jcrSku.getSkuAttributes (), jcrom, skusNode, childMixins,
										Constants.NS_PRODUCT_ATTRIBUTES_NODE_NAME,update);
								notUpdated = false;
								updatedSKUNodeSet.add(skuValue);
								break;
							}
						}
						//Not Updated means new sku so needs to add it
						if(notUpdated)
						{
							updatedSKUNodeSet.add(sku.getSku());
							jcrSku.setNodeName (Constants.NS_SKU_NODE_NAME);
							Node skusNode = jcrom.addNode (productNode, jcrSku, childMixins);
							buildAttributes (false, jcrSku.getSkuAttributes (), jcrom, skusNode, childMixins,
									Constants.NS_PRODUCT_ATTRIBUTES_NODE_NAME,update);
						}
					}
					else
					{
						jcrSku.setNodeName (Constants.NS_SKU_NODE_NAME);
						Node skusNode = jcrom.addNode (productNode, jcrSku, childMixins);
						buildAttributes (false, jcrSku.getSkuAttributes (), jcrom, skusNode, childMixins,
								Constants.NS_PRODUCT_ATTRIBUTES_NODE_NAME,update);
					}
					for (Entry<String, ?> entry : sku.getSkuAttributes ().entrySet ()) {
						String key = entry.getKey ();
						Object value = entry.getValue ();
						if (key.indexOf ("SKU_") != -1) {
							countries.add (key);
						}else{
							if (value instanceof String) {
								if (key.equalsIgnoreCase ("IMG_SWATCH")) {
									colorFinish[i] = (String) value;
									i++;
								} else if (key.equalsIgnoreCase ("RELEASE_FOR_SHIPMENT") && isDefaultSku) {
									releaseForShipment = (String) value;
								}else if (key.equalsIgnoreCase ("PRICE_RANGE")) {
									priceRangeSet.add((String) value);
								}else if (key.equalsIgnoreCase (Constants.FTR_PRICE_SEGMENT)) {
									priceSegmentSet.add((String) value);
								}
								else if (key.equalsIgnoreCase ("LIST_PRICE")  && isDefaultSku) {
									if(value != null)
									{
										try{
											if (value.toString().matches(Constants.REG_EX_DOUBLE))
											{
												listPrice = Double.valueOf(value.toString());
											}
										}catch(Exception ex){
											LOG.error(ex.getMessage());
										}
									}
								}
							}
						}
					}
				}
			}
			//Needs to remove sku node which is not present in the list while update
			if(update){
				NodeIterator nodeIterator = productNode.getNodes(Constants.NS_SKU_NODE_NAME);
				while(nodeIterator.hasNext()){
					Node skusNode = nodeIterator.nextNode();
					Property property = skusNode.getProperty(Constants.NS_SKU);
					String skuValue = property.getString();
					if(!updatedSKUNodeSet.contains(skuValue)){
						skusNode.remove();
					}
				}
			}
			productNode.setProperty (Constants.NS_PRICE_RANGE, priceRangeSet.toArray (new String[priceRangeSet.size ()]));
			productNode.setProperty (Constants.NS_PRICE_SEGMENT, priceSegmentSet.toArray (new String[priceSegmentSet.size ()]));
			productNode.setProperty (Constants.NS_COUNTRY, countries.toArray (new String[countries.size ()]));
			productNode.setProperty (Constants.NS_COLOR, colorFinish);
			productNode.setProperty (Constants.NS_IS_NEW, productIsNew (releaseForShipment));
			productNode.setProperty (Constants.NS_LIST_PRICE, listPrice);

		}
	}



	/**
	 *
	 * @param productAttributes
	 *            having list of product attributes to describe the product
	 * @param jcrom
	 *            to persist mapped java object to JCR node in repository
	 * @param parentNode
	 *            under which Attributes node will get persist
	 * @param childMixins
	 *            consist of required mixin types for Attributes JCR Node
	 * @param nodeName
	 *            to set name of the JCR node which is mandatory to create JCR
	 *            node in Repository
	 */
	@SuppressWarnings("unchecked")
	private void buildAttributes(Boolean flag, Map<String, ?> productAttributes, Jcrom jcrom, Node parentNode, String[] childMixins,String nodeName,Boolean update) throws Exception {
		//Needs to reset Product Filter properties if it is update product
		
		if(update && flag){
			for (Map.Entry<String, String> entry : productFilterNamespaceMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				/*if ((key.equals(Constants.OVERALL_LENGTH_MM)) || (key.equals(Constants.OVERALL_HEIGHT_MM))
						|| (key.equals(Constants.OVERALL_WIDTH_MM))) {
					parentNode.setProperty(value, new Long (0));
				} else {
					parentNode.setProperty(value, "");
				}*/
				if (key.equals(Constants.FTR_PRODUCT_TYPE)) {
					try {
						Property productType = parentNode.getProperty(value);
						if (productType != null && StringUtils.isNotEmpty(productType.getPath())) {
							productType.remove();
						}
					} catch (Exception e) {

					}

				} else {
					parentNode.setProperty(value, "");
				}
			}
		}
		
		//in update case delete all nodes from parent node if no data available into the parent
		if (productAttributes == null && update) {
			NodeIterator nodeIterator = parentNode.getNodes(nodeName);
			while(nodeIterator.hasNext()){
				Node attributeNode = nodeIterator.nextNode();
				attributeNode.remove();
			}
		} else {
			Map<String, String[]> attributesForUpdateMap = new HashMap<String, String[]>();

			Map<String, String> productLevelValueMap = new HashMap<String, String>();

		if (productAttributes != null){
			for (Entry<String, ?> entry : productAttributes.entrySet ()) {
				String[] values = null;
				String key = entry.getKey ();
				Object value = entry.getValue ();
				if (value instanceof Map) {
					Map<String, ?> subAttribute = (Map<String, ?>) value;
					Collection<String> values1 = (Collection<String>) subAttribute.values ();
					values = new String[values1.toArray ().length];
					values = Arrays.copyOf (subAttribute.values ().toArray (),
							subAttribute.values ().toArray ().length, String[].class);

				} else if (value instanceof String) {
					values = new String[1];
					String valueStr = (String) value;
					values[0] = valueStr;
				}

				if(update){
					attributesForUpdateMap.put(key, values);
				}
				else{
					ProductAttributes productAttributesObj = new ProductAttributes (key, values);
					productAttributesObj.setNodeName (nodeName);
					jcrom.addNode(parentNode, productAttributesObj, childMixins);
				}


				if(productFilterNamespaceMap.containsKey(key)){
					 if(values.length > 0){
						 productLevelValueMap.put(key, values[0]);
					 }
				}
			}
		}
			//Setting product properties for filter
			for (Map.Entry<String, String> entry : productLevelValueMap.entrySet()) {
				if ((entry.getKey().equals(Constants.OVERALL_LENGTH_MM)) || (entry.getKey().equals(Constants.OVERALL_HEIGHT_MM)) || (entry.getKey().equals(Constants.OVERALL_WIDTH_MM))) {
					parentNode.setProperty(productFilterNamespaceMap.get(entry.getKey()),
							Long.parseLong(entry.getValue().trim().replaceAll("[^0-9]", "")));
				} else if (entry.getKey().equals(Constants.FTR_LENGTH_RANGE_MM)) {
					String allLengthRangeValues = entry.getValue();
					String below450mm = Constants.LENGTH_RANGE_BELOW_450_MM;
					String between450To599mm = Constants.LENGTH_RANGE_BELOW_450_500_MM;
					String between600To799mm = Constants.LENGTH_RANGE_BELOW_600_799_MM;
					String between800To999mm = Constants.LENGTH_RANGE_BELOW_800_999_MM;
					String between1000To1199mm = Constants.LENGTH_RANGE_BELOW_1000_1199_MM;
					String above1199mm = Constants.LENGTH_RANGE_ABOVE_1199_MM;
					try {
						int rangeValue = Integer.parseInt(allLengthRangeValues);
						if (rangeValue < 450) {
							allLengthRangeValues = below450mm;
						} else if (rangeValue >= 450 && rangeValue <= 599) {
							allLengthRangeValues = between450To599mm;
						} else if (rangeValue >= 600 && rangeValue <= 799) {
							allLengthRangeValues = between600To799mm;
						} else if (rangeValue >= 800 && rangeValue <= 999) {
							allLengthRangeValues = between800To999mm;
						} else if (rangeValue >= 1000 && rangeValue <= 1199) {
							allLengthRangeValues = between1000To1199mm;
						} else if (rangeValue > 1199) {
							allLengthRangeValues = above1199mm;
						}
					} catch (NumberFormatException numberformatException) {
						LOG.error(numberformatException.getMessage());
					}
					parentNode.setProperty(Constants.NS_LENGTHRANGE, allLengthRangeValues);
				} else if (entry.getKey().equals(Constants.FTR_LIGHTING_TYPE)) {
					if (entry.getValue().equals("Yes") || entry.getValue().equals("有") || entry.getValue().equals("포함")
							|| entry.getValue().equals("มี")) {
						parentNode.setProperty(Constants.NS_LIGHTINGTYPE, Constants.WITH_LIGHTING);
					} else if(entry.getValue().equals("No") || entry.getValue().equals("沒有") || entry.getValue().equals("불포함")
							|| entry.getValue().equals("ไม่มี")){
						parentNode.setProperty(Constants.NS_LIGHTINGTYPE, Constants.WITHOUT_LIGHTING);
					}

				} else if (entry.getKey().equals(Constants.FTR_PRODUCT_TYPE)) {
				// Rainhead and Panels for all EN sites
				if (entry.getValue().equals(Constants.RAINHEADS_EN) || entry.getValue().equals(Constants.SHOWER_PANEL_EN) || entry.getValue().equals(Constants.RAINHEADS_ID)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
					// Rainhead and Panels combine for TW site
				} else if (entry.getValue().equals(Constants.RAINHEADS_TW) || entry.getValue().equals(Constants.SHOWER_PANEL_TW)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
					// Rainhead and Panels combine for HK site
				} else if (entry.getValue().equals(Constants.RAINHEADS_HK) || entry.getValue().equals(Constants.SHOWER_PANEL_HK)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
					// Rainhead and Panels combine for KR site
				} else if (entry.getValue().equals(Constants.RAINHEADS_KR) || entry.getValue().equals(Constants.SHOWER_PANEL_EN)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
					// Rainhead and Panels combine for TH site
				} else if (entry.getValue().equals(Constants.RAINHEADS_TH) || entry.getValue().equals(Constants.SHOWER_PANEL_EN)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
					// Rainhead and Panels combine for VN site
				} else if (entry.getValue().equals(Constants.RAINHEADS_VN) || entry.getValue().equals(Constants.SHOWER_PANEL_EN)) {
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue()),
							valueFactory.createValue(Constants.RAINHEAD_PANELS_EN) };
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
				} else{
					Value[] multiValues = new Value[] { valueFactory.createValue(entry.getValue())};
					parentNode.setProperty(Constants.NS_PRODUCT_TYPE, multiValues);
				}
				
			} else if (entry.getKey().equals(Constants.COLLECTION)) {
				String replacedValue = entry.getValue().replace("™", "(TM)");
				replacedValue = replacedValue.replace("®", "(R)");
				parentNode.setProperty(productFilterNamespaceMap.get(entry.getKey()), replacedValue);
			} else {
					parentNode.setProperty(productFilterNamespaceMap.get(entry.getKey()), entry.getValue());
				}
			}

			//If update needs to update existing nodes or add new node if not exist
			if(update){
				Set <String> updatedNodeKeySet = new HashSet<String>();
				NodeIterator nodeIterator = parentNode.getNodes(nodeName);
				while(nodeIterator.hasNext()){
					Node attributeNode = nodeIterator.nextNode();
					Property property = attributeNode.getProperty(Constants.NS_KEY);
					String propertyValue = property.getString();
					if(attributesForUpdateMap.containsKey(propertyValue)){
						String updateValues[] = attributesForUpdateMap.get(propertyValue);
						attributeNode.setProperty(Constants.NS_VALUE, updateValues);
						updatedNodeKeySet.add(propertyValue);
					}else{
						attributeNode.remove();
					}
				}
				for(Map.Entry<String, String[]> attributeForUpdateEntry : attributesForUpdateMap.entrySet()){
					if(!updatedNodeKeySet.contains(attributeForUpdateEntry.getKey())){
						ProductAttributes productAttributesObj = new ProductAttributes (attributeForUpdateEntry.getKey(), attributeForUpdateEntry.getValue());
						productAttributesObj.setNodeName (nodeName);
						jcrom.addNode(parentNode, productAttributesObj, childMixins);
					}
				}
			}
		}
	}



	/**
	 *
	 * @param adCopy
	 *            consists of list adCopy in information related to the product
	 * @param jcrom
	 *            to persist mapped java object to JCR node in repository
	 * @param parentNode
	 *            under which AdCopy node will get persist
	 * @param childMixins
	 *            consist of required mixin types for adCopy JCR Node
	 */
	private void buildAddCopy(Map<String, String> adCopy, Jcrom jcrom, Node parentNode, String[] childMixins,Boolean update) throws Exception {
		buildAttributes (false, adCopy, jcrom, parentNode, childMixins, Constants.NS_ADCOPY_NODE_NAME,update);
	}

	/**
	 *
	 * @param ProductAttributes
	 *            consists of list ProductAttributes in information related to the product
	 * @param parentNode
	 *            under which AdCopy node will get persist
	 */
	@SuppressWarnings("unchecked")
	private String[] buildFeatureStyleValues(Map<String, ?> productAttributes, Node parentNode,String attribute,String attribute_1,String attribute_2,String nameSpaceAttribute)
			throws Exception {
		try {
			parentNode.getProperty(nameSpaceAttribute).remove();
		} catch (Exception e) {
		}
		Set<String> attributevalues = new HashSet<String>();
		for (Entry<String, ?> entry : productAttributes.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.toString().equals(attribute.toString()) || key.toString().equals(attribute_1.toString()) || key.toString().equals(attribute_2.toString())) {
				String[] styleArray = null;
				if (value instanceof Map) {
					Map<String, ?> subAttribute = (Map<String, ?>) value;
					Collection<String> values1 = (Collection<String>) subAttribute.values();
					styleArray = new String[values1.toArray().length];
					styleArray = Arrays.copyOf(subAttribute.values().toArray(), subAttribute.values().toArray().length,
							String[].class);
					String valueStr = styleArray[0];
					attributevalues.add(valueStr);
				}
				else if (value instanceof String) {
					String valueStr = (String) value;
					attributevalues.add(valueStr);
				}
			}
		}
		return attributevalues.toArray(new String[attributevalues.size()]);
	}

	/**
	 * Creates 2 level directories if not exists
	 * @param session
	 * @param firstlevelFolder Name
	 * @param secondLevelFolder Name
	 */
   private void createTwoLevelDirectories(Session session,String firstLevelFolder,String secondLevelFolder) {
	   try {
		   Node productNode = session.getRootNode ().getNode (contentBase + productFolder );
		   Jcrom jcrom = new Jcrom ();
		   jcrom.mapPackage (Constants.JCROM_MAP_PACKAGE);
		   Node productSubNode= JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +firstLevelFolder, session);
		   if(productSubNode==null){
			   ProductSubFolder productSubFolder = new ProductSubFolder ();
			   productSubFolder.setNodeName (firstLevelFolder);
			   productSubFolder.setName (firstLevelFolder);
			   productSubFolder.setLocale (locale + "_" + country);
			   productSubFolder.setTranslationId (UUID.randomUUID ().toString ());
			   productSubNode=jcrom.addNode (productNode, productSubFolder,
					   new String[] { Constants.HIPPOTRANSLATION_TRANSLATED, Constants.MIX_VERSIONABLE,
							   Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE, Constants.MIX_SIMPLE_VERSIONABLE,
							   Constants.PRODUCT_FOLDER_PRIMARY_TYPE });
			   session.save();
		   }
		   Node subNode=JcrUtils.getNodeIfExists ("/"  + contentBase + productFolder + "/" +firstLevelFolder+ "/" +secondLevelFolder, session);
		   if(subNode==null){
			   ProductSubFolder SubFolder = new ProductSubFolder ();
			   SubFolder.setNodeName (secondLevelFolder);
			   SubFolder.setName (secondLevelFolder);
			   SubFolder.setLocale (locale + "_" + country);
			   SubFolder.setTranslationId (UUID.randomUUID ().toString ());
			   jcrom.addNode(productSubNode, SubFolder,
					   new String[] { Constants.HIPPOTRANSLATION_TRANSLATED, Constants.MIX_VERSIONABLE,
							   Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE, Constants.MIX_SIMPLE_VERSIONABLE,
							   Constants.PRODUCT_FOLDER_PRIMARY_TYPE });
			   session.save();
		   }
	   }
	   catch(Exception e)
	   {
		   LOG.error("Exception Occurs-->" +e.getMessage());
	   }
   }

   /**
	 * This Private Method called by internal Method Name updatedRelatedProducts for update each crossSelling productNode
	 * @param session
	 * @param productId
	 * @param directoriesFolders
	 * @param similarProducts
	 */
	private Boolean updateNode (String itemNo, Session session, Map<String,List<String>> directoriesFolders, Map <Long, String> similarProducts) {
	   itemNo = itemNo.replaceAll(Constants.PATTERN_KEEP_ALPHANUM, "");
       Boolean update=false;
       if (!Strings.isNullOrEmpty(itemNo)) {
    	   Set<Long> keySet = similarProducts.keySet();
    	   if (!keySet.isEmpty()) {
    		   try{
    			   String productId = null;
    			   for (Long key : keySet) {
    				   productId = similarProducts.get(key);
    				   if (productId != null) {
    					   break;
    				   }
    			   }
    			   if (!Strings.isNullOrEmpty(productId)) {
	    			   valueFactory = session.getValueFactory();
	    			   Node productNode = JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId, session);
	    			   updateRelatedAllNodes(keySet, "/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId+"/" + productId, session);
	    			   updateRelatedAllNodes(keySet, "/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId+"/" + productId+"[3]", session);
	    			   productNode.getSession().save();
	    			   session.save();
    			   }
    		   }catch(Exception e)
    		   {
    			   LOG.error(e.getMessage());
    		   }
    	   }
       }
       return update;
	}


	/**
	 * Updated Similar Products for Each Products
	 * @param session
	 * @param productTypes
	 * @param productAncestors
	 * @return List of updated productsIds
	 */
	@Override
	public Map <String, Boolean> updatedRelatedProducts(Session session, Map<String, Map <Long, String>> relatedProductMap, long start, long end, Boolean logenable) {
		// TODO Auto-generated method stub
		Map <String, Boolean> updatedProducts = new HashMap<String, Boolean> ();
		int count=0;
		if (relatedProductMap != null) {
			try{
				Map<String,List<String>> directoriesFolders = getallDirectories(session);
				if(logenable){
					LOG.info("Updating Related products ");
				}
				for ( Map.Entry<String, Map <Long, String>> entry : relatedProductMap.entrySet()) {
					String productItemNo = entry.getKey();
					count++;
					LOG.info(count+"--updated similar productItemNo-->"+contentBase+"--"+productItemNo+" similarList-->"+entry.getValue());
					Map <Long, String> similarList = entry.getValue();
					if ((similarList == null) || (similarList.isEmpty())) {
						updatedProducts.put(productItemNo, false);
					}
					else {
						Boolean similarChangedFlag = true;
						//If same similar all the values of similarList will be null then no need update product
						for (Map.Entry<Long, String> similarEntry : similarList.entrySet()) {
							if (similarEntry.getValue() == null) {
								similarChangedFlag = false;
								break;
							}
						}
						if (similarChangedFlag) {
							Boolean flag = updateNode(productItemNo, session, directoriesFolders, similarList);
							updatedProducts.put(productItemNo, flag);
						} else {
							updatedProducts.put(productItemNo, true);
						}
					}
				}
			}catch(Exception e)
			{
				LOG.error("Exception while updating related products ", e.getMessage());
			}
		}else {
			LOG.info("Not Updating Related products map is null");
		}
		return updatedProducts;
	}

	private void updateRelatedAllNodes(Set<Long> keySet,String path,Session session){
		try{
		   LOG.info("Updating for nodePath-->" +path);
		   Node productUpdateNode = JcrUtils.getNodeIfExists (path, session);
		   if(productUpdateNode==null)
			   return;
		   NodeIterator nodeIterator = productUpdateNode.getNodes(Constants.NS_CROSS_SELLING_NODENAME);
		   Node relatedProductsNode = null;
		   while(nodeIterator.hasNext()){
			   Node crossSellingsNode = nodeIterator.nextNode();
			   Property property = crossSellingsNode.getProperty(Constants.NS_KEY);
			   String propertyValue = property.getString();
			   if(propertyValue.equalsIgnoreCase(Constants.RELATED_PRODUCTS)){
				   relatedProductsNode = crossSellingsNode;
			   }
		   }
		   if (relatedProductsNode == null) {
			    Long [] values = new Long [keySet.size()];
			    values = keySet.toArray(values);
			   	CrossSelling crossSelling = new CrossSelling (Constants.RELATED_PRODUCTS, values);
				crossSelling.setNodeName (Constants.NS_CROSS_SELLING_NODENAME);
				Jcrom jcrom = new Jcrom ();
				jcrom.mapPackage (Constants.JCROM_MAP_PACKAGE);
				String[] childMixins = { Constants.HIPPO_CONTAINER, Constants.HIPPOSTD_CONTAINER,
						Constants.HIPPOSTD_RELAXED };
				jcrom.addNode (productUpdateNode, crossSelling, childMixins);
		   }else {
			   Value[] valueArray = new Value[keySet.size()];
			   Integer i = 0;
			   for(Long key : keySet){
				   valueArray[i] = valueFactory.createValue(key);
				   i++;
			   }
			   relatedProductsNode.setProperty(Constants.NS_VALUES, valueArray);
		   }
		}catch(Exception e)
		{
			LOG.error("Exception in related nodes ",e.getMessage());
		}

	}




	/**
	 * Updated Cross Promotion for Each Products
	 * @param session
	 * @return List of updated productsIds
	 */
	@Override
	public Map <String, Boolean> updateCrossPromotionsProducts(Session session, Map<String, Map<Long, List<Long>>> crossPromotionsProductMap) {
		// TODO Auto-generated method stub
		Map <String, Boolean> updatedProducts = new HashMap<String, Boolean> ();
		if (crossPromotionsProductMap != null) {
			try{
				Map<String,List<String>> directoriesFolders = getallDirectories(session);
				for ( Map.Entry<String, Map <Long, List<Long>>> entry : crossPromotionsProductMap.entrySet()) {
					String productItemNo = entry.getKey();
					Map<Long, List<Long>> crossIds=entry.getValue();
					Map.Entry<Long,List<Long>> entryCrossProductId = crossIds.entrySet().iterator().next();
					Long productId=entryCrossProductId.getKey();
					List<Long> listCrossIds=entryCrossProductId.getValue();
					LOG.info(productId+" productId--updated cross Promotion productItemNo-->"+contentBase+"--"+productItemNo+" similarCrossList-->"+listCrossIds);
					if ((listCrossIds == null) || (listCrossIds.isEmpty())) {
						updatedProducts.put(productItemNo, false);
					}
					else {
						Boolean similarChangedFlag = true;
						if (similarChangedFlag) {
							Boolean flag = updateCrossPromotionsNode(productItemNo, session, directoriesFolders, productId, listCrossIds);
							updatedProducts.put(productItemNo, flag);
						} else {
							updatedProducts.put(productItemNo, true);
						}
					}
				}
			}catch(Exception e)
			{
				LOG.error("update in cross promotion ", e.getMessage());
			}
		}else {
			LOG.info("Not Updating Cross Promotion products map is null");
		}
		return updatedProducts;
	}


	/**
	 * This Private Method called by internal Method Name updateCrossPromotionsNode for update each crossSelling productNode
	 * @param session
	 * @param productId
	 * @param directoriesFolders
	 * @param similarProducts
	 */
	private Boolean updateCrossPromotionsNode (String itemNo, Session session, Map<String,List<String>> directoriesFolders,Long productId_l,List <Long> similarProducts) {
		itemNo = itemNo.replaceAll(Constants.PATTERN_KEEP_ALPHANUM, "");
		Boolean update=true;
       if (!Strings.isNullOrEmpty(itemNo)) {
    	   Set<Long> keySet = new HashSet<Long>();
    	   keySet.addAll(similarProducts);
    	   if (!keySet.isEmpty()) {
    		   try{
    			   if (productId_l!=null) {
    				   String productId=String.valueOf(productId_l);
	    			   valueFactory = session.getValueFactory();
	    			   Node productNode = JcrUtils.getNodeIfExists ("/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId, session);
	    			   updateCrossPromotionsAllNodes(keySet, "/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId+"/" + productId, session);
	    			   updateCrossPromotionsAllNodes(keySet, "/" + contentBase + productFolder + "/" +itemNo.charAt(0)+ "/" +itemNo.charAt(1)+"/" + productId+"/" + productId+"[3]", session);
	    			   productNode.getSession().save();
	    			   session.save();
	    			   return true;
    			   }
    		   }catch(Exception e)
    		   {
    			   LOG.error("Exception while updating cross promotion nodes ", e.getMessage());
    			   return false;
    		   }
    	   }
       }
       return update;
	}



	private void updateCrossPromotionsAllNodes(Set<Long> keySet, String path, Session session) {
		try {
			LOG.info("Updating for nodePath-->" + path);
			Node productUpdateNode = JcrUtils.getNodeIfExists(path, session);
			if (productUpdateNode == null)
				return;
			NodeIterator nodeIterator = productUpdateNode.getNodes(Constants.NS_CROSS_SELLING_NODENAME);
			Node relatedProductsNode = null;
			while (nodeIterator.hasNext()) {
				Node crossSellingsNode = nodeIterator.nextNode();
				Property property = crossSellingsNode.getProperty(Constants.NS_KEY);
				String propertyValue = property.getString();
				if (propertyValue.equalsIgnoreCase(Constants.CROSS_PROMOTIONS)) {
					relatedProductsNode = crossSellingsNode;
				}
			}
			if (relatedProductsNode == null) {
				Long[] values = new Long[keySet.size()];
				values = keySet.toArray(values);
				CrossSelling crossSelling = new CrossSelling(Constants.CROSS_PROMOTIONS, values);
				crossSelling.setNodeName(Constants.NS_CROSS_SELLING_NODENAME);
				Jcrom jcrom = new Jcrom();
				jcrom.mapPackage(Constants.JCROM_MAP_PACKAGE);
				String[] childMixins = { Constants.HIPPO_CONTAINER, Constants.HIPPOSTD_CONTAINER,
						Constants.HIPPOSTD_RELAXED };
				jcrom.addNode(productUpdateNode, crossSelling, childMixins);
			} else {
				Value[] valueArray = new Value[keySet.size()];
				Integer i = 0;
				for (Long key : keySet) {
					valueArray[i] = valueFactory.createValue(key);
					i++;
				}
				relatedProductsNode.setProperty(Constants.NS_VALUES, valueArray);
			}
		} catch (Exception e) {
			LOG.error("Exception while updating all cross promotion nodes ", e.getMessage());
		}
	}
	
	/*
	 check WEB_INCLUDED = No present in sku attribute level 
	 */
	private Boolean checkAllSKUsNoWebIncluded(List<Skus> skus) {
		Boolean isAllSkusNowebIncludedFlag = true;
		for (Skus sku : skus) {
			if (sku.getSkuAttributes() != null) {
				if ((sku.getSkuAttributes().get(Constants.WEB_INCLUDED) != null
						&& sku.getSkuAttributes().get(Constants.WEB_INCLUDED).toString().equalsIgnoreCase("NO"))) {
					continue;
				} else {
					isAllSkusNowebIncludedFlag = false;
					break;
				}
			}
		}
		return isAllSkusNowebIncludedFlag;
	}
	
	/*
	 check WEB_INCLUDED = No present in product level 
	 */
	private Boolean checkNoWebIcluded(Product product) {
		Boolean isAllSkusNowebIncluded = false;
		Object isWebIncludedObj = product.getAttributes().get(Constants.WEB_INCLUDED);
		if (isWebIncludedObj != null && isWebIncludedObj.toString().equalsIgnoreCase("NO")) {
			isAllSkusNowebIncluded = true;
		} else {
			isAllSkusNowebIncluded = checkAllSKUsNoWebIncluded(product.getSkus());
		}
		return isAllSkusNowebIncluded;

	}
}
