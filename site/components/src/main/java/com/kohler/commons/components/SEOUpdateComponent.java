package com.kohler.commons.components;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.workflow.BaseWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.hippoecm.hst.util.PathUtils;
import org.hippoecm.hst.util.SearchInputParsingUtils;
import org.hippoecm.repository.HippoRepository;
import org.hippoecm.repository.HippoRepositoryFactory;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.util.JcrUtils;
import org.onehippo.repository.documentworkflow.DocumentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.kohler.beans.ArticleBannerDocument;
import com.kohler.beans.DocLinks;
import com.kohler.beans.GlobalProject;
import com.kohler.beans.MaterialColorPalette;
import com.kohler.beans.Pressreleases;
import com.kohler.beans.Product;
import com.kohler.beans.SeoCategory;
import com.kohler.beans.SeoSupport;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.dto.SeoRequest;
import com.kohler.commons.exceptions.DocumentInUseException;
import com.kohler.commons.service.ProductDetailService;
import com.kohler.commons.service.SeoService;
import com.kohler.commons.util.CommonUtil;

/**
 * SEO Update component class
 * @author dhanwan.r
 * Date: 10/07/2019
 * @version 1.0
 */
public class SEOUpdateComponent extends BaseHstComponent {

	private static final String HIPPOSTD_HOLDER = "hippostd:holder";

	private static final String ON = "on";

	public static final Logger LOGGER = LoggerFactory.getLogger(SEOUpdateComponent.class);

	private static final String FORWARD_SLASH = "/";

