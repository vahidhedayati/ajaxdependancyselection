package ajaxdependancyselection

import grails.plugins.Plugin

class AjaxdependancyselectionGrailsPlugin extends Plugin {
   	def grailsVersion = "3.0 > *"
	def title = "Ajax Dependancy Selection Plugin"
	def description = 'Defines next auto completion/selection form field values ensuring it is bound on previous auto completed/selected form field. This can be used on two or more objects of hasMany and belongsTo. Provides: g:autocomplete, g:autoCompletePrimary, g:autoCompleteSecondary, g:autoCompleteSecondaryNR, g:selectPrimary, g:selectSecondary , g:selectSecondaryNR &  g:selectController. g:selectAutoComplete and g:selectPrimaryNR. Now also supporting 1 object with multiple dependencies.'
	def documentation = "http://grails.org/plugin/ajaxdependancyselection"
	def license = "Apache"
	def developers = [
		[name: "Vahid Hedayati", email: "badvad@gmail.com"],
		[name: "Alidad Soleimani", email: "badvad@gmail.com"]
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/vahidhedayati/Ajaxdependancyselection/issues']
	def scm = [url: 'https://github.com/vahidhedayati/Ajaxdependancyselection']
}
