package com.kohler.rest;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleUtils;
import org.hippoecm.repository.HippoRepository;
import org.hippoecm.repository.HippoRepositoryFactory;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kohler.commons.constants.Constants;
import com.kohler.commons.service.SeoService;
import com.kohler.commons.util.CommonUtil;
import com.kohler.utils.ValidateHeaders;

@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })

@Path("/seoservice/")
public class SEORestService extends BaseRestResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(SEORestService.class);

	@GET
	@Path("/getAllSeoDetails")
	public LinkedHashMap<String,LinkedHashMap<String, String>> getAllSeoDetails(@Context HttpHeaders headers ) throws RepositoryException {
		Session session = null;
		LinkedHashMap<String,LinkedHashMap<String, String>> seoDetails=new LinkedHashMap<>();
		try {
			LOGGER.info("Inside getAllSeoDetails Method and siteName  is-->");
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale bundleLocale = new Locale(language, country);
			ValidateHeaders validateHeaders = new ValidateHeaders();
			if (!validateHeaders.ValidateApiKey(headers, bundleLocale)) {
				return null;
			}
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, bundleLocale);
			String editorUserName=bundle.getString("editorUserName");
			String editorPassword=bundle.getString("editorPassword");
			ResourceBundle seoBundle= ResourceBundleUtils.getBundle(Constants.SEO_LABLES, bundleLocale);
			String containerPath=seoBundle.getString(Constants.HSTCONTAINERPATH);
			HippoRepository hippoRepository = HippoRepositoryFactory.getHippoRepository("vm://");
			session = hippoRepository.login(editorUserName, editorPassword.toCharArray());
			SeoService seoService = new SeoService();
			seoDetails = seoService.getAllSeoValues(session, containerPath);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			if (session != null && session.isLive()) {
				try {
					session.save();
					session.logout();
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.info("Exception while saving getAllSeoDetails session " + e.getMessage());
				}
			}
		}
		return seoDetails;
	}
	
	@POST
	@Path("/updateSeoDetails")
	public Map<String,Boolean> updateSeoDetails(@Context HttpHeaders headers,
			Map<String,LinkedHashMap<String, String>> seoDetails ) throws RepositoryException {
		Session session = null;
		LinkedHashMap<String,Boolean> returnValue= new LinkedHashMap<>();
		try {
			LOGGER.info("Inside updateSeoDetails Method and siteName  is-->");
			HstRequestContext requestContext = RequestContextProvider.get();
			Mount mount = requestContext.getResolvedMount().getMount();
			String language = CommonUtil.getLanguage(mount);
			String country = CommonUtil.getCountry(mount);
			Locale bundleLocale = new Locale(language, country);
			ValidateHeaders validateHeaders = new ValidateHeaders();
			if (!validateHeaders.ValidateApiKey(headers, bundleLocale)) {
				return null;
			}
			ResourceBundle bundle = ResourceBundleUtils.getBundle(Constants.APAC_LABELS, bundleLocale);
			String editorUserName=bundle.getString("editorUserName");
			String editorPassword=bundle.getString("editorPassword");
			HippoRepository hippoRepository = HippoRepositoryFactory.getHippoRepository("vm://");
			session = hippoRepository.login(editorUserName, editorPassword.toCharArray());
			SeoService seoService = new SeoService();
			ResourceBundle seoBundle= ResourceBundleUtils.getBundle(Constants.SEO_LABLES, bundleLocale);
			MessageFormat messageFormat = new MessageFormat(seoBundle.getString(Constants.SEOPATH));
			for (Map.Entry<String,LinkedHashMap<String, String>> entry : seoDetails.entrySet()) {
				String seoPath = messageFormat.format(new Object[] { entry.getKey() });
				Boolean update = seoService.updateSeoTemplate(entry.getValue(), seoPath, session);
				returnValue.put(entry.getKey(), update);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			if (session != null && session.isLive()) {
				try {
					session.save();
					session.logout();
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.info("Exception while saving the updateSeoDetails session " + e.getMessage());
				}
			}
		}
		return returnValue;
	}

}
