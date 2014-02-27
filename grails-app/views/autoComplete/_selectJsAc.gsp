<g:javascript>
 function ${attrs.id}Update(e) { 
  	if (e) {
  		$('#${attrs.hidden}').val(e.value);
  	}
 }	
</g:javascript>
<input type=hidden id="${attrs.hidden}" name="${attrs.hidden}" value=""/>

