/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
*/
package com.kohler.commons.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.kohler.commons.constants.Constants;
import com.onehippo.cms7.eforms.hst.util.mail.MailAddress;
import com.onehippo.cms7.eforms.hst.util.mail.MailAddressImpl;
import com.onehippo.cms7.eforms.hst.util.mail.MailSender;
import com.onehippo.cms7.eforms.hst.util.mail.MailSenderImpl;
import com.onehippo.cms7.eforms.hst.util.mail.MailTemplate;

/**
 * 
 * @author dhanwan.r
 *
 */
public class MailService {

	private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

	public static void sendEmail(String sender, String recipients, String subject, String emailBody) {
		javax.mail.Session session = null;
		if (!Strings.isNullOrEmpty(sender) && !Strings.isNullOrEmpty(recipients)) {
			try {
				MailAddress mailSendAddress = new MailAddressImpl(sender);
				List<Address> addresses = new ArrayList<>();
				List<String> addList = Arrays.asList(recipients.split(","));
				addList.forEach(address -> {
					Address addr = MailTemplate.createAddress(address, address);
					if (addr != null) {
						addresses.add(addr);
					}
				});
				session = getSession(Constants.MAIL_SESSION);
				MailSender senders = new MailSenderImpl(session, mailSendAddress, mailSendAddress);
				MailTemplate mail = new MailTemplate(senders, addresses, subject, emailBody, emailBody);
				mail.sendMessage();
			} catch (Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(" Sending Mail Failed--> ", e);
				}
			}
		}
	}

	public static void sendEmailWithAttachment(String sender, String recipients, String subject, String emailBody,
			InputStream inputStream, String fileName) {
		javax.mail.Session session = null;
		if (!Strings.isNullOrEmpty(sender) && !Strings.isNullOrEmpty(recipients)) {
			try {
				MailAddress mailSendAddress = new MailAddressImpl(sender);
				List<Address> addresses = new ArrayList<>();
				List<String> addList = Arrays.asList(recipients.split(","));
				addList.forEach(address -> {
					Address addr = MailTemplate.createAddress(address, address);
					if (addr != null) {
						addresses.add(addr);
					}
				});
				session = getSession(Constants.MAIL_SESSION);
				MailSender senders = new MailSenderImpl(session, mailSendAddress, mailSendAddress);
				MailTemplate mail = new MailTemplate(senders, addresses, subject, emailBody, emailBody);
				mail.addAttachment(fileName, inputStream);
				mail.sendMessage();
			} catch (Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	protected static javax.mail.Session getSession(String sessionName) {
		InitialContext initialContext = null;
		try {
			initialContext = new InitialContext();
			javax.naming.Context e = (javax.naming.Context) initialContext.lookup("java:comp/env");
			return (javax.mail.Session) e.lookup(sessionName);
		} catch (NamingException var6) {
			if (LOG.isErrorEnabled()) {
				LOG.error(" getting Session name --> ", var6);
			}
			try {
				if (initialContext != null) {
					initialContext.close();
				}
			} catch (NamingException var5) {
				LOG.error(" getting Session name --> ", var6);
			}
			return null;
		}
	}
}
