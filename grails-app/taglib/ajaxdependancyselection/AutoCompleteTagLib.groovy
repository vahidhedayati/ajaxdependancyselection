package ajaxdependancyselection


class AutoCompleteTagLib {
	def autoCompleteService
	
	def selectController = {attrs ->
		def clazz = ""
		def name = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) { 
			attrs.action= "ajaxSelectControllerAction"
		}	
		if (!attrs.setId) {
			attrs.setId = "selectSecondary"
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [autoComplete] is missing required attribute [noSelection]")
		}
		if (!attrs.appendValue) {
			attrs.appendValue='null'
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}	
		def primarylist=autoCompleteService.returnControllerList()
		def gsattrs=[ 'id': "${attrs.id}", value: "${attrs.value}", name: name, optionKey: "${attrs.searchField}", optionValue: "${attrs.collectField}" ]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value)',onSuccess:'updateControllerAction(data)')}"
		out<<  g.select(gsattrs)
		out << "<script type='text/javascript'>\n"
		out << "function updateControllerAction(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		//out << "var rselect = \$('#" + attrs.setId+"').get(0);"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n"
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
		out << "var opt = document.createElement('option');\n"
		out << "opt.value='"+attrs.appendValue+"'\n"
		out << "opt.text='"+attrs.appendName+"'\n "
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}"
		out << "  for (var i=0; i < e.length; i++) {\n"
		out << "    var s = e[i]\n"
		out << "    var opt = document.createElement('option');\n"
		out << "    opt.text = s.name\n"
		out << "    opt.value = s.id\n"
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}\n}\n}\n}\n"
		out << "var zselect = document.getElementById('"+attrs.id+"')\n"
		out << "var zopt = zselect.options[zselect.selectedIndex]\n"
		out << "${remoteFunction(controller:"${attrs.controller}", action:"${attrs.action}", params:"'id=' + zopt.value", onComplete:"updateControllerAction(data)")}\n"
		out << "</script>\n"
		}

	def selectPrimary = {attrs ->
		def clazz = ""
		def name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectPrimary] is missing required attribute [id]")
		}
		if (!attrs.controller) {
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondary"
		}	
		if (!attrs.noSelection) {
			throwTagError("Tag [selectPrimary] is missing required attribute [noSelection]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain2]")
		}
		if (!attrs.bindid) {
			throwTagError("Tag [selectPrimary] is missing required attribute [bindid]")
		}
		if (attrs.searchField == null) {
			throwTagError("Tag [selectPrimary] is missing required attribute [searchField]")
		}
		if (!attrs.setId) {
			attrs.setId = "selectSecondary"
		}	
		if (!attrs.value) {
			attrs.value =""
		}
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		if (!attrs.collectField2) {
			attrs.collectField2=attrs.collectField
		}	
		if (!attrs.searchField2) {
			attrs.searchField2=attrs.searchField
		}	
		if (!attrs.appendValue)  {
			attrs.appendValue='null'
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}	
		List primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out<<  g.select(gsattrs)
		out << "\n<script type='text/javascript'>\n"
		out << "function ${attrs.id}Update(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n"
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
		out << "var opt = document.createElement('option');\n"
		out << "opt.value='"+attrs.appendValue+"'\n"
		out << "opt.text='"+attrs.appendName+"'\n "
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}"
		out << "  for (var i=0; i < e.length; i++) {\n"
		out << "    var s = e[i]\n"
		out << "    var opt = document.createElement('option');\n"
		out << "    opt.text = s.name\n"
		out << "    opt.value = s.id\n"
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}}}}\n"
		out << "var zselect = document.getElementById('"+attrs.id+"')\n"
		out << "var zopt = zselect.options[zselect.selectedIndex]\n"
		out << "${remoteFunction(controller:"${attrs.controller}", action:"${attrs.action}", params:"'id=' + zopt.value", onComplete:"'${attrs.id}Update(data)'")}\n"
		out << "</script>\n"
	}
	
	// Added taglib for primary No reference look ups
	// When a user has a primary item that secondary is a no reference
	def selectPrimaryNR = {attrs ->
		def clazz = ""
		def name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectPrimary] is missing required attribute [id]")
		}
		if (!attrs.controller) {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondaryNR"
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [selectPrimary] is missing required attribute [noSelection]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain2]")
		}
		if (attrs.searchField == null) {
			throwTagError("Tag [selectPrimary] is missing required attribute [searchField]")
		}
		if (!attrs.setId) {
			attrs.setId = "selectSecondaryNR"
		}
		if (!attrs.value) {
			attrs.value =""
		}
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		if (!attrs.appendValue)  {
			attrs.appendValue='null'
		}
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}
		List primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&bindid='+ attrs.bindid+'&domain='+attrs.domain+'&domain2='+attrs.domain2+'&setId='+attrs.setId+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out<<  g.select(gsattrs)
		out << "\n<script type='text/javascript'>\n"
		out << "function ${attrs.id}Update(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n"
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
		out << "var opt = document.createElement('option');\n"
		out << "opt.value='"+attrs.appendValue+"'\n"
		out << "opt.text='"+attrs.appendName+"'\n "
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}"
		out << "  for (var i=0; i < e.length; i++) {\n"
		out << "    var s = e[i]\n"
		out << "    var opt = document.createElement('option');\n"
		out << "    opt.text = s.name\n"
		out << "    opt.value = s.id\n"
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}}}}\n"
		out << "var zselect = document.getElementById('"+attrs.id+"')\n"
		out << "var zopt = zselect.options[zselect.selectedIndex]\n"
		out << "${remoteFunction(controller:"${attrs.controller}", action:"${attrs.action}", params:"'id=' + zopt.value", onComplete:"'${attrs.id}Update(data)'")}\n"
		out << "</script>\n"
	}
	
	//  Reveted back changes - this was not working 
	// selectSecondary is used by both gsp calls to g:selectPrimary and g:selectSecondary 
	def selectSecondary = {attrs ->
		def clazz = ""
		def name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectScondary] is missing required attribute [id]")
		}
		if (!attrs.searchField2) {
			throwTagError("Tag [selectScondary] is missing required attribute [searchField2]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondary"
		}	
		if (!attrs.noSelection) {
			throwTagError("Tag [selectScondary] is missing required attribute [noSelection]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [selectScondary] is missing required attribute [domain2]")
		}
		if (!attrs.bindid) {
			throwTagError("Tag [selectScondary] is missing required attribute [bindid]")
		}
		if (!attrs.setId) {
			attrs.setId = "selectSecondary"
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (!attrs.collectField2) {
			attrs.collectField2 = attrs.searchField2
		}
		if (!attrs.searchField) {
			attrs.searchField = attrs.searchField2
		}
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField2
		}
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		if (!attrs.appendValue)  attrs.appendValue='null'
		if (!attrs.appendName) attrs.appendName='Values Updated'
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = []
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		out<<  g.select(gsattrs)
		out << "\n<script type='text/javascript'>\n"
		out << "function ${attrs.id}Update(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n"
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
		out << "var opt = document.createElement('option');\n"
		out << "opt.value='"+attrs.appendValue+"'\n"
		out << "opt.text='"+attrs.appendName+"'\n "
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}"
		out << "  for (var i=0; i < e.length; i++) {\n"
		out << "    var s = e[i]\n"
		out << "    var opt = document.createElement('option');\n"
		out << "    opt.text = s.name\n"
		out << "    opt.value = s.id\n"
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}}}}\n"
		out << "var zselect = document.getElementById('"+attrs.id+"')\n"
		out << "var zopt = zselect.options[zselect.selectedIndex]\n"
		out << "${remoteFunction(controller:"${attrs.controller}", action:"${attrs.action}", params:"'id=' + zopt.value", onComplete:"'${attrs.id}Update(data)'")}\n"
		out << "</script>\n"
	}

	
	// No Reference Selection
	def selectSecondaryNR = {attrs ->
		def clazz = ""
		def name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [id]")
		}
		if (attrs.searchField == null) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [searchField]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondaryNR"
		}	
		if (!attrs.noSelection) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [noSelection]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [domain]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [domain2]")
		}
		if (!attrs.bindid) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [bindid]")
		}
		if (!attrs.setId) {
			attrs.setId = "selectSecondary"
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (!attrs.collectField2) {
			attrs.collectField2=attrs.collectField
		}	
		if (!attrs.searchField2) {
			attrs.searchField2=attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		if (!attrs.appendValue)  {
			attrs.appendValue='null'
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}	
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = []
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&domain='+attrs.domain+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		out<<  g.select(gsattrs)
		out << "\n<script type='text/javascript'>\n"
		out << "function ${attrs.id}Update(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n"
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
		out << "var opt = document.createElement('option');\n"
		out << "opt.value='"+attrs.appendValue+"'\n"
		out << "opt.text='"+attrs.appendName+"'\n "
		out << "    try {\n"
		out << "  	    	rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "	  rselect.add(opt)\n"
		out << "}"
		out << "  for (var i=0; i < e.length; i++) {\n"
		out << "    var s = e[i]\n"
		out << "    var opt = document.createElement('option');\n"
		out << "    opt.text = s.name\n"
		out << "    opt.value = s.id\n"
		out << "    try {\n"
		out << "              rselect.add(opt, null)\n"
		out << "    } catch(ex) {\n"
		out << "      rselect.add(opt)\n"
		out << "}}}}\n"
		out << "var zselect = document.getElementById('"+attrs.id+"')\n"
		out << "var zopt = zselect.options[zselect.selectedIndex]\n"
		out << "${remoteFunction(controller:"${attrs.controller}", action:"${attrs.action}", params:"'id=' + zopt.value", onComplete:"'${attrs.id}Update(data)'")}\n"
		out << "</script>\n"
	}
   
   
	def autocomplete = {attrs ->
		def clazz = ""
		def name = ""
		def styles = ""
		if (attrs.id == null) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller) { 
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) {
			attrs.action= "autocomplete"
		}	
		if (attrs.domain == null) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain]")
		}
		if (attrs.searchField == null) {
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
		}
		if (!attrs.max) {
			attrs.max = 10
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (!attrs.order) {
			attrs.order = "asc"
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.style) {
			styles = " styles='${attrs.style}'"
		}	
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		} else {
			name = " name ='${attrs.id}'"
		}
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out << " <input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${styles} ${name} />"
		out << "<script type='text/javascript'>"
		out << " \$(document).ready(function() {"
		out << "\$('#" + attrs.id+"').autocomplete({ "
		out << " source: '"
		out << createLink(link)
		out << "?"
		out << "domain="+ attrs.domain
		out << "&searchField="+attrs.searchField
		out << "&max="+attrs.max
		out << "&order="+attrs.order
		out << "&collectField="+attrs.collectField
		out << "', dataType: 'json'"
		out << "});});"
		out << "</script>"
	}


	
	
	def autoCompletePrimary = {attrs ->
		def clazz = ""
		def name = ""
		def cid=""
		def styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller)  { 
			attrs.controller= "autoComplete" 
		}
		if (!attrs.action) { 
			attrs.action= "autocompletePrimaryAction"
		}	
		if (!attrs.domain) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain]")
		}
		if (!attrs.searchField) {
			throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
		}
		if (!attrs.hidden) {
			throwTagError("Tag [autoComplete] is missing required attribute [hidden]")
		}
		if (!attrs.setId) {
			attrs.setId = "secondarySearch"
		}
		if (!attrs.max) {
			attrs.max = 10
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (!attrs.order) {
			attrs.order = "asc"
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.style) {
			styles = " styles='${attrs.style}'"
		}	
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		} else {
			name = " name ='${attrs.id}'"
		}
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out << " <input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${styles} ${name} />"
		out << "<script type='text/javascript'>"
		out << " \$(document).ready(function() {"
		out << "\$('#" + attrs.id+"').autocomplete({ "
		out << " source: '"
		out << createLink(link)
		out << "?"
		out << "domain="+ attrs.domain
		out << "&searchField="+attrs.searchField
		out << "&max="+attrs.max
		out << "&order="+attrs.order
		out << "&collectField="+attrs.collectField
		out << "',"
		if ((attrs.setId)||(attrs.hidden)) {
			out << "select: function(event, ui) {"
			if(attrs.hidden) {
				out << "    \$('#" + attrs.hidden+"').val(ui.item.id);"
			}
			if (attrs.setId) {
				out << "  \$('#" + attrs.setId+"').attr('primaryid',ui.item.id);"
			}	
			out <<"},"
		}
		out << " dataType: 'json'"
		out << "});"

		out << "});"
		out << "</script>"
	}
	

	def autoCompleteSecondary = {attrs ->
		def clazz = ""
		def name = ""
		def cid=""
		def styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocompleteSecondaryAction"
		}
		if (!attrs.domain) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain]")
		}
		if (!attrs.primarybind) {
			throwTagError("Tag [autoComplete] is missing required attribute [primarybind]")
		}
		if (!attrs.searchField) {
			throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
		}
		if (!attrs.hidden) {
			throwTagError("Tag [autoComplete] is missing required attribute [hidden]")
		}
		if (!attrs.max) {
			attrs.max = 10
		}	
		if (!attrs.value) {
			attrs.value =""
		}	
		if (attrs.cid ==null ) {
			attrs.cid =""
		}	
		if (!attrs.order) {
			attrs.order = "asc"
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.style) {
			styles = " styles='${attrs.style}'"
		}	
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		} else {
			name = " name ='${attrs.id}'"
		}
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out << " <input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${styles} ${name} />"
		out << "<script type='text/javascript'>"
		out << " \$(document).ready(function() {"
		out << "\$('#" + attrs.id+"').autocomplete({ "
		out << " source: "
		out << " function(request, response) { "
		out << " \$.getJSON(' "
		out << createLink(link)
		out << "?"
		out << "term=' + request.term + '"
		out << "&domain="+ attrs.domain
		out << "&primarybind="+ attrs.primarybind
		out << "&searchField="+attrs.searchField
		out << "&max="+attrs.max
		out << "&order="+attrs.order
		out << "&collectField="+attrs.collectField
		out << "', { primaryid: \$('#" + attrs.hidden+"').val() }, "
		out << " response);  }, "
		if ((attrs.setId)||(attrs.hidden2)) {
			out << "select: function(event, ui) {"
			if(attrs.hidden2) {
				out << "    \$('#" + attrs.hidden2+"').val(ui.item.id);"
			}
			if (attrs.setId) {
				out << "  \$('#" + attrs.setId+"').attr('primaryid',ui.item.id);"
			}
			out <<"},"
		}
		out << " dataType: 'json'"
		out << "});});"
		out << "</script>"
	}
	
	// No reference Auto complete tag lib
	def autoCompleteSecondaryNR = {attrs ->
		def clazz = ""
		def name = ""
		def cid=""
		def styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}	
		if (!attrs.action) {
			attrs.action= "autocompleteSecondaryNR"
		}	
		if (!attrs.domain) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain2]")
		}
		if (!attrs.primarybind) {
			throwTagError("Tag [autoComplete] is missing required attribute [primarybind]")
		}
		if (!attrs.searchField) {
			throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
		}
		if (!attrs.hidden) {
			throwTagError("Tag [autoComplete] is missing required attribute [hidden]")
		}
		if (!attrs.max) {
			attrs.max = 10
		}	
		if (!attrs.value) {
			attrs.value =""
		}
		if (attrs.cid ==null ) {
			attrs.cid =""
		}	
		if (!attrs.order) {
			attrs.order = "asc"
		}	
		if (!attrs.collectField) {
			attrs.collectField = attrs.searchField
		}	
		if (attrs.class) {
			clazz = " class='${attrs.class}'"
		}	
		if (attrs.style) {
			styles = " styles='${attrs.style}'"
		}	
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		} else {
			name = " name ='${attrs.id}'"
		}
		def link = ['action': attrs.action , 'controller': attrs.controller ]
		out << " <input type='text' ${clazz} id='${attrs.id}' value = '${attrs.value}' ${styles} ${name} />"
		out << "<script type='text/javascript'>"
		out << " \$(document).ready(function() {"
		out << "\$('#" + attrs.id+"').autocomplete({ "
		out << " source: "
		out << " function(request, response) { "
		out << " \$.getJSON(' "
		out << createLink(link)
		out << "?"
		out << "term=' + request.term + '"
		out << "&domain="+ attrs.domain
		out << "&domain2="+ attrs.domain2
		out << "&primarybind="+ attrs.primarybind
		out << "&searchField="+attrs.searchField
		out << "&max="+attrs.max
		out << "&order="+attrs.order
		out << "&collectField="+attrs.collectField
		out << "', { primaryid: \$('#" + attrs.hidden+"').val() }, "
		out << " response);  }, "
		if ((attrs.setId)||(attrs.hidden2)) {
			out << "select: function(event, ui) {"
			if(attrs.hidden2) {
				out << "    \$('#" + attrs.hidden2+"').val(ui.item.id);"
			}
			if (attrs.setId) {
				out << "  \$('#" + attrs.setId+"').attr('primaryid',ui.item.id);"
			}
			out <<"},"
		}
		out << " dataType: 'json'"
		out << "});});"
		out << "</script>"
	}
	def autoCompleteHeader = {
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}
	
	
	def autoCompletePrimaryHeader = { attrs ->
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}

	def autoCompleteSecondaryHeader = { attrs ->
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}
	
	def autoCompleteSecondaryNRHeader = { attrs ->
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}

}
