<#include "../include/imports.ftl">
<@fmt.message key="facebook" var="facebook" />
    <@fmt.message key="twitter" var="twitter" />
    <@fmt.message key="print" var="print" />
    <@fmt.message key="share" var="share" />
    <@fmt.message key="innovationMetaKeyword" var="innovationMetaKeyword" />
        
<#if document??>
	<section class="c-koh-warranty-top-banner">
    	<div class="koh-article-pages-container">
    		<#assign colorCode="" />
			<#if document.textColor?? && document.textColor?has_content>
				<#assign colorCode=document.textColor />
			</#if>
            <#if document.title?? && document.title?has_content>
			     <#assign title = document.title?replace("(R)","<sup>&reg;</sup>")/>
				 <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
			</#if>
			 <#if document.subTitle?? && document.subTitle?has_content>
			     <#assign subTitle = document.subTitle?replace("(R)","<sup>&reg;</sup>")/>
				 <#assign subTitle = subTitle?replace("(TM)","<sup>&trade;</sup>")/>
			</#if> 
			<#if document.imageAlt?? && document.imageAlt?has_content>
        		<#assign imgAlt=document.imageAlt>
        		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
				<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
        	</#if>
        	<#if document.CTALink?? && document.CTALink?has_content>
        		<#assign navigationLink=document.CTALink />
    			<#assign target="_blank" />
			   <#if navigationLink?starts_with("/")>
      				<#assign target="_self" />
				</#if>
			</#if>
            <#if document.imagePath?? && document.imagePath?has_content>
                 <#assign docImgLink=document.imagePath />
                 <#if navigationLink?? && navigationLink?has_content>
	                <div class="koh-article-pages-banner">
		                <a href="${navigationLink}" target="${target}"> 
		                    <img src="${docImgLink}" <#if document.imageAlt?? && document.imageAlt?has_content>alt="${imgAlt}" </#if><#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if>>
		                    <#if title?? && title?has_content>
			                    <div class="koh-banner-content koh-valignment-middle">
									<span class="koh-banner-text">
										<span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${title}</span>
									</span>
								</div>
							</#if>
						</a>
	                </div>
            	<#else>
	            	<div class="koh-article-pages-banner">
	                    <img src="${docImgLink}" <#if document.imageAlt?? && document.imageAlt?has_content>alt="${imgAlt}" </#if><#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if>>
	                    <#if title?? && title?has_content>
		                    <div class="koh-banner-content koh-valignment-middle">
								<span class="koh-banner-text">
									<span class="koh-banner-title" style="<#if colorCode?has_content>color:${colorCode}</#if>">${title}</span>
								</span>
							</div>
						</#if>
	                </div>
                </#if>
            </#if>
            <header>
                <h1 class="koh-article-pages-title"><#if document.subTitle?? && document.subTitle?has_content>${subTitle}</#if></h1>
                <div class="koh-warranty-print-share-tools">
                    <div class="koh-page-print">
                        <button onclick="javascript:window.print()" class="koh-page-print-button" data-hasqtip="2">
                            <span class="icon" data-icon=""></span>
                            <span class="label">${print}</span>
                        </button>
                    </div>
                    <div class="koh-page-share">
                        <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">${share}</span></button>
                        <ul class="koh-page-share-popover">
                            <li>
                                <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">${facebook}</span></button>
                            </li>
                            <li>
                                <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">${twitter}</span></button>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
	        <#if (document.subDescription?? && document.subDescription?has_content)>
	            <div class="col-xs-12 koh-article-description">
	                <#if document.subDescription?? && document.subDescription?has_content>
	                    <h2 class="koh-article-pages-text-body">
	                        <@hst.html hippohtml=document.subDescription/>
	                    </h2>
	                </#if>
	            </div>
	        </#if>
        </div>
    </section>
</#if>
