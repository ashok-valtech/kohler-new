package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.ArticleCarouselCompoundDocument;

@HippoEssentialsGenerated(internalName = "kohler:articleCarouselDocument")
@Node(jcrType = "kohler:articleCarouselDocument")
public class ArticleCarouselDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:kohler_articlecarouselcompounddocument")
    public List<ArticleCarouselCompoundDocument> getKohler_articlecarouselcompounddocument() {
        return getChildBeansByName(
                "kohler:kohler_articlecarouselcompounddocument",
                ArticleCarouselCompoundDocument.class);
    }
}
