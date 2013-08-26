ajaxdependancyselection 0.10
=======================

Grails plugin using auto complete to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box. This is in cases where domain object 1 hasMany of domainclass2 and domainclass2 belongs to domainclass1

#Summary
Defines secondary auto completion form field values ensuring it is bound on first auto completed form field
Installation

       grails install-plugin ajaxdependancyselection

#Description
Grails plugin using auto complete to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box. This is in cases where domain object 1 hasMany of domainclass2 and domainclass2 belongs to domainclass1 Required:

       jquery-ui
       ajaxdependancyselection
  
  




# Howto:

1. your layouts main.gsp: (add jquery-ui and jquery - or add them into ApplicationResources.groovy and ensure you refer to it in your main.gsp or relevant file


        <g:javascript library="jquery-ui"/>

        <g:javascript library="jquery"/>

        Above    <g:layoutHead/>




2. 2 domain classes that depend on each other here is an example:




       package testingv
       
       class MyCountry {

              String name

              static hasMany=[mycity: MyCity]
       }




       package testingv
    
       class MyCity {

              String name

              MyCountry mycountry
       }




Now generated controllers and views for above, once done edit the controller for master which is MyCountry add :

    def example2() {
     [countryInstance: new MyCountry(params)]
    }


The following example will call a single auto complete example, 
1. a single autocomplete showing name setting value as collectionField
2. a single autocomplete return result showing collectionField
3. A Primary/Secondary call followed by another primary / Secondary call to two new objects on the same gsp page:


Ofcourse calling it what you what and ensuring the new MyCountry matches actual class


The GSP:

       <%@ page import="testingv.MyCountry" %>
       <%@ page import="testingv.MyCity" %>
       <%@ page import="testingv.Department" %>
       <%@ page import="testingv.Employee" %>

       <!DOCTYPE html>
       <html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'myCountry.label', default: 'MyCountry')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	
	
	
# Standard call with no dependancy 
- a simple auto complete function

This example shows the  name but set the value as collectField in this case the id for name..

       <label>Countries:</label>
       <g:autocomplete id="primarySearch" name="myId"
            domain='testingv.MyCountry'
            searchField='name'
            collectField='id'
            value=''/>
            
            

            
# Show collection as result:

       <label>Countries:</label>
       <g:autocomplete id="primarySearch2" name="myId2"
            action='autocompleteShowCollect'
            domain='testingv.MyCountry'
            searchField='name'
            collectField='id'
            value=''/>

# Select dependancy :

 	<form method=post action=exampl5>
	<g:selectPrimary id="selectPrimary" name="MyCountry"
            domain='testingv.MyCountry'
            domain2='testingv.MyCity'
            bindid="mycountry.id"
            searchField='name'
            collectField='id'
            noSelection="['null': 'Please choose Country']" 
            setId="MyCity1"
            value=''/>
    
       	<g:select name="MyCity" id="MyCity1"  
       		optionKey="id" optionValue="name" 

       		from="[]" noSelection="['null': 'Please choose Country']" />
 <input type=submit value=go>  
     </form> 

returns:

	[MyCity:2, MyCountry:2, action:exampl5, controller:myCountry]
	params.Mycity=2 params.MyCountry=2
	
Please note using this select option the value returned is the id of selection


Please note selects can only be done once per gsp page - 


# On second gsp page the following could be done:

 	<g:selectPrimary id="selectPrimaryTest2" name="Department"
            domain='testingv.Department'
            domain2='testingv.Employee'
            bindid="department.id"
            searchField='name'
            collectField='id'
            noSelection="['null': 'Please choose Department']" 
            setId="Employee1"
            value=''/>
    
       	<g:select name="testingv.Employee1" id="Employee1"  	
       		optionKey="id" optionValue="name" 
       		from="[]" noSelection="['null': 'Please choose Department']" />

Until i figure out why....




# Gsp query controller:Actions
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
       		<br> 		
     	<input type=submit value=go>  
     	</form> 
     
     
returns:

     [mycontrollers:employee, myname:list, action:exampl5, controller:myCountry]
     params.myname=list params.mycontroller=employee 
       		
       		



