<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-150-2by2-tile-image">
		<div class ="koh-150-2by2-tile-promo-container">
			<div class="row koh-150-2by2-tile-promo">
				<#if document.articleThreeImageCompound??>
					<#list document.articleThreeImageCompound as item>
	         			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
							<figure class="koh-150-2by2-tile-promo-item">
								<#if item.imagePath?? && item.imagePath?has_content>
									<img src="${item.imagePath}">
								</#if>
	            			</figure>
	            			<#if item.imageTitle?? && item.imageTitle?has_content>
	            				<h3 class="koh-2by2-150-image-title">${item.imageTitle}</h3>
	        				</#if>
	        				<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
		            			<div class="koh-2by2-150-image-description"><@hst.html hippohtml=item.imageDescription/></div>
	        				</#if>
	         			</div>
	     		  	</#list>
	     		</#if>
			</div>
		</div>
	</section>
</#if>
