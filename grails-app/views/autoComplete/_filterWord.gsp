 <div class="filterWord">
 <label>Filter by:</label>
 <input type="text" name="triggerWord" value="${params.triggerWord }"  onClick="this.value=''" onmouseout="${params.id}AppendValue(this)" >
 </div>  
<script>
function ${params.id}AppendValue(event) {
	$('#${params.hidden}').val(event.value);
	$.getJSON('${createLink(controller:"${params?.acontroller}", action: "${params?.filteraction2}")}?term=' + event.value + '&value=${params?.triggerWord}&collectField=${params?.collectField}&searchField=${params?.searchField}&domain=${params?.domain}',function(data){
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
<div id="FilterWord" class="filterWordReturn">
</div>
<input type=hidden id="${params.hidden}" name="${params.hidden}" value=""/>
