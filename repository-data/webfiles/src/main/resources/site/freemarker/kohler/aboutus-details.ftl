<#include "../include/imports.ftl">
<#if document??>
	<section class="c-koh-simple-content v-koh-full-page">
	  	<div class="koh-simple-content-container">
		    <div class="koh-simple-content-body">
		    	 <#if document.title?? && document.title?has_content>
		    	 	${document.title} 
		    	 </#if>
		         <@hst.html hippohtml=document.content/>
			</div>
		</div>
	</section>
</#if>