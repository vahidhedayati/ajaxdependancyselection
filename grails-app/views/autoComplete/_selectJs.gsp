<g:javascript>
  function ${attrs.id}Update(data) { 
  var e=data;
  if (e) { 
    var rselect = document.getElementById('${attrs.setId}')
    var l = rselect.length
    while (l > 0) {
     l--
     rselect.remove(l)
  }
  var opt = document.createElement('option');
  opt.value="${attrs.appendValue}"
  opt.text="${attrs.appendName}" 
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
  }}}}
  var zselect = document.getElementById('${attrs.id}')
  var zopt = zselect.options[zselect.selectedIndex]
  <g:remoteFunction  controller="${attrs.controller}"  action="${attrs.action}" onComplete="'${attrs.id}Update(data)'" params="\'id=\'+zopt.value" />        
</g:javascript>