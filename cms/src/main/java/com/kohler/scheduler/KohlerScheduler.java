package com.kohler.scheduler;

import java.net.HttpURLConnection;

import javax.jcr.RepositoryException;

import org.onehippo.repository.scheduling.RepositoryJob;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KohlerScheduler implements RepositoryJob {

	private static final Logger LOG = LoggerFactory.getLogger(KohlerScheduler.class);

	@Override
	public void execute(RepositoryJobExecutionContext context) throws RepositoryException {
		
	}

	protected void closeURLConnection (HttpURLConnection urlConnection) {
		try {
			urlConnection.getInputStream().close();
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	protected void disconnectURLConnection (HttpURLConnection urlConnection) {
		try {
			urlConnection.disconnect();
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
}
