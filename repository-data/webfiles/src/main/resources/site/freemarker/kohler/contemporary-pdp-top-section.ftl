<#include "../include/imports.ftl">

<@fmt.message key="share" var="share"/>
<@fmt.message key="facebook" var="facebook"/>
<@fmt.message key="twitter" var="twitter"/>

<#if document??>
<section class="c-koh-shop-by-look-pdp v-koh-default c-koh-shop-by-look-details-ap">
   <div class="koh-product-top-row">
	   
      <div class="koh-product-image-sliders">
         <div class="product-slider product-images__primary">
	         <#if document.kohler_articledescriptivecarousaldocument??>
	            <#list document.kohler_articledescriptivecarousaldocument as item>
	            	<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
			            <div class="product-slide">
			               <div class="product-zoom product-zoom-contract">
			                  <img class="koh-product-img" src="${item.carousalImageURL}" data-magnify-src="${item.carousalImageURL}" width="1800" height="800" alt="">
			               </div>
			               <div class="product-zoom product-zoom-expand">
			                  <img class="koh-product-img" src="${item.carousalImageURL}" data-magnify-src="${item.carousalImageURL}" width="1800" height="800" alt="">
			               </div>
			               <div class="product-slide-expand">
			                  <button class="product-slide-expand--open"></button>
			               </div>
		            	</div>
	            	</#if>
             	</#list>
         	</#if>
     	</div>
	         
       <div class="product-slider">
        <div class="product-images__navigation">
           <#if document.kohler_articledescriptivecarousaldocument??>
               <#list document.kohler_articledescriptivecarousaldocument as item>
	            	<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
			            <div class="product-slide">
		                  <img src="${item.carousalImageURL}" data-magnify-src="${item.carousalImageURL}" width="1800" height="800" alt="">
		               </div>
    	   			</#if>
             	</#list>
         	</#if>
        </div>
      </div>
		         
         <div class="product-slider product-slider-mobile">
            <div class="product-images__navigation-mobile">
               <#if document.kohler_articledescriptivecarousaldocument??>
               		<#list document.kohler_articledescriptivecarousaldocument as item>
		            	<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
				            <div class="product-slide">
			                  <img src="${item.carousalImageURL}" data-magnify-src="${item.carousalImageURL}" width="1800" height="800" alt="">
			                </div>
        	   			</#if>
             		</#list>
         		</#if>
            </div>
         </div>
      </div>
	      
     <div class="koh-product-expand-container">
         <div class="koh-product-expand-inner">
         </div>
         <div class="product-slide-expand">
            <button class="product-slide-expand--close"></button>
         </div>
      </div>
      
	     
      <div class="koh-product-details">
	      <#if document.kohler_articledescriptivecarousaldocument??>
            <#list document.kohler_articledescriptivecarousaldocument as item> 
		         <div class="koh-product-name-and-short-description">
		            <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content>
		            	<h1 class="koh-product-name">${item.carousalImageTitle}</h1>
		            </#if>
		           <#if item.navigationLink?? && item.navigationLink?has_content>
		            	<div class="koh-product-short-description">${item.navigationLink}</div>
		            </#if>
		            <#if item.description?? && item.description?has_content>
			            <div class="koh-product-short-description"><@hst.html hippohtml=item.description/></div>
		            </#if>
		         </div>
		         <#if item.logoImageURL1?? && item.logoImageURL1?has_content>
			         <div class="koh-product-skus-colors">
			            <div class="koh-product-colors">
			               <span class="koh-product-colors-title">${item.logoImageURL1}</span>
			            </div>
			         </div>
		         </#if>
		         <#if item.learnMoreLink?? && item.learnMoreLink?has_content>
		        	<div class="koh-buythis-look-button">
		            	<a href="${item.learnMoreLink}" target="_blank" rel="noopener noreferrer"><#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>${item.learnMoreLabel}</#if></a>
		         	</div>
	         	</#if>
	         	<#if item.viewAllLink?? && item.viewAllLink?has_content>
			         <div class="koh-customize-button">
			            <a href="${item.viewAllLink}" target="_self" rel="noopener noreferrer"><#if item.viewAllLabel?? && item.viewAllLabel?has_content>${item.viewAllLabel}</#if></a>
			         </div>
	         	</#if>
         	</#list>
 		  </#if>
	  <div class="koh-product-tools">
	  	<div class="koh-product-share koh-shopby-look-share">
   		  	<div class="koh-share-title">${share}:</div>
   			<ul class="koh-product-popover">
          	   <li>
                 <a class="koh-share-facebook" href="https://www.facebook.com/" target="_blank"><i class="fa fa-facebook"></i></a>
               </li>
   			</ul>
    	 </div>
	  </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
		<script src="${link}/js/custom/c-koh-shopby-look-pdp.min.js" type="text/javascript"></script>
</@hst.headContribution>
<@hst.headContribution category="ext-scripts">
		<script src="${link}/js/zoom/jquery.zoom.min.js" type="text/javascript"></script>
</@hst.headContribution>
<@hst.headContribution category="ext-scripts">
		<script src="${link}/js/magnify/jquery.magnify.min.js" type="text/javascript"></script>
</@hst.headContribution>
<@hst.headContribution category="ext-scripts">
		<script src="${link}/js/imageviewer/imageviewer.min.js" type="text/javascript"></script>
</@hst.headContribution>

<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css'>


</#if>
