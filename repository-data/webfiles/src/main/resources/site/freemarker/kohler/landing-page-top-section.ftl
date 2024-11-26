<#include "../include/imports.ftl">
<@fmt.message key="facebook" var="facebook" />
    <@fmt.message key="twitter" var="twitter" />
    <@fmt.message key="print" var="print" />
    <@fmt.message key="share" var="share" />
    <@fmt.message key="innovationMetaKeyword" var="innovationMetaKeyword" />
    <#assign seoTitle="">
    <#assign metaDescContent="">
    <#assign ogThumbnail="">
        
<#if document??>
	<section class="c-koh-article-pages">
    	<div class="koh-article-pages-container">
            <#if document.title?? && document.title?has_content>
			     <#assign title = document.title?replace("(R)","<sup>&reg;</sup>")/>
				 <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
			     <#assign imgAlt = document.title?replace("(R)","&reg;")/>
				 <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
			</#if> 
			<#if document.imageAlt?? && document.imageAlt?has_content>
        		<#assign imgAlt=document.imageAlt>
        		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
				<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
        	</#if>
        	<#if document.seoTitle?? && document.seoTitle?has_content>
        		<#assign seoTitle="${document.seoTitle}">
        	<#else>
        		<#assign seoTitle="${document.title}">
        	</#if>
        	<#if document.metaDescription?? && document.metaDescription?has_content>
        		<#assign metaDescContent="${document.metaDescription}">
        	</#if>
        	<#if document.thumbnailUrl?? && document.thumbnailUrl?has_content>
        		<#assign ogThumbnail="${document.thumbnailUrl}">
        	</#if>
            <#if document.videoUrl?? && document.videoUrl?has_content>
                <#assign externalLink=document.videoUrl />
                <div class="koh-article-pages-banner embed-responsive embed-responsive-16by9">
                    <iframe src="${externalLink}?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>
                </div>
            <#elseif document.imageLink?? && document.imageLink?has_content>
                 <#assign docImgLink=document.imageLink />
                <div class="koh-article-pages-banner">
                    <img src="${docImgLink}" alt="${imgAlt}" <#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if>>
                </div>
            </#if>
            <header>
                <h1 class="koh-article-pages-title"><#if document.title?? && document.title?has_content>${title}</#if></h1>
                <div class="koh-print-share-tools">
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
	        <#if (document.titleHeader?? && document.titleHeader?has_content) || (document.description?? && document.description?has_content) || (document.link?? && document.link?has_content)>
	            <div class="col-xs-12 koh-article-description">
	              <#if document.titleHeader?? && document.titleHeader?has_content>
	               <#assign titleHeader = document.titleHeader?replace("(R)","<sup>&reg;</sup>")/>
				   <#assign titleHeader = titleHeader?replace("(TM)","<sup>&trade;</sup>")/>
	                <h3 class="koh-article-pages-text-title"> ${titleHeader}</h3>
	              </#if>   
	                <#if document.description?? && document.description?has_content>
	                    <div class="koh-article-pages-text-body">
	                        <@hst.html hippohtml=document.description/>
	                    </div>
	                </#if>
	                <#if document.link??>
	                    <#assign hstLink=document.link />
	                    <#if hstLink?has_content>
	                        <#assign target="_blank" />
	                        <#assign rel="noopener noreferrer" />
	                        <#if hstLink?starts_with( "/")>
	                            <#assign target="_self" />
	                            <#assign rel="" />
	                        </#if>
	                        <a class="koh-article-pages-text-link" href="${hstLink}" target="${target}" <#if rel?has_content>rel="${rel}"</#if>>
	                            <#if document.link?has_content>${document.linkLable}</#if>
	                        </a>
	                    </#if>
	                </#if>
	            </div>
	        </#if>
        </div>
    </section>
</#if>
