<#include "../include/imports.ftl">
<#if document??>
	<div class="koh-cmf-main-content">
		<#if document.description.content?? && document.description.content?has_content>
			<div class="koh-main-txt-container">
				<div class="row">
					<div class="col">
						<div class="koh-inner-txt-padding">
							<@hst.html hippohtml=document.description/>
						</div>
					</div>
				</div>
			</div>
		</#if>
		<div class="koh-cmf-tabs">
			<ol class="koh-cmf-tab-list">
				<#if document.articleThreeImageCompound??>
					<#list document.articleThreeImageCompound as item>
						<#if item.navigationLink?? && item.navigationLink?has_content>
			         		<#assign navigationLink = item.navigationLink />
			        		<#assign target="_blank" />
			                <#if navigationLink?starts_with( "/")>
			                    <#assign target="_self" />
			                </#if>
			            </#if>
			            <#if item.imagePath?? && item.imagePath?has_content>
			            	<#assign tabId = "${item.imagePath}">
			            </#if>
						<#if navigationLink?? && item.navigationLink?has_content>
							<a href="${navigationLink}" target="${target}">
								<li id="koh-cmf-tab-${tabId}">
									<#if item.imageTitle?? && item.imageTitle?has_content>${item.imageTitle}</#if>
								</li>
							</a>
						</#if>
					</#list>
				</#if>
			</ol>
		</div>
	</div>
</#if>

<@hst.headContribution category="ext-scripts">
	<@hst.webfile path="/" var="link" />
	<script src="${link}/js/custom/c-koh-cmf-component.min.js" type="text/javascript"></script>
</@hst.headContribution>
