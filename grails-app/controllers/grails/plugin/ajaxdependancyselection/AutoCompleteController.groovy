package grails.plugin.ajaxdependancyselection

class AutoCompleteController {

	def autoCompleteService

	def ajaxSelectControllerAction(String id) {
        if (id) {
            render autoCompleteService.returnControllerActions(id)
        }
        render ''
	}
		
	def ajaxSelectSecondary(String domain2, String searchField2, String searchField, String collectField2, String collectField, String bindid) {
		render autoCompleteService.selectSecondary(domain2, searchField2 ?: searchField, collectField2 ?: collectField, params.id as Long, bindid, params.filter2, params.filterType2)
	}
	
	def ajaxSelectSecondaryNR() {
		render autoCompleteService.selectSecondaryNR(params)
	}
	
	def autocomplete(String domain, String searchField, String collectField, String term, String max, String order) {
        render autoCompleteService.autocompletePrimaryAction(domain, searchField, collectField, term, max, order)
	}
	
	def autocompleteShowCollect(String domain, String searchField, String collectField, String term, String max, String order) {
		render autoCompleteService.autocomplete(domain, searchField, collectField, term, max, order)
	}
	
	def autocompletePrimaryAction(String domain,String showSearchField, String searchField, String collectField, String term, String max, String order) {
			render autoCompleteService.autocompletePrimaryAction(showSearchField,domain, searchField, collectField, term, max, order)
	}
	
	def autocompleteSecondaryAction(String domain, String showSearchField, String searchField, String collectField, String term, String max, String order, String primarybind) {
		   render autoCompleteService.autocompleteSecondaryAction(showSearchField,domain, searchField, collectField, term, max, order, primarybind, params.primaryid as Long)
	}
	
	def autocompleteSecondaryNR() {
		render autoCompleteService.autocompleteSecondaryNR(params)
	}
	
	def returnPrimarySearch(){
		render autoCompleteService.returnPrimarySearch('json',params.term,params.domain,params)
	}
	
	def secondarySearch() {
        render autoCompleteService.secondarySearch(params)
    }

    def loadFilterWord() {
        render (template: '/autoComplete/filterWord',  model: [params:params])
    }

    def loadFilterWord2() {
        render (template: '/autoComplete/filterWord2',  model: [params:params])
    }
}
