package ajaxdependancyselection

import grails.converters.JSON
import grails.web.Action
import java.lang.reflect.Method
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass


class AutoCompleteService {
	
	static transactional = false
	def grailsApplication
	
	def autocomplete (params) {
		def domainClass = grailsApplication.getDomainClass(params.domain).clazz
		def results = domainClass.createCriteria().list {
			ilike params.searchField, params.term + '%'
			maxResults(Integer.parseInt(params.max,10))
			order(params.searchField, params.order)
		}
		if (results.size()< 5){
			results = domainClass.createCriteria().list {
				ilike params.searchField, "%${params.term}%"
				maxResults(Integer.parseInt(params.max,10))
				order(params.searchField, params.order)
			}
		}
		results = results.collect {     [label:it."${params.collectField}"] }.unique()
		return results as JSON
	}
	
	
	def autocompletePrimaryAction (params) {
		def domainClass = grailsApplication.getDomainClass(params.domain).clazz
		def query = {
			or{
				ilike params.searchField, params.term + '%'
			}
			projections { 
				property(params.collectField)
				property(params.searchField)
			}
			maxResults(Integer.parseInt(params.max,10))
			order(params.searchField, params.order)
		}
		def query1 = {
			or{
				ilike params.searchField, "%${params.term}%"
			}
			projections { 
				property(params.collectField)
				property(params.searchField)
			}
			maxResults(Integer.parseInt(params.max,10))
			order(params.searchField, params.order)
		}
		def results =domainClass.createCriteria().list(query)
		if (results.size()< 5){
			results = domainClass.createCriteria().list(query1)
		}
		def primarySelectList = []
		results.each {
			def primaryMap = [:]
			primaryMap.put('id', it[0])
			primaryMap.put('label', it[1])
			primarySelectList.add(primaryMap)
		}
		return primarySelectList as JSON
	}
	
	
	def autocompleteSecondaryAction (params) {
		def domainClass = grailsApplication.getDomainClass(params.domain).clazz
		def query = {
			eq (params.primarybind, params.primaryid.toLong())
			and{
				ilike params.searchField, params.term + '%'
			}
			projections { 
				property(params.collectField)
				property(params.searchField)
			}
			maxResults(Integer.parseInt(params.max,10))
			order(params.searchField, params.order)
		}
		def query1 = {
			eq (params.primarybind, params.primaryid.toLong())
			and{
				
				ilike params.searchField, "%${params.term}%"
			}
			projections { 
				property(params.collectField)
				property(params.searchField)
			}
			maxResults(Integer.parseInt(params.max,10))
			order(params.searchField, params.order)
		}
		def results =domainClass.createCriteria().list(query)
		if (results.size()< 5){
			results = domainClass.createCriteria().list(query1)
		}
		def primarySelectList = []
		results.each {
			def primaryMap = [:]
			primaryMap.put('id', it[0])
			primaryMap.put('label', it[1])
			primarySelectList.add(primaryMap)
		}
		return primarySelectList as JSON
	}
	
	
	// No reference auto complete service
	def autocompleteSecondaryNR (params) {
		def domainClass2 = grailsApplication?.getDomainClass(params.domain2)?.clazz
		def domainClass = grailsApplication?.getDomainClass(params.domain)?.clazz
		def domaininq=domainClass.get(params.primaryid.toLong())	
		def primarySelectList = []
		domaininq."${params.primarybind}".each { dq ->
			def query = {
				eq (params.searchField , dq.toString().trim())
				and { ilike  params.searchField ,params.term+ '%'}
				projections {
					property(params.collectField)
					property(params.searchField)
				}
				order(params.searchField)
			}
			def query1 = {
				eq (params.searchField , dq.toString().trim())
				and { ilike  params.searchField ,"%${params.term}%"}
				projections {
					property(params.collectField)
					property(params.searchField)
				}
				order(params.searchField)
			}
			def results =domainClass2.createCriteria().list(query)
			if (results.size()< 5){
				results = domainClass2.createCriteria().list(query1)
			}
			results.each {
				def primaryMap = [:]
				primaryMap.put('id', it[0])
				primaryMap.put('label', it[1])
				primarySelectList.add(primaryMap)
			}
		}
		return primarySelectList as JSON
	}
	
	
	def returnControllerList() {
		def clazz=grailsApplication.controllerClasses.logicalPropertyName
		def results = clazz.collect {[	'id': it, 'name': it ]}.unique()
		return results
	}
	
	
	
	def selectSecondary(params) {
		if (params.domain2 && params.id) {
			def domainClass = grailsApplication?.getDomainClass(params?.domain2)?.clazz
			def query = {
				eq  (params.bindid, params.id.toLong())
				if ((params.filter2)&&(!params.filter2.toString().equals('null'))) {
					def filter=params.filter2
					def filterType=params.filterType2
					def myfilter=parseFilter(filter,filterType)
					and { ilike  params.searchField ,myfilter}
				}
				projections {
					property(params.collectField)
					property(params.searchField)
				}
				order(params.searchField)
			}
			def results =domainClass.createCriteria().list(query)
			def primarySelectList = []
			results.each {
				def primaryMap = [:]
				primaryMap.put('id', it[0])
				primaryMap.put('name', it[1])
				primarySelectList.add(primaryMap)
			}
			return primarySelectList as JSON
		}
	}
	
