<#include "../include/imports.ftl">

<#if pageable?? && pageable.items?has_content>
	<section class = "c-koh-ramadan-store-list">
		<div class = "koh-ramadan-store-list-container">
			<#list pageable.items as item>
				<#if item.imagePath?? && item.imagePath?has_content>
					<div class = "koh-top-static-image">
						<img src="${item.imagePath}">
					</div>
				</#if>
				<#if item.title?? && item.title?has_content>
					<div class = "koh-ramadan-store-header">
		                <span class="koh-ramadan-store-list-title">${item.title}</span>
		            </div>
	            </#if>
				<div class = "row koh-ramadan-store-list-row">
					<#if item.kohler_promolandingpagestorelistcompound??>
				 		<#list item.kohler_promolandingpagestorelistcompound as storeList>
					 			<#if storeList.address.content?? && storeList.address.content?has_content>
				                    <div class = "col-lg-4 col-md-4 col-sm-4 col-xs-12 koh-ramadan-list-col">
				                       <@hst.html hippohtml=storeList.address/>
				                    </div>
			                    </#if>
	                		</li>
	            		</#list>
	        		</#if>
    			</div>
	        </#list>   
		</div>
	</section>
</#if>