<#include "../include/imports.ftl">

<#if document??>
	<section class="koh-customer-says-carousel-component">
		<#if document.title?? && document.title?has_content>
	      <div class="koh-text-description">${document.title}</div>
	  	</#if>
	   <div class="koh-customer-says-carousel">
	      <div class="koh-customer-carousel">
	  		<#list document.kohler_articledescriptivecarousaldocument as item>
	         	<div class="koh-customer-caption">
		         	<#if item.navigationLink?? && item.navigationLink?has_content>
		         		<#assign navigationLink = item.navigationLink />
	            		<#assign target="_blank" />
	                    <#if navigationLink?starts_with( "/")>
	                        <#assign target="_self" />
	                    </#if>
							<a href="${navigationLink}" target="${target}">
								<#if item.description.content?? && item.description.content?has_content>
						            <div class="koh-customer-description">
						            	<@hst.html hippohtml=item.description/>
						            </div>
		           		   		</#if>
							</a>
						<#else>
							<#if item.description.content?? && item.description.content?has_content>
					            <div class="koh-customer-description">
					            	<@hst.html hippohtml=item.description/>
					            </div>
		           		   </#if>
						</#if>
					</div>
				</#list>
			</div>
		</div>
	</section>
</#if>


<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-customer-says.min.js" type="text/javascript"></script>
</@hst.headContribution>