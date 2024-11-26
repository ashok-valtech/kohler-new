package com.kohler.commons.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.lang.UnhandledException;
import org.apache.jackrabbit.commons.JcrUtils;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.jcrom.Jcrom;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.jcr.beans.HandleNode;
import com.kohler.commons.jcr.beans.ProductFolder;
import com.kohler.commons.json.beans.Dealer;
import com.kohler.commons.servicebase.dealer.AbstractDealerAPI;
import com.kohler.commons.servicebase.dealer.DealerAPI;

/**
 * Dealer Updating class for APAC 
 * @author dhanwan.r
 * Date: 08/30/2017
 * @version 1.0 
 */
public class APACDealerAPIImpl extends AbstractDealerAPI implements DealerAPI {

	private static final Logger LOG = LoggerFactory.getLogger(APACDealerAPIImpl.class);


	public APACDealerAPIImpl(){

	}

	public APACDealerAPIImpl(String contentBase, String dealerFolderPath, String locale, String country){
		super(contentBase, dealerFolderPath, locale, country);
	}

	@Override
	public void dropAllDealers(Session session) {
		LOG.info("Deleting all Dealers...");
		try {
			Node dealesNode = session.getNode("/" + contentBase + dealerFolderPath);
			NodeIterator nodeItr = dealesNode.getNodes();
			dealesNode.hasNodes();
			while(nodeItr.hasNext()){
				Node childNode = (Node) nodeItr.next();
				childNode.remove();	
			}
			session.save();
			LOG.info("All Dealer documents are deleted");
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> createDelearFolder(Session session, List<Dealer> dealers) {
		Jcrom jcrom = new Jcrom();
		jcrom.mapPackage(Constants.JCROM_MAP_PACKAGE);
		Node parentNode = null;
		try {
			LOG.info("Processing creation of Dealers");
			parentNode = JcrUtils.getNodeIfExists("/" + contentBase + dealerFolderPath, session);
			if (parentNode == null) {
				LOG.info("Creating 'dealerdocuments' folder");
				Node rootNode = session.getRootNode().getNode(contentBase);
				ProductFolder dealersFolder = new ProductFolder();
				dealersFolder.setNodeName(Constants.DEALERS_FOLDER_NODE_NAME);
				dealersFolder.setName(Constants.DEALERS_FOLDER_NAME);
				dealersFolder.setLocale(locale);
				dealersFolder.setTranslationId(UUID.randomUUID().toString());
				parentNode = jcrom.addNode(rootNode, dealersFolder,
						new String[] { Constants.HIPPOTRANSLATION_TRANSLATED, Constants.MIX_VERSIONABLE,
								Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE, Constants.MIX_SIMPLE_VERSIONABLE,
								Constants.PRODUCT_FOLDER_PRIMARY_TYPE });
				session.save();
				LOG.info("Created 'dealerdocuments' folder");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return addDealers(session, dealers, parentNode, jcrom);
	} 

	private List<String> addDealers(Session session, List<com.kohler.commons.json.beans.Dealer> dealerList, Node parentNode,
			Jcrom jcrom) {

		List<String> returnDealerList = new ArrayList<String>();
		String[] mixins = { Constants.MIX_REFERENCEABLE, Constants.HIPPO_CONTAINER, Constants.HIPPO_DERIVED,
				Constants.HIPPOSTD_CONTAINER, Constants.HIPPOSTD_PUBLISHABLE, Constants.HIPPOSTD_PUBLISHABLE_SUMMARY,
				Constants.HIPPOSTD_RELAXED, Constants.HIPPOSTDPUBWF_DOCUMENT, Constants.HIPPOTRANSLATION_TRANSLATED,
				Constants.MIX_VERSIONABLE, Constants.MIX_SIMPLE_VERSIONABLE, Constants.HIPPOTAXONOMY_CLASSIFIABLE };
		for (com.kohler.commons.json.beans.Dealer dealer : dealerList) {
			if (null != dealer.getStoreId() && !dealer.getStoreId().equals("")) {
				try {
					ObjectMapper mapper = new ObjectMapper();
					int dealerHash= mapper.writeValueAsString(dealer).hashCode();
					dealer.setDealerhash(Long.valueOf(dealerHash));
					Boolean isdealersSameHashCode = isDealerSameHashCode(session, dealer.getStoreId(), dealerHash, Constants.NS_DEALER_HASH);
					if(isdealersSameHashCode)
					{
					returnDealerList.add(dealer.getStoreId());
					continue;
					}
					Node isDealerExist=JcrUtils.getNodeIfExists("/" + contentBase + dealerFolderPath+"/"+ dealer.getStoreId(), session);
					if(isDealerExist==null){	
						HandleNode handleNode = new HandleNode();
						handleNode.setPath(contentBase);
						handleNode.setNodeName(dealer.getStoreId());
						handleNode.setName(dealer.getStoreId());
						Node workFlowNode = jcrom.addNode(parentNode, handleNode,
								new String[] { Constants.HIPPO_NAMED, Constants.MIX_REFERENCEABLE });
						isDealerExist=buildJCRNode(dealer, jcrom, workFlowNode, mixins, session.getUserID(),false);
						LOG.info("Saving Dealer:" + "dealer" + dealer.getStoreId() + " into CMS");
						returnDealerList.add(dealer.getStoreId());
						session.save();
						LOG.info("Workflow Started for Dealer:" + dealer.getStoreId() + "");
						HippoWorkspace hippoWorkspace = (HippoWorkspace) session.getWorkspace();
						DocumentWorkflow workflow = (DocumentWorkflow) hippoWorkspace.getWorkflowManager()
								.getWorkflow(Constants.DEFAULT, workFlowNode);
						workflow.obtainEditableInstance();
						workflow.commitEditableInstance();
						workflow.publish();
						session.save();
					}else{
						LOG.info("Updating Dealer:" + "dealer" + dealer.getStoreId() + " into CMS");
						try{
							Node updateDealer=JcrUtils.getNodeIfExists("/" + contentBase + dealerFolderPath+"/"+ dealer.getStoreId()+"/"+ dealer.getStoreId(), session);
							buildJCRNode(dealer, jcrom, updateDealer, mixins, session.getUserID(),true);
							returnDealerList.add(dealer.getStoreId());
						}catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Exception in updating live node");
						}
						try{
							Node updateDealer1=JcrUtils.getNodeIfExists("/" + contentBase + dealerFolderPath+"/"+ dealer.getStoreId()+"/"+ dealer.getStoreId()+"[3]", session);
							buildJCRNode(dealer, jcrom, updateDealer1, mixins, session.getUserID(),true);
						}catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Exception in updating draft node");	
						}
						isDealerExist.getSession().save();
					}
				} catch (AccessDeniedException | ItemExistsException | ReferentialIntegrityException
						| ConstraintViolationException | InvalidItemStateException | VersionException | LockException
						| NoSuchNodeTypeException | RemoteException | WorkflowException e) {
					e.printStackTrace();
				} catch (RepositoryException e) {
					e.printStackTrace();
				}catch(JsonProcessingException e)
				{
					e.printStackTrace();
				}
			}
		}
		Set<String> existingDealers=getExistingDealers(session);
		existingDealers.removeAll(returnDealerList);
		dropOldDealers(existingDealers, session);
		return returnDealerList;
	}

	private Node buildJCRNode(com.kohler.commons.json.beans.Dealer dealer, Jcrom jcrom, Node parentNode,
			String[] mixins, String userName,Boolean update) {
		try {
			if(!update) {
				LOG.info("Creating Dealer-->"+dealer.getStoreId().trim());
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				com.kohler.commons.jcr.beans.Dealer dealerJcr = new com.kohler.commons.jcr.beans.Dealer();
				LOG.info("Copied all properties to Dealer for Dealer" + dealer.getStoreId());
				dealerJcr.setStoreId(dealer.getStoreId().trim());
				dealerJcr.setStoreName(dealer.getStoreName());
				dealerJcr.setPrimaryContactName(dealer.getPrimaryContactName().trim());
				dealerJcr.setAddressLineOne(dealer.getAddressLineOne().trim());
				dealerJcr.setAddressLineTwo(dealer.getAddressLineTwo().trim());
				dealerJcr.setAddressLineThree(dealer.getAddressLineThree().trim());
				dealerJcr.setCity(dealer.getCity().trim());
				dealerJcr.setDealerState(dealer.getDealerState().trim());
				dealerJcr.setZipCode(dealer.getZipCode().trim());
				dealerJcr.setCountry(dealer.getCountry().trim());
				dealerJcr.setLatitude(dealer.getLatitude().trim());
				dealerJcr.setLongitude(dealer.getLongitude().trim());
				dealerJcr.setPhone(dealer.getPhone());
				dealerJcr.setMobile(dealer.getMobile());
				dealerJcr.setFax(dealer.getFax());
				dealerJcr.setGeneralEmail(dealer.getGeneralEmail());
				dealerJcr.setPrimaryContactEmail(dealer.getPrimaryContactEmail());
				dealerJcr.setWebsite(dealer.getWebsite());
				dealerJcr.setFacebookLink(dealer.getFacebookLink());
				dealerJcr.setTwitterLink(dealer.getTwitterLink());
				dealerJcr.setInstagramLink(dealer.getInstagramLink());
				dealerJcr.setLineLink(dealer.getLineLink());
				dealerJcr.setDealerType(dealer.getDealerType());
				dealerJcr.setDescription(dealer.getDescription());
				dealerJcr.setOpeningHours(dealer.getOpeningHours());
				dealerJcr.setStoreLogo(dealer.getStoreLogo());
				dealerJcr.setDetailView(dealer.getDetailView());
				dealerJcr.setShowroomType(dealer.getShowroomType());
				dealerJcr.setNodeName(dealer.getStoreId());
				dealerJcr.setCreatedBy(Constants.USERNAME);
				dealerJcr.setCreationDate(cal);
				dealerJcr.setLocale(locale);
				dealerJcr.setLastModificationDate(cal);
				dealerJcr.setState(Constants.PUBLISHED);
				dealerJcr.setLastModifiedBy(Constants.USERNAME);
				dealerJcr.setStateSummary(Constants.STATESUMMARY);
				dealerJcr.setName(dealer.getStoreId());
				dealerJcr.setDealerHash(dealer.getDealerhash());
				String newTranslationId = UUID.randomUUID().toString();
				dealerJcr.setTranslationId(newTranslationId);
				jcrom.addNode(parentNode, dealerJcr, mixins);
			}else
			{
				LOG.info("Updating Dealer-->"+dealer.getStoreId().trim());
				parentNode.setProperty(Constants.NS_STOREID, dealer.getStoreId().trim());	
				parentNode.setProperty(Constants.NS_STORENAME, dealer.getStoreName().trim());
				parentNode.setProperty(Constants.NS_PRIMARY_CONTACT_NAME, dealer.getPrimaryContactName().trim());
				parentNode.setProperty(Constants.NS_ADDRESSLINE_ONE, dealer.getAddressLineOne().trim());
				parentNode.setProperty(Constants.NS_ADDRESSLINE_TWO, dealer.getAddressLineTwo().trim());
				parentNode.setProperty(Constants.NS_ADDRESSLINE_THREE, dealer.getAddressLineThree().trim());
				parentNode.setProperty(Constants.NS_CITY, dealer.getCity().trim());
				parentNode.setProperty(Constants.NS_STATE, dealer.getDealerState().trim());
				parentNode.setProperty(Constants.NS_ZIPCODE, dealer.getZipCode().trim());
				parentNode.setProperty(Constants.NS_COUNTRY, dealer.getCountry().trim());
				parentNode.setProperty(Constants.NS_PHONE, dealer.getPhone());
				parentNode.setProperty(Constants.NS_MOBILE, dealer.getMobile());
				parentNode.setProperty(Constants.NS_GENERAL_EMAIL, dealer.getGeneralEmail().trim());
				parentNode.setProperty(Constants.NS_PRIMARY_CONTACT_EMAIL, dealer.getPrimaryContactEmail().trim());
				parentNode.setProperty(Constants.NS_WEBSITE, dealer.getWebsite().trim());
				parentNode.setProperty(Constants.NS_DEALERTYPE, dealer.getDealerType().trim());
				parentNode.setProperty(Constants.NS_DESCRIPTION, dealer.getDescription().trim());
				parentNode.setProperty(Constants.NS_LATITUDE, dealer.getLatitude().trim());
				parentNode.setProperty(Constants.NS_LONGITUDE, dealer.getLongitude().trim());
				parentNode.setProperty(Constants.NS_FACEBOOK_LINK, dealer.getFacebookLink().trim());
				parentNode.setProperty(Constants.NS_TWITTER_LINK, dealer.getTwitterLink().trim());
				parentNode.setProperty(Constants.NS_OPENING_HOURS, dealer.getOpeningHours());
				parentNode.setProperty(Constants.NS_LINE_LINK, dealer.getLineLink().trim());
				parentNode.setProperty(Constants.NS_INSTAGRAM_LINK, dealer.getInstagramLink().trim());
				parentNode.setProperty(Constants.NS_STORE_LOGO, dealer.getStoreLogo().trim());
				parentNode.setProperty(Constants.NS_FAX, dealer.getFax().trim());
				parentNode.setProperty(Constants.NS_DETAIL_VIEW, dealer.getDetailView());
				parentNode.setProperty(Constants.NS_SHOWROOM_TYPE, dealer.getShowroomType().trim());
				parentNode.setProperty(Constants.NS_DEALER_HASH,dealer.getDealerhash());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return parentNode;
	}

	private Set<String> getExistingDealers(Session session) {
		LOG.info("get Existing all Dealers...");
		Set<String> dealerIds=new HashSet<String>();
		try {
			Node dealesNode = session.getNode("/" + contentBase + dealerFolderPath);
			NodeIterator nodeItr = dealesNode.getNodes();
			while(nodeItr.hasNext()){
				Node childNode = (Node) nodeItr.next();
				dealerIds.add(childNode.getName());
			}
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return dealerIds;
	}

	private void dropOldDealers(Set<String> oldDealers,Session session) {
		LOG.info("Deleting old Dealers...");
		try {
			for(String dealerId: oldDealers){
				Node dealesNode = session.getNode("/" + contentBase + dealerFolderPath+"/"+dealerId);
				System.out.println("Deleting Old Dealer-->" +dealesNode.getName());
				dealesNode.remove();
			}	
			session.save();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDealerSameHashCode(final Session session, String dealerId,int dealerHashcode, String NS_DEALER_HASH) {
		try {
			  Boolean isdealerExist = session.itemExists ("/"  + contentBase + dealerFolderPath +  "/" + dealerId + "/" +dealerId);
				if (isdealerExist) {
					Node dealerNode = session.getNode  ("/"  + contentBase + dealerFolderPath +  "/" + dealerId + "/" +dealerId);
					Long hashCode=dealerNode.getProperty(NS_DEALER_HASH).getValue().getLong();
					if(hashCode==dealerHashcode)
					  return true;
					else
					  return false;	
				} else {
					LOG.info ("dealer:" + dealerId + " is not found");
					return false;
				}
		} catch (Exception e) {
			LOG.error ("Error while getting the product hashcode-" + e.getMessage ());
			return false;
		}
	}
}
