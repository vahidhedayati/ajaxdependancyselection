package ajaxdependancyselection


class AutoCompleteTagLib {
	def autoCompleteService
	def basicjs='/autoComplete/selectJs'

	def selectController = {attrs ->
		def clazz,name=""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "ajaxSelectControllerAction"
		}
		if (!attrs.searchField) {
			attrs.searchField="name"
		}
		if (!attrs.setId) {
			attrs.setId = "selectController"
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
		if ((attrs.appendValue)&&(!attrs.appendName)) {
			attrs.appendName='Values Updated'
		}
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}


		def primarylist=autoCompleteService.returnControllerList()
		def gsattrs=[ 'id': "${attrs.id}", value: "${attrs.value}", name: name, optionKey: "${attrs.searchField}", optionValue: "${attrs.collectField}" ]
		gsattrs['from'] = primarylist
		if (requireField) {
			gsattrs['required'] = 'required'
		}
		if (attrs.multiple) {
			gsattrs['multiple']= "${attrs.multiple}"
		}
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value)',onSuccess:''+attrs.id+'Update(data)')}"
		out << g.select(gsattrs)
		attrs.filteraction2=attrs.action
		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.selectBasicJS
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs])
		}else{
			out << g.render(contextPath: pluginContextPath,template: basicjs,  model: [attrs:attrs])
		}
	}


	def selectPrimary = {attrs ->
		def clazz,name = ""

		if (!attrs.id) {
			throwTagError("Tag [selectPrimary] is missing required attribute [id]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain]")
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [selectPrimary] is missing required attribute [noSelection]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain2) {
				throwTagError("Tag [selectPrimary] is missing required attribute [domain2]")
			}
			if (!attrs.bindid) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid]")
			}
			if (!attrs.searchField) {
				throwTagError("Tag [selectPrimary] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller) {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondary"
		}
		if (!attrs.setId) {
			attrs.setId = "selectPrimary"
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
		if ((attrs.appendValue)&&(!attrs.appendName)) {
			attrs.appendName='Values Updated'
		}

		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}

		List primarylist
		if (attrs.filter) {
			if (!attrs.filterController) {
				attrs.filterController=attrs.controller
			}
			if (!attrs.filteraction) {
				attrs.filteraction="loadFilterWord"
			}
			if (!attrs.filteraction2) {
				attrs.filteraction2="returnPrimarySearch"
			}
			def filter=''
			if (!attrs.hidden) {
				attrs.hidden="hidden${attrs.id}"
			}
			if (!attrs.filterDisplay) {
				attrs.filterDisplay='all'
			}

			if (attrs.filter.equals('_ON')) {
				def userTemplate=grailsApplication?.config?.ajaxdependancyselection.filterField
				if (userTemplate) {
					out << g.render(template:userTemplate, model: [attrs:attrs])
				}else{
					out << g.render(contextPath: pluginContextPath, template: '/autoComplete/filterField', model: [attrs:attrs])
				}
			}else if (attrs.filter){
				filter=attrs.filter
			}
			primarylist=autoCompleteService.returnPrimarySearch('',filter,attrs.domain, attrs)
		}else{
			if (attrs.primaryList) {
				primarylist=attrs.remove('primaryList')?.toList()
			}else{
				primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
			}

		}


		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.selectBasicJS
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs])
		}else{
			out << g.render(contextPath: pluginContextPath, template: basicjs, model: [attrs:attrs])
		}

		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist
		if (requireField) {
			gsattrs['required'] = 'required'
		}
		if (attrs.multiple) {
			gsattrs['multiple']= "${attrs.multiple}"
		}

		/*
		 if (attrs.value) {
		 gsattrs['value'] =attrs.value
		 }*/

		gsattrs['noSelection'] =attrs.noSelection
		def changeAddon=returnAddon(attrs)

		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&prevId='+attrs.prevId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"

		if (attrs.value && attrs.secondaryValue) {
			out << """
				<script type='text/javascript'>
					${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id='+attrs.value+'&value='+attrs.secondaryValue+'&setId='+attrs.setId+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&prevId='+attrs.prevId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}
				</script>
			"""
		}
		out << g.select(gsattrs)

	}


	// Added taglib for primary No reference look ups
	// When a user has a primary item that secondary is a no reference
	def selectPrimaryNR = {attrs ->
		def clazz,name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectPrimary] is missing required attribute [id]")
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [selectPrimary] is missing required attribute [noSelection]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectPrimary] is missing required attribute [domain]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain2) {
				throwTagError("Tag [selectPrimary] is missing required attribute [domain2]")
			}
			if (attrs.searchField == null) {
				throwTagError("Tag [selectPrimary] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller) {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondaryNR"
		}

		if (!attrs.setId) {
			attrs.setId = "selectPrimaryNR"
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
		if ((attrs.appendValue)&&(!attrs.appendName)) {
			attrs.appendName='Values Updated'
		}


		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}

		List primarylist
		if (attrs.filter) {
			if (!attrs.filterController) {
				attrs.filterController=attrs.controller
			}
			if (!attrs.filteraction) {
				attrs.filteraction="loadFilterWord"
			}
			if (!attrs.filteraction2) {
				attrs.filteraction2="returnPrimarySearch"
			}
			def filter=''
			if (!attrs.hidden) {
				attrs.hidden="hidden${attrs.id}"
			}
			if (!attrs.filterDisplay) {
				attrs.filterDisplay='all'
			}

			if (attrs.filter.equals('_ON')) {
				def userTemplate=grailsApplication?.config?.ajaxdependancyselection.filterField
				if (userTemplate) {
					out << g.render(template:userTemplate, model: [attrs:attrs])
				}else{
					out << g.render(contextPath: pluginContextPath, template: '/autoComplete/filterField', model: [attrs:attrs])
				}
			}else if (attrs.filter){
				filter=attrs.filter
			}

			primarylist=autoCompleteService.returnPrimarySearch('',filter,attrs.domain, attrs)
		}else{
			primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
		}

		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.selectBasicJS
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs])
		}else{
			out << g.render(contextPath: pluginContextPath, template: basicjs, model: [attrs:attrs])
		}

		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist

		if (requireField) {
			gsattrs['required'] = 'required'
		}
		if (attrs.multiple) {
			gsattrs['multiple']= "${attrs.multiple}"
		}

		gsattrs['noSelection'] =attrs.noSelection
		def changeAddon=returnAddon(attrs)
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&bindid='+ attrs.bindid+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&prevId='+attrs.prevId+'&domain='+attrs.domain+'&domain2='+attrs.domain2+'&setId='+attrs.setId+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		out<< g.select(gsattrs)
	}



	// selectSecondary is used by both gsp calls to g:selectPrimary and g:selectSecondary
	def selectSecondary = {attrs ->
		def clazz,name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectScondary] is missing required attribute [id]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.searchField2) {
				throwTagError("Tag [selectScondary] is missing required attribute [searchField2]")
			}
			if (!attrs.domain2) {
				throwTagError("Tag [selectScondary] is missing required attribute [domain2]")
			}
			if (!attrs.bindid) {
				throwTagError("Tag [selectScondary] is missing required attribute [bindid]")
			}
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

		if (!attrs.setId) {
			attrs.setId = "selectSecondary"
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
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}

		if ((attrs.appendValue)&&(!attrs.appendName)) {
			attrs.appendName='Values Updated'
		}


		List secondarylist=[]
		if (attrs.filter) {
			if (!attrs.filterController) {
				attrs.filterController=attrs.controller
			}
			if (!attrs.filteraction) {
				attrs.filteraction="loadFilterWord2"
			}
			if (!attrs.filteraction2) {
				attrs.filteraction2="secondarySearch"
			}
			if (!attrs.hidden) {
				attrs.hidden="hidden${attrs.id}"
			}
			if (!attrs.filterDisplay) {
				attrs.filterDisplay='all'
			}
			if (!attrs.filterbind) {
				throwTagError("Tag [selectScondary] is missing required attribute [filterbind]")
			}
			if (attrs.filter.equals('_ON')) {
				def userTemplate=grailsApplication?.config?.ajaxdependancyselection.filterField
				if (userTemplate) {
					out << g.render(template:userTemplate, model: [attrs:attrs])
				}else{
					out << g.render(contextPath: pluginContextPath, template: '/autoComplete/filterField', model: [attrs:attrs])
				}
			}
		}


		def selectSecondaryJs=grailsApplication?.config?.ajaxdependancyselection.selectSecondaryJsFilter
		if (selectSecondaryJs) {
			out << g.render(template:selectSecondaryJs, model: [attrs:attrs])
		}else{
			basicjs='/autoComplete/selectJs1'
			out << g.render(contextPath: pluginContextPath, template: basicjs, model: [attrs:attrs])
		}

		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = secondarylist
		if (requireField) {
			gsattrs['required'] = 'required'
		}

		if (attrs.multiple) {
			gsattrs['multiple']= "${attrs.multiple}"
		}
		/*if (attrs.value) {			
		 gsattrs['value'] = attrs.value
		 }*/

		gsattrs['noSelection'] =attrs.noSelection
		def changeAddon=returnAddon(attrs)
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&domain2='+attrs.domain2+'&prevId='+attrs.prevId+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"

		/*if (!attrs.secondaryValue) {
		 attrs.secondaryValue=""
		 }
		 */
		if (attrs.value && attrs.secondaryValue) {
			out << """
				<script type='text/javascript'>
					${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id='+attrs.value+'&value='+attrs.secondaryValue+'&setId='+attrs.setId+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&domain2='+attrs.domain2+'&prevId='+attrs.prevId+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}

				</script>
			"""
		}

		out <<  g.select(gsattrs)

	}


	// No Reference Selection
	def selectSecondaryNR = {attrs ->
		def clazz,name = ""
		if (!attrs.id) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [id]")
		}
		if (!attrs.noSelection) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [noSelection]")
		}
		if (!attrs.domain) {
			throwTagError("Tag [selectSecondaryNR] is missing required attribute [domain]")
		}
		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain2) {
				throwTagError("Tag [selectSecondaryNR] is missing required attribute [domain2]")
			}
			if (!attrs.bindid) {
				throwTagError("Tag [selectSecondaryNR] is missing required attribute [bindid]")
			}
			if (attrs.searchField == null) {
				throwTagError("Tag [selectSecondaryNR] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "ajaxSelectSecondaryNR"
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
		if ((attrs.appendValue)&&(!attrs.appendName)) {
			attrs.appendName='Values Updated'
		}
		/*if (!attrs.appendValue) {
		 attrs.appendValue=''
		 }*/
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}

		if (attrs.filter) {
			if (!attrs.filterController) {
				attrs.filterController=attrs.controller
			}
			if (!attrs.filteraction) {
				attrs.filteraction="loadFilterWord2"
			}
			if (!attrs.filteraction2) {
				attrs.filteraction2="secondarySearch"
			}
			if (!attrs.hidden) {
				attrs.hidden="hidden${attrs.id}"
			}
			if (!attrs.filterDisplay) {
				attrs.filterDisplay='all'
			}
			if (!attrs.filterbind) {
				throwTagError("Tag [selectScondaryNR] is missing required attribute [filterbind]")
			}
			if (attrs.filter.equals('_ON')) {
				def userTemplate=grailsApplication?.config?.ajaxdependancyselection.filterField
				if (userTemplate) {
					out << g.render(template:userTemplate, model: [attrs:attrs])
				}else{
					out << g.render(contextPath: pluginContextPath, template: '/autoComplete/filterField', model: [attrs:attrs])
				}
			}

		}

		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.selectSecondaryNrJS
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs])
		}else{
			basicjs='/autoComplete/selectJsNr1'
			out << g.render(contextPath: pluginContextPath, template: basicjs, model: [attrs:attrs])
		}

		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = []

		if (requireField) {
			gsattrs['required'] = 'required'
		}

		if (attrs.multiple) {
			gsattrs['multiple']= "${attrs.multiple}"
		}

		gsattrs['noSelection'] =attrs.noSelection
		def changeAddon=returnAddon(attrs)
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+changeAddon+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&bdomain='+attrs.bdomain+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&domain='+attrs.domain+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		out <<  g.select(gsattrs)


	}


	def autocomplete = {attrs ->
		def clazz,name,cid,styles = ""
		if (attrs.id == null) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
			if (attrs.domain == null) {
				throwTagError("Tag [autoComplete] is missing required attribute [domain]")
			}
			if (attrs.searchField == null) {
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller) {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocomplete"
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
			clazz = "${attrs.class}"
		}
		if (attrs.style) {
			styles = "${attrs.style}"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		def  required=""
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}
		if (requireField) {
			required="required"
		}
		def template='/autoComplete/AutoCompleteBasic'
		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.AutoComplete
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs, clazz:clazz, styles:styles,name:name,required:required ])
		}else{
			out << g.render(contextPath: pluginContextPath, template: template, model: [attrs:attrs,clazz:clazz, styles:styles,name:name,required:required  ])
		}
	}


	def autoCompletePrimary = {attrs ->
		def clazz,name,cid,styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain) {
				throwTagError("Tag [autoComplete] is missing required attribute [domain]")
			}
			if (!attrs.searchField) {
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocompletePrimaryAction"
		}

		if (!attrs.hidden) {
			throwTagError("Tag [autoComplete] is missing required attribute [hidden]")
		}
		if (!attrs.setId) {
			attrs.setId = "autoCompletePrimary"
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
			clazz = "${attrs.class}"
		}
		if (attrs.style) {
			styles = "${attrs.style}"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}
		if (requireField) {
			required="required"
		}

		def template='/autoComplete/AutoCompletePrimary'
		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.AutoCompletePrimary
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs, clazz:clazz, styles:styles,name:name,required:required ])
		}else{

			out << g.render(contextPath: pluginContextPath, template: template, model: [attrs:attrs,clazz:clazz, styles:styles,name:name,required:required  ])
		}
	}


	def autoCompleteSecondary = {attrs ->
		def clazz,name,cid,styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain) {
				throwTagError("Tag [autoComplete] is missing required attribute [domain]")
			}
			if (!attrs.primarybind) {
				throwTagError("Tag [autoComplete] is missing required attribute [primarybind]")
			}
			if (!attrs.searchField) {
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
			}
		}

		if (!attrs.hidden) {
			throwTagError("Tag [autoComplete] is missing required attribute [hidden]")
		}
		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocompleteSecondaryAction"
		}
		if (!attrs.setId) {
			attrs.setId = "autoCompleteSecondary"
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
			clazz = "${attrs.class}"
		}
		if (attrs.style) {
			styles = "${attrs.style}"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}
		if (requireField) {
			required="required"
		}
		def template='/autoComplete/AutoCompleteSecondary'
		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.AutoCompleteSecondary
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs, clazz:clazz, styles:styles,name:name,required:required ])
		}else{
			out << g.render(contextPath: pluginContextPath, template: template, model: [attrs:attrs,clazz:clazz, styles:styles,name:name,required:required  ])
		}
	}


	// No reference Auto complete tag lib
	def autoCompleteSecondaryNR = {attrs ->
		def clazz,name,cid,styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}

		if ( (!attrs.controller) && (!attrs.action) ) {
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
		}

		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocompleteSecondaryNR"
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
			clazz = "${attrs.class}"
		}
		if (attrs.style) {
			styles = "${attrs.style}"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}
		if (requireField) {
			required="required"
		}
		def template='/autoComplete/AutoCompleteSecondaryNR'
		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.AutoCompleteSecondaryNR
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs, clazz:clazz, styles:styles,name:name,required:required ])
		}else{
			out << g.render(contextPath: pluginContextPath, template: template, model: [attrs:attrs,clazz:clazz, styles:styles,name:name,required:required  ])
		}
	}


	def selectAutoComplete = {attrs ->
		def clazz,name,cid,styles = ""
		if (!attrs.id) {
			throwTagError("Tag [autoComplete] is missing required attribute [id]")
		}
		if (!attrs.id2) {
			throwTagError("Tag [autoComplete] is missing required attribute [id2]")
		}
		if ( (!attrs.controller) && (!attrs.action) ) {
			if (!attrs.domain) {
				throwTagError("Tag [autoComplete] is missing required attribute [domain]")
			}
			if (!attrs.primarybind) {
				throwTagError("Tag [autoComplete] is missing required attribute [primarybind]")
			}
			if (!attrs.searchField) {
				throwTagError("Tag [autoComplete] is missing required attribute [searchField]")
			}
		}

		if (!attrs.controller)  {
			attrs.controller= "autoComplete"
		}
		if (!attrs.action) {
			attrs.action= "autocompleteSecondaryAction"
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
			clazz = "${attrs.class}"
		}
		if (attrs.style) {
			styles = "${attrs.style}"
		}
		if (attrs.name) {
			name = "${attrs.name}"
		} else {
			name = "${attrs.id}"
		}
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}

		if (attrs.required) {
			requireField=attrs.remove('required')?.toBoolean()
		}
		if (requireField) {
			required="required"
		}

		def template='/autoComplete/selectAutoComp'
		List primarylist=autoCompleteService.returnPrimaryList(attrs.domain)
		def gsattrs=['optionKey' : "${attrs.collectField}" , 'optionValue': "${attrs.searchField}", 'id': "${attrs.id}", 'value': "${attrs.value}", 'name': "${name}"]
		gsattrs['from'] = primarylist
		if (requireField) {
			gsattrs['required'] = 'required'
		}
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${attrs.id}Update(this);"
		out<< g.select(gsattrs)

		def userTemplate1=grailsApplication?.config?.ajaxdependancyselection.selectAutoCompleteJS
		if (userTemplate1) {
			out << g.render(template:userTemplate1, model: [attrs:attrs])
		}else{
			out << g.render(contextPath: pluginContextPath, template: '/autoComplete/selectJsAc', model: [attrs:attrs])
		}

		def userTemplate=grailsApplication?.config?.ajaxdependancyselection.selectAutoComplete
		if (userTemplate) {
			out << g.render(template:userTemplate, model: [attrs:attrs, clazz:clazz, styles:styles,name:name,required:required ])
		}else{
			out << g.render(contextPath: pluginContextPath, template: template, model: [attrs:attrs,clazz:clazz, styles:styles,name:name,required:required  ])
		}
	}


	def autoField = { attrs, body ->
		def title = attrs.remove("title")
		def isDisabled = attrs.remove("disabled")

		if ("true".equals(isDisabled)) {
			out << """<input title="${title}" disabled="${isDisabled}" """
			attrs.each { k,v ->
				out << k << "=\"" << v << "\" "
			}
			out << "/>"
		} else {
			out << """<input title="${title}" """
			attrs.each { k,v ->
				out << k << "=\"" << v << "\" "
			}
			out << "/>"
		}
	}

	def returnAddon(attrs) {
		def changeAddon=""
		if (attrs.domain3) {
			if (!attrs.bindid3) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid3]")
			}
			if (!attrs.setId3) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId3]")
			}
			changeAddon="&collectField3=${attrs.collectField3}&searchField3=${attrs.searchField3}&domain3=${attrs.domain3}&controller3=${attrs.controller3}&action3=${attrs.action3}&setId3=${attrs.setId3}&bindid3=${attrs.bindid3}"
		}
		if (attrs.domain4) {
			if (!attrs.bindid4) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid4]")
			}
			if (!attrs.setId4) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId4]")
			}
			changeAddon+="&collectField4=${attrs.collectField4}&searchField4=${attrs.searchField4}&domain4=${attrs.domain4}&controller4=${attrs.controller4}&action4=${attrs.action4}&setId4=${attrs.setId4}&bindid4=${attrs.bindid4}"
		}
		if (attrs.domain5) {
			if (!attrs.bindid5) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid5]")
			}
			if (!attrs.setId5) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId5]")
			}
			changeAddon+="&collectField5=${attrs.collectField5}&searchField5=${attrs.searchField5}&domain5=${attrs.domain5}&controller5=${attrs.controller5}&action5=${attrs.action5}&setId5=${attrs.setId5}&bindid5=${attrs.bindid5}"
		}
		if (attrs.domain6) {
			if (!attrs.bindid6) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid6]")
			}
			if (!attrs.setId6) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId6]")
			}
			changeAddon+="&collectField6=${attrs.collectField6}&searchField6=${attrs.searchField6}&domain6=${attrs.domain6}&controller6=${attrs.controller6}&action6=${attrs.action6}&setId6=${attrs.setId6}&bindid6=${attrs.bindid6}"
		}
		if (attrs.domain7) {
			if (!attrs.bindid7) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid7]")
			}
			if (!attrs.setId7) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId7]")
			}
			changeAddon+="&collectField7=${attrs.collectField7}&searchField7=${attrs.searchField7}&domain7=${attrs.domain7}&controller7=${attrs.controller7}&action7=${attrs.action7}&setId7=${attrs.setId7}&bindid7=${attrs.bindid7}"
		}
		if (attrs.domain8) {
			if (!attrs.bindid8) {
				throwTagError("Tag [selectPrimary] is missing required attribute [bindid8]")
			}
			if (!attrs.setId8) {
				throwTagError("Tag [selectPrimary] is missing required attribute [setId8]")
			}
			changeAddon+="&collectField8=${attrs.collectField8}&searchField8=${attrs.searchField8}&domain8=${attrs.domain8}&controller8=${attrs.controller8}&action8=${attrs.action8}&setId8=${attrs.setId8}&bindid8=${attrs.bindid8}"
		}
		return changeAddon
	}
	def autoCompleteHeader = {
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}

}
