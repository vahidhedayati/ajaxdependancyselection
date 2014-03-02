package ajaxdependancyselection


class AutoCompleteController {

	def autoCompleteService
	
	def ajaxSelectControllerAction() { 
		render autoCompleteService.returnControllerActions(params)
	}
		
	def ajaxSelectSecondary() {
		render autoCompleteService.selectSecondary(params)
	}
	
	def ajaxSelectSecondaryNR() {
		render autoCompleteService.selectSecondaryNR(params)
	}
	
	def autocomplete() {
		render autoCompleteService.autocompletePrimaryAction(params)
	}
	
	def autocompleteShowCollect() {
		render autoCompleteService.autocomplete(params)
	}
	
	def autocompletePrimaryAction() {
	   	render autoCompleteService.autocompletePrimaryAction(params)
	}
	
	def autocompleteSecondaryAction() {
	   	render autoCompleteService.autocompleteSecondaryAction(params)
	}
	
	def autocompleteSecondaryNR() {
		render autoCompleteService.autocompleteSecondaryNR(params)
	}
	
	def loadFilterWord() {
		render (template: '/autoComplete/filterWord',  model: [params:params])
	}
	
	def loadFilterWord2() {
		render (template: '/autoComplete/filterWord2',  model: [params:params])
	}
	
	def returnPrimarySearch(){
		render autoCompleteService.returnPrimarySearch('json',params.term,params.filterType,params.domain,params)
	}
	
	def secondarySearch() {
		render autoCompleteService.secondarySearch(params)
	}
}
