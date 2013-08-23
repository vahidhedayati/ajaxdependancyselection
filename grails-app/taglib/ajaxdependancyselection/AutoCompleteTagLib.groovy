package ajaxdependancyselection


class AutoCompleteTagLib {

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
		out << "  \$('#secondarySearch').attr('primaryid',ui.item.id);"
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
