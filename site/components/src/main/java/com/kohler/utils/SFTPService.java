package com.kohler.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.kohler.commons.components.ResourceBundleCustom;
import com.kohler.commons.constants.Constants;

/**
 * SFTP file manipulation code
 * 
 * @author Sumit.Pallakhe
 *
 */
public class SFTPService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SFTPService.class);

	private SftpATTRS allAttr = null;
	String sftpHost;
	int sftpPort;
	String sftpUser;
	String sftpPassword;
	String workingDirectory;
	
	public SFTPService(Locale locale) {
		ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
		ResourceBundle bundle = resourceBundle.getBundle(Constants.APAC_LABELS, locale);
		sftpHost = resourceBundle.getPropertyValue(bundle, Constants.SFTP_HOST);
		sftpPort = Integer.parseInt(resourceBundle.getPropertyValue(bundle, Constants.SFTP_PORT));
		sftpUser = resourceBundle.getPropertyValue(bundle, Constants.SFTP_USER);
		sftpPassword = resourceBundle.getPropertyValue(bundle, Constants.SFTP_PASSWORD);
		workingDirectory = resourceBundle.getPropertyValue(bundle, Constants.SFTP_WORKING_DIR);
	}
	
	/**
	 * @param sftpHost
	 * @param sftpPort
	 * @param sftpUser
	 * @param sftpPassword
	 * @param workingDirectory
	 * @param fileName
	 * @return SftpATTRS :: SFTP attributes
	 * 
	 * Method returns metadata of the file present on SFTP
	 */
	public SftpATTRS getFileMetadata(String fileName) {
		
		LOG.info("reading filemetadata..!!!");
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(sftpUser, sftpHost, sftpPort);
			session.setPassword(sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			LOG.info("SFTP session opened..!!!");
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(workingDirectory);
			allAttr = channelSftp.lstat(fileName);
		} catch (Exception ex) {
			LOG.error("SFTP Error reading file metadata ..!!!", ex);
		}finally {
			if ((channel != null) && (channel.isConnected())) {
				try {
					channel.disconnect();
					LOG.info("SFTP channel disconnected..!!!");
				} catch (Exception ex) { 
					LOG.error(" Error disconnecting channel ", ex);
				}
			}
			if ((session != null) && (session.isConnected())) {
				try {
					session.disconnect();
					LOG.info("SFTP session disconnected..!!!");
				} catch (Exception ex) { 
					LOG.error(" Error disconnecting session ", ex);
				}
			}
		}		
		LOG.info("Done with reading filemetadata..!!!");
		return allAttr;
	}

	/**
	 * @param context
	 * @return String : actual file data
	 * 
	 * method returns actual data of the file present on SFTP
	 */
	public String getFileData(String fileName) {
		LOG.info("Started SFTP product file reading..!!!");
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		StringBuilder sb = new StringBuilder("");
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(sftpUser, sftpHost, sftpPort);
			session.setPassword(sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();			
			LOG.info("SFTP session opened..!!!");
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(workingDirectory);
			InputStream is = channelSftp.get(fileName);
			BufferedReader rdr = new BufferedReader(new InputStreamReader(is));
			for (int c; (c = rdr.read()) != -1;) {
				sb.append((char) c);
			}
		} catch (IOException ex){
			LOG.info("SFTP error while reading file ", ex);
		} catch (Exception ex) {
			LOG.info("SFTP Error reading file metadata ..!!! ", ex);
		} finally {
			if ((channel != null) && (channel.isConnected())) {
				try {
					channel.disconnect();
					LOG.info("SFTP channel disconnected..!!!");
				} catch (Exception ex) { 
					LOG.error(" Error disconnecting channel ", ex);
				}
			}
			if ((session != null) && (session.isConnected())) {
				try {
					session.disconnect();
					LOG.info("SFTP session disconnected..!!!");
				} catch (Exception ex) { 
					LOG.error(" Error disconnecting session ", ex);
				}
			}
		}
		LOG.info("Done with SFTP product file reading..!!!");
		return sb.toString();
	}

	public SftpATTRS getAllAttr() {
		return allAttr;
	}

	public void setAllAttr(SftpATTRS allAttr) {
		this.allAttr = allAttr;
	}
}
