package org.hippoecm.hst.security.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;

/**
 * Login Servlet for handling login request for custom things
 * @author dhanwan.r
 * Date: 07/05/2019
 * @version 1.0
 */
public class CustomLoginServlet extends LoginServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomLoginServlet.class);

	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Current URL: %s", request.getRequestURL().toString()));
			LOG.info(String.format("ServletPath: %s", request.getServletPath()));
		}
		request.setAttribute("servletPath", request.getServletPath());
		String mode = getMode(request);
		if (MODE_LOGIN_FORM.equals(mode)) {
			String destination = "";
			HttpSession session = request.getSession(false);
	        if (session != null) {
	        	if (LOG.isInfoEnabled()) {
	    			LOG.info(String.format("destination Session: %s", session.getAttribute(DESTINATION_ATTR_NAME)));
	    		}
		        destination = session.getAttribute(DESTINATION_ATTR_NAME) != null?(String)session.getAttribute(DESTINATION_ATTR_NAME):"";
	        }
	        destination = request.getParameter("destination") == null?destination:request.getParameter("destination");
			destination = request.getAttribute("destination") == null? destination :(String)request.getAttribute("destination");
			request.setAttribute("destination", destination);
	        setLoginFormResourceProperties(request);
        } else if (MODE_LOGIN_ERROR.equals(mode)) {
        	HttpSession session = request.getSession(false);
        	if (session != null) {
        		if (LOG.isInfoEnabled()) {
	    			LOG.info(String.format("j_username Session: %s", session.getAttribute(USERNAME_ATTR_NAME)));
	    		}
        		String j_username = session.getAttribute(USERNAME_ATTR_NAME) != null?(String)session.getAttribute(USERNAME_ATTR_NAME):"";
        		request.setAttribute("j_username", j_username);
        	}
        	setLoginErrorResourceProperties(request);
        }
        super.doGet(request, response);
    }
	
	@Override
	protected void doLoginLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        String destination="";
    	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
		if (bundle != null) {
			destination=resourceBundle.getPropertyValue(bundle, Constants.DESTINATION_PAGE);
		}
        response.sendRedirect(response.encodeURL(getFullyQualifiedURL(request, destination)));
    }
	
	private void setLoginFormResourceProperties(HttpServletRequest request) {
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
		if (bundle != null) {
			String captchaSecretKey = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_CAPTCHA_SECRET_KEY);
			String userNameLabel = resourceBundle.getPropertyValue(bundle, Constants.LABEL_USERNAME);
			String passwordLabel = resourceBundle.getPropertyValue(bundle, Constants.LABEL_PASSWORD);
			String loginPageTitle = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_PAGE_TITLE);
			String copyRight = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_COPY_RIGHT);
			String loginPageHeader = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_PAGE_HEADER);
			String loginLabel = resourceBundle.getPropertyValue(bundle, Constants.LABEL_LOGIN);
			String cancelLabel = resourceBundle.getPropertyValue(bundle, Constants.LABEL_CANCEL);
			String captchaErrorMessage = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_ERROR_CAPTCHA);
			String captchaErrorMessageHidIn = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_ERROR_CAPTCHA_HIDE_IN);
			LocalDate localDate = LocalDate.now();
			int year = localDate.getYear();
			copyRight = copyRight.replace("{0}", String.valueOf(year));
			request.setAttribute("captchaClientSecreKey", captchaSecretKey);
			request.setAttribute("userNameLabel", userNameLabel);
			request.setAttribute("passwordLabel", passwordLabel);
			request.setAttribute("loginPageTitle", loginPageTitle);
			request.setAttribute("copyRight", copyRight);
			request.setAttribute("loginPageHeader", loginPageHeader);
			request.setAttribute("loginLabel", loginLabel);
			request.setAttribute("cancelLabel", cancelLabel);
			request.setAttribute("captchaError", captchaErrorMessage);
			request.setAttribute("captchaErrorHideIn", captchaErrorMessageHidIn);
		}
	}

	private void setLoginErrorResourceProperties(HttpServletRequest request) {
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
		if (bundle != null) {
			String tryAgainMessage = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_TRY_AGAIN);
			String authFailedMessage = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_AUTH_FAILED);
			String loginFailurePageTitle = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_FAILURE_PAGE_TITLE);
			String copyRight = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_COPY_RIGHT);
			String loginPageHeader = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_PAGE_HEADER);
			LocalDate localDate = LocalDate.now();
			int year = localDate.getYear();
			copyRight = copyRight.replace("{0}", String.valueOf(year));
			request.setAttribute("copyRight", copyRight);
			request.setAttribute("tryAgainMessage", tryAgainMessage);
			request.setAttribute("authFailedMessage", authFailedMessage);
			request.setAttribute("loginFailurePageTitle", loginFailurePageTitle);
			request.setAttribute("loginPageHeader", loginPageHeader);
		}
	}
}

