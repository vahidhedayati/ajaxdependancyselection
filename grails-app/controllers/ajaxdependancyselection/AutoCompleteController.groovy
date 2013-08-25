package ajaxdependancyselection

class AutoCompleteController {

	def autoCompleteService
	
	def ajaxSelectControllerAction() { 
		render autoCompleteService.returnControllerActions(params)
	}	
	def ajaxSelectSecondary() {
		render autoCompleteService.selectSecondary(params)
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
	def autocompleteMiddleAction() {
		render autoCompleteService.autocompleteMiddleAction(params)
	}
	def autocompleteSecondaryAction() {
	   render autoCompleteService.autocompleteSecondaryAction(params)
	}
}
