<#include "../include/imports.ftl">
        
<#if document??>
	<section class="c-koh-150-anniversary-top-banner">
    	<div class="koh-150-anniversary-top-banner-container">
            <#if document.title?? && document.title?has_content>
			     <#assign title = document.title?replace("(R)","<sup>&reg;</sup>")/>
				 <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
			     <#assign imgAlt = document.title?replace("(R)","&reg;")/>
				 <#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
			</#if> 
			<#if document.imageAlt?? && document.imageAlt?has_content>
        		<#assign imgAlt=document.imageAlt>
        		<#assign imgAlt = imgAlt?replace("(R)","&reg;")/>
				<#assign imgAlt = imgAlt?replace("(TM)","&trade;")/>
        	</#if>
            <#if document.imageLink?? && document.imageLink?has_content>
                 <#assign docImgLink=document.imageLink />
                <div class="koh-150-anniversary-banner">
                    <img src="${docImgLink}" alt="${imgAlt}" <#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if>>
                    <div class="koh-150-anniversary-content">
		              <#if document.title?? && document.title?has_content>
			               <#assign title = document.title?replace("(R)","<sup>&reg;</sup>")/>
						   <#assign title = title?replace("(TM)","<sup>&trade;</sup>")/>
			                <div class="koh-150-anniversary-2nd-banner-title">${title}</div>
		              </#if>   
		        	  <#if document.description?? && document.description?has_content>
		                <div class="koh-150-anniversary-2nd-banner-desc">
		                    <@hst.html hippohtml=document.description/>
		                </div>
		              </#if>
		            </div>
                </div>
            </#if>
        </div>
    </section>
</#if>