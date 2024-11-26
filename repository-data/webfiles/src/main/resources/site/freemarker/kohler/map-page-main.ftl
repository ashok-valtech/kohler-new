<#include "../include/imports.ftl">
<@hst.webfile path="/" var="link" />

<@fmt.message key="bingdatasource" var="bingdatasource"/>
<@fmt.message key="bingdatasourcekeys" var="bingdatasourcekeys"/>
<#assign language = hstRequest.getLocale().getLanguage() />
<input type="hidden" class="koh-bingdatasource" data-bingdatasource="${bingdatasource}">
<input type="hidden" class="koh-bingdatasourcekeys" data-bingdatasourcekeys="${bingdatasourcekeys}">
<input type="hidden" class="koh-bingdatasourcelanguage" data-bingdatasourcelanguage="${language}">

<#if document??>
	<section class="c-koh-simple-content v-koh-full-page koh-title-dec-map-comp">
	  	<div class="koh-simple-content-container row">
	  		<#if document.logoUrl?? && document.logoUrl?has_content>
			    <div class="koh-title-dec-map-img col-xs-12">
	  				<img src= "${document.logoUrl}"  height="232" alt="<#if document.title?? && document.title?has_content>${document.title?html}</#if>">
			    </div>
	  		</#if>	
		    <div class="koh-simple-content-body col-lg-7 col-md-7 col-sm-12 col-xs-12">
				<#if document.title?? && document.title?has_content>
					<h2 class="koh-simple-content-title" style="">${document.title?html}</h2>
				</#if>
		         <@hst.html hippohtml=document.address/>
			</div>
			<div class="koh-title-dec-map-middle col-lg-1 col-md-1 col-sm-12 col-xs-12"></div>
			<div class="koh-simple-content-body col-lg-4 col-md-4 col-sm-12 col-xs-12">
		         <@hst.html hippohtml=document.description/>
			</div>
			<#if document.latitude?? && document.latitude?has_content>
			<!-- Load Latitude Here -->
				<input type="hidden"  class="koh-latitude" data-latitude="${ document.latitude}">
			</#if>
			<#if document.longitude?? && document.longitude?has_content>
			<!-- Load Longitude Here -->
				<input type="hidden"  class="koh-longitude" data-longitude="${ document.longitude}">
			</#if>
			<#if document.mapUrl?? && document.mapUrl?has_content>
				<div class="koh-title-dec-map-bing col-xs-12">
	  				<div id='myMap' class="koh-title-dec-map-content"></div>
				</div>	  
	  		</#if>
		</div>
	</section>
</#if>

<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-kec-locator.min.js"></script> 
</@hst.headContribution>
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="https://www.bing.com/api/maps/mapcontrol?callback=GetMap"></script> 
</@hst.headContribution>
