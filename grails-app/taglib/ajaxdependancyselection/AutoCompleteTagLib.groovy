package ajaxdependancyselection


class AutoCompleteTagLib {

	def selectPrimary = {attrs ->
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if (!attrs.controller) {
			throwTagError("Tag [autoComplete] is missing required attribute [controller]")
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [autoComplete] is missing required attribute [noSelection]")
		}
		if (!attrs.action) {
			throwTagError("Tag [autoComplete] is missing required attribute [action]")
		}

		if (!attrs.domain) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain]")
		}
		if (!attrs.domain2) {
			throwTagError("Tag [autoComplete] is missing required attribute [domain2]")
		}
		if (!attrs.bindid) {
			throwTagError("Tag [autoComplete] is missing required attribute [bindid]")
		}
		def clazz = ""
		def name = ""
		if (!attrs.setId) attrs.setId = "selectSecondary"
		if (!attrs.value) attrs.value =""
		if (!attrs.collectField) attrs.collectField = attrs.searchField
		if (attrs.class) clazz = " class='${attrs.class}'"
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		}
		else {
			name = " name ='${attrs.id}'"
		}
		
		List primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:'AutoComplete', action:'ajaxSelectSecondary', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:'updateSecondary(data)')}"
		out<<  g.select(gsattrs) 
		out << "<script type='text/javascript'>\n"
		out << "function updateSecondary(data) { \n"
		out << "var e=data;\n"
		out << "if (e) { \n"
		out << "  var rselect = document.getElementById('" + attrs.setId+"')\n"
		//out << "var rselect = \$('#" + attrs.setId+"').get(0);"
		out << "  var l = rselect.length\n"
		out << "  while (l > 0) {\n" 
		out << "   l--\n"
		out << "   rselect.remove(l)\n"
		out <<"   }\n"
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
		out << "${remoteFunction(controller:"AutoComplete", action:"ajaxSelectSecondary", params:"'id=' + zopt.value", onComplete:"updateSecondary(e)")}\n"
		out << "</script>\n"
	}
	def autocomplete = {attrs ->
		if (attrs.id == null)
				throwTagError("Tag [autoComplete] is missing required attribute [id]")

		if (attrs.controller == null)
				throwTagError("Tag [autoComplete] is missing required attribute [controller]")

		if (attrs.action == null)
				throwTagError("Tag [autoComplete] is missing required attribute [action]")

		if (attrs.domain == null)
				throwTagError("Tag [autoComplete] is missing required attribute [domain]")

		if (attrs.searchField == null)
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")

		def clazz = ""
		def name = ""
		def styles = ""
		if (!attrs.max) attrs.max = 10
		if (!attrs.value) attrs.value =""
		if (!attrs.order) attrs.order = "asc"
		if (!attrs.collectField) attrs.collectField = attrs.searchField
		if (attrs.class) clazz = " class='${attrs.class}'"
		if (attrs.style) styles = " styles='${attrs.style}'"
		if (attrs.name)
				name = " name ='${attrs.name}'"
		else
				name = " name ='${attrs.id}'"

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
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if (!attrs.controller) {
			throwTagError("Tag [autoComplete] is missing required attribute [controller]")
		}

		if (!attrs.action) {
			throwTagError("Tag [autoComplete] is missing required attribute [action]")
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

		def clazz = ""
		def name = ""
		def cid=""
		def styles = ""
		if (!attrs.setId) attrs.setId = "secondarySearch"
		if (!attrs.max) attrs.max = 10
		if (!attrs.value) attrs.value =""
		if (!attrs.order) attrs.order = "asc"
		if (!attrs.collectField) attrs.collectField = attrs.searchField
		if (attrs.class) clazz = " class='${attrs.class}'"
		if (attrs.style) styles = " styles='${attrs.style}'"
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		}
		else {
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
		out << "&domain="+ attrs.domain
		out << "&searchField="+attrs.searchField
		out << "&max="+attrs.max
		out << "&order="+attrs.order
		out << "&collectField="+attrs.collectField
		out << "',select: function(event, ui) {"
		out << "    \$('#" + attrs.hidden+"').val(ui.item.id);"
		out << "  \$('#" + attrs.setId+"').attr('primaryid',ui.item.id);"
		out <<"}"
		out << ", dataType: 'json'"
		out << "});"

		out << "});"
		out << "</script>"
	}

	def autoCompleteSecondary = {attrs ->
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if (!attrs.controller) {
			throwTagError("Tag [autoComplete] is missing required attribute [controller]")
		}

		if (!attrs.action) {
			throwTagError("Tag [autoComplete] is missing required attribute [action]")
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

		def clazz = ""
		def name = ""
		def cid=""
		def styles = ""
		def var1="";
		if (!attrs.max) attrs.max = 10
		if (!attrs.value) attrs.value =""

		if (attrs.cid ==null ) attrs.cid =""
		if (!attrs.order) attrs.order = "asc"
		if (!attrs.collectField) attrs.collectField = attrs.searchField
		if (attrs.class) clazz = " class='${attrs.class}'"
		if (attrs.style) styles = " styles='${attrs.style}'"
		if (attrs.name) {
			name = " name ='${attrs.name}'"
		}
		else {
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
		out << "', { primaryid: \$('#" + attrs.hidden+"').val() },  "
		out << " response);  } "
		out << ", dataType: 'json'"
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
}
