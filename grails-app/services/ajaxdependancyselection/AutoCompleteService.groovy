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
		if (params.domain2) {
			def domainClass = grailsApplication?.getDomainClass(params?.domain2)?.clazz
			def query = {
				eq params.bindid, params.id.toLong()
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
				def results =domainClass2.createCriteria().list(query)
				results.each {
					def primaryMap = [:]
					primaryMap.put('id', it[0])
					primaryMap.put('name', it[1])
					primarySelectList.add(primaryMap)
				}
			}
			return primarySelectList as JSON
		}
	}
	def returnPrimarySearch(String json, String filter, String className, params) {
		if (!className.equals('')) {
			def clazz = grailsApplication.getDomainClass(className).clazz
			if (!filter.equals('')) { params.filterWord=filter } 
			if (params.filterWord) {
				def query = {
					or { ilike  params.searchField ,params.filterWord+ '%'}
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
					if (json.equals('json')) {
						def mylist=clazz.list()					
						def newlist=mylist?.collect {[	'id': it."${params.collectField}", 'name': it."${params.searchField}" ]}.unique()
						return newlist as JSON
		
					}else{	 
						clazz.list()
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
	List returnPrimaryList(String className) {
		if (!className.equals('')) {
			Class clazz = grailsApplication.getDomainClass(className).clazz
			clazz.list()
		}
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
	/*private HttpSession getSession() {
		return RequestContextHolder.currentRequestAttributes().getSession()
	}*/
	
}
