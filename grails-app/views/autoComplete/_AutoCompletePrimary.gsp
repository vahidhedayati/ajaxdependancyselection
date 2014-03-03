<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />

<g:javascript>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source: '<g:createLink action='${attrs.action}' controller="${attrs.controller}"
	 	params="[domain: ''+attrs.domain+'', searchField: ''+attrs.searchField+'', max: ''+attrs.max+'', order: ''+attrs.order+'', collectField: ''+attrs.collectField+'']"/>',
	 	<g:if test="${attrs.hidden&&attrs.setId}">
		select: function(event, ui) {
			<g:if test="${attrs.hidden}">
				$('#${attrs.hidden}').val(ui.item.id);
			</g:if>
			<g:if test="${attrs.setId}">
				$('#${attrs.setId}').attr('primaryid',ui.item.id);
			</g:if>	
		}
		</g:if>
		,dataType: 'json'
	});
});
</g:javascript>
