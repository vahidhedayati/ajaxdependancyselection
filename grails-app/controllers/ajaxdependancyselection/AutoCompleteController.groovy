package ajaxdependancyselection

class AutoCompleteController {

	def autoCompleteService

	def autocompletePrimaryAction() {
	   render autoCompleteService.autocompletePrimaryAction(params)
	}

	def autocompleteSecondaryAction() {
	   render autoCompleteService.autocompleteSecondaryAction(params)
	}
}
