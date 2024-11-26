<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-shopping-list-3promo">
		<#if document.title?? && document.title?has_content>
			<div class="koh-head-video-tile">
				<h2>${document.title}</h2>
			</div>
		</#if>
		<#if document.description.content?? && document.description.content?has_content>
			<div class="koh-head-description">
				<@hst.html hippohtml=document.description/>
			</div>
		</#if>
		<div class="row koh-shopping-list-3promo">
			<div class = "koh-shopping-guide-container"> 
				<#if document.articleThreeImageCompound??>
					<#list document.articleThreeImageCompound as item>
	         			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 koh-shopping-guide-3promo">
							<figure class="koh-shopping-item-3promo">
								<#if item.imagePath?? && item.imagePath?has_content>
									<iframe src="${item.imagePath}?rel=0&amp;showinfo=0" width="100%" height="410px" frameborder="0" allowfullscreen></iframe>
								</#if>
	            			</figure>
            				<#if item.imageTitle?? && item.imageTitle?has_content>
	            				<div class="koh-video-title">
		            				${item.imageTitle}
		            			</div>
            				</#if>
            				<#if item.imageDescription.content?? && item.imageDescription.content?has_content>
		            			<div class="koh-video-description">
		            				<@hst.html hippohtml=item.imageDescription/>
		            			</div>
            				</#if>
	         			</div>
         		  	</#list>
         		</#if>
     		</div>
		</div>
	</section>
</#if>
