<#include "../include/imports.ftl">

<#if document??>
	<div class="koh-moxie">
      <#if document.navigationPath?? && document.navigationPath?has_content>
	      <#assign ctaLink = document.navigationPath>
	      <#assign target="_blank" />
          <#if ctaLink?starts_with( "/")>
            <#assign target="_self" />
          </#if>
      </#if>
      <#if document.navigationPath?? && document.navigationPath?has_content>
        <a class="koh-moxie-cta" href="${ctaLink}" target="${target}">
        	${document.buttonText}
    	</a>
      </#if>	
	</div>
</#if>