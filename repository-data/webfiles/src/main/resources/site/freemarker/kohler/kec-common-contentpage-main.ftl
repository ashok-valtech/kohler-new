<#include "../include/imports.ftl">
	<#if document??>
		<section class="c-koh-simple-content v-koh-full-page koh-title-desc-comp">
			<div class="koh-simple-content-container">
				<#if document.title?? && document.title?has_content>
					<h2>${document.title?html}</h2>
				</#if>
				<div class="koh-simple-content-body">
					<@hst.html hippohtml=document.content/>
				</div>
			</div>
		</section>
	</#if>