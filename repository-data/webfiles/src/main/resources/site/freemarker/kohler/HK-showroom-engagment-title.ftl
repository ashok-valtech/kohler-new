<#include "../include/imports.ftl">

<#if document??>
	<section class="c-koh-article-pages">
    	<div class="koh-article-pages-container page-heading-container">
			<h1 class="koh-article-pages-title"><#if document.title?? && document.title?has_content>${document.title}</#if></h1>
		</div>
	</section>
</#if>