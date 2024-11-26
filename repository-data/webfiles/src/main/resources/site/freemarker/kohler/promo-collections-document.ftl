<#include "../include/imports.ftl">

<#if pageable?? && pageable.items?has_content>
<#assign counter=0>
  <#list pageable.items as item>
     <#if item?? && item.navigationURL?has_content>
       <#assign link=item.navigationURL />
        <#assign target="_blank" />
        <#if link?starts_with("/")>
            <#assign target="_self" />
        </#if>
     </#if> 
	<div class="koh-collections-row">
	   <#if (counter%2)==0>
		<div class="row koh-top-row">
			<div class="col-sm-5">
				<a href="${link}" class="koh-collection-item-title" target="${target}">
				  <#if (item?? && item.title?has_content) || (item?? && item.shortDescription?has_content) || (item?? && item.navigationLabel?has_content)>
				    <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
				    <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
				    <#assign navigationLabel = item.navigationLabel?replace("(R)","<sup>&reg;</sup>")/>
				    <#assign navigationLabel = navigationLabel?replace("(TM)","<sup>&trade;</sup>")/>
					<div class="koh-collection-item-title-inner">
						<h6>${title}</h6>
						<p>${item.shortDescription?html}</p>
						<span class="koh-collection-item-title-link"><span>${navigationLabel}</span> <i class="icon--chevron-right"></i></span>
					</div>
				  </#if>	
				</a>
			   <#if item?? && item.smallImageURL?has_content>
		        <#assign smallImageURL=item.smallImageURL[0]/>	
				 <a href="${link}" class="koh-collection-top-small-img" target="${target}"><img src="${smallImageURL}" <#if item.imageSmallAlt?? && item.imageSmallAlt?has_content>alt="${item.imageSmallAlt}"</#if> <#if item.imageSmallTitle?? && item.imageSmallTitle?has_content>title="${item.imageSmallTitle}"</#if>></a>
			   </#if>	 		
			</div>
		  <#if item?? && item.largeImageURL?has_content>
		   <#assign largeImageURL=item.largeImageURL/>	
			<div class="col-sm-7">
				<a href="${link}" class="koh-collection-top-big-img" target="${target}"><img src="${largeImageURL}" <#if item.imageLargeAlt?? && item.imageLargeAlt?has_content>alt="${item.imageLargeAlt}"</#if> <#if item.imageLargeTitle?? && item.imageLargeTitle?has_content>title="${item.imageLargeTitle}"</#if>></a>
			</div>
		  </#if>	
		</div>
	
		<div class="koh-middle-row">
		   <#if item?? && item.title?has_content>
		     <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
		     <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/> 
			 <h6>${title}<i class="icon--chevron-right"></i></h6>
		   </#if>	 
			<section class="c-koh-shopping-guide-carousel v-koh-hero">
				<div class="koh-carousel koh-theme-dark">
					<div class="koh-slide-collection">
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.largeImageURL?has_content>
							   <a href="${link}" target="${target}">
								 <div class="koh-carousel-img-div" style="background-image:url('${item.largeImageURL}');">&nbsp;</div>
							   </a>	
						       </#if>		
							</div>
						</div>
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.smallImageURL?has_content>
							   <a href="${link}" target="${target}"> 
								 <div class="koh-carousel-img-div" style="background-image:url('${item.smallImageURL[0]}');">&nbsp;</div>
							   </a> 	
							   </#if>	
							</div>
						</div>	
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.smallImageURL?has_content>
							   <a href="${link}" target="${target}"> 
								 <div class="koh-carousel-img-div" style="background-image:url('${item.smallImageURL[1]}');">&nbsp;</div>
							   </a>		
							   </#if>	
							</div>
						</div>					
					</div>
				</div>
			</section>
		</div>
	
		<div class="row koh-bottom-row">
		  <#if item?? && item.description?has_content>
			<div class="col-sm-5">
				<div class="koh-collection-item-description">
					<div class="koh-collection-item-description-inner">
						<@hst.html hippohtml=item.description/>
					</div>
				</div>
			</div>
		  </#if>
          <#if item?? && item.smallImageURL?has_content>
            <#assign smallImageURL1=item.smallImageURL[1]/>  	
			<div class="col-sm-5">
				<a href="${link}" class="koh-collection-bottom-img" target="${target}">
					<img src="${smallImageURL1}" <#if item.imageSmallAlt1?? && item.imageSmallAlt1?has_content>alt="${item.imageSmallAlt1}"</#if> <#if item.imageSmallTitle1?? && item.imageSmallTitle1?has_content>title="${item.imageSmallTitle1}"</#if>>
				</a>
			</div>
		  </#if>	
			<div class="col-sm-2">&nbsp;</div>		
		</div>
		
