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
		if (!attrs.appendValue) {
			attrs.appendValue=''
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		
		def primarylist=autoCompleteService.returnControllerList()
		def gsattrs=[ 'id': '${attrs.id}', value: "${attrs.value}", name: name, optionKey: "${attrs.searchField}", optionValue: "${attrs.collectField}" ]
		gsattrs['from'] = primarylist
		if (requireField) {
			gsattrs['required'] = 'required'
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
			attrs.appendValue=''
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}
		
		Boolean requireField=true
		
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
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
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&prevId='+attrs.prevId+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		def link = ['action': attrs.action , 'controller': attrs.controller ]
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
		if (!attrs.appendValue)  {
			attrs.appendValue=''
		}
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}
		
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
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
		
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&bindid='+ attrs.bindid+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&prevId='+attrs.prevId+'&domain='+attrs.domain+'&domain2='+attrs.domain2+'&setId='+attrs.setId+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
		def link = ['action': attrs.action , 'controller': attrs.controller ]
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
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (!attrs.appendValue)  { attrs.appendValue='' }
		if (!attrs.appendName) { attrs.appendName='Values Updated' }
		
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
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&domain2='+attrs.domain2+'&prevId='+attrs.prevId+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
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
		if (!attrs.appendValue)  {
			attrs.appendValue=''
		}	
		if (!attrs.appendName) {
			attrs.appendName='Values Updated'
		}
		Boolean requireField=true
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
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
		gsattrs['noSelection'] =attrs.noSelection
		gsattrs['onchange'] = "${remoteFunction(controller:''+attrs.controller+'', action:''+attrs.action+'', params:'\'id=\' + escape(this.value) +\'&setId='+attrs.setId+'&filterController='+attrs.filterController+'&filterDisplay='+attrs.filterDisplay+'&bdomain='+attrs.bdomain+'&filterType='+attrs.filterType+'&filterType2='+attrs.filterType2+'&filter='+attrs.filter+'&filter2='+attrs.filter2+'&bindid='+ attrs.bindid+'&collectField='+attrs.collectField2+'&searchField='+attrs.searchField2+'&domain2='+attrs.domain2+'&domain='+attrs.domain+'&controller='+attrs.controller+'\'',onSuccess:''+attrs.id+'Update(data)')}"
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
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (requireField) {
			 required=" required='required' "
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
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (requireField) {
			 required=" required='required' "
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
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (requireField) {
			 required=" required='required' "
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
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (requireField) {
			 required=" required='required' "
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
		Boolean requireField=true
		def  required=""
		if (attrs.require) {
			requireField=attrs.remove('require')?.toBoolean()
		}
		if (requireField) {
			 required=" required='required' "
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
	
	
	def autoCompleteHeader = {
		out << "<style>"
		out <<  ".ui-autocomplete-loading"
		out << "        { background: white url(${resource(dir:'images',file:'ajax-loader.gif')}) right center no-repeat   }"
		out << " </style>"
	}
	
}