	// This is now set up for secondary filtering method 
	def secondarySearch(params) {
		if (params.domain && params.prevValue) {
			def domainClass = grailsApplication?.getDomainClass(params?.domain)?.clazz
			def query = {
				eq  (params.filterbind, params.prevValue.toLong())
				if ((params.term)&&(!params.term.toString().equals('null'))) {
					def filter="${params.term}"
					def filterType="${params.filterType}"
					def myfilter=parseFilter(filter,filterType)
					and { ilike  params.searchField ,myfilter}
				}
				projections {
					property(params.collectField)
					property(params.searchField)
				}
				order(params.searchField)
			}
			def results =domainClass.createCriteria().list(query)
			def primarySelectList = []
			results.each {
				def primaryMap = [:]
				primaryMap.put('id', it[0])
				primaryMap.put('name', it[1])
				primarySelectList.add(primaryMap)
			}
			return primarySelectList as JSON
		}
	}
	
	
	// No reference selection method i.e. belongsTo=UpperClass 
	def selectSecondaryNR(params) {
		if ((params.domain2) && (params.domain)) {
			//println "--"+params
			def domainClass2 = grailsApplication?.getDomainClass(params.domain2)?.clazz
			def domainClass = grailsApplication?.getDomainClass(params.domain)?.clazz
			def domaininq=domainClass.get(params.id.toLong())
			def primarySelectList = []
			domaininq."${params.bindid}".each { dq ->
				def query = {
					eq (params.searchField , dq.toString().trim())
					projections {
						property(params.collectField)
						property(params.searchField)
					}
					order(params.searchField)
				}
				def results =domainClass2?.createCriteria().list(query)
				if (results) {
					results.each {
						def primaryMap = [:]
						primaryMap.put('id', it[0])
						primaryMap.put('name', it[1])
						primarySelectList.add(primaryMap)
					}
				}
			}
			if (primaryList) {
				return primarySelectList as JSON
			}
		}
	}
	
	
	def returnPrimarySearch(String json, String filter, String className, params) {
		if (!className.equals('')) {
			def filterType=params.filterType ?: 'A'
			def clazz = grailsApplication.getDomainClass(className).clazz
			if (filter) {
				def query = {
					if (!filter.toString().equals('null')) { 
						if ((params.filterType)&&(!params.filterType==null)) { 
							filterType=params.filterType
						}
						def myfilter=parseFilter(filter,filterType)
						and { ilike  params.searchField ,myfilter}
					}
					if (json.equals('json')) {
						projections {
							property(params.collectField)
							property(params.searchField)
						}
					}
					order(params.searchField)
				}
				def result=clazz?.createCriteria()?.list(query)
				if (!result) {
					if (params.filterDisplay.toString().toLowerCase().equals('all')) {
						if (json.equals('json')) {
							def mylist=clazz.list()					
							def newlist=mylist?.collect {[	'id': it."${params.collectField}", 'name': it."${params.searchField}" ]}.unique()
							return newlist as JSON
			
						}else{	 
							clazz.list()
						}
					}		
				}else{
					if (json.equals('json')) {
						def primarySelectList = []
						result.each {
							def primaryMap = [:]
							primaryMap.put('id', it[0])
							primaryMap.put('name', it[1])
							primarySelectList.add(primaryMap)
						}
						return primarySelectList as JSON
					}else{
						result
					}	
				}	
				//return results
			}else{
				if (params.filterDisplay.toString().toLowerCase().equals('all')) {
					if (json.equals('json')) {
						def mylist=clazz.list()
						def newlist=mylist?.collect {[	'id': it."${params.collectField}", 'name': it."${params.searchField}" ]}.unique()
						return newlist as JSON
					}else{	 
						clazz.list()
					}	
				}
			}
			
		}
	}
	
	
	List returnPrimaryList(String className) {
		if (!className.equals('')) {
			Class clazz = grailsApplication.getDomainClass(className).clazz
			clazz.list()
		}
	}
	
	def parseFilter(def filter,def filterType) { 
		def myfilter='%'+filter+'%'
		if (filterType.equals('S')) {
			myfilter=filter+'%'
		} else if (filterType.equals('E')) {
			myfilter='%'+filter
		}
		return myfilter
	}
	
	def returnControllerActions(params) {
		String s = params.id
		if (s) {
			String domclass1= (s.substring(0,1).toUpperCase())
			String domclass2=s.substring(1,s.length())
			String domclass=domclass1+domclass2+"Controller"
			List<String> list=new ArrayList<String>();
			grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controller ->
				Class controllerClass = controller.clazz
				if (controllerClass.name.endsWith(domclass.toString())) {
					String logicalControllerName = controller.logicalPropertyName
					controllerClass.methods.each { Method method ->
						if (method.getAnnotation(Action)) {
							list.add(method.name)
						}
					}
				}
			}
			def results = list.collect {	['id':it,'name':it] }.unique()
			return results as JSON
		}	
	}
	
}
