package com.kohler.commons.components.componentsInfo;

import org.hippoecm.hst.core.parameters.Parameter;

/**
 * 
 * @author Krushna.Mahunta
 *
 */
public interface PromotionStoreLocatorComponentInfo {

	@Parameter(name = "storeCompomemt", required = true)
    Boolean getStoreCompomemt();
}
