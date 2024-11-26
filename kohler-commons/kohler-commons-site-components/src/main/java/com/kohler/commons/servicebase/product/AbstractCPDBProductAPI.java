/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/

package com.kohler.commons.servicebase.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.json.beans.Product;
import com.kohler.commons.json.beans.Skus;
import com.kohler.commons.service.APACCPDBProductAPIImpl;
/**
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0
 */
public class AbstractCPDBProductAPI {

	private static final Logger LOG = LoggerFactory.getLogger (APACCPDBProductAPIImpl.class);

	public Map<String, String> taxonomyMap = null;

	protected String contentBase;

	protected String productFolder;

	protected String locale;

	protected String country;

	public Set<String> countryFields = new HashSet<String>();

	public AbstractCPDBProductAPI(){

	}

	public AbstractCPDBProductAPI(String contentBase, String productFolder, String locale, String country){
		this.contentBase = contentBase;
		this.productFolder = productFolder;
		this.locale = locale;
		this.country = country;
		try {
			Locale bundleLocale = new Locale(locale, country);
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, bundleLocale);
			String countryJsonFields = bundle.getString(Constants.COUNTRY_JSON_FIELDS);
			String []countryJsonFieldsArray = countryJsonFields.split(",");
			for (String countryJsonField : countryJsonFieldsArray) {
				countryFields.add(countryJsonField.toUpperCase());
			}
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public boolean deleteOtherProducts(Session session, List<String> validproductIds, String NS_ITEM_NO) throws Exception{
		boolean isProductDropped = true;
		List<String> existingProducts = getAllProductIds(session);
		List<String> invalidProducts = new ArrayList<String>();
		for (String productId : existingProducts) {
			if (!validproductIds.contains(productId)) {
				invalidProducts.add(productId);
			}
		}
		LOG.info("====================invalid products========= " + invalidProducts);
		List<String> removedProducts = new ArrayList<String>();
		
		Map<String, List<String>> directoriesFolders = getallDirectories(session);
		for (String productId : invalidProducts) {
			try {
				String productItemNo = getProductItemNo(session,productId, directoriesFolders, NS_ITEM_NO);
				if (!productItemNo.isEmpty()) {
					String itemNo = productItemNo.replaceAll(Constants.PATTERN_KEEP_ALPHANUM, "");
					String firstLevelFolder = String.valueOf(itemNo.charAt(0));
					String secondLevelFolder = String.valueOf(itemNo.charAt(1));
					Node productNode = session.getNode(
							"/" + contentBase + productFolder + "/" + firstLevelFolder + "/" + secondLevelFolder + "/" + productId);
					productNode.remove();
					session.save();
					removedProducts.add(productItemNo);
					Node secondLevelFolderNode=session.getNode("/" + contentBase + productFolder + "/" + firstLevelFolder + "/"  +secondLevelFolder);
					if(secondLevelFolderNode.getNodes().getSize()==0){
						secondLevelFolderNode.remove();
						session.save();
						Node firstLevelFolderNode=session.getNode("/" + contentBase + productFolder + "/" + firstLevelFolder);
						if(firstLevelFolderNode.getNodes().getSize()==0){
							firstLevelFolderNode.remove();
							session.save();
						}
					}
				}
			} catch (Exception e) {
				StringBuilder message = new StringBuilder();
				message.append("===================================");
				message.append(e.getMessage());
				message.append(productId);
				LOG.error(message.toString());
				e.printStackTrace();
			}
		}
		LOG.info("====================removed products========= " + removedProducts);
		return isProductDropped;
	}


	public Map<String, String> dropProductsfromJcr(Session session, List<String> productIds, String NS_ITEM_NO ) {
		Map<String,String> failedProductIds = new HashMap<String,String> ();
		Map<String,List<String>> directoriesFolders=null;
		try {
			directoriesFolders = getallDirectories(session);
		} catch (Exception e) {
		}
		if(productIds.size()==1&&productIds.get(0).toLowerCase().toString().equalsIgnoreCase("ALL")){
			try {
				return deleteAllFiles(session);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String productId : productIds) {
			try {
				String productItemNo = getProductItemNo(session,productId,directoriesFolders, NS_ITEM_NO);
				if(!productItemNo.isEmpty()){
					productItemNo = productItemNo.replaceAll(Constants.PATTERN_KEEP_ALPHANUM, "");
					String firstLevelFolder=String.valueOf(productItemNo.charAt(0));
					String secondLevelFolder=String.valueOf(productItemNo.charAt(1));
					Boolean isProductExist = session.itemExists ("/" + contentBase + productFolder + "/" + firstLevelFolder + "/" +secondLevelFolder + "/" +productId);
					if (isProductExist) {
						Node productNode = session.getNode("/" + contentBase + productFolder + "/" + firstLevelFolder + "/"  +secondLevelFolder + "/" +productId);
						productNode.remove ();
						session.save();
						failedProductIds.put(productId, "Product Deleted Successfully");
					} else {
						failedProductIds.put(productId, "No Product Exists");
					}
					Node secondLevelFolderNode=session.getNode("/" + contentBase + productFolder + "/" + firstLevelFolder + "/"  +secondLevelFolder);
					if(secondLevelFolderNode.getNodes().getSize()==0){
						secondLevelFolderNode.remove();
						session.save();
						Node firstLevelFolderNode=session.getNode("/" + contentBase + productFolder + "/" + firstLevelFolder);
						if(firstLevelFolderNode.getNodes().getSize()==0){
							firstLevelFolderNode.remove();
							session.save();
						}
					}
				}
				else
				{
					failedProductIds.put(productId, "No Product Exists");
				}
			}
			catch (Exception e) {
				failedProductIds.put(productId, "Exception occur while deleting");
			}

		}
			return failedProductIds;
	}


	/**
	 * Gets two-level-directories folders
	 * @param session
	 * @return Map of two-level-directories folders
	 */
	public  Map<String, String>  deleteAllFiles(Session session) throws Exception
	{
		Map<String, String> deletedProductIds = new HashMap<String, String>();
		try{
			Node parentNode = JcrUtils.getNodeIfExists("/" + contentBase + productFolder, session);
			NodeIterator firstLevelNodeIterator = parentNode.getNodes();
			while(firstLevelNodeIterator.hasNext()){
			    Node firstLevelFolder=firstLevelNodeIterator.nextNode();
			    NodeIterator secondLevelNodeIterator = firstLevelFolder.getNodes();
			    while(secondLevelNodeIterator.hasNext()){
		        	 Node secondLevelFolder=secondLevelNodeIterator.nextNode();
		        	 NodeIterator secondLevelFolderNodes=secondLevelFolder.getNodes();
		        	 while(secondLevelFolderNodes.hasNext()){
		        		 Node secondNode=secondLevelFolderNodes.nextNode();
		        		 String nodeName=secondNode.getName();
		        		 try{
		        		 secondNode.remove();
		        		 deletedProductIds.put(nodeName, "Product Deleted Successfully");
		        		 }catch(Exception e)
		        		 {
		        		deletedProductIds.put(nodeName, "Product Deleted failed");
		        		 }
		        	 }
		        	 secondLevelFolder.remove();
		        	 session.save();
			    }
			    firstLevelFolder.remove();
			    session.save();
			}
		}catch (Exception ex){
			LOG.error(ex.getMessage() + "Exception getting directoreds from " + contentBase + productFolder);
		}
		return deletedProductIds;
	}

	/**
	 * Gets two-level-directories folders
	 * @param session
	 * @return Map of two-level-directories folders
	 */
	public Map<String,List<String>> getallDirectories(Session session) throws Exception
	{
		Map<String,List<String>> directoriesFolders=null;
		try{
			directoriesFolders=new HashMap<String,List<String>>();
			Node parentNode = JcrUtils.getNodeIfExists("/" + contentBase + productFolder, session);
			NodeIterator firstLevelNodeIterator = parentNode.getNodes();
			while(firstLevelNodeIterator.hasNext()){
			    Node firstLevelFolder=firstLevelNodeIterator.nextNode();
			    String firstLevelNodeName=firstLevelFolder.getName();
			    NodeIterator secondLevelNodeIterator = firstLevelFolder.getNodes();
			    List<String> secondLevelNodeList=new ArrayList<String>();
			    while(secondLevelNodeIterator.hasNext()){
		        	 Node secondLevelFolder=secondLevelNodeIterator.nextNode();
				       String secondLevelNodeName=secondLevelFolder.getName();
				        secondLevelNodeList.add(secondLevelNodeName);
			    }
			    directoriesFolders.put(firstLevelNodeName, secondLevelNodeList);
			}
		}catch (Exception ex){
			LOG.error(ex.getMessage() + "Exception getting directoreds from " + contentBase + productFolder);
		}
		return directoriesFolders;
	}


	public Boolean isAncestorExists(Long[] ancestors) {
		if (ancestors != null) {
			if (ancestors.length > 0) {
				Boolean ancestorFlag=true;
				for(long ancestorId:ancestors){
					String taxonomyID = String.valueOf(ancestorId);
					String taxonomyString = taxonomyMap.get(taxonomyID);
					if (Strings.isNullOrEmpty(taxonomyString)) {
						ancestorFlag=false;
					}
				}
				return ancestorFlag;
			} else
				return false;
		} else
			return false;

	}

	public List<String> getAllProductIds(Session session) {
		List<String> productIds=new ArrayList<String>();
		try {
			Node parentNode = JcrUtils.getNodeIfExists("/" + contentBase + productFolder, session);
			  NodeIterator firstLevelNodeIterator = parentNode.getNodes();
			  while(firstLevelNodeIterator.hasNext()){
				   Node firstLevelNode=firstLevelNodeIterator.nextNode();
			       NodeIterator secondLevelNodeIterator = firstLevelNode.getNodes();
			         while(secondLevelNodeIterator.hasNext()){
			        	 Node secondLevelFolder=secondLevelNodeIterator.nextNode();
			        	NodeIterator iterator=secondLevelFolder.getNodes();
						if(secondLevelFolder.getNodes().getSize()>0)
			             while(iterator.hasNext())
			             {
			              Node node=iterator.nextNode();
			              productIds.add(node.getName());
			             }
					   }
				}
		}catch(Exception e)
		{
			LOG.error(e.getMessage() + "Exception getting all product IDs from " + contentBase + productFolder);
			e.printStackTrace();
		}
	    return productIds;
	}

	/**
	 * Gets product ItemNO using productId
	 * @param session
	 * @param productId
	 * @param directoriesFolders
	 * @return product ItemNo
	 */
	public String getProductItemNo(Session session,String productId,Map<String,List<String>> directoriesFolders, String NS_ITEM_NO) {
		String itemNo = "";
		try {
			Set<String> firstLevelfolders=directoriesFolders.keySet();
			for (String folderName:firstLevelfolders) {
				List<String> secondLevelfolders=directoriesFolders.get(folderName);
				for(String  secondLevelFolderName: secondLevelfolders)
				{
					Node isNodeExists = JcrUtils.getNodeIfExists("/" + contentBase + productFolder + "/" + folderName + "/" + secondLevelFolderName, session);
					if (isNodeExists != null) {
						Node rootNode = session.getRootNode().getNode(contentBase + productFolder + "/" + folderName + "/" + secondLevelFolderName);
						NodeIterator iterator = rootNode.getNodes();
						while (iterator.hasNext()) {
							Node node = iterator.nextNode();
							if (node.getName().equals(productId)) {
								itemNo = node.getProperty("hippo:name").getValue().getString();
								break;
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemNo;
	}

	/**
	 *
	 * @param shipmentDateStr
	 *            to check the product is New or Not
	 * @return boolean success or failure
	 *      values(true/false)
	 *
	 */
	public boolean productIsNew(String shipmentDateStr) {
		boolean isNew = false;
		try {
			if (!Strings.isNullOrEmpty(shipmentDateStr)) {
				SimpleDateFormat sdf = new SimpleDateFormat (Constants.RELEASE_FOR_SHIPMENT_DATE_FORMAT);
				Date shipmentDate = sdf.parse (shipmentDateStr);
				Calendar c = Calendar.getInstance ();
				c.setTime (shipmentDate); // Now use today date.
				c.add (Calendar.DATE, 365); // Adding 365 days
				shipmentDate = c.getTime ();
				Date currentDate = new Date ();
				if (shipmentDate.after (currentDate)) {
					isNew = true;
				}
			}
		} catch (Exception e) {
			LOG.error ("Error while Calculating Product is New:" + e.getMessage ());
		}
		return isNew;
	}


	/**
	 * @param session
	 *            to get the session to do necessary manipulation of
	 *            JCR Nodes
	 * @param productId
	 *            containing productId Value
	 * @param productJsonHash
	 *            containing productId Hash Code Value
	 * @return boolean success or failure of Product HashCode check with Old Product
	 *      values(true/false)
	 */
	public boolean isProductSameHashCode(final Session session, String productId,int productJsonHash,Map<String,List<String>> directoriesFolders, String NS_ITEM_NO, String NS_PRODUCT_HASH) {
		try {
			String productItemNo = getProductItemNo(session,productId,directoriesFolders, NS_ITEM_NO);
			if(!productItemNo.isEmpty()){
				String firstLevelFolder=String.valueOf(productItemNo.charAt(0));
				String secondLevelFolder=String.valueOf(productItemNo.charAt(1));
			  Boolean isProductExist = session.itemExists ("/"  + contentBase + productFolder +  "/" + firstLevelFolder + "/" +secondLevelFolder +"/" + productId +"/" + productId);
				if (isProductExist) {
					Node productNode = session.getNode ("/" + contentBase + productFolder +  "/" + firstLevelFolder + "/" +secondLevelFolder + "/" + productId + "/" + productId);
					Long hashCode=productNode.getProperty(NS_PRODUCT_HASH).getValue().getLong();
					if(hashCode==productJsonHash)
					  return true;
					else
					  return false;
				} else {
					LOG.info ("Product:" + productId + " is not found");
					return false;
				}
			}else
			{
				return false;
			}
		} catch (Exception e) {
			LOG.error ("Error while getting the product hashcode-" + e.getMessage ());
			return false;
		}
	}

	/**
	 *
	 * @param product
	 * @param locale
	 * Method to add appropriate JSON field (as per locale) to product.
	 */
	public void convertToLocale(Product product, String langugeJsonFieldSuffix){
		locale = locale.toUpperCase();
		if(product.getAttributes() != null){
			product.setAttributes(convertAttributesToLocale(product.getAttributes(), langugeJsonFieldSuffix));
		}
		if(product.getSkus() != null){
			product.setSkus(convertSkusToLocale(product.getSkus(), langugeJsonFieldSuffix));
		}
		if(product.getAdCopy() != null){
			product.setAdCopy(convertAdCopyToLocale(product.getAdCopy(),langugeJsonFieldSuffix));
		}
	}

	protected Map<String, Object> convertAttributesToLocale(Map<String, Object> attributesOld, String langugeJsonFieldSuffix){
		Map<String, Object> attributesNew = new HashMap<String, Object>();
		if(attributesOld!=null) {
		for(Map.Entry<String, Object> entry : attributesOld.entrySet()){
			String key = entry.getKey();
			if(key.contains(langugeJsonFieldSuffix)){
				String newKey = key.substring(0, key.indexOf(langugeJsonFieldSuffix));
				attributesNew.put(newKey, entry.getValue());
			}else{
				if(!attributesNew.containsKey(key) && !countryFields.contains(key)){
					attributesNew.put(key, entry.getValue());
				}
			}
		 }
	  }
		return attributesNew;
	}

	protected List<Skus> convertSkusToLocale(List<Skus> skusOld, String langugeJsonFieldSuffix) {
		List<Skus> newSkus = new ArrayList<Skus>();
		if (skusOld != null) {
			for (Skus skus : skusOld) {
				Map<String, Object> attributesNew = new HashMap<String, Object>();
				attributesNew = convertAttributesToLocale(skus.getSkuAttributes(), langugeJsonFieldSuffix);
				skus.setSkuAttributes(attributesNew);
				newSkus.add(skus);
			}
		}
		return newSkus;
	}

	protected Map<String, String> convertAdCopyToLocale(Map<String, String> adCopy, String langugeJsonFieldSuffix){
		Map<String, String> adCopyNew = new HashMap<String, String>();
		if (adCopy !=null) {
		for(Map.Entry<String, String> entry : adCopy.entrySet()){
			String key = entry.getKey();
			if(key.contains(langugeJsonFieldSuffix)){
				String newKey = key.substring(0, key.indexOf(langugeJsonFieldSuffix));
				adCopyNew.put(newKey, entry.getValue());
			}else{
				if(!adCopyNew.containsKey(key) && !countryFields.contains(key.toUpperCase())){
					adCopyNew.put(key, entry.getValue());
				}
			}
		}
	 }
		return adCopyNew;
	}


	/**
	 *
	 * @param product
	 * @param skus
	 * @return Booleans true or false. If product's default sku contains discontinued date field it will return true or false.
	 */
	public Boolean productDiscontinued (com.kohler.commons.jcr.beans.Product product, List <Skus> skus){
		Boolean discontinuedFlag = false;
		if ( (product.getAttributes() != null) && (!product.getAttributes().isEmpty()) && (skus != null) && (!skus.isEmpty())) {
			Object defaultSkuIdObject = product.getAttributes().get(Constants.DEFAULT_SKU);
			if(defaultSkuIdObject != null){
				if (defaultSkuIdObject instanceof String) {
					String defaultSkuId = (String) defaultSkuIdObject;
					for (Skus sku : skus) {
						if(sku.getSku().equalsIgnoreCase(defaultSkuId)){
							if (sku.getSkuAttributes() != null) {
								Object discontinuedDateObj = sku.getSkuAttributes().get(Constants.DISCONTINUED_DATE);
								if(discontinuedDateObj != null){
									if(!Strings.isNullOrEmpty(discontinuedDateObj.toString())){
										discontinuedFlag = true;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return discontinuedFlag;
	}

	/**
	 *
	 * @param product
	 * @param skus
	 * @return Booleans true or false. If product's default sku contains discontinued date field it will return true or false.
	 */
	public Boolean isProductStar (com.kohler.commons.jcr.beans.Product product){
		Boolean isStar = false;
		try{
			if(product.getAttributes().get(Constants.STAR)!=null){
				Object starProductObject=	product.getAttributes().get(Constants.STAR);
				if (starProductObject instanceof String) {
					String  starProductValue=(String) starProductObject;
					if(starProductValue.equalsIgnoreCase("YES"))
						isStar=true;
				}
			}
		}catch(Exception e) {
			isStar=false;
		}
		return isStar;
	}

}
