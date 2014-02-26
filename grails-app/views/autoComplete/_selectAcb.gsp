<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />
<script type='text/javascript'>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source: '<g:createLink action='${attrs.action}' controller="${attrs.controller}"
	 	params="[domain: ''+attrs.domain+'', searchField: ''+attrs.searchField+'', max: ''+attrs.max+'', order: ''+attrs.order+'', collectField: ''+attrs.collectField+'']"/>',
		dataType: 'json'
	});
});
</script>