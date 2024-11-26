/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.service;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.component.HstRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.kohler.commons.constants.Constants;

/**
 * @author Sumit.Pallakhe
 *
 *	class provides implementation for creating XML from list of URL's 
 */
public class SitemapService {

	/**
	 * @param allUrlsWithoutHost
	 * @param hostName
	 * @return
	 * 
	 * create XML from List of URL's and returns that XML as a string
	 */
	public String createXML(List<String> allUrlsWithoutHost, HstRequest request) {
		Mount mount = request.getRequestContext().getResolvedMount().getMount();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(Constants.URLSET);
		rootElement.setAttribute(Constants.SITEMAP_XML_ROOT_ATTR, Constants.SITEMAP_XML_ROOT_ATTR_VAL);
		allUrlsWithoutHost.forEach((productUrl) -> {
			Element url = doc.createElement(Constants.URL);
			Element loc = doc.createElement(Constants.LOC);
			loc.appendChild(doc.createTextNode(request.getRequestContext().getHstLinkCreator().create(productUrl, mount).toUrlForm(request.getRequestContext(), true)));
			url.appendChild(loc);
			rootElement.appendChild(url);
		});
		doc.appendChild(rootElement);
		StringWriter sw = null;
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			sw = new StringWriter();
			trans.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (Exception ex) {

		}
		return sw.toString();
	}
	
	
	/**
	 * @param allProductUrlsWithOutHosts
	 * @param hstRequest
	 * @return
	 * 
	 * create XML from List of Product URL's and returns that XML as a string
	 */
	public String productsXML(List<String> allProductUrlsWithOutHosts, HstRequest request) {
		Mount mount = request.getRequestContext().getResolvedMount().getMount();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(Constants.URLSET);
		rootElement.setAttribute(Constants.SITEMAP_XML_ROOT_ATTR, Constants.SITEMAP_XML_ROOT_ATTR_VAL);
		Calendar now = new GregorianCalendar(TimeZone.getTimeZone(Constants.TIME_ZONE));
		Date date=now.getTime();
		SimpleDateFormat sm = new SimpleDateFormat(Constants.SITEMAP_DATE_FORMAT.replaceAll("Z$", "+00:00"));
		sm.setTimeZone(TimeZone.getTimeZone(Constants.GMT));
		String currentDate=sm.format(date);
		allProductUrlsWithOutHosts.forEach((productUrl) -> {
			Element url = doc.createElement(Constants.URL);
			Element loc = doc.createElement(Constants.LOC);
			Element priority = doc.createElement(Constants.PRIORITY);
			Element lastModDate = doc.createElement(Constants.LASTMODDATE);
			Element changeFreq = doc.createElement(Constants.CHANGEFREQ);
			loc.appendChild(doc.createTextNode(request.getRequestContext().getHstLinkCreator().create(productUrl, mount).toUrlForm(request.getRequestContext(), true)));
			lastModDate.appendChild(doc.createTextNode(currentDate));
			changeFreq.appendChild(doc.createTextNode(Constants.DAILY));
			priority.appendChild(doc.createTextNode(new BigDecimal(Constants.PRIORITYVALUE).toString()));
			url.appendChild(loc);
			url.appendChild(lastModDate);
			url.appendChild(changeFreq);
			url.appendChild(priority);
			rootElement.appendChild(url);
		});
		doc.appendChild(rootElement);
		StringWriter sw = null;
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			sw = new StringWriter();
			trans.transform(new DOMSource(doc), new StreamResult(sw));
		} catch (Exception ex) {

		}
		return sw.toString();
	}
	
}
