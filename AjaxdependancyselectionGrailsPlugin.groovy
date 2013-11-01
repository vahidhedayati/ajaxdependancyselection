class AjaxdependancyselectionGrailsPlugin {
	def version = "0.21"
	def grailsVersion = "2.0 > *"
 
	def title = "Ajax Dependancy Selection Plugin"
	def description = 'Defines next auto completion/selection form field values ensuring it is bound on previous auto completed/selected form field. This can be used on two or more objects of hasMany and belongsTo. Provides: g:autocomplete, g:autoCompletePrimary, g:autoCompleteSecondary, g:autoCompleteSecondaryNR, g:selectPrimary, g:selectSecondary , g:selectSecondaryNR &  g:selectController.'
	def documentation = "http://grails.org/plugin/ajaxdependancyselection"
	def license = "GPL2"
	def developers = [
		[name: "Vahid Hedayati", email: "badvad@gmail.com"],
		[name: "Alidad Soleimani", email: "badvad@gmail.com"]
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/vahidhedayati/Ajaxdependancyselection/issues']
	def scm = [url: 'https://github.com/vahidhedayati/Ajaxdependancyselection']
}
