package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;
import com.kohler.beans.ArticleThreeImageCompound;

@HippoEssentialsGenerated(internalName = "kohler:articleThreeImageDocument")
@Node(jcrType = "kohler:articleThreeImageDocument")
public class ArticleThreeImageDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:title")
    public String getTitle() {
        return getSingleProperty("kohler:title");
    }

    @HippoEssentialsGenerated(internalName = "kohler:description")
    public HippoHtml getDescription() {
        return getHippoHtml("kohler:description");
    }

    @HippoEssentialsGenerated(internalName = "kohler:articleThreeImageCompound")
    public List<ArticleThreeImageCompound> getArticleThreeImageCompound() {
        return getChildBeansByName("kohler:articleThreeImageCompound",
                ArticleThreeImageCompound.class);
    }
}
