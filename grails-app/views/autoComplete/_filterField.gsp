
<div class="triggerFilterss">

<label>Enable Filter?</label> <input type="checkbox" name="check" id="${attrs.id}TriggerCheck"   onChange="${attrs.id}TriggerFilter(this)" /></div>
<g:javascript>

function ${attrs.id}TriggerFilter(e) {
	
	if (e.checked==true) {
		$.get('<g:createLink action='${attrs.filteraction}' controller="${attrs.controller}" params="[ id: ''+attrs.id+'', hidden:''+attrs.hidden+'',acontroller: ''+attrs.controller+'', filteraction2: ''+attrs.filteraction2+'', domain: ''+attrs?.domain+'', searchField: ''+attrs?.searchField+'', max: ''+attrs?.max+'', order: ''+attrs?.order+'', collectField: ''+attrs?.collectField+'' ,appendValue: ''+attrs.appendValue+'' , appendName: ''+attrs.appendName+'' ]"/>',function(data){
			$('#FilterField').hide().html(data).fadeIn('slow');
		});
	}else{
		$('#FilterField').hide().html('').fadeIn('slow');
	}
	
	
}
</g:javascript>

<div id="FilterField" class="filterField">

</div>
