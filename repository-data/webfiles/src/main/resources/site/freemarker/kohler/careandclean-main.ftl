<#include "../include/imports.ftl">

<@hst.link var="careAndCleanLink" siteMapItemRefId="careandclean"/>
<@fmt.message key="select-article" var="selectArticle"/>
<@fmt.message key="success-cleaners" var="successCleaners"/>
<@fmt.message key="warranty-toll" var="warrantyToll"/>
<@fmt.message key="here" var="here"/>
<@fmt.message key="click-careandclean" var="clickCareAndClean"/>
<@fmt.message key="careandclean-warranty-link" var="careAndCleanWarrantyLink"/>

<@hst.include ref="careandclean-container"/>

<section class="c-koh-tabbed-article">
	<div class="koh-article-breadcrumbs"><ul></ul></div>
	<div class="koh-article-container">
		<div class="koh-article-header">
			<div class="koh-article-controls contained">
				<button class="koh-article-select">
					<#if document?? && document?has_content>${document.title?html}<#else>${selectArticle?html}</#if>
				</button>
				<ul class="koh-article-set">
				    <#list pageable.items as item>
						<@hst.link var="link" hippobean=item />
			 			<li><a class="active" href="${link?html}">${item.title?html}</a></li>
			 	 	</#list>
				</ul>
			</div>
		</div>
	</div>
</section>

<section class="c-koh-simple-content v-koh-snippet">
	<div class="koh-simple-content-container">
		<h1 class="koh-simple-content-title" style="">
		<#if document.title?? && document.title?has_content>
			${document.title?html}
		</#if>	
		</h1>
		<div class="koh-simple-content-body">
		</div>
	</div>
</section>

<#if document?? && document?has_content>
	<#assign counter=0>
	<#list document.documentLink as relateditem>
	 <#if relateditem.title?? && relateditem.title?has_content>
	    <#assign title = relateditem.title?replace("(R)","<sup>&reg;</sup>")/>
		<#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
	    <#assign imgAlt = relateditem.title?replace("(R)","&reg;")/>
		<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
	 </#if>
	 <#if relateditem.imageAlt?? && relateditem.imageAlt?has_content>
	     <#assign imgAlt=relateditem.imageAlt>
	     <#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
	     <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
     </#if>
		<#if counter==0>
			<#assign imageClass="m-koh-image-left">
		<#else>	
			<#assign imageClass="m-koh-image-right">
		</#if>
		<section class="c-koh-simple-content v-koh-snippet ${imageClass}">
			<div class="koh-simple-content-image">
				<#if relateditem.imageUrl?? && relateditem.imageUrl?has_content>
					<img src="${relateditem.imageUrl?html}" width="400" height="290" alt="${imgAlt}" <#if relateditem.imageTitle?? && relateditem.imageTitle?has_content> title="${relateditem.imageTitle}"</#if>>
			    </#if>
			</div>
			<div class="koh-simple-content-container">
				<h2 class="koh-simple-content-title" style="">
				<#if relateditem.title?? && relateditem.title?has_content>
					${title}
				</#if>	
				</h2>
				<div class="koh-simple-content-body">
				<#if relateditem.description?? && relateditem.description?has_content>
					<p><@hst.html hippohtml=relateditem.description/></p>
				</#if>	
				</div>
			</div>
		</section>
		<#if counter==0>
			<#assign counter=1>
		<#else>	
			<#assign counter=0>
		</#if>
	</#list>
	<section class="c-koh-simple-content v-koh-full-page">
        <div class="koh-simple-content-container">
            <div class="koh-simple-content-body">
                <p>${clickCareAndClean} <a href="${careAndCleanWarrantyLink}">${here} </a> ${warrantyToll}</p>
				<p><sub>${successCleaners}</sub></p>
				<p>&nbsp;</p>
			</div>
        </div>
    </section>
</#if>		

<@hst.webfile path="/" var="jsLink" />
<@hst.headContribution category="ext-scripts">
	<script src="${jsLink}/js/c-koh-tabbed-article.min.js" type="text/javascript"></script>
</@hst.headContribution>
