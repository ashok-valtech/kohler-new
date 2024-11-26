<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-showroom-locations">
		<div class="container">
			<div class="row koh-map-outer-container">
				<#if document.title?? && document.title?has_content>
	  				<div class="koh-location-title">
	  					<h2>${document.title}</h2>
	  				</div>
				</#if>
				<#if document.kohler_articledescriptivecarousaldocument??>
				<div class="koh-location-img-carousel">
					<div class="koh-carousel-showroom koh-theme-dark">
						<div class="koh-slide-collection-showroom">
							<#list document.kohler_articledescriptivecarousaldocument as item>
								<div class="koh-banner-slide-showroom v-koh-simple m-koh-linked">
									<div class="koh-banner-background-showroom">								
										<#if item.carousalImageURL?? && item.carousalImageURL?has_content>
											<img src="${item.carousalImageURL}" alt="" title=""/>
										</#if>
									</div>
								</div>
							</#list>	
						</div>
					</div>	
			    </div>
		        </#if>
				<div class="koh-showroom-lists col-xs-12 col-sm-12 col-sm-offset-0 col-md-4">
				<ul>
					<#if document.kohler_articledescriptivecarousaldocument??>
						<#list document.kohler_articledescriptivecarousaldocument as item>
								<#if item.learnMoreLink?? && item.learnMoreLink?has_content>
			                		<#assign directionCTA = item.learnMoreLink />
			                		<#assign target="_blank" />
		                            <#if directionCTA?starts_with( "/")>
		                            	<#assign target="_self" />
		                            </#if>
		                        </#if>
		                        <#if item.viewAllLink?? && item.viewAllLink?has_content>
			                		<#assign virtualShowroomCTA = item.viewAllLink />
			                		<#assign target="_blank" />
		                            <#if virtualShowroomCTA?starts_with( "/")>
		                            	<#assign target="_self" />
		                            </#if>
		                        </#if>
		                        <#if item.logoImageURL2?? && item.logoImageURL2?has_content>
			                		<#assign appointmentCTA = item.logoImageURL2 />
			                		<#assign target="_blank" />
		                            <#if appointmentCTA?starts_with( "/")>
		                            	<#assign target="_self" />
		                            </#if>
		                        </#if>
				          		<div class="koh-showroom-list row">
				          			<#if item.description?? && item.description?has_content>
				          				<div class="koh-showroom-name">
				                    		<@hst.html hippohtml=item.description/>
			                    		</div>
				                    </#if>
				                    <#if item.carousalImageTitle?? && item.carousalImageTitle?has_content>
					                    <div class="koh-showroom-address">
				                    		${item.carousalImageTitle}
			                    		</div>
		                    		</#if>
		                    		<#if item.navigationLink?? && item.navigationLink?has_content>
			                    		 <div class="koh-showroom-phone">
				                    		${item.navigationLink}
			                    		</div>
		                    		</#if>
		                    		<#if item.carousalImageAlt?? && item.carousalImageAlt?has_content>
			                    		 <div class="koh-showroom-openhours">
				                    		${item.carousalImageAlt}
			                    		</div>
		                    		</#if>
				                    <#if item.learnMoreLink?? && item.learnMoreLink?has_content>
					                    <a class="koh-direction-title col-xs-12 col-xs-offset-1 col-sm-12 col-sm-offset-0 col-md-12 p-0 mb-2" href="${directionCTA}" target="${target}"><#if item.learnMoreLabel?? && item.learnMoreLabel?has_content>${item.learnMoreLabel}</#if></a>
				                    </#if>
				                    <#if (item.viewAllLink?? && item.viewAllLink?has_content) && (item.logoImageURL2?? && item.logoImageURL2?has_content)>
					                    <a class="koh-virtual-title col-xs-12 col-xs-offset-1 col-sm-6 col-sm-offset-0 col-md-6 p-0 pl-md-1 mb-2 mb-lg-0" href="${virtualShowroomCTA}" target="${target}"><#if item.viewAllLabel?? && item.viewAllLabel?has_content>${item.viewAllLabel}</#if></a>
					                    <a class="koh-appointment-title col-xs-12 col-xs-offset-1 col-sm-6 col-sm-offset-0 col-md-6 p-0 pl-md-1 mb-2 mb-lg-0" href="${appointmentCTA}" target="${target}"><#if item.logoImageURL1?? && item.logoImageURL1?has_content>${item.logoImageURL1}</#if></a>
				                    <#elseif item.viewAllLink?? && item.viewAllLink?has_content>
					                    <a class="koh-virtual-title col-xs-12 col-xs-offset-1 col-sm-12 col-sm-offset-0 col-md-12 p-0 mb-2" href="${virtualShowroomCTA}" target="${target}"><#if item.viewAllLabel?? && item.viewAllLabel?has_content>${item.viewAllLabel}</#if></a>
				                    <#else>
				                    	<#if item.logoImageURL2?? && item.logoImageURL2?has_content>
						                    <a class="koh-appointment-title col-xs-12 col-xs-offset-1 col-sm-12 col-sm-offset-0 col-md-12 p-0 mb-2" href="${appointmentCTA}" target="${target}"><#if item.logoImageURL1?? && item.logoImageURL1?has_content>${item.logoImageURL1}</#if></a>
					                    </#if>
				                    </#if>
				               </div>
		         			</#list>
	         			</#if>
         			</ul>
     			</div>
     		</div>
		</div>
	</section>
</#if>

<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-matterport-location.min.js" type="text/javascript"></script>
</@hst.headContribution>

