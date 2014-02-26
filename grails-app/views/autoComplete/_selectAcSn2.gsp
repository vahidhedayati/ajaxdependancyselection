<input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${required} ${styles} ${name} />

<script type='text/javascript'>
$(document).ready(function() {
	$('#${attrs.id}').autocomplete({ 
		source:	function(request, response) { 
			$.getJSON('${createLink(controller:"${attrs.controller}", action: "${attrs.action}")}?term=' + request.term + '&domain=${attrs.domain}&domain2=${attrs.domain2}&primarybind=${attrs.primarybind}&searchField=${attrs.searchField}&max=${attrs.max}&order=${attrs.order}&collectField=${attrs.collectField}',
			{ primaryid: $('#${attrs.hidden}').val() },
			response);  
		},
		select: function(event, ui) {
			$('#${attrs.setId}').attr('primaryid',ui.item.id);
		}
		,
		dataType: 'json'});
	});
</script>