package ajaxdependancyselection

import grails.converters.JSON

class AutoCompleteService {

	static transactional = false

	def grailsApplication

	def autocompleteSecondaryAction (params) {
		def domainClass = grailsApplication.getDomainClass(params.domain).clazz
		def results = domainClass.createCriteria().list {
			eq (params.primarybind, params.primaryid.toLong())
			ilike params.searchField, params.term + '%'
			order(params.searchField, params.order)
		}
		
		results = results.collect {	[label:it."${params.collectField}"] }.unique()
		return results as JSON
	}
	
	def autocompletePrimaryAction (params) {
		def domainClass = grailsApplication.getDomainClass(params.domain).clazz
		def query = {
			or{
				ilike params.searchField, params.term + '%'
			}
			projections { // good to select only the required columns.
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
			projections { // good to select only the required columns.##
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
}
