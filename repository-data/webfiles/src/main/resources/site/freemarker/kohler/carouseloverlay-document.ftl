<#include "../include/imports.ftl">
<@fmt.message key="hotspotProductImageURL" var="hotspotProductImageURL"/>
<@fmt.message key="discoverPossibility" var="discoverPossibility"/>
<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@fmt.message key="where-to-buy" var="whereToBuy"/>
<@fmt.message key="facebook" var="facebook" />
<@fmt.message key="twitter" var="twitter" />
<@fmt.message key="share" var="share" />
<@fmt.message key="hotspotProductsTitle" var="hotspotProductsTitle" />
<@fmt.message key="hotspotProductDetails" var="hotspotProductDetails" />
<#if document??>
		<section class="c-koh-shopping-guide-carousel c-koh-banner-carousel-spot v-koh-inline">
			<h2 class="koh-carousel-title koh-alignment-left" style="font-family: [[FONT]]; font-size: [[TEXT_SIZE]];color: #[[COLOR]];">${discoverPossibility}</h2>
			<div class="koh-carousel koh-theme-dark">
				<div class="koh-slide-collection">
					<#list document as carouselOverlayView>
						<#if carouselOverlayView??>
							<#assign carouselImage="">
							<#assign title="">
							<#assign description="">
							<#if carouselOverlayView.imageURL??>
								<#assign carouselImage=carouselOverlayView.imageURL>
							<#else>	
								<#assign carouselImage="http://placehold.it/1800x800">
							</#if> 
							<#if carouselOverlayView.title?? && carouselOverlayView.title?has_content>
							    <#assign title = carouselOverlayView.title?replace("(R)","<sup>&reg;</sup>")/>
								<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
							    <#assign imgAlt = carouselOverlayView.title?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
							</#if>
							<#if carouselOverlayView.imageAlt?? && carouselOverlayView.imageAlt?has_content>
            					<#assign imgAlt=carouselOverlayView.imageAlt>
            					<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            				</#if>
							<#if carouselOverlayView.description??>
								<#assign description=carouselOverlayView.description?replace("(R)","<sup>&reg;</sup>")/>
								<#assign description = description?replace("(TM)","<sup>&trade;</sup>")/>
							</#if>
							<div class="koh-banner-slide v-koh-hotspot">
								<div class="koh-banner-background">
								   <#if carouselOverlayView.link?? && carouselOverlayView.link?has_content>
								     	<#assign mobilelink=carouselOverlayView.link/>
								        <#assign target="_blank" />
								        <#assign rel="noopener noreferrer" />
								        <#if mobilelink?starts_with( "/")> 
								          <#assign target="_self" />
								          <#assign rel="" />
								        </#if>  
										<a href="${mobilelink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
											<img src="${carouselImage}" width="1800" height="800" alt="${imgAlt}" <#if carouselOverlayView.imageTitle?? && carouselOverlayView.imageTitle?has_content> title="${carouselOverlayView.imageTitle}"</#if> />
										</a>
								   <#else>
										<img src="${carouselImage}" width="1800" height="800" alt="${imgAlt}" <#if carouselOverlayView.imageTitle?? && carouselOverlayView.imageTitle?has_content> title="${carouselOverlayView.imageTitle}"</#if> />
								   </#if>
								</div>
								<div class="koh-banner-overlay koh-theme-dark">
								  <#if carouselOverlayView.title?? && carouselOverlayView.title?has_content>
									<span class="koh-overlay-title">${title}</span>
								  </#if>	 
									<span class="koh-overlay-text">${description}</span>
									<div class="koh-overlay-controls">
										<#if carouselOverlayView.link?? && carouselOverlayView.link?has_content>	
											<#assign link=carouselOverlayView.link/>
										   <#assign shareLink=carouselOverlayView.link/>
										   <#assign target="_blank" />
										   <#assign rel="noopener noreferrer" />
										   <#if shareLink?starts_with( "/")>
					                            <#assign target="_self" />
					                            <#assign rel="" />
					                            <#assign currentSite=hstRequestContext.getResolvedMount().getMount() />
					                            <#assign shareLink = hstRequestContext.getHstLinkCreator().create(shareLink, currentSite).toUrlForm(hstRequestContext, true)/>
					                       </#if> 
					                        <#assign linkLabel="Learn More" />
											<#if carouselOverlayView.label?? && carouselOverlayView.label?has_content>
												<#assign linkLabel=carouselOverlayView.label>
											</#if>	
											<span class="koh-overlay-link"><a href="${link}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>><#if linkLabel?has_content>${linkLabel}</#if></a></span>
										</#if>
                                       <#if shareLink?? && shareLink?has_content>	
										<div class="koh-overlay-social">
											<button class="koh-overlay-share-button"><span class="icon" data-icon="&#xe603;"></span><span class="label">${share}</span></button>
											<ul class="koh-overlay-share-menu">
												<li><button class="koh-share-facebook" data-share-url="<#if shareLink?? && shareLink?has_content> ${shareLink} </#if>"><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button></li>
												<li><button class="koh-share-twitter" data-share-url="<#if shareLink?? && shareLink?has_content> ${shareLink} </#if>" data-share-title="${title}"><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button></li>
											</ul>
										</div>
								      </#if>		
									</div>
								</div><!-- /end koh-banner-overlay -->
								
								<#if carouselOverlayView.carouselOverlayCompoundViews??>
						            <#assign compoundViewFlag = "" >
						 			<#list carouselOverlayView.carouselOverlayCompoundViews as carouselOverlayCompoundView>
	                      				<#if (carouselOverlayCompoundView.xcord &gt; 0.0 && carouselOverlayCompoundView.xcord &lt; 520) && (carouselOverlayCompoundView.ycord &gt; 0.0 && carouselOverlayCompoundView.ycord &lt; 950)>
	                      				 	<#assign compoundViewFlag = "true" >
	                      			    </#if>
                                    </#list> 	 	
									<#if compoundViewFlag?? && compoundViewFlag?has_content>
										<!-- Hotspots -->
										<div class="koh-banner-hotspots koh-theme-dark open">
										  
											<span class="koh-banner-hotspots-header">${hotspotProductsTitle}</span>
										  	
											<div class="koh-banner-hotspots-collection">
												<#list carouselOverlayView.carouselOverlayCompoundViews as carouselOverlayCompoundView>
													<#assign productDescription = "" >
													<#if carouselOverlayCompoundView.description?? && carouselOverlayCompoundView.description?has_content>
	                                  					<#assign productDescription =  carouselOverlayCompoundView.description?replace("(R)","<sup>&reg;</sup>")/>
	                                  					<#assign productDescription =  productDescription?replace("(TM)","<sup>&trade;</sup>")/>
	                                  				</#if>
	                                  				<#if (carouselOverlayCompoundView.xcord &gt; 0.0 && carouselOverlayCompoundView.xcord &lt; 520) && (carouselOverlayCompoundView.ycord &gt; 0.0 && carouselOverlayCompoundView.ycord &lt; 950)>
													<div class="koh-banner-hotspot" style="top: ${carouselOverlayCompoundView.xcord}px; left: ${carouselOverlayCompoundView.ycord}px">
														<div class="koh-banner-hotspot-icon"></div>
														<div class="koh-banner-hotspot-tile">
															<div class="koh-hotspot-image">
																<#assign defImg = "http://placehold.it/396x297">
																<#if carouselOverlayCompoundView.IMG_ISO?? && carouselOverlayCompoundView.IMG_ISO?has_content>
	                                              					<#assign defImg = hotspotProductImageURL?replace("{0}",carouselOverlayCompoundView.IMG_ISO) />
	                                              				</#if>	
																<img src="${defImg}" width="396" height="297" alt="">
															</div>
															<div class="koh-hotspot-content">
																<span class="koh-hotspot-description">${productDescription}</span>
																<#assign productLink = productDetailslink + "/" + carouselOverlayCompoundView.itemNo />
																<#assign sku="" />
																<#if carouselOverlayCompoundView.sku?? && carouselOverlayCompoundView.sku?has_content>
	                                              					<#assign productLink = productLink +   "?skuid=" + carouselOverlayCompoundView.sku/>
	                                              					<#assign sku=carouselOverlayCompoundView.sku />
	                                              				</#if>	
																<span class="koh-hotspot-link"><a href="${productLink}" id="${carouselOverlayCompoundView.itemNo}">${hotspotProductDetails}</a></span>
																<span class="koh-hotspot-sku">K-${sku}</span>
																<span class="koh-hotspot-store-locator"><a href="${storelink}">${whereToBuy}</a></span>
															</div>
														</div>
													</div>
												   </#if>	
												</#list>
											</div><!-- /end koh-banner-hotspots-collection -->
										</div><!-- /end koh-banner-hotspots koh-theme-dark -->
										<!-- End Hotspots -->
									</#if>
								</#if>
							</div><!-- /end koh-banner-slide v-koh-hotspot -->
						</#if> 
					</#list>
				</div><!-- /end koh-slide-collection -->
			</div><!-- /end koh-carousel -->
		</section>
		<!-- /end Carousel Components -->
</#if>

<@hst.headContribution category="ext-scripts">
 <script type="text/javascript">
 		function getNextProduct(id) {
		    try {
		        localStorage.removeItem("similarProducts");
		    	} catch (e) {
		    }
		}    
 </script>
</@hst.headContribution>
