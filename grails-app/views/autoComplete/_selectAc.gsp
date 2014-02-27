<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />
<g:javascript>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source: '<g:createLink action='${attrs.action}' controller="${attrs.controller}"
	 	params="[domain: ''+attrs.domain+'', searchField: ''+attrs.searchField+'', max: ''+attrs.max+'', order: ''+attrs.order+'', collectField: ''+attrs.collectField+'']"/>',
		select: function(event, ui) {
			$('#${attrs.hidden}').val(ui.item.id);
			$('#${attrs.setId}').attr('primaryid',ui.item.id);
		},dataType: 'json'
	});
});
</g:javascript>