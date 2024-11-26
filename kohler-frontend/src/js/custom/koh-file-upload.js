
"use strict";
var apikeyerror = $('#apikeyerror').val();
var fileuploaderror = $('#fileuploaderror').val();
var fileuploadsizeerror = $('#fileuploadsizeerror').val();

$(document).ready(function() {	
$(".fileuploadImage").on('change', function() {
			    $("#errormsg").hide();
        		$("#successmsg").hide();
        		var s1=validationFileField();
  		    });
			$("#apiKey").on('keyup', function() {
			    $("#errormsg").hide();
        		$("#successmsg").hide();
        		var s2=validateApiKeyField(); 
        		     			
  		    });
			
			function validationFileField(){	
				$("#errorKeyFileSize").hide();
				$("#errorKeyFile").hide();	
				
				
				var storefileuploadextn = $('#storefileextn').val().split(",");
			    var storefileuploadsize = $('#storefilesize').val();		    
			    var filesize = parseInt(storefileuploadsize);				
				var filePath = $(".fileuploadImage")[0].value;
		        var fileUploadExtn = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();	        	       
		        if(storefileuploadextn.indexOf(fileUploadExtn)!==-1)
				{
                   $("#errorKeyFile").hide();
                   if($(".fileuploadImage")[0].files[0].size >= 1024*filesize){		
	                     $("#errorKeyFile").html(fileuploadsizeerror).show();
	                     $(".fileuploadImage")[0].focus();
	                     return false;
		            }else{
		            	$("#errorKeyFile").hide()
		                     return true;
		            }	 
				} 
				else
				{
					$("#errorKeyFile").html(fileuploaderror).show();
					$(".fileuploadImage")[0].focus();
					return false;
				}      	
			}
			
        function validateApiKeyField() {
        	
        	var apikey = document.forms["uploadForm"]["api-key"].value;
		    if (apikey == "") {	        
		        $("#errormsg").html(apikeyerror).show();
		        var textbox = document.getElementById("apiKey");
                textbox.focus();
		        return false;
		    }else{
		    	$("#errormsg").hide();
		        var textbox = document.getElementById("apiKey");
		        return true;
		    }		    
           }
			
			$("form").submit(function(e){
				e.preventDefault();			
				var formAction = $(this).attr('action');
				$("#errormsg").hide();
        		$("#successmsg").hide();
				var s2=validationFileField();
				var s1=validateApiKeyField();			
				if(s1){
        			    if(!s2){
        					return false;
        				}
        			}else{
        				return false;
        			}
        			
                var data = new FormData();
                var apiKey = document.getElementById('apiKey').value;
                var storeFile = document.getElementById('storeFile');
                data.append('api-key', apiKey);
                data.append('file', storeFile.files[0]);
        
                if (typeof(FileReader) != "undefined") {
                	console.log ('FileReader is available');
	                var reader = new FileReader();
	                reader.onloadend = function(e) {
		            	  console.log(e);
		            	  console.log('calling ajax: ');
		            	  $('#koh-dealerssubmit').prop('disabled', true);
		                  $.ajax({
		      	       		url: formAction,
		    	       	    data: data,
		    	       	    cache: false,
		    	       	    contentType: false,
		    	       	    processData: false,
		    	       	    type: 'POST',
		    	       	    success: function(data,textStatus,jqXHR){
		    	       	   		   var responseData=jQuery.parseJSON(JSON.stringify(data));	   		    	       	 
		    	       	    	   if(responseData.flag == "Failed To Upload"){
		    	       	    	   		$("#errormsg").html(responseData.message).show();
		    	       	    	   		$('#koh-dealerssubmit').prop('disabled', false);
		    	       	    	   }else{
			                       		$("#successmsg").html(responseData.message).show();
			                       		$(".fileuploadImage")[0].value="";
			                       		$('#koh-dealerssubmit').prop('disabled', false);
			                       }
			                       document.forms["uploadForm"]["api-key"].value="";
		    	       	    },
		                    error: function(){
		                         console.log ('Error in attachment');		                        		                         
		                          document.forms["uploadForm"]["api-key"].value="";
		                          $(".fileuploadImage")[0].value="";
		                          $('#koh-dealerssubmit').prop('disabled', false);
		                        
		                      }
		                  });
		             } 
		             reader.readAsDataURL(storeFile.files[0]);    
		    	  } else {
		    	  	console.log ('FileReader undefined');
		    	  }    
			});
			
});