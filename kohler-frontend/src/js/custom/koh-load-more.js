/*
 * Copyright © 2000-2017 Kohler Company. All Rights Reserved.
 */

"use strict";
$( document ).ready(function() {
	var pressReleasesLazyLink = $('#pressReleasesLazyLink').val();
	var totalProjects = $('#totalProjects').val();
	var offset = $('#offset').val();
    $(".koh-load-more").click(function(){
    	$.ajax({
            url: pressReleasesLazyLink,
            cache: !1,
            data: {'offset':offset},
            success: function (data) { 
            	if(data.success == true){
            		offset=data.offset;
            		$('#offset').val(offset);
            		var mainList=$("#mainList");
            		if(offset >= totalProjects || data.items===""){
            			$(".koh-load-more").hide();
            		}
            		for(var i=0; i < data.items.length; i++){
            			var descItem = data.items[i].description;
            			var desc=descItem.replace(/\(R\)/g,"<sup>&reg;</sup>");
            			var desc=desc.replace(/\(TM\)/g,"<sup>&trade;</sup>");
                        var html="<li><time>"+data.items[i].date+"</time><a href='"+data.items[i].hstLink+"'>"+desc+"</a></li>";
            			mainList.append(html);
            		}
            	}
            }
	        }).done(function () {
	        
	        }).fail(function () {
	        }) 
	    });
});

 