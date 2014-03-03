<%@page defaultCodec="none" %>
<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />
<g:javascript>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source:	function(request, response) { 
			$.getJSON('${createLink(controller:"${attrs.controller}", action: "${attrs.action}")}?term=' + request.term + '&domain=${attrs.domain}&primarybind=${attrs.primarybind}&searchField=${attrs.searchField}&max=${attrs.max}&order=${attrs.order}&collectField=${attrs.collectField}',
			{ primaryid: $('#${attrs.hidden}').val() },
			response);  
		},
		<g:if test="${attrs.hidden2&&attrs.setId}">
		select: function(event, ui) {
			<g:if test="${attrs.hidden2}">
				$('#${attrs.hidden2}').val(ui.item.id);
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