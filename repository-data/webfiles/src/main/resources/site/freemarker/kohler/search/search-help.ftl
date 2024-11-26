 <div class="koh-search-results-help">
	 <#list resultHelpList as document>
		<div class="has-edit-button">
			<@hst.manageContent hippobean=document/>
			<div class="koh-article-type">${document.question?html}</div>
			<@hst.html hippohtml=document.answer />
	    </div>
	 </#list>
</div>
