<#include "../../include/imports.ftl">

<section class="c-koh-product-faceted-search v-koh-default">  
	<@hst.include ref="container"/>
	
	<@hst.setBundle basename="apac.labels"/>
    <@fmt.message key="back-to-top" var="backTotop"/>
	<div class="koh-back-top">
      <button>
      		<span class="icon" data-icon="&#xe613"></span>
       ${backTotop}
      </button>
    </div>
</section>