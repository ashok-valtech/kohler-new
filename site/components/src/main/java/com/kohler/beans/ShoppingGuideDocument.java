package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.ShoppingGuideCompoundDocument;

@HippoEssentialsGenerated(internalName = "kohler:shoppingGuideDocument")
@Node(jcrType = "kohler:shoppingGuideDocument")
public class ShoppingGuideDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:shoppingGuideCompoundDocument")
    public List<ShoppingGuideCompoundDocument> getShoppingGuideCompoundDocument() {
        return getChildBeansByName("kohler:shoppingGuideCompoundDocument",
                ShoppingGuideCompoundDocument.class);
    }
}
