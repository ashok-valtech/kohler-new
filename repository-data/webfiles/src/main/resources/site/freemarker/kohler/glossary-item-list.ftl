 <#include "../include/imports.ftl">
<@fmt.message key="glossary" var="glossary"/>
<@fmt.message key="back-to-top" var="backToTop"/>
<@fmt.message key="glossaryMetaDesc" var="glossaryMetaDesc"/>

 		<section class="c-koh-simple-content v-koh-full-page">
 			<div class="koh-simple-content-container">
 				<h2 class="koh-simple-content-title">${glossary}</h2>
 				<div class="koh-simple-content-body">
 					<#if pageable?? && pageable.items?has_content>
 						<#assign counter=0>
 						<#list pageable.items as item>
 							<#if counter == 0>
 								<#assign counter=1>
 							<#else>
 								<#assign counter=0>	
 							</#if>
 							<h3>
 								<a name = ""></a>
 								${item.title?html}
 							</h3>
 							<p>${item.introduction?html}</p>
 							<#if counter == 0>
 								<ul>
								 	<li><a href="#top">${backToTop}</a></li>
								</ul>
 							</#if>
 						</#list>
 					</#if>
 				</div>
 			</div>
 		</section>
 
	<@fmt.message key="company.name" var="companyName"/>	
	<@fmt.message key="glossary" var="glossary"/>			
	<@hst.headContribution category="title">
		<title>${glossary} | ${companyName}</title>
	</@hst.headContribution>
	
<@hst.headContribution category="meta-tags">
	<meta name="description" content="${glossaryMetaDesc}"/>
</@hst.headContribution>
	