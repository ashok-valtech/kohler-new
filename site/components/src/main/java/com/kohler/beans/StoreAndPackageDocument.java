package com.kohler.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import com.kohler.beans.StoreCompound;
import com.kohler.beans.PackageCompound;

@HippoEssentialsGenerated(internalName = "kohler:storeAndPackageDocument")
@Node(jcrType = "kohler:storeAndPackageDocument")
public class StoreAndPackageDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "kohler:kohler_storecompound")
    public List<StoreCompound> getKohler_storecompound() {
        return getChildBeansByName("kohler:kohler_storecompound",
                StoreCompound.class);
    }

    @HippoEssentialsGenerated(internalName = "kohler:kohler_packagecompound")
    public List<PackageCompound> getKohler_packagecompound() {
        return getChildBeansByName("kohler:kohler_packagecompound",
                PackageCompound.class);
    }
}
