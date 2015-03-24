<g:autoField   class = "${clazz}" styles="${styles}" id='${attrs.id}' value = '${attrs.value}' required = "${required}"  name = "${name}" disabled = "${attrs.disabled }"  />
<g:javascript>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source: '<g:createLink action='${attrs.action}' controller="${attrs.controller}"
	 	params="[domain: ''+attrs.domain+'', searchField: ''+attrs.searchField+'', max: ''+attrs.max+'', order: ''+attrs.order+'', collectField: ''+attrs.collectField+'']"/>',
		dataType: 'json'
	});
});
</g:javascript>