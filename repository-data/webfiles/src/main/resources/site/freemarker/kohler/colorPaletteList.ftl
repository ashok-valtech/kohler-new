<#include "../include/imports.ftl">
<@hst.include ref="colorbanner"/>
   
   <@fmt.message key="bathroom-planning" var="bathroomPlanning"/>
   <@fmt.message key="bathroom-trends" var="bathroomTrends"/>
   <@fmt.message key="bathroom-gallery" var="bathroomGallery"/>
   <@fmt.message key="bathroom-trends-items-description" var="bathroomTrendsItemsDescription"/>
   <@fmt.message key="bathroom-gallery-items-description" var="bathroomGalleryItemsDescription"/>
   <@fmt.message key="see-product-color" var="seeProductColor"/>
   <@fmt.message key="facebook" var="facebook"/>
   <@fmt.message key="twitter" var="twitter"/>
   <@fmt.message key="share" var="share"/>
   <@fmt.message key="materialColorPaletteMetaKeyword" var="materialColorPaletteMetaKeyword"/>
   
<@hst.include ref="colorbanner"/>

<@hst.link siteMapItemRefId="results" var="siteMapURL"/>
<#assign searchNavigationURL = siteMapURL />
<#assign ogImage="" />
<#assign ogTitle="" />
<#assign ogDescription="" />
<#assign colorUUIDParam="" />
<#if hstRequestContext??>
  	<#if hstRequestContext.servletRequest.getParameter('colorUUID')?has_content>
   		<#assign colorUUIDParam =hstRequestContext.servletRequest.getParameter('colorUUID')>
   </#if>
</#if>   
  <section class="c-koh-material-color-palette c-koh-material-color-palette-inner">
    <#if document.color?? && document.color?has_content>
	 <#list document.color as categoryItem>
	 	<#assign categorySearchURL="" />
	 	<#if categoryItem.searchURL?? && categoryItem.searchURL?has_content>
    		<#assign categorySearchURL=searchNavigationURL + "/" + categoryItem.searchURL />
    	<#else>
    		<#assign categorySearchURL=searchNavigationURL />	
    	</#if>
        <div class="koh-meterial-category col-xs-12">
            <header class="koh-meterial-container">
                <h2 class="koh-material-title">${categoryItem.string?html}</h2>
            </header>
            <div class="koh-color-palette-product-panels">
                <div class="koh-color-palette-product-panel-wrapper" data-webtest-id="colours-wrapper">
                <#list categoryItem.kohler_colorcoumpound as categoryColor>
                   <#if categoryColor.title?? && categoryColor.title?has_content>
	                    <#assign title = categoryColor.title?replace("(R)","<sup>&reg;</sup>")/>
						<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
					    <#assign imgAlt = categoryColor.title?replace("(R)","&reg;")/>
						<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
                   </#if>
                   <#if categoryColor.imageSmallAlt?? && categoryColor.imageSmallAlt?has_content>
				        <#assign imgAlt=categoryColor.imageSmallAlt>
				        <#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
				        <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
			      </#if>
                	<#if colorUUIDParam == categoryColor.canonicalUUID>
                		<#assign ogImage=categoryColor.largeImage />
						<#assign ogTitle=categoryColor.title />
						<#assign ogDescription=categoryColor.description />
                	</#if>
                	<#assign colorCodesearchURL="" />
                	<#if categoryColor.colorCode?? && categoryColor.colorCode?has_content>
                		<#assign colorCodesearchURL=categorySearchURL + "?search=" + categoryColor.colorCode />
                	</#if>
                    <div class="koh-color-palette-panel col-lg-2 col-md-2 col-sm-3 col-xs-4 koh-colorPaletteInfo" data-webtest-id="colour-panel">
                       <#assign colorUUID = "${categoryColor.canonicalUUID}">
                        <div id="${categoryColor.canonicalUUID}"  class="koh-color-palette-panel-inner" data-toggle="modal" data-target="#koh-modal-color-palette-detail" data-title='${title}' data-description='${categoryColor.description}' data-largeimage='${categoryColor.largeImage}' data-colorcodesearchurl='${colorCodesearchURL}' data-coloruuid='${colorUUID}' onclick="populateValues(this)">
                            <div class="koh-color-palette-panel-photo-wrapper">
                                <img src="${categoryColor.smallImage?html}" alt="${imgAlt}" <#if categoryColor.imageSmallTitle?? && categoryColor.imageSmallTitle?has_content> title="${categoryColor.imageSmallTitle}"</#if>>
                            </div>
                            <div class="koh-color-palette-panel-caption">
                                <p class="koh-color-palette-panel-title">${title}</p>
                            </div>
                        </div>
                    </div>
                 </#list>
                </div>
            </div>
        </div>
        <hr class="gray-line">
      </#list>
  </#if>
   </section>
    
    <div id="koh-modal-color-palette-detail" class="modal koh-modal">
        <div class="koh-modal-inner">
            <button class="koh-modal-close-button close"  data-dismiss="modal">
                <span class="icon" data-icon=""></span>
            </button>
            <p class="koh-modal-title"></p>
            <div class="koh-modal-color-palette-detail-container" data-webtest-id="koh-colour-description">
                <div class="koh-row no-gutter-sm">
                    <div class="koh-modal-photo-wrapper col-12-sm">
                        <img src="" id="modalImage" alt="">
                    </div>
                </div>
                <div class="koh-row no-gutter-sm">
                    <div class="modal-details col-12-sm">
                        <p id="modalDescription"></p>
                    </div>
                </div>
                <div class="koh-row no-gutter-sm">
                    <div class="modal-products-link">
                        <a href="#" id="colorCodeSearchId">${seeProductColor}</a></div>
                <div class="koh-print-share-tools">
                        <div class="koh-page-share">
                            <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">${share}</span></button>
                            <ul class="koh-page-share-popover">
                                <li>
                                    <button class="koh-share-facebook" data-share-url=""><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button>
                                </li>
                                <li>
                                    <button class="koh-share-twitter" data-share-url=""><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
     </div>

<input type="hidden" id="shareTitle" />
<input type="hidden" id="shareImage" />
<input type="hidden" id="shareDescription" />
<input type="hidden" id="colorUUID" />
    
<@hst.headContribution category="ext-scripts">
  <@hst.webfile path="/" var="link" />
  <script type="text/javascript" src="${link}/js/custom/koh-color-palette.min.js"></script>
</@hst.headContribution>
	 
<#if colorUUIDParam?? && colorUUIDParam?has_content>
  <@hst.headContribution category="ext-scripts">
	<script type="text/javascript">
		var $id = $('#' + '${colorUUIDParam}') ;
		$id.click();
    </script>
  </@hst.headContribution>
</#if>

<@hst.headContribution category="meta-tags">
  	<meta property="og:title" content="${ogTitle?html}" />
</@hst.headContribution>
<@hst.headContribution category="meta-tags">
  	<meta property="og:image" content="${ogImage?html}" />
</@hst.headContribution>
<@hst.headContribution category="meta-tags">
	<meta property="og:description" content="${ogDescription?html}" /> 
</@hst.headContribution>

<@fmt.message key="company.name" var="companyName"/>	
<@hst.headContribution category="meta-tags">
  	<meta property="og:site_name" content="${companyName}" />
</@hst.headContribution>