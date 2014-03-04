<g:javascript>
	
	function ${attrs.id}Update(e) { 
  		if (e) { 	
  		var zselect = document.getElementById('${attrs.id}')
  		var zopt = zselect.options[zselect.selectedIndex]
		<g:if test="${attrs.setId }">

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
  				}
  			}
  			</g:if>
  			<g:if test="${attrs.setId3 }">
  				var myDocumentId="${attrs.setId3 }"
  				${attrs.id}localGetter(zopt.value,myDocumentId, "${attrs?.domain3}","${attrs?.searchField3}","${attrs?.collectField3}","${attrs?.bindid3}","${attrs.filterDisplay3}","${attrs?.filter3}","${attrs?.filterType3}")
  			</g:if>
  			<g:if test="${attrs.setId4 }">
  				var myDocumentId="${attrs.setId4 }"
  				${attrs.id}localGetter(zopt.value,myDocumentId,"${attrs?.domain4}","${attrs?.searchField4}","${attrs?.collectField4}","${attrs?.bindid4}","${attrs.filterDisplay4}","${attrs?.filter4}","${attrs?.filterType4}")
  			</g:if>
  			<g:if test="${attrs.setId5 }">
  				var myDocumentId="${attrs.setId5 }"
  				${attrs.id}localGetter(zopt.value,myDocumentId,"${attrs?.domain5}","${attrs?.searchField5}","${attrs?.collectField5}","${attrs?.bindid5}","${attrs.filterDisplay5}","${attrs?.filter5}","${attrs?.filterType5}")
  			</g:if>
  			<g:if test="${attrs.setId6 }">
  				var myDocumentId="${attrs.setId6 }"
  				localGetter(zopt.value,myDocumentId,"${attrs?.domain6}","${attrs?.searchField6}","${attrs?.collectField6}","${attrs?.bindid6}","${attrs.filterDisplay6}","${attrs?.filter6}","${attrs?.filterType6}")
  			</g:if>
  			<g:if test="${attrs.setId7 }">
  				var myDocumentId="${attrs.setId7 }"
  				${attrs.id}localGetter(zopt.value,myDocumentId,"${attrs?.domain7}","${attrs?.searchField7}","${attrs?.collectField7}","${attrs?.bindid7}","${attrs.filterDisplay7}","${attrs?.filter7}","${attrs?.filterType7}")
  			</g:if>
  			<g:if test="${attrs.setId8 }">
  				var myDocumentId="${attrs.setId8 }"
  				${attrs.id}localGetter(zopt.value,myDocumentId,"${attrs?.domain8}","${attrs?.searchField8}","${attrs?.collectField8}","${attrs?.bindid8}","${attrs.filterDisplay8}","${attrs?.filter8}","${attrs?.filterType8}")
  			</g:if>
  			
  		}
  		
  	}
  	function ${attrs.id}localGetter(myvalue,myDocumentId,mydomain,mysearch,mycollect,mybind,myfilterDisplay,myfilter,myfilterType) {
  	
  		$.getJSON('${createLink(controller:"${attrs.controller}", action: "${attrs.action}")}?id='+myvalue+'&value=${attrs?.triggerWord}&bindid='+mybind+'&collectField='+mycollect+'&filterDisplay='+myfilterDisplay+'&searchField='+mysearch+'&domain=&domain2='+mydomain+'&filter2='+myfilter+'&filterType2='+myfilterType+'',function(e){
		if (e) { 
		    var rselect = document.getElementById(myDocumentId)
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
		  		}
		  	}
		}
		
		});		
  	}
  	
  	var zselect = document.getElementById('${attrs.id}')
  	var zopt = zselect.options[zselect.selectedIndex]
  	<g:if test="${attrs?.filterController}">
		<g:remoteFunction  controller="${attrs.filterController}"  action="${attrs.filteraction2}" onComplete="'${attrs.id}Update(data)'"  params= "\'term=${attrs?.term}${changeAddon}&bindid=${attrs.bindid}&filter=${attrs.filter}&filterType=${attrs.filterType}&filterDisplay=${attrs.filterDisplay}&domain=${attrs.domain}&primarybind=${attrs.primarybind}&searchField=${attrs.searchField}&collectField=${attrs.collectField}&filter2=${attrs.filter2}&filterbind=${attrs?.filterbind}&id=\'+ zopt.value"/>
	</g:if>
	<g:else>
		<g:remoteFunction  controller="${attrs.controller}"  action="${attrs.action}" onComplete="'${attrs.id}Update(data)'"  params= "\'term=${attrs?.term}${changeAddon}&bindid=${attrs.bindid}&filter=${attrs.filter}&filterType=${attrs.filterType}&filterDisplay=${attrs.filterDisplay}&domain=${attrs.domain}&primarybind=${attrs.primarybind}&searchField=${attrs.searchField}&collectField=${attrs.collectField}&filter2=${attrs.filter2}&filterbind=${attrs?.filterbind}&id=\'+ zopt.value"/>        
	</g:else>
		        
</g:javascript>