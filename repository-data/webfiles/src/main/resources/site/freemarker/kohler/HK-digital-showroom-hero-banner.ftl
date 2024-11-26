<#include "../include/imports.ftl">
        
<#if document??>
	<section class="c-koh-150-anniversary-full-width-banner">
    	<div class="koh-150-anniversary-top-banner-container">
			<#if document.imagePath?? && document.imagePath?has_content>
				<img src="${document.imagePath}">
			</#if>
        </div>
    </section>
</#if>