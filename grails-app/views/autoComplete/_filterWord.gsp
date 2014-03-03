 <div class="filterWord">
 <label>Filter by:</label>
 <input type="text" name="triggerWord" value="${params.triggerWord }"  onClick="this.value='';${params.id}resetValue('${params.filterDisplay}')"" onChange="${params.id}AppendValue(this)" >

<script type='text/javascript'>
function ${params.id}resetValue(displayType) {
	if ((!displayType.toLowerCase()=='all')||(!displayType.toLowerCase()=='null')) {	
var rselect1 = document.getElementById('${params.id}')
	var opt1 = document.createElement('option');
	var l = rselect1.length
while (l > 0) {
     l--
     rselect1.remove(l)
}
	opt1.value="${params.appendValue}"
	opt1.text="${params.appendName}" 
try {
  	rselect1.add(opt1, null)
} catch(ex) {
		rselect1.add(opt)
	}
	}	 	
}	

function ${params.id}AppendValue(event) {
	$('#${params.id}${params.hidden}').val(event.value);
	$.getJSON('${createLink(controller:"${params?.acontroller}", action: "${params?.filteraction2}")}?term=' + event.value + '&value=${params?.triggerWord}&filterDisplay=${params?.filterDisplay}&bindid=${params?.prevValue}&collectField=${params?.collectField}&searchField=${params?.searchField}&domain=${params?.domain}&domain2=${params?.domain2}&bindid=${params?.bindid}&filterbind=${params.filterbind}&filter=${params.filter}&filter2=${params.filter2}&filterType=${params.filterType}&filterType2=${params.filterType2}',function(data){
		var e=data;
		  if (e) { 
		    var rselect = document.getElementById('${params.id}')
		    var l = rselect.length
		    while (l > 0) {
		     l--
		     rselect.remove(l)
		  	}
		  	var opt = document.createElement('option');
		  	opt.value="${params.appendValue}"
		  	opt.text="${params.appendName}" 
		    try {
		       	rselect.add(opt, null)
		    } catch(ex) {
		  	 	rselect.add(opt)
		  	}
		    for (var i=0; i < e.length; i++) {
		      var s = e[i]
		      var opt = document.createElement('option');
		      opt.text = s.name
		      opt.value = s.id
		      try {
		    	    	rselect.add(opt, null)
		      } catch(ex) {
		  	  	rselect.add(opt)
		  	  }
			 }
			}
		});		
}
</script>
<div id="FilterWord-${params.id}" class="filterWordReturn-${params.id}">
</div>
<input type="hidden" id="${params.id}${params.hidden}"  class="filterWordhidden-${params.id}" name="${params.id}${params.hidden}" value=""/>
</div>