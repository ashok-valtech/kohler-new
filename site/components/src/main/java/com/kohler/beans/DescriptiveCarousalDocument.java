package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import com.kohler.beans.ArticleDescriptiveCarousalDocument;
import java.util.List;

@HippoEssentialsGenerated(internalName = "kohler:descriptiveCarousalDocument")
@Node(jcrType = "kohler:descriptiveCarousalDocument")
public class DescriptiveCarousalDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:kohler_articledescriptivecarousaldocument")
    public List<ArticleDescriptiveCarousalDocument> getKohler_articledescriptivecarousaldocument() {
        return getChildBeansByName(
                "kohler:kohler_articledescriptivecarousaldocument",
                ArticleDescriptiveCarousalDocument.class);
    }
}
