<%@page defaultCodec="none" %>
<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />
<g:javascript>
$(document).ready(function() {
	$('#${attrs.id2}').autocomplete({ 
		source:	function(request, response) { 
			$.getJSON('${createLink(controller:"${attrs.controller}", action: "${attrs.action}")}?term=' + request.term + '&domain=${attrs.domain2}&primarybind=${attrs.primarybind}&searchField=${attrs.searchField2}&max=${attrs.max}&order=${attrs.order}&collectField=${attrs.collectField2}',
			{ primaryid: $('#${attrs.hidden}').val() },
			response);  
		},
		<g:if test="${attrs.hidden2&&attrs.setId2}">
		select: function(event, ui) {
			<g:if test="${attrs.hidden2}">
				$('#${attrs.hidden2}').val(ui.item.id);
			</g:if>
			<g:if test="${attrs.setId2}">
				$('#${attrs.setId2}').attr('primaryid',ui.item.id);
			</g:if>	
		}
		</g:if>
		,dataType: 'json'		
	});
});
</g:javascript>
<g:if test="${attrs.hidden2}">
	<input type=hidden id="${attrs.hidden2}" name="${attrs.hidden2}" value=""/>
</g:if>