	@Override
	public void doBeforeRender(final HstRequest request, final HstResponse response) {
		super.doBeforeRender(request, response);
		Session session = null;
		String siteName;
		String seoTemplateName;
		String type;
		String isProduct;
		String seoTitle;
		String seoDesc;
		String seoPath;
		try {
			SeoRequest seoRequest = getRequestParameters(request.getRequestContext());
			SeoService seoService = new SeoService();
			Mount mount = request.getRequestContext().getResolvedMount().getMount();
			siteName = mount.getHstSite().getName();
			session = getSession(request);
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale bundleLocale = new Locale(language, country);
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.SEO_LABLES, bundleLocale);
			Map<String, Map<String, String>> siteSeoPagesValues = seoService.getSitePagesValues(request.getRequestContext().getSiteContentBasePath(), session, mount, bundle);
			Map<String, Map<String, String>> siteSeoProductCategories = seoService.getProductCategories(request,seoRequest,request.getRequestContext().getSiteContentBasePath(), session, mount, bundle);
			request.setAttribute(Constants.SITESEOPAGES, siteSeoPagesValues);
			request.setAttribute(Constants.SITESEOPROCATEGORYS, siteSeoProductCategories);
			request.setAttribute(Constants.SITENAME, siteName);
			seoTemplateName = request.getRequestContext().getServletRequest().getParameter(Constants.SEOTEMPLATENAME);
			type = request.getRequestContext().getServletRequest().getParameter(Constants.TYPE);
			isProduct = request.getRequestContext().getServletRequest().getParameter(Constants.ISPRODUCT);
			setDestinationPage(request);
			if ((type == null) && (seoTemplateName == null)) {
				return;
			}
			if ((seoTemplateName == null) && Boolean.valueOf(isProduct)) {
				request.setAttribute(Constants.ISPRODUCT, seoRequest.getIsProduct());
				return;
			}
			request.setAttribute(Constants.TYPE, type);
			request.setAttribute(Constants.ISPRODUCT, seoRequest.getIsProduct());
			request.setAttribute(Constants.SEOTEMPLATENAME, seoTemplateName);
			request.setAttribute(Constants.CURRENT_PAGE_ATTRIBUTE, seoRequest.getCurrentPage());
			if (!Strings.isNullOrEmpty(type) && type.equalsIgnoreCase(Constants.TEMPLATE)) {
				if (!Strings.isNullOrEmpty(seoRequest.getClassName())) {
					setALLPagesByPageName(request, seoTemplateName, seoRequest.getRelativePath(), seoRequest.getClassName(), bundle,seoRequest.getCurrentPage());
				}
				MessageFormat messageFormat = new MessageFormat(bundle.getString(Constants.SEOPATH));
				seoPath = messageFormat.format(new Object[] { seoTemplateName });
				getTemplateValues(seoPath, request, siteName, session,isProduct,bundle);
			} 
			else if (!Strings.isNullOrEmpty(seoRequest.getPageDocument())) {
				LOGGER.info(getLogMessage(request, siteName, seoTemplateName, seoRequest.getPageDocument()), "");
				Boolean success = false;
				StringBuilder documentPath = new StringBuilder(FORWARD_SLASH + request.getRequestContext().getSiteContentBasePath());
				String categoriesSiteMapItemList = bundle.getString(Constants.CATEGORY_SITEMAP_ITEM_LIST);
				if (Arrays.asList(categoriesSiteMapItemList.split(",")).contains(seoRequest.getSiteMapItem())) {
					seoRequest.setRelativePath(bundle.getString(Constants.CATEGORY_RELATIVE_BASE_PATH) + "/" + seoRequest.getSiteMapItem());
				} 
				documentPath.append(FORWARD_SLASH + seoRequest.getRelativePath());
				documentPath.append(FORWARD_SLASH + seoRequest.getPageDocument());
				success = executeUpdate(request, session, seoRequest, documentPath);
				setRequestAttributeByBoolean(request, success);
				request.setAttribute(Constants.REQUEST_UPDATED_DOCUMENT, seoRequest.getPageDocument());
				request.setAttribute(Constants.SITETITLEATTRIBUTE, seoRequest.getSeoTitle());
				request.setAttribute(Constants.DEFAULTMETADESCATTRIBUTE, seoRequest.getSeoDesc());
				setRequestAttributeByBoolean(request, seoRequest.getNoIndex(), Constants.NOINDEXATTRIBUTE);
				setRequestAttributeByBoolean(request, seoRequest.getNoFollow(), Constants.NOFOLLOWATTRIBUTE);
				request.setAttribute(Constants.CANONICALATTRIBUTE, seoRequest.getCanonicalUrl());
				request.setAttribute(Constants.PAGEDOCUMENTATTRIBUTE, seoRequest.getPageDocument());
				request.setAttribute(Constants.RELATIVEPATHATTRIBUTE, seoRequest.getRelativePath());
				request.setAttribute(Constants.CLASSNAMEATTRIBUTE, seoRequest.getClassName());
				request.setAttribute(Constants.SEOSITEMAPITEM, seoRequest.getSiteMapItem());
				request.setAttribute(Constants.CURRENT_PAGE_ATTRIBUTE, seoRequest.getCurrentPage());
				if (!Strings.isNullOrEmpty(seoRequest.getClassName())) {
					setALLPagesByPageName(request, seoTemplateName, seoRequest.getRelativePath(), seoRequest.getClassName(), bundle,seoRequest.getCurrentPage());
				}
				if (success) {
					LOGGER.info(getLogMessage(request, siteName, seoTemplateName, seoRequest.getPageDocument()),
							"Finished SuccessFully");
				} 
				else {
					LOGGER.info(getLogMessage(request, siteName, seoTemplateName, seoRequest.getPageDocument()),
							"Not Finished SuccessFully");
				}
			} 
			else if (!Strings.isNullOrEmpty(seoTemplateName)) {
				LOGGER.info(getLogMessage(request, siteName, seoTemplateName, seoRequest.getPageDocument()));
				MessageFormat messageFormat = new MessageFormat(bundle.getString(Constants.SEOPATH));
				seoPath = messageFormat.format(new Object[] { seoTemplateName });
				seoTitle = request.getRequestContext().getServletRequest().getParameter(Constants.SEOTITLEPARAMETER);
				seoDesc = request.getRequestContext().getServletRequest().getParameter(Constants.SEODESCRIPTIONPARAMETER);
				Boolean success = seoService.updateSeoDetails(seoTitle, seoDesc, seoPath, seoRequest.getNoIndex(),seoRequest.getNoFollow(), seoRequest.getCanonicalUrl(), session);
				setRequestAttributeByBoolean(request, success);
				String categoriesSiteMapItemList = bundle.getString(Constants.CATEGORY_SITEMAP_ITEM_LIST);
				if (Arrays.asList(categoriesSiteMapItemList.split(",")).contains(seoRequest.getSiteMapItem())) {
					seoRequest.setRelativePath(bundle.getString(Constants.CATEGORY_RELATIVE_BASE_PATH) + "/" + seoRequest.getSiteMapItem());
				} 
				if (!Strings.isNullOrEmpty(seoRequest.getRelativePath())) {
					setALLPagesByPageName(request, seoTemplateName, seoRequest.getRelativePath(), seoRequest.getClassName(), bundle,seoRequest.getCurrentPage());
				}
				getTemplateValues(seoPath, request, siteName, session,isProduct,bundle);
				if (success) {
					LOGGER.info(getLogMessage (request, siteName, seoTemplateName, seoRequest.getPageDocument()),
							"Finished SuccessFully");
				} 
				else {
					LOGGER.info(getLogMessage (request, siteName, seoTemplateName, seoRequest.getPageDocument()),
							"Not Finished SuccessFully");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occurs in SEO UpdateDocument ".concat(e.getMessage()));
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage());
			}
			request.setAttribute(Constants.ERROR_MESSAGE_KEY, Constants.ERRORMESSAGE);
		} finally {
			if (session != null && session.isLive()) {
				try {
					session.save();
					session.logout();
				} catch (Exception e) {
					LOGGER.error("Exception while saving the session ".concat(e.getMessage()));
				}
			}
		}

	}

	private Boolean executeUpdate(final HstRequest request, Session session, SeoRequest seoRequest, StringBuilder documentPath) {
		Boolean success = false;
		try {
			if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(ArticleBannerDocument.class.getName())){
				LOGGER.info("For articles update");
				success = updateArticle(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(), seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			} else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(GlobalProject.class.getName())) {
				LOGGER.info("For Global Project update");
				success = this.updateGlobalProject(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(), seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			} else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(MaterialColorPalette.class.getName())) {
				LOGGER.info("For colorpalette-list update");
				success = this.updateMaterialColorPalette(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(), seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			} else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(Pressreleases.class.getName())) {
				LOGGER.info("For pressreleases update");
				success = this.updatePressreleases(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(), seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			} else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(DocLinks.class.getName())) {
				LOGGER.info("For careandclean update");
				success = this.updateDocLinks(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(),seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			} else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(SeoCategory.class.getName())) {
				LOGGER.info("For SeoCategory update");
				success = this.updateSeoCategories(session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(),seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl());
			}
			else if (!Strings.isNullOrEmpty(seoRequest.getClassName()) && seoRequest.getClassName().equalsIgnoreCase(Product.class.getName())) {
				LOGGER.info("For Product update");
				success = this.updateProductCategory(request,session, documentPath.toString(), seoRequest.getSeoTitle(), seoRequest.getSeoDesc(),seoRequest.getNoIndex(), seoRequest.getNoFollow(), seoRequest.getCanonicalUrl(),seoRequest.getPageDocument());
			}
			
		} catch (DocumentInUseException e) {
			request.setAttribute(Constants.REQUEST_DOCUMENT_IN_USE, "TRUE");
		}
		return success;
	}

	private Session getSession(final HstRequest request) {
		Session session = null;
		try {
			Mount mount = request.getRequestContext().getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale bundleLocale = new Locale(language, country);
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, bundleLocale);
			String editorUserName = bundle.getString("editorUserName");
			String editorPassword = bundle.getString("editorPassword");
			request.setAttribute("language", language);
			request.setAttribute("country", country);
			HippoRepository hippoRepository = HippoRepositoryFactory.getHippoRepository("vm://");
			session = hippoRepository.login(editorUserName, editorPassword.toCharArray());
		} catch (Exception e) {
			LOGGER.error("Exception in getting Session ".concat(e.getMessage()));
			return null;
		}
		return session;
	}


	private void setALLPagesByPageName(HstRequest request, String templateName, String relativePath, String className, ResourceBundle bundle,String currentPage) {
		try {
			String ingoreList = bundle.getString(Constants.IGNORELIST);
			if (!Arrays.asList(ingoreList.split(",")).contains(templateName)) {
				Map<String, Map<String, String>> seoPageDocumentsValues = getSeoSupportDocuments(request, relativePath, className,currentPage,bundle);
				request.setAttribute(Constants.SEOPAGEDOCUMENTVALUES, seoPageDocumentsValues);
			}
		} catch (Exception e) {
			e.getMessage();
			LOGGER.error("Exception in getting resource bundle property ".concat(e.getMessage()));
		}
	}

	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if GlobalProject SEO fields updated
	 */
	protected Boolean updateGlobalProject(Session session, String documentPath, String seoTitle, String seoDescription, Boolean noIndex, Boolean noFollow, String canonicalUrl) {
		try {
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, RepositoryException{
								try {
									wf.publish();
								} catch (WorkflowException e) {
									if (LOGGER.isErrorEnabled()) {
										LOGGER.error(e.getMessage());
									}
								}
						}
					});
			
			GlobalProject globalProject = (GlobalProject) wpm.getObject(documentPath);
			if (globalProject.getProperty(HIPPOSTD_HOLDER) == null) {
				SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
				globalProject.setSeo(seoSupport);
				wpm.update(globalProject);
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		}
	}

	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if Article SEO fields updated
	 */
	protected Boolean updateArticle(Session session, String documentPath, String seoTitle, String seoDescription,
			Boolean noIndex, Boolean noFollow, String canonicalUrl) {
		try {
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
							try {
								wf.publish();
							} catch (WorkflowException e) {
								if (LOGGER.isErrorEnabled()) {
									LOGGER.error(e.getMessage());
								}
							}
						}
					});
			ArticleBannerDocument articleBannerDocument = (ArticleBannerDocument) wpm.getObject(documentPath);
			if (articleBannerDocument.getProperty(HIPPOSTD_HOLDER) == null) {
				articleBannerDocument.setSeoTitle(seoTitle);
				articleBannerDocument.setMetaDescription(seoDescription);
				articleBannerDocument.setNoIndex(noIndex);
				articleBannerDocument.setNoFollow(noFollow);
				articleBannerDocument.setCanonicalUrl(canonicalUrl);
				wpm.update(articleBannerDocument);
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		}
	}

	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if MaterialColorPalette SEO fields updated
	 */
	protected Boolean updateMaterialColorPalette(Session session, String documentPath, String seoTitle,
			String seoDescription, Boolean noIndex, Boolean noFollow, String canonicalUrl) {
		try {
			LOGGER.info("colorPallete document path-->" +documentPath);
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
							try {
								wf.publish();
							} catch (WorkflowException e) {
								if (LOGGER.isErrorEnabled()) {
									LOGGER.error(e.getMessage());
								}
							}
						}
					});
			MaterialColorPalette materialColorPalette = (MaterialColorPalette) wpm.getObject(documentPath);
			if (materialColorPalette.getProperty(HIPPOSTD_HOLDER) == null) {
				SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
				materialColorPalette.setSeo(seoSupport);
				wpm.update(materialColorPalette);
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		}
	}

	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if Pressreleases SEO fields updated
	 */
	protected Boolean updatePressreleases(Session session, String documentPath, String seoTitle, String seoDescription,
			Boolean noIndex, Boolean noFollow, String canonicalUrl) {
		try {
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
							try {
								wf.publish();
							} catch (WorkflowException e) {
								if (LOGGER.isErrorEnabled()) {
									LOGGER.error(e.getMessage());
								}
							}
						}
					});
			Pressreleases pressreleases = (Pressreleases) wpm.getObject(documentPath);
			if (pressreleases.getProperty(HIPPOSTD_HOLDER) == null) {
				SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
				pressreleases.setSeo(seoSupport);
				wpm.update(pressreleases);
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		}
	}
	
	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if Product SEO fields updated
	 */
	protected Boolean updateProductCategory(HstRequest request,Session session, String itemNo, String seoTitle,
			String seoDescription, Boolean noIndex, Boolean noFollow, String canonicalUrl,String productId) {
		try {
			ProductDetailService pds = new ProductDetailService(); 
			String productItemNo=itemNo.substring(itemNo.lastIndexOf('/') + 1);
			StringBuilder documentPath=new StringBuilder(itemNo.substring(0,itemNo.lastIndexOf('/')));
			documentPath.append(FORWARD_SLASH);
			documentPath.append(productItemNo.charAt(0));
			documentPath.append(FORWARD_SLASH);
			documentPath.append(productItemNo.charAt(1));
			documentPath.append(FORWARD_SLASH);
			Product updateProduct=pds.getProductByItemNo(request, productItemNo);
			String ProductId=String.valueOf(updateProduct.getId());
			documentPath.append(ProductId);
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
							try {
								wf.publish();
							} catch (WorkflowException e) {
								if (LOGGER.isErrorEnabled()) {
									LOGGER.error(e.getMessage());
								}
							}
						}
					});
			Product product = (Product) wpm.getObject(documentPath.toString());
			if (product.getProperty(HIPPOSTD_HOLDER) == null) {
				SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
				product.setSeo(seoSupport);
				wpm.update(product);
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param session
	 * @param documentPath
	 * @param seoTitle
	 * @param seoDescription
	 * @param noIndex
	 * @param noFollow
	 * @param canonicalUrl
	 * @return true if DocLinks SEO fields updated
	 */
	protected Boolean updateDocLinks(Session session, String documentPath, String seoTitle, String seoDescription,
			Boolean noIndex, Boolean noFollow, String canonicalUrl) {
		try {
			WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
			wpm.setWorkflowCallbackHandler(
					new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
						public void processWorkflow(
								DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
							try {
								wf.publish();
							} catch (WorkflowException e) {
								if (LOGGER.isErrorEnabled()) {
									LOGGER.error(e.getMessage());
								}
							}
						}
					});
			DocLinks docLinks = (DocLinks) wpm.getObject(documentPath);
			if (docLinks.getProperty(HIPPOSTD_HOLDER) == null) {
				SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
				docLinks.setSeo(seoSupport);
				wpm.update(docLinks);;
			} else {
				throw new DocumentInUseException ();
			}
			return true;
		} catch (ObjectBeanManagerException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
     * 
      * @param session
     * @param documentPath
     * @param seoTitle
     * @param seoDescription
     * @param noIndex
     * @param noFollow
     * @param canonicalUrl
     * @return true if SEO Categories SEO fields updated
     */
     protected Boolean updateSeoCategories(Session session, String documentPath, String seoTitle,
                String seoDescription, Boolean noIndex, Boolean noFollow, String canonicalUrl) {
           try {
                WorkflowPersistenceManager wpm = getWorkflowPersistenceManager(session);
                wpm.setWorkflowCallbackHandler(
                           new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                                public void processWorkflow(
                                           DocumentWorkflow wf) throws RemoteException, WorkflowException, RepositoryException{
                                     try {
                                           wf.publish();
                                     } catch (WorkflowException e) {
                                           if (LOGGER.isErrorEnabled()) {
                                                LOGGER.error(e.getMessage());
                                           }
                                     }
                                }
                           });
               SeoCategory seoCategory = (SeoCategory) wpm.getObject(documentPath);
                if (seoCategory.getProperty(HIPPOSTD_HOLDER) == null) {
                     SeoSupport seoSupport = new SeoSupport(seoTitle, seoDescription, canonicalUrl, noIndex, noFollow);
                     seoCategory.setSeo(seoSupport);
                     wpm.update(seoCategory);
                } else {
                     throw new DocumentInUseException ();
                }
                return true;
           } catch (ObjectBeanManagerException e) {
                if (LOGGER.isErrorEnabled()) {
                     LOGGER.error(e.getMessage(), e);
                }
                return false;
           }
     }

	

	private void getTemplateValues(String seoPath, HstRequest request, String sitename, Session session,String isProduct,ResourceBundle bundle) {
		try {
			SeoService seoService = new SeoService();
			if(Boolean.valueOf(isProduct)){
		     String containerPath = bundle.getString(Constants.HSTCONTAINERPATH);
			 String productSeoPath = containerPath + FORWARD_SLASH + Constants.PRODUCTS_DETAILS_CPATH + FORWARD_SLASH
						+ Constants.MAINSEO;
			Node producSeoNode = JcrUtils.getNodeIfExists(productSeoPath, session);
			if (producSeoNode != null) {
				Property parameterNames = producSeoNode.getProperty(Constants.HSTPARAMETERNAMES);
				Property parameterValues = producSeoNode.getProperty(Constants.HSTPARAMETERVALUES);
				Map <String, String> valueListMap = valuesToMap (parameterNames.getValues(), parameterValues.getValues());
				request.setAttribute(Constants.RELATIVEPATHATTRIBUTE,Constants.PRODUCT_FOLDER_NODE_NAME);
				request.setAttribute(Constants.CLASSNAMEATTRIBUTE, valueListMap.get(Constants.SEOCLASSNAME));
				request.setAttribute(Constants.SEOSITEMAPITEM, valueListMap.get(Constants.SEOSITEMAPITEM));
			 return;	
			} else
				return;
		  }	
			Node rootTaxonomy = JcrUtils.getNodeIfExists(seoPath, session);
			if (rootTaxonomy != null) {
				Property parameterNames = rootTaxonomy.getProperty(Constants.HSTPARAMETERNAMES);
				Property parameterValues = rootTaxonomy.getProperty(Constants.HSTPARAMETERVALUES);
				Map<String, String> valueListMap = valuesToMap(parameterNames.getValues(), parameterValues.getValues());
				setRequestAttributeString(request, valueListMap, Constants.SITETITLE, Constants.SITETITLEATTRIBUTE);
				setRequestAttributeString(request, valueListMap, Constants.DEFAULTMETADESC,
						Constants.DEFAULTMETADESCATTRIBUTE);
				setRequestAttributeBoolean(request, valueListMap, Constants.NOINDEXPARAMETER,
						Constants.NOINDEXATTRIBUTE);
				setRequestAttributeBoolean(request, valueListMap, Constants.NOFOLLOWPARAMETER,
						Constants.NOFOLLOWATTRIBUTE);
				setRequestAttributeString(request, valueListMap, Constants.CANONICALPARAMETER,
						Constants.CANONICALATTRIBUTE);
				setRequestAttributeString(request, valueListMap, Constants.SEOSITEMAPITEM, Constants.SEOSITEMAPITEM);
				Node siteMapNode = null;
				if (valueListMap.containsKey(Constants.SEOCLASSNAME)
						&& !Strings.isNullOrEmpty(valueListMap.get(Constants.SEOCLASSNAME))
						&& valueListMap.containsKey(Constants.SEOSITEMAPITEM)) {
					StringBuilder siteMap = new StringBuilder(Constants.HSTSHST);
					siteMap.append(FORWARD_SLASH + Constants.HSTCONFIGURATION);
					siteMap.append(FORWARD_SLASH + sitename);
					siteMap.append(FORWARD_SLASH + Constants.HSTSITEMAP);
					siteMap.append(FORWARD_SLASH + valueListMap.get(Constants.SEOSITEMAPITEM));
					siteMapNode = JcrUtils.getNodeIfExists(siteMap.toString(), session);
				}
				if (siteMapNode == null) {
					request.setAttribute(Constants.RELATIVEPATHATTRIBUTE, Constants.BLANK);
				} else {
					request.setAttribute(Constants.RELATIVEPATHATTRIBUTE,
							seoService.getPropertyFromNode(siteMapNode, Constants.HSTRELATIVECONTENTPATH));
				}
				setRequestAttributeString(request, valueListMap, Constants.SEOCLASSNAME, Constants.CLASSNAMEATTRIBUTE);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	private Map<String, String> valuesToMap(Value[] values, Value[] values1) {
		Map<String, String> valueList = new LinkedHashMap<>();
		for (Integer i = 0; i < values.length; i++) {
			try {
				valueList.put(values[i].getString(), values1[i].getString());
			} catch (IllegalStateException | RepositoryException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return valueList;
	}

	private void setRequestAttributeBoolean(HstRequest request, Map<String, String> valueListMap, String parameter,
			String requestAttribute) {
		if (valueListMap.containsKey(parameter) && valueListMap.get(parameter).equalsIgnoreCase(ON)) {
			request.setAttribute(requestAttribute, Constants.CHECKED);
		} else {
			request.setAttribute(requestAttribute, Constants.UNCHECKED);
		}
	}

	private void setRequestAttributeString(HstRequest request, Map<String, String> valueListMap, String parameter,
			String requestAttribute) {
		if (valueListMap.containsKey(parameter)) {
			request.setAttribute(requestAttribute, valueListMap.get(parameter));
		}
	}

	private void setRequestAttributeByBoolean(HstRequest request, Boolean flag) {
		if (flag) {
			request.setAttribute(Constants.SUCCESS_MESSAGE_KEY, Constants.SUCCESSMESSAGE);
		} else {
			request.setAttribute(Constants.ERROR_MESSAGE_KEY, Constants.ERRORMESSAGE);
		}
	}

	private void setRequestAttributeByBoolean(HstRequest request, Boolean flag, String requestAttribute) {
		if (flag) {
			request.setAttribute(requestAttribute, Constants.CHECKED);
		} else {
			request.setAttribute(requestAttribute, Constants.UNCHECKED);
		}
	}

	private Boolean isNoIndexNoFollow(String[] noIndexFollow) {
		Boolean flag = false;
		if ((noIndexFollow != null) && (noIndexFollow.length > 0)) {
			flag = true;
		}
		return flag;
	}

	private SeoRequest getRequestParameters(HstRequestContext hstRequestContext) {
		SeoRequest seoRequest = new SeoRequest();
		seoRequest.setCanonicalUrl(hstRequestContext.getServletRequest().getParameter(Constants.CANONICALPARAMETER));
		seoRequest.setNoIndex(isNoIndexNoFollow(hstRequestContext.getServletRequest().getParameterValues(Constants.NOINDEXPARAMETER)));
		seoRequest.setNoFollow(isNoIndexNoFollow(hstRequestContext.getServletRequest().getParameterValues(Constants.NOFOLLOWPARAMETER)));
		seoRequest.setSeoTitle(hstRequestContext.getServletRequest().getParameter(Constants.SEOTITLEPARAMETER));
		seoRequest.setPageDocument(hstRequestContext.getServletRequest().getParameter(Constants.PAGEDOCUMENT));
		seoRequest.setSeoDesc(hstRequestContext.getServletRequest().getParameter(Constants.SEODESCRIPTIONPARAMETER));
		seoRequest.setRelativePath(hstRequestContext.getServletRequest().getParameter(Constants.RELATIVEPATH));
		seoRequest.setClassName(hstRequestContext.getServletRequest().getParameter(Constants.CLASSNAME));
		seoRequest.setSiteMapItem(hstRequestContext.getServletRequest().getParameter(Constants.SEOSITEMAPITEM));
		seoRequest.setCurrentPage(hstRequestContext.getServletRequest().getParameter(Constants.CURRENTPAGE));
		seoRequest.setQuery(hstRequestContext.getServletRequest().getParameter(Constants.QUERY));
		seoRequest.setIsProduct(hstRequestContext.getServletRequest().getParameter(Constants.ISPRODUCT));
		return seoRequest;
	}
	
	private String getLogMessage(final HstRequest request, String siteName, String seoTemplateName, String pageDocument) {
		String logMessage = "";
		try {
			if (StringUtils.isNotEmpty(pageDocument)) {
				logMessage = String.format("Seo User [ %s ] - Started Saving Template [ %s ] and PageDocument [ %s ] for Site [ %s ] {}", request.getRemoteUser(), seoTemplateName, pageDocument, siteName);
			} else {
				logMessage = String.format("Seo User - [ %s ] Started Saving Template [ %s ] for Site [ %s ] {} ", request.getRemoteUser(), seoTemplateName, pageDocument, siteName);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getting Session ".concat(e.getMessage()));
		}
		return logMessage.toString();
	}
	
	/**
     * Find HippoBean for given path. If path is null or empty, site root bean will be returned
     *
     * @param path document (or folder) path relative to site-root
     * @return bean identified by path. Site root bean if path empty or no corresponding bean.
     */
    public HippoBean getScopeBean(final String path) {
        final HstRequestContext context = RequestContextProvider.get();
        final HippoBean siteBean = context.getSiteContentBaseBean();

        if (!Strings.isNullOrEmpty(path)) {
            final String myPath = PathUtils.normalizePath(path);
            HippoBean scope = siteBean.getBean(myPath);
            if (scope != null) {
                return scope;
            }
        }
        return siteBean;
    }
    /**
     * 
     * @param bean
     * @param className
     * @return SeoSupport document from Bean
     */
	private Map<String,Map<String, String>> getSeoSupportDocuments (HstRequest request, String rootpath, String className,String currentPage,ResourceBundle bundle) {
		HippoBean rootBean = null;
		Map<String,Map<String, String>> allPages = new HashMap<>();
		if(!Strings.isNullOrEmpty(rootpath)){
			rootBean = getScopeBean(rootpath);
		}
		if (className.equalsIgnoreCase(ArticleBannerDocument.class.getName())){
			return getAllArticleDocuments(request, rootBean);
		} else if (className.equalsIgnoreCase(GlobalProject.class.getName())) {
			return getAllGlobalProjectDocuments(request, rootBean);
		} else if (className.equalsIgnoreCase(MaterialColorPalette.class.getName())) {
			return getAllMaterialColorPaletteDocuments(request, rootBean);
		} else if (className.equalsIgnoreCase(Pressreleases.class.getName())) {
			return getAllPressreleasesDocuments(request, rootBean);
		} else if (className.equalsIgnoreCase(DocLinks.class.getName())) {
			return getAllDocLinksDocuments(request, rootBean);
		} else if (className.equalsIgnoreCase(SeoCategory.class.getName())) {
			return getAllSeoCategoryDocuments(request, rootBean);
		} 
		else if (className.equalsIgnoreCase(Product.class.getName())) {
			return getProductBySeoCategory(request, rootBean,currentPage,bundle);
		} 
		return allPages;
	}

	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return Article SEO documents
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllArticleDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, ArticleBannerDocument.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				ArticleBannerDocument document = (ArticleBannerDocument) iterator.next();
				Map<String, String> seo = new LinkedHashMap<>();
				seo.put(Constants.REQUEST_SEO_TITLE, document.getSeoTitle());
				seo.put(Constants.REQUEST_SEO_DESC, document.getMetaDescription());
				seo.put(Constants.REQUEST_HIPPO_NAME, document.getDisplayName());
				seo.put(Constants.REQUEST_NO_INDEX, String.valueOf(document.getNoIndex()));
				seo.put(Constants.REQUEST_NO_FOLLOW, String.valueOf(document.getNoFollow()));
				seo.put(Constants.REQUEST_CANONICAL_URL, document.getCanonicalUrl());
				allPages.put(document.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return GlobalProject SEO documents 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllGlobalProjectDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, GlobalProject.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				GlobalProject blobalProject = (GlobalProject) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(blobalProject.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, blobalProject.getDisplayName());
				allPages.put(blobalProject.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return MaterialColorPalette SEO documents 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllMaterialColorPaletteDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, MaterialColorPalette.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				MaterialColorPalette materialColorPalette = (MaterialColorPalette) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(materialColorPalette.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, materialColorPalette.getDisplayName());
				allPages.put(materialColorPalette.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return Pressreleases SEO documents 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllPressreleasesDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, Pressreleases.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				Pressreleases pressreleases = (Pressreleases) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(pressreleases.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, pressreleases.getDisplayName());
				allPages.put(pressreleases.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return DocLinks SEO documents 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllDocLinksDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, DocLinks.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				DocLinks dockLinks = (DocLinks) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(dockLinks.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, dockLinks.getDisplayName());
				allPages.put(dockLinks.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return Category SEO documents 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getAllSeoCategoryDocuments (HstRequest request, HippoBean rootBean) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery query;
		try {
			query = request.getRequestContext().getQueryManager().createQuery(rootBean, SeoCategory.class);
			HstQueryResult queryResult = query.execute();
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			while(iterator.hasNext()){
				SeoCategory seoCategory = (SeoCategory) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(seoCategory.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, seoCategory.getCategoryName());
				seo.put(Constants.REQUEST_SEO_CATEGORY_KEY, seoCategory.getCategoryKey());
				allPages.put(seoCategory.getName(), seo);
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param request
	 * @param rootBean
	 * @return Get Products By SEO Category 
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Map<String, String>> getProductBySeoCategory (HstRequest request, HippoBean rootBean,String currentPage,ResourceBundle bundle) {
		Map<String,Map<String, String>> allPages = new HashMap<>();
		HstQuery productHstQuery;
		try {
			Integer pageSize=0;
			try{
			String size=bundle.getString(Constants.PRODUCTPAGESIZE);
			pageSize=Integer.valueOf(size);
			}catch (Exception e){
				pageSize=10;
			}
			String seoTemplateName = request.getRequestContext().getServletRequest().getParameter(Constants.SEOTEMPLATENAME);
			String searchQuery = request.getRequestContext().getServletRequest().getParameter(Constants.QUERY);
			request.setAttribute(Constants.QUERY, searchQuery);
			productHstQuery = request.getRequestContext().getQueryManager().createQuery(rootBean, Product.class);
			Filter discontinuedFilter = productHstQuery.createFilter();
			String fieldRestriction = Constants.NS_DISCONTINUED;
			discontinuedFilter.addNotEqualTo(fieldRestriction, true);
			if(!Strings.isNullOrEmpty(seoTemplateName) && !seoTemplateName.equalsIgnoreCase(Constants.PRODUCTRESULTS)){
				Filter categoryFilter = productHstQuery.createFilter();
				categoryFilter.addEqualTo(Constants.HIPPOTAXONOMY_KEYS, seoTemplateName);
				discontinuedFilter.addAndFilter(categoryFilter);	
			} else
			{   
				if (!Strings.isNullOrEmpty(searchQuery)) {
					String parsedSearchTermQuery = SearchInputParsingUtils.parse(searchQuery, true);
					if(Strings.isNullOrEmpty(parsedSearchTermQuery))
						return allPages;
					Filter searchFilter = productHstQuery.createFilter();
					searchFilter.addContains(Constants.NS_ITEM_NO, parsedSearchTermQuery+"%");
					
					Filter itemNoCaseFilter = productHstQuery.createFilter();
					itemNoCaseFilter.addLike(Constants.NS_ITEM_NO, parsedSearchTermQuery+"%");
					itemNoCaseFilter.addOrFilter(searchFilter);
					discontinuedFilter.addAndFilter(itemNoCaseFilter);
				} else {
					return allPages;
				}
			}
			String idFieldNameAttribute = Constants.NS_ITEM_NO;
			productHstQuery.addOrderByAscending(idFieldNameAttribute);
			productHstQuery.setFilter(discontinuedFilter);;
			HstQueryResult queryResult = productHstQuery.execute();
			int totalSize=queryResult.getSize();
			setPaginationAttributes(request, totalSize, pageSize,currentPage);
			HippoBeanIterator iterator = queryResult.getHippoBeans();
			if(Integer.valueOf(currentPage)>1){
				Integer value=(Integer.valueOf(currentPage)-1)*pageSize;
			    iterator.skip(value);
			}
			while(iterator.hasNext()){
				Product product = (Product) iterator.next();;
				Map<String, String> seo = getSeoDetailsForPage(product.getSeo());
				seo.put(Constants.REQUEST_HIPPO_NAME, product.getItemNo());
				allPages.put(product.getItemNo(), seo);
				Long position=iterator.getPosition();
				if(pageSize*Integer.valueOf(currentPage)==position || position==0l){
					break;
				}	
			}
		} catch (IllegalStateException | QueryException e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		}	
		 catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}	
		}
		return allPages;
	}
	
	/**
	 * 
	 * @param seoSupport
	 * @return Map of SEO details
	 */
	private Map<String, String> getSeoDetailsForPage (SeoSupport seoSupport) {
		Map<String, String> seo = new LinkedHashMap<>();
		if (seoSupport != null) {
			try {
				seo.put(Constants.REQUEST_SEO_TITLE, seoSupport.getSeoTitle());
				seo.put(Constants.REQUEST_SEO_DESC, seoSupport.getSeoDescription());
				seo.put(Constants.REQUEST_NO_INDEX, String.valueOf(seoSupport.getNoIndex()));
				seo.put(Constants.REQUEST_NO_FOLLOW, String.valueOf(seoSupport.getNoFollow()));
				seo.put(Constants.REQUEST_CANONICAL_URL, seoSupport.getCanonicalUrl());
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return seo;
	}
	
	private void setDestinationPage(HstRequest request)
	{
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle loginbundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
		if (loginbundle != null) {
			request.setAttribute(Constants.DESTINATION_PAGE,resourceBundle.getPropertyValue(loginbundle, Constants.DESTINATION_PAGE));
		}
		else{
			request.setAttribute(Constants.DESTINATION_PAGE,"/");		
		}
	}
	
	/**
	 * sets Pagination for attributes
	 * @param request
	 * @param currentPage
	 * @param totalResults 
	 */
	private void setPaginationAttributes(HstRequest request, Integer totalResults,Integer psize,String cPage) {
		Integer totalPages=0;
		Integer currentPage =Integer.valueOf(cPage);
		if ((totalResults % psize) > 0) {
			totalPages = (totalResults / psize) + 1;
		 } else {
			totalPages = (totalResults / psize);
		}
		ArrayList<Integer> pages= new ArrayList<>();
		for(int i=1;i<=totalPages;i++){
			pages.add(i);
		}
		request.setAttribute(Constants.REQUEST_ATTR_TOTALPAGES, pages);
		if(currentPage+1 <= pages.size())
		{
		  request.setAttribute(Constants.NEXTPAGE, currentPage+1);	
		}
		if(currentPage+2 <= pages.size())
		{
		  request.setAttribute(Constants.NEXTPAGEGT, currentPage+2);	
		}
		if(currentPage > 1)
		{
		  request.setAttribute(Constants.PREVIOUSPAGE, currentPage-1);	
		}
		
		if(currentPage > 2)
		{
		  request.setAttribute(Constants.PREVIOUSPAGELS, currentPage-2);	
		}

	}
	
}
