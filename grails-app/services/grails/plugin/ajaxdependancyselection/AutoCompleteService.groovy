package grails.plugin.ajaxdependancyselection

import grails.converters.JSON
import grails.web.Action

import java.lang.reflect.Method

import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AutoCompleteService {

	static transactional = false

	def grailsApplication

	def autocomplete(String domain, String searchField, String collectField,
			String term, String max, String orderBy ) {

		if (verifySecurity(domain, searchField, collectField)) {
			def domainClass = grailsApplication.getDomainClass(domain).clazz
			def results = domainClass?.createCriteria()?.list {
				ilike searchField, term + '%'
				maxResults(Integer.parseInt(max,10))
				order(searchField, orderBy)
			}
			if (results.size()< 5){
				results = domainClass?.createCriteria()?.list {
					ilike searchField, "%${term}%"
					maxResults(Integer.parseInt(max,10))
					order(searchField, orderBy)
				}
			}
			results = results?.collect {[label:it."${collectField}"] }?.unique()
			return results as JSON
		}
	}

	def autocompletePrimaryAction(String showSearchField=null,String domain, String searchField, String collectField,
			String term, String max, String orderBy) {

		ArrayList primarySelectList = []

		if (verifySecurity(domain, searchField, collectField)) {
			def domainClass = grailsApplication.getDomainClass(domain).clazz
			def query = {
				or{
					ilike searchField, term + '%'
				}
				projections {
					property(collectField)
					property(searchField)
				}
				maxResults(Integer.parseInt(max,10))
				order(searchField, orderBy)
			}
			def query1 = {
				or{
					ilike searchField, "%${term}%"
				}
				projections {
					property(collectField)
					property(searchField)
				}
				maxResults(Integer.parseInt(max,10))
				order(searchField, orderBy)
			}
			def results = domainClass?.createCriteria()?.list(query)
			if (results.size()< 5){
				results = domainClass?.createCriteria()?.list(query1)
			}
			if (results) {
				primarySelectList = resultSet1(results, showSearchField)
			}
		}
		return primarySelectList as JSON
	}


	def autocompleteSecondaryAction(String showSearchField=null,String domain, String searchField, String collectField, String term,
			String max, String orderBy, String primarybind, Long primaryid) {

		ArrayList primarySelectList = []

		if (verifySecurity(domain, searchField, collectField)) {
			def domainClass = grailsApplication.getDomainClass(domain).clazz
			def query = {
				eq (primarybind, primaryid)
				and {
					ilike searchField, term + '%'
				}
				projections {
					property(collectField)
					property(searchField)
				}
				maxResults(Integer.parseInt(max,10))
				order(searchField, orderBy)
			}
			def query1 = {
				eq (primarybind, primaryid.toLong())
				and{

					ilike searchField, "%${term}%"
				}
				projections {
					property(collectField)
					property(searchField)
				}
				maxResults(Integer.parseInt(max,10))
				order(searchField, orderBy)
			}
			def results = domainClass?.createCriteria()?.list(query)
			if (results.size()< 5){
				results = domainClass?.createCriteria()?.list(query1)
			}
			if (results) {
				primarySelectList = resultSet1(results, showSearchField)
			}
		}
		return primarySelectList as JSON
	}


	// No reference auto complete service
	def autocompleteSecondaryNR (params) {
		def primarySelectList=[]
		def domainClass2 = grailsApplication?.getDomainClass(params.domain2)?.clazz
		def domainClass = grailsApplication?.getDomainClass(params.domain)?.clazz
		def domaininq=domainClass.get(params.primaryid.toLong())
		//def primarySelectList = []
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
			def results =domainClass2?.createCriteria().list(query)
			if (results.size()< 5){
				results = domainClass2?.createCriteria().list(query1)
			}
			if (results) {
				primarySelectList=resultSet1(results)
			}
		}
		return primarySelectList as JSON
	}


	def returnControllerList() {
		def clazz = grailsApplication?.controllerClasses?.logicalPropertyName
		def results = clazz?.collect {[	'id': it, 'name': it ]}?.unique()
		return results
	}

	def selectSecondary(String domain2, String searchField, String collectField, Long id, String bindid,
			String filter=null, String filterType=null) {

		ArrayList primarySelectList = []

		if (verifySecurity(domain2, searchField, collectField)) {
			if (domain2 && id) {
				def domainClass = grailsApplication?.getDomainClass(domain2)?.clazz
				def query = {
					eq  (bindid, id)
					if ((filter)&&(!filter.toString().equals('null'))) {
						def myfilter = parseFilter(filter,filterType)
						and { ilike  searchField ,myfilter}
					}
					projections {
						property(collectField)
						property(searchField)
					}
					order(searchField)
				}
				def results = domainClass?.createCriteria()?.list(query)
				if (results) {
					primarySelectList = resultSet2(results)
				}
			}
		}
		return primarySelectList as JSON
	}

	// This is now set up for secondary filtering method
	def secondarySearch(params) {
		def primarySelectList=[]
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
			def results =domainClass?.createCriteria().list(query)
			if (results) {
				primarySelectList=resultSet2(results)
			}
		}
		return primarySelectList as JSON
	}

	// No reference selection method i.e. belongsTo=UpperClass
	def selectSecondaryNR(params) {
		def primarySelectList = []
		if ((params.domain2) && (params.domain) &&( params.id)) {
			def domainClass2 = grailsApplication?.getDomainClass(params.domain2)?.clazz
			def domainClass = grailsApplication?.getDomainClass(params.domain)?.clazz
			def domaininq=domainClass?.get(params.id.toLong())
			if (domaininq) {
				domaininq."${params.bindid}".each { dq ->
					def query = {
						if ((params.filter2)&&(!params.filter2.toString().equals('null'))) {
							def filter=params.filter2
							def filterType=params.filterType2
							def myfilter=parseFilter(filter,filterType)
							and { ilike  params.searchField ,myfilter}
						}
						eq (params.searchField , dq.toString().trim())
						projections {
							property(params.collectField)
							property(params.searchField)
						}
						order(params.searchField)
					}
					def results =domainClass2?.createCriteria().list(query)
					results.each {
						def primaryMap = [:]
						primaryMap.put('id', it[0])
						primaryMap.put('name', it[1])
						primarySelectList.add(primaryMap)
					}
				}
			}
		}
		return primarySelectList as JSON
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
				def results=clazz?.createCriteria()?.list(query)
				if (!results) {
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
						if (results) {
							def primarySelectList=resultSet2(results)
							return primarySelectList as JSON
						}
					}else{
						results
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


	def returnControllerActions(String s) {
		String domclass = currentController(s)
		ArrayList mList = []
		grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controller ->
			Class controllerClass = controller.clazz
			if (controllerClass.name.endsWith(domclass)) {
				String logicalControllerName = controller.logicalPropertyName
				controllerClass.methods.each { Method method ->
					if (method.getAnnotation(Action)) {
						mList.add(method.name)
					}
				}
			}
		}
		def results = mList.collect {['id':it,'name':it] }?.unique()
		return results as JSON
	}


	private ArrayList resultSet1(def results, String showSearchField=null) {
		ArrayList primarySelectList = []
		if (results) {
			results.each {
				def primaryMap = [:]
				primaryMap.put('id', it[0])
				if (showSearchField && showSearchField != 'null') {
					primaryMap.put('label', "${it[0]}${showSearchField}${it[1]}")
				}else{
					primaryMap.put('label',it[1])
				}

				primarySelectList.add(primaryMap)
			}
		}
		return primarySelectList
	}

	private ArrayList resultSet2(def results) {
		ArrayList primarySelectList = []
		if (results) {
			results.each {
				def primaryMap = [:]
				primaryMap.put('id', it[0])
				primaryMap.put('name', it[1])
				primarySelectList.add(primaryMap)
			}
		}
		return primarySelectList
	}

	private List returnPrimaryList(String className) {
		if (!className.equals('')) {
			Class clazz = grailsApplication?.getDomainClass(className)?.clazz
			clazz?.list()
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


	// Security addition for 0.43
	public Boolean verifySecurity (String domainClass, String search, String collection) {
		boolean status = false
		if (config) {
			def clist = config.whitelist
			if (config.security && config.security == 'enabled') {
				if (clist.dc && clist.dc.contains(domainClass))  {
					clist.each {cl ->
						if ((cl.dc == domainClass) && (cl.search && cl.search == search)
						&& (cl.collect && cl.collect == collection)) {
							status = true
						}
					}
				}
			}else{
				status = true
			}
		}else{
			status = true
		}
		return status
	}

	private ConfigObject getConfig() {
		grailsApplication.config?.ads
	}

	private String currentController(String s) {
		s.substring(0,1).toUpperCase() + s.substring(1)+"Controller"
	}
}
