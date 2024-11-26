package com.kohler.beans;

import java.util.List;

import javax.jcr.NodeIterator;

import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HippoEssentialsGenerated(internalName = "kohler:Filter")
@Node(jcrType = "kohler:Filter")
public class Filter extends BaseDocument implements ContentNodeBinder{
	
	private static final Logger LOGGER = LoggerFactory.getLogger (Filter.class);
	
	List<FilterCompound> kohler_filtercompound;
	
    @HippoEssentialsGenerated(internalName = "kohler:kohler_filtercompound")
    public List<FilterCompound> getKohler_filtercompound() {
        return ((kohler_filtercompound == null) || (kohler_filtercompound.isEmpty()))?getChildBeansByName("kohler:kohler_filtercompound",
                FilterCompound.class): kohler_filtercompound;
    }

	public void setKohler_filtercompound(List<FilterCompound> kohler_filtercompound) {
		this.kohler_filtercompound = kohler_filtercompound;
	}

	@Override
	public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
		try {
			Filter bean =  (Filter) content;
        	NodeIterator iterator  = node.getNodes("kohler:kohler_filtercompound");
        	while (iterator.hasNext()) {
        		javax.jcr.Node compoundNode = (javax.jcr.Node) iterator.next();
        		deleteNode(compoundNode);
        	}
        	
        	for (FilterCompound compoundBean: bean.getKohler_filtercompound()) {
        		javax.jcr.Node compound = (javax.jcr.Node) node.addNode("kohler:kohler_filtercompound","kohler:FilterCompound");
        		compound.setProperty("kohler:defaultVal", compoundBean.getDefaultVal());
        		compound.setProperty("kohler:alternate", compoundBean.getAlternate());
        	}
        } catch (Exception e) {
        	if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e.getMessage(), e);
			}
            throw new ContentNodeBindingException(e);
        }
        return true;

	}
	
	private void deleteNode (javax.jcr.Node compoundNode) {
		try {
    		compoundNode.remove();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