<#--Re Arrange Part -->
     <#else>
		<div class="koh-middle-row">
		   <#if item?? && item.title?has_content>
		     <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
			 <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/> 
			 <h6>${title}<i class="icon--chevron-right"></i></h6>
		   </#if>	 
			<section class="c-koh-shopping-guide-carousel v-koh-hero">
				<div class="koh-carousel koh-theme-dark">
					<div class="koh-slide-collection">
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.largeImageURL?has_content>
							   <a href="${link}" target="${target}">
								 <div class="koh-carousel-img-div" style="background-image:url('${item.largeImageURL}');">&nbsp;</div>
							   </a>	
						       </#if>		
							</div>
						</div>
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.smallImageURL?has_content>
							   <a href="${link}" target="${target}"> 
								 <div class="koh-carousel-img-div" style="background-image:url('${item.smallImageURL[0]}');">&nbsp;</div>
							   </a> 	
							   </#if>	
							</div>
						</div>	
						<div class="koh-banner-slide v-koh-simple m-koh-linked">
							<div class="koh-banner-background">
							   <#if item?? && item.smallImageURL?has_content>
							   <a href="${link}" target="${target}"> 
								 <div class="koh-carousel-img-div" style="background-image:url('${item.smallImageURL[1]}');">&nbsp;</div>
							   </a>		
							   </#if>	
							</div>
						</div>					
					</div>
				</div>
			</section>
		</div>
		
	    <div class="row koh-top-row">
	     <#if item?? && item.largeImageURL?has_content>
		   <#assign largeImageURL=item.largeImageURL/>	
			<div class="col-sm-7">
				<a href="${link}" class="koh-collection-top-big-img" target="${target}"><img src="${largeImageURL}" <#if item.imageLargeAlt?? && item.imageLargeAlt?has_content>alt="${item.imageLargeAlt}"</#if> <#if item.imageLargeTitle?? && item.imageLargeTitle?has_content>title="${item.imageLargeTitle}"</#if>></a>
			</div>
		  </#if>
			<div class="col-sm-5">
				<a href="${link}" class="koh-collection-item-title" target="${target}">
				  <#if (item?? && item.title?has_content) || (item?? && item.shortDescription?has_content) || (item?? && item.navigationLabel?has_content)>
					<div class="koh-collection-item-title-inner">
					 <#assign title = item.title?replace("(R)","<sup>&reg;</sup>")/>
				     <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
				     <#assign navigationLabel = item.navigationLabel?replace("(R)","<sup>&reg;</sup>")/>
				     <#assign navigationLabel = navigationLabel?replace("(TM)","<sup>&trade;</sup>")/>
						<h6>${title}</h6>
						<p>${item.shortDescription?html}</p>
						<span class="koh-collection-item-title-link"><span>${navigationLabel}</span> <i class="icon--chevron-right"></i></span>
					</div>
				  </#if>	
				</a>
			   <#if item?? && item.smallImageURL?has_content>
		        <#assign smallImageURL=item.smallImageURL[0]/>	
				 <a href="${link}" class="koh-collection-top-small-img" target="${target}"><img src="${smallImageURL}" <#if item.imageSmallAlt?? && item.imageSmallAlt?has_content>alt="${item.imageSmallAlt}"</#if> <#if item.imageSmallTitle?? && item.imageSmallTitle?has_content>title="${item.imageSmallTitle}"</#if>></a>
			   </#if>	 		
			</div>
		</div>
		
		<div class="row koh-bottom-row">
		<div class="col-sm-2">&nbsp;</div>
		  <#if item?? && item.smallImageURL?has_content>
            <#assign smallImageURL1=item.smallImageURL[1]/>  	
			<div class="col-sm-5">
				<a href="${link}" class="koh-collection-bottom-img" target="${target}">
					<img src="${smallImageURL1}" <#if item.imageSmallAlt1?? && item.imageSmallAlt1?has_content>alt="${item.imageSmallAlt1}"</#if> <#if item.imageSmallTitle1?? && item.imageSmallTitle1?has_content>title="${item.imageSmallTitle1}"</#if>>
				</a>
			</div>
		  </#if>
		  <#if item?? && item.description?has_content>
			<div class="col-sm-5">
				<div class="koh-collection-item-description">
					<div class="koh-collection-item-description-inner">
						<@hst.html hippohtml=item.description/>
					</div>
				</div>
			</div>
		  </#if>
		</div>
	  </#if>		
	</div>
   <#assign counter=counter+1/> 
  </#list>
</#if>
