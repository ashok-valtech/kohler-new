<#include "../include/imports.ftl">

<@hst.link var="globalProjects" siteMapItemRefId="globalprojectKey"/>
<@fmt.message key="share" var="share"/>
<@fmt.message key="facebook" var="facebook"/>
<@fmt.message key="twitter" var="twitter"/>
<@fmt.message key="print" var="print"/> 
<@fmt.message key="product-portfolio" var="productPortfolio"/>
<@fmt.message key="see-more-project" var="seeMoreProject"/>
<@fmt.message key="countryRegionMetaKeyword" var="countryRegionMetaKeyword"/>
<@fmt.message key="company.name" var="companyName"/>	

<#assign imageShareUrl="" />
<@hst.include ref="globalprojectbanner" />
<#if document??>
	<section class="c-koh-projects-pages">
        <div class="koh-projects-pages-container">
		   <div class="koh-project-previous-link"><a href="${globalProjects}?taxonomyKey=${document.keys[0]}">${seeMoreProject?html} ${countries[document.keys[0]]}</a></div>
            <header>
                <h2 class="koh-projects-pages-title koh-projects-pages-title-single">${document.title?html}</h2>
            </header>
			<div class="koh-project-sub-title"><p>${document.place?html}, ${countries[document.keys[0]]}</p></div>
			<div class=""><p><@hst.html hippohtml=document.content/></p></div>
			<div class="koh-print-share-tools">
                <div class="koh-page-print">
                    <button onclick="javascript:window.print()" class="koh-page-print-button" data-hasqtip="2">
                        <span class="icon" data-icon=""></span>
                        <span class="label">Print</span>
                    </button>
                </div>
                <div class="koh-page-share">
                    <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">Share</span></button>
                    <ul class="koh-page-share-popover">
                        <li>
                            <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">Facebook</span></button>
                        </li>
                        <li>
                            <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">Twitter</span></button>
                        </li>
                    </ul>
                </div>
            </div>
            
		<#if allBanners??> 	 
		<div class="koh-projects-pages-container-banner">
			<section class="c-koh-shopping-guide-carousel v-koh-inline">
			    <div class="koh-carousel koh-theme-dark">
			        <div class="koh-slide-collection">
			            <#list allBanners as item>
			                <#if item.title?? && item.title?has_content>
                                <#assign imgAlt=item.title>
			            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
                            </#if>
                             <#if item.imageAlt?? && item.imageAlt?has_content>
			            		<#assign imgAlt=item.imageAlt>
			            		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
								<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
            	            </#if>
			            	<#if imageShareUrl?? && imageShareUrl?has_content>
			            	<#else>	
			            		<#if item.imagePath?? && item.imagePath?has_content>
			            			<#assign imageShareUrl=item.imagePath />
			            		</#if>
			            	</#if>
							<#assign colorCode="" />
							<#if item.textColor?? && item.textColor?has_content>
								<#assign colorCode=item.textColor />
							</#if>
						   <#assign link="" />
						   <#assign target="_blank" />
						   <#assign rel="noopener noreferrer" />
						   <#if item.navigationPath?? && item.navigationPath?has_content>
						   	   <#assign link=item.navigationPath?html />
							   <#if item.navigationPath?starts_with("/")>
			          				<#assign target="_self" />
			          				<#assign rel="" />
			          				<#assign link=item.navigationPath/>
								</#if>
							</#if>	
				            <div class="koh-banner-slide v-koh-simple">
				                <div class="koh-banner-background">
				                <#if link?? && link?has_content>
				                    <a href="${link?html}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
				                       <img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath?html}</#if>" width="1800" height="800" alt="${imgAlt?html}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
									</a>
								<#else>	
								  <img src="<#if item.imagePath?? && item.imagePath?has_content>${item.imagePath?html}</#if>" width="1800" height="800" alt="${imgAlt?html}" <#if item.imageTitle?? && item.imageTitle?has_content> title="${item.imageTitle}"</#if>>
								</#if>
				                </div>
				                <div class="koh-banner-overlay koh-theme-dark">
				                    <span class="koh-overlay-title">${item.title?html}</span>
				                    <span class="koh-overlay-text"><@hst.html hippohtml=item.content/></span>
				                </div>
				            </div>
						</#list>
			        </div>
			    </div>		    
		     </section>
		</div>
		</#if>			
			
			<#assign defaultImage="blank" />
			<#if relatedProductList?? && relatedProductList?has_content>
				<section class="c-koh-product-details">
				   <div class="koh-similar-products">
				        <h2 class="koh-similar-products-title">
				            ${productPortfolio?html}
				        </h2>
						<#list relatedProductList as relateditem>
							<@hst.link var="productDetailslink" siteMapItemRefId="productDetails"/>
					        <div class="koh-product-tiles">
					            <div class="koh-product-tile">
					                <div class="koh-product-tile-content">
					                      <a href="${productDetailslink?html}/${relateditem.itemNo?html}" id="${relateditem.itemNo?html}">
						                   <#list relateditem.attributes as productAttributes>
										        <#if productAttributes.key == "IMG_ISO"> 
										        <#if productAttributes.value[0]?? && productAttributes.value[0]?has_content>
													<#assign defaultImage=productAttributes.value[0] />
												</#if>
												</#if>
									        </#list>
									             <#assign brandName="" />
					                        <#assign defaultSku="" />
					                        <#list relateditem.attributes as attr>
							                    <#if attr.key == "BRAND_NAME">
							                    	<#assign brandName = attr.value[0]?replace("(R)","<sup>&reg;</sup>")/>
    	                                            <#assign brandName = brandName?replace("(TM)","<sup>&trade;</sup>")/>
					                            </#if>
					                            <#if attr.key == "DEFAULT_SKU">
					                            	<#assign defaultSku=attr.value[0] />
					                            </#if>
							                </#list>
											<div class="koh-product-image">
												<@fmt.message key="galleryUrl" var="gridImageUrl"/>
					                            <#assign defImg = gridImageUrl?replace("{0}","${defaultImage}") />
							                    <img src="${defImg}" width="300" height="232" alt="${defaultSku}" />
						                    </div>
							                <#if brandName?? && brandName?has_content>
					                        	<span class="koh-product-description" style="padding:0 5px">${brandName}</span>
				                            </#if>
				                            <#if defaultSku?? && defaultSku?has_content>
			                        			<span class="koh-product-description" style="padding:0 5px">K-${defaultSku}</span>
			                            	</#if>
					                    </a>
					                </div>
					            </div>
					        </div>
					     </#list>
				    </div>
				</section>    
			</#if>
  		</div>
	</section>
</#if>

<#if document.title??>
  	<#-- og tags for twitter-->
	<@hst.headContribution category="meta-tags">
	  	<meta name="twitter:title" content="${document.title?html}" />
	</@hst.headContribution>
	
	<#-- og tags for facebook-->
	<@hst.headContribution category="meta-tags">
	  	<meta property="og:title" content="${document.title?html}" />
	</@hst.headContribution>
</#if>

<@hst.webfile path="/" var="productLink" />
<@hst.headContribution category="ext-scripts">
     <script src="${productLink?html}/js/c-koh-product-details.min.js" type="text/javascript"></script>
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
  	<meta name="twitter:site" content="@${companyName?html}" />
</@hst.headContribution>

<@hst.headContribution category="meta-tags">
  	<meta property="og:site_name" content="${companyName?html}" />
</@hst.headContribution>
<#if imageShareUrl?? && imageShareUrl?has_content>
	<@hst.headContribution category="meta-tags">
			<meta property="og:image" content="${imageShareUrl?html}" />
	</@hst.headContribution>	
</#if>


