<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-bathtub-3by3-tile-image">
		<div class ="koh-bathtub-3by3-tile-promo-container">
			<#if document.title?? && document.title?has_content>
				<div class="koh-bathtub-head-title">
					<h2>${document.title}</h2>
				</div>
			</#if>
			<#if document.description.content?? && document.description.content?has_content>
				<div class="koh-bathtub-head-description">
					<@hst.html hippohtml=document.description/>
				</div>
			</#if>
			<div class="row koh-bathtub-3by3-tile-promo">
				<#if document.articleThreeImageCompound??>
					<#list document.articleThreeImageCompound as item>
	         			<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
							<figure class="koh-bathtub-3by3-tile-promo-item">
								<#if item.imagePath?? && item.imagePath?has_content>
									<img src="${item.imagePath}">
								</#if>
	            			</figure>
	            			<#if item.imageTitle?? && item.imageTitle?has_content>
	            				<h3 class="koh-bathtub-image-title">${item.imageTitle}</h3>
	        				</#if>
	        				<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
		            			<div class="koh-bathtub-image-description"><@hst.html hippohtml=item.imageDescription/></div>
	        				</#if>
	         			</div>
	     		  	</#list>
	     		</#if>
			</div>
		</div>
	</section>
</#if>
