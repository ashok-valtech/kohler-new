<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-simple-content v-koh-full-page">
	  	<div class="koh-simple-content-container">
	  		<@hst.manageContent hippobean=document/>
		    <div class="koh-simple-content-body">
		         <@hst.html hippohtml=document.content/>
			</div>
		</div>
	</section>
</#if>

<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="company.region" var="companyRegion"/>
<#assign pageTitle=companyRegion + " | " +  companyName />
<#if document.pageTitle?? && document.pageTitle?has_content>
	<#assign pageTitle=document.pageTitle + " | " + companyRegion />
<#else>
	<#assign pageTitle=companyRegion + " | " + companyName />
</#if>

<@hst.headContribution  category="ext-scripts">
  <script type="text/javascript">
  		if(document.title === "" ){
			document.title = '${pageTitle}';
		}
  </script>
</@hst.headContribution>
