ajaxdependancyselection 0.14
=======================

Grails plugin using auto complete to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box. This is in cases where domain object 1 hasMany of domainclass2 and domainclass2 belongs to domainclass1


Refer to: https://github.com/vahidhedayati/ajaxdependancyselectexample for examples of this plugin

# Grails plugin summary:

1. Auto complete  to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box.

2. Select method to choose first field , using the id it binds to second form field where its values are based on first chosen select field.


# Installation:

       grails install-plugin ajaxdependancyselection

#Description
Grails plugin using auto complete to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box. This is in cases where domain object 1 hasMany of domainclass2 and domainclass2 belongs to domainclass1 Required:


#Required:

	jquery-ui
	ajaxdependancyselection



# Howto:


# Configuration of your grails app - include jquery, jquery-ui libraries:
  
your layouts main.gsp: (add jquery-ui and jquery - or add them into ApplicationResources.groovy and ensure you refer to it in your main.gsp or relevant file

	<g:javascript library="jquery-ui"/>
	<g:javascript library="jquery"/>
	…
	<g:layoutHead/>



Sample domainclasses for below examples (all under my test app called testingv which all classes are packages with:

	package testingv


which has these domains - left out Deparment/employee same as mycountry/mycity

	class MyContinent {
      		String name
      		static hasMany=[mycountry: MyCountry]
      		String toString()  { "${name}"}
	}

	class MyCountry { 
      		String name 
      		MyContinent mycontinent 
      		static hasMany=[mycity: MyCity] 
      		String toString()  { "${name}"}
	}

	class MyCity { 
     		String name 
    		MyCountry mycountry 
    		String toString()  { "${name}"}
	}


# Add new controller actions
Now generated controllers and views for above, once done edit the controller for master which is MyCountry add :

	def example2() {}
	def example3() {}
	def example4() {}
	def example5() {}


# Add new gsp pages
Create example2 3 4 5 gsp files in your views for mycountry for now containing:

	<%@ page import="testingv.MyContinent" %>
	<%@ page import="testingv.MyCountry" %>
	<%@ page import="testingv.MyCity" %>

	<!DOCTYPE html> <html> <head> 
	<meta name="layout" content="main"> <g:set var="entityName" 
	value="${message(code: 'myCountry.label', default: 'MyCountry')}" /> 
	<title><g:message code="default.create.label" args="[entityName]" /></title> </head>


# Provided examples:,


	1.0 g:selectPrimary/Secondary  - How to do use selectPrimary/selectSecondary function  
	1.1 g:selectPrimary Multi element select example
	1.2  g:selectPrimary Multiple calls on one gsp


	2.0 g:autoCompletePrimary/Secondary - How to do use autoCompletePrimary/Secondary function  
	2.1 g:autocomplete Multi element autocomplete example
	2.2 g:autocomplete Multiple calls on one gsp
	2.3 g:autocomplete showing name setting value as collectionField
	2.4.g:autocomplete return result showing collectionField


	3. g:selectController : Your apps controllers & actions 








# 1.0 g:selectPrimary
This part of the plugin is based on http://www.grails.org/AJAX-Driven+SELECTs+in+GSP I have expanded the idea and made things modular enough for a gsp to provide the required values. In order to get your site to produce select dependancy options you will need to define:


	<g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='testingv.MyCountry'
        domain2='testingv.MyCity'
        bindid="mycountry.id"
        searchField='name'
        collectField='id'
        noSelection="['null': 'Please choose Country']" 
        setId="MyCity1"
        value=''/>

  	<g:select name="MyCity1" id="MyCity1"  
        optionKey="id" optionValue="name" 
        from="[]" noSelection="['null': 'Please choose Country']" />
        
        
        
Example2: 

         <g:selectPrimary id="MyContinent1" 
         name="MyContinent1" 
         domain='ajaxdependancyselectexample.MyCountry' 
         searchField='countryName' 
         collectField='id'
         
         domain2='ajaxdependancyselectexample.MyCity' 
         bindid="mycountry.id" 
         searchField2='cityName' 
         collectField2='id'
         
         noSelection="['null': 'Please choose Country']" setId="MyCity1" value=''/>
         
         <g:select name="MyCity1" id="MyCity1" 
         optionKey="id" optionValue="cityName" 
         from="[]" 
         noSelection="['null': 'Please choose Country']" />


the tag g:selectPrimary followed by its id and name, the domain and domain variables are domain is the actual domain for the select you wish to display, domain2 is the secondary domain this object is bound with. followed by bindid, the bindid is the object name.id as defined in your domainClass. In my city domainclass I had MyCountry mycountry, so the bindid here was mycountry.id, the search and collectFields are usually the id and the given field which would represent the name of the object.

With this now in place a simple g:select box with from set to [] and id that matches the setId of the actual g:selectPrimary : setId="MyCity1" followed by <g:select ... id="MyCity1"  

This is a basic two object mapping that works fine in order to expand to multiple objects go to 1.1



I have added Example 2 above which works fine on verion 0.14 onwards.. It has new searchField2 and collectionField2, if these are not defined it will default to searchField and collectField. Due to this object containing a cityName and countryName there had to be differet definition made. Sorry my examples were all very basic I have had to update plugin to work with more dynamic setups.



# 1.1 g:selectPrimary Multi element autocomplete example

	<form method=post action=exampl5>
	<g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='testingv.MyContinent'
        domain2='testingv.MyCountry'
        bindid="mycontinent.id"
        searchField='name'
        collectField='id'
        noSelection="['null': 'Please choose Continent']" 
        setId="MyCountry1"
        value=''/>
        
	<g:selectSecondary id="MyCountry1" name="MyCountry1"
	domain2='testingv.MyCity'
        bindid="mycountry.id"
        searchField='name'
        collectField='id'
        noSelection="['null': 'Please choose Continent']" 
        setId="MyCity1"
        value=''/>
        

    	<g:select name="MyCity1" id="MyCity1"  
        optionKey="id" optionValue="name" 
        from="[]" noSelection="['null': 'Please choose Country']" />
 
     	<input type=submit value=go>  
    	</form>

The only thing different in this block is the new usage of <g:selectSecondary, this now only takes in domain2 - which is the  secondary domain that it has a relationship with in the case of this example MyCountry has a relationship with MyCity, the very last call returns back to basic g:select call. Feel free to repeat g:selectSecondary on more objects finalising the call by the final g:select for the last object that is not supposed to be mapped to anything.



# 1.2 g:selectPrimary Multiple times on one gp:

	<g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='testingv.MyContinent'
        domain2='testingv.MyCountry'
        bindid="mycontinent.id"
        searchField='name'
        collectField='id'
        noSelection="['null': 'Please choose Continent']" 
        setId="MyCountry1"
        value=''/>
        
	<g:select name="MyCountry1" id="MyCountry1"  
        optionKey="id" optionValue="name" 
        from="[]" noSelection="['null': 'Please choose Continent']" />


	<g:selectPrimary id="MyCountry22" name="MyCountry22"
	domain='testingv.MyCountry'
	domain2='testingv.MyCity'
        bindid="mycountry.id"
        searchField='name'
        collectField='id'
        noSelection="['null': 'Please choose Country']" 
        setId="MyCity11"
        value=''/>

    	<g:select name="MyCity11" id="MyCity11"  
        optionKey="id" optionValue="name" 
        from="[]" noSelection="['null': 'Please choose Country']" />



Above example is two calls of selectPrimary on one gsp and can be called more times if required

If you are using g:selectPrimary/g:selectSecondary multiple times, please ensure the ids: i.e. id="selectPrimaryTest2" differ per call. Since JavaScript created is bound to this set id name.





# 2.0 g:autoCompletePrimary/Secondary

This part of the plugin expands on this idea: https://github.com/alidadasb/CountryCityAutoComplete, the g:autocomplete option has a variety methods to be called :


# 2.1 g:autocomplete Multi element autocomplete example

	<form method=post action=exampl5>
	<label>Continent:</label>
	<g:autoCompletePrimary id="primarySearch1" 	
  	name="continentName"
  	domain='testingv.MyContinent'
  	searchField='name'
  	collectField='id'
  	setId="secondarySearch2"
  	hidden='hidden3'
  	value=''/>

	<input type=hidden id="hidden3" name="continentId" value=""/>

	<label>Country:</label> 
	<g:autoCompleteSecondary id="secondarySearch2" 
  	name="countryName" 
  	domain='testingv.MyCountry' 
	primarybind='mycontinent.id' 
  	hidden='hidden3' 
  	hidden2='hidden4' 
  	searchField='name' 
  	collectField='id'
  	setId="secondarySearch3" 
  	value=''/>

	<input type=hidden id="hidden4" name="countryId" value=""/>

	<label>City:</label>
	<g:autoCompleteSecondary id="secondarySearch3" 
  	name="cityName" 
  	domain='testingv.MyCity' 
  	primarybind='mycountry.id' 
  	hidden='hidden4' 
  	hidden2='hidden5' 
  	searchField='name' 
  	collectField='id' 
  	value=''/>

	<input type=hidden id="hidden5" name="cityId" value=""/>

	<input type=submit value=go> </form>


Above returns:

	[countryId:4, continentName:Europe, countryName:France, continentId:2, cityId:4, cityName:Paris, action:exampl5, controller:myCountry]

	
g:autoCompletePrimary initially followed by g:autoCompleteSecondary as many times as required and on final g:autoCompleteSecondary there is no setId=

Each object as a hidden variable which binds to its own hidden field, this stores the ids of the auto completed objects and the actual auto complete values are the names as can be seen in the output above

PrimaryObject has its own hiddenField, secondary objects all have two definitions, hidden in secondary is the value from above hidden field and hidden2 is the hidden field for this secondary object to put its ID into and where another secondary object would query again..


# 2.2 g:autocomplete Multiple calls on one gsp

	<form method=post action=exampl5>
 	<label>Continent:</label>
 	<label>Countries:</label>
 	<g:autoCompletePrimary id="primarySearch" name="myCountryId"
     	domain='testingv.MyCountry'
     	searchField='name'
     	collectField='id'
     	hidden='hidden2'
     	value=''/>
	<input type=hidden id="hidden2" name="hiddenField" value=""/>

	<label>Cities:</label>
	<g:autoCompleteSecondary id="secondarySearch"
  	name="myCityId" 
  	domain='testingv.MyCity' 
  	primarybind='mycountry.id' 
  	hidden='hidden2' 
  	hidden2='hidden5' 
  	searchField='name' 
  	collectField='id' value=''/>
	<input type=hidden id="hidden5" name="cityId" value=""/>

	# secondary object - you will need to create these domain objects 
	<label>Department:</label>
	<g:autoCompletePrimary id="primarySearch1" name="deparmentName"
  	domain='testingv.Department'
  	searchField='name'
  	collectField='id'
  	setId="secondarySearch2"
  	hidden='hidden44'
	value=''/>
	<input type=hidden id="hidden44" name="departmentId" value=""/>

	<label>Cities:</label>
	<g:autoCompleteSecondary id="secondarySearch2" 
  	name="employeeName" 
  	domain='testingv.Employee' 
  	primarybind='department.id' 
  	hidden='hidden44' 
  	hidden2='hidden55' 
  	searchField='name' 
  	collectField='id' 
  	value=''/> 
	<input type=hidden id="hidden55" name="employeeId" value=""/> <br/><br/>

	<input type=submit value=go> </form>


The very first example of 2.2 has no setId this is because the default behaviour or Id of autoCompleteSecondary is secondarySearch, if either your id is different to this id for secondarySearch or if you need to call this multiple times, the please ensure you define the setId as per the 2nd example further down on 2.2 on autoCompletePrimary






# 2.3 g:autocomplete showing name setting value as collectionField

This example shows the name but set the value as collectField in this case the id for name..

	<label>Countries:</label>
	<g:autocomplete id="primarySearch" name="myId"
  	domain='testingv.MyCountry'
  	searchField='name'
  	collectField='id'
  	value=''/>


# 2.4 g:autocomplete return result showing collectionField
	
	<label>Countries:</label>
	<g:autocomplete id="primarySearch2" name="myId2"
  	action='autocompleteShowCollect'
  	domain='testingv.MyCountry'
  	searchField='name'
  	collectField='id'
  	value=''/>



# 3. g:selectController : Your apps controllers & actions 
        
tag to query all controllers and using select dependancy show its relevant actions - useful for permissions or anything related to what actions controllers have avaialble:

	<form method=post action=exampl5>
	<g:selectController id="selectPrimaryTest2" name="mycontrollers"
  	searchField='name'
  	collectField='name'
  	noSelection="['null': 'Please choose Controller']" 
  	setId="ControllerActions"
  	value=''/>

	<g:select name="myname" id="ControllerActions" 
	optionKey="name" optionValue="name" 
	from="[]" noSelection="['null': 'Please choose controller']" /> 
	<br> <input type=submit value=go> </form>


returns:

	[mycontrollers:employee, myname:list, action:exampl5, controller:myCountry]
 	params.myname=list params.mycontroller=employee (my selection at the time)

{code}


How it works internally:

(Please note controllers/actions are no longer required to be defined as part of the call) - this is just for the purpose of understanding how plugin works:

	g:selectPrimary - Methods: Controller:autoComplete | Actions: ajaxSelectSecondary
	g:selectSecondary - Methods: Controller:autoComplete | Actions: ajaxSelectSecondary
	g:autoCompletePrimary  - Methods: Controller:autoComplete   | Actions: autocompletePrimaryAction
	g:autoCompleteSecondary - Methods: Controller:autoComplete | Actions: autocompleteSecondaryAction
	g:autocomplete - Methods: Controller:autoComplete | Actions: autocomplete, autocompleteShowCollect
	g:selectController - Methods: Controller:autoComplete | Actions: ajaxSelectControllerAction


    

Please note MyCity and Mycountry was just an example, you could create and call any of your own classes as long as the g:autocomplete tags then match the actual domain classes etc :

Country|City, Continent|Country, Department|Employee

The list goes on...