# Multiple elements in Auto Completion:

     	<form method=post action=exampl5>
       	<label>Continent:</label>
      	<g:autoCompletePrimary id="primarySearch1" name="continentName"
            domain='testingv.MyContinent'
            searchField='name'
            collectField='id'
            setId="secondarySearch2"
            hidden='hidden3'
            value=''/>
            

	<input type=hidden id="hidden3" name="continentId" value=""/>


  	<label>Country:</label>
  	<g:autoCompleteSecondary id="secondarySearch2" name="countryName"  
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
  	<g:autoCompleteSecondary id="secondarySearch3" name="cityName"
            domain='testingv.MyCity'
            primarybind='mycountry.id'
            hidden='hidden4'
             hidden2='hidden5'
            searchField='name'
            collectField='id'
            value=''/>    
            
    	<input type=hidden id="hidden5" name="cityId" value=""/>
    
     	<input type=submit value=go>   
        </form>  
          
 Above returns:
 
 	[countryId:4, continentName:Asia, countryName:India, continentId:2, cityId:4, cityName:Bombay, action:exampl5, controller:myCountry]

      
Above example lists continents, then countries and finally cities, the key was in the actions called if you 
notice the 2nd calls autocompleteSecondary with a setid where as the last autocomplete has none. 
Repeat this for a bigger dependant autocomplete selection, and as shown on the last do not include the setId.



# Multiple calls of auto complete on one page

First example uses the default id primarySearch, thus no requirement to use the variable setId

EXAMPLE Primary / Secondary autocomplete:
     
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
  	<g:autoCompleteSecondary id="secondarySearch" name="myCityId"
            domain='testingv.MyCity'
            primarybind='mycountry.id'
             hidden='hidden2'
             hidden2='hidden5'
            searchField='name'
            collectField='id'
            value=''/>    
                     
    	<input type=hidden id="hidden5" name="cityId" value=""/>
    
     	<input type=submit value=go>  
     	</form> 
     
returns:

	[cityId:4, myCityId:Bombay, hiddenField:4, myCountryId:India, action:exampl5, controller:myCountry]
        
            
In this example which is actually on the same gsp page, the id's have to differ inorder for correct results to be returned, 

An addtional setId field is set on primary auto selects:

Please take note:
#setId on primary is actual id of secondary Field

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
       <g:autoCompleteSecondary id="secondarySearch2" name="employeeName"
            domain='testingv.Employee'
            primarybind='department.id'
            hidden='hidden44'
             hidden2='hidden55'
            searchField='name'
            collectField='id'
            value=''/>    
              <input type=hidden id="hidden55" name="employeeId" value=""/>
       <br/><br/>
   


This is now using the plugin to work out the values.

I need to explain a few things first though for it be used on your setup




Primary:

       domain='testingv.MyCountry'
       searchField='name'
       collectField='id'
       hidden='hidden2'


The first is full path to actual domain class including the package name, search field is what to look for, the collectionField is what to send to secondary field, hidden field is the id of the hiddenfield



Secondary:

      primarybind='mycountry.id'


This must match the field that there is a corelation with in the sql it will by mycountry_id in the domain class it was MyCountry mycountry, in this it needs to be

       name_you_selected_for_mapping.id




# How it works internally:

(Please note controllers/actions are no longer required to be defined as part of the call) - this is just for the purpose of understanding how plugin works:
	
	g:selectPrimary - Methods: Controller:autoComplete | Actions: ajaxSelectSecondary
  	g:autoCompletePrimary  - Methods: Controller:autoComplete   | Actions: autocompletePrimaryAction
   	g:autoCompleteSecondary - Methods: Controller:autoComplete | Actions: autocompleteSecondaryAction
 	g:autocomplete - Methods: Controller:autoComplete | Actions: autocomplete, autocompleteShowCollect
 	g:selectController - Methods: Controller:autoComplete | Actions: ajaxSelectControllerAction
 	
 # Multi dimension auto complete:
 
 	g:autoCompletePrimary  - Methods: Controller: autoCompletePrimary | Actions: autocompletePrimaryAction 
 	g:autoCompletePrimary  - Methods: Controller: autoCompleteSecondary | Actions:   autocompleteSecondaryAction
 	
Above example expanded means a primary is called with its action, a secondary can be called as many times as required last call should not include: setId



That is it - this should now allow you to have a relationship selection without much hassle.

Please note MyCity and Mycountry was just an example, you could create and call any of your own classes as long as the g:autocomplete tags then match the actual domain classes etc :

Country|City, Continent|Country, Department|Employee

The list goes on...
