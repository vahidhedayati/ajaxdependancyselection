ajaxdependancyselection 0.2
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
	
	
	
h3. Standard call with no dependancy - a simple auto complete function

This example shows the  name but set the value as collectField in this case the id for name..

       <label>Countries:</label>
       <g:autocomplete id="primarySearch" name="myId"
            action='autocomplete'
            controller='autoComplete'
            domain='testingv.MyCountry'
            searchField='name'
            collectField='id'
            value=''/>
            
            

            
h3. This example shows the collection field in the drop down box and is what is to be set:

       <label>Countries:</label>
       <g:autocomplete id="primarySearch2" name="myId2"
            action='autocompleteShowCollect'
            controller='autoComplete'
            domain='testingv.MyCountry'
            searchField='name'
            collectField='id'
            value=''/>

h3. Auto complete multiple times on one page

h2. First example uses the default id primarySearch, thus no requirement to use the variable setId

       <label>Countries:</label>
       <g:autoCompletePrimary id="primarySearch" name="myCountryId"
            action='autocompletePrimaryAction'
            controller='autoComplete'
            domain='testingv.MyCountry'
            searchField='name'
            collectField='id'
            hidden='hidden2'
            value=''/>


       <input type=hidden id="hidden2" name="hiddenField" value=""/>


       <label>Cities:</label>
       <g:autoCompleteSecondary id="secondarySearch" name="myCityId"
            action='autocompleteSecondaryAction'
            controller='autoComplete'
            domain='testingv.MyCity'
            primarybind='mycountry.id'
            hidden='hidden2'
            searchField='name'
            value=''/>
       <br/><br/>
            
In this example which is actually on the same gsp page, the id's have to differ inorder for correct results to be returned, 

An addtional setId field is set on primary auto selects:

Please take note:
#setId on primary is actual id of secondary Field

            <label>Department:</label>
       <g:autoCompletePrimary id="primarySearch1" name="deparmentId"
            action='autocompletePrimaryAction'
            controller='autoComplete'
            domain='testingv.Department'
            searchField='name'
            collectField='id'
            setId="secondarySearch2"
            hidden='hidden3'
            value=''/>
            
       <input type=hidden id="hidden3" name="hiddenField" value=""/>


       <label>Cities:</label>
       <g:autoCompleteSecondary id="secondarySearch2" name="employeeId"
            action='autocompleteSecondaryAction'
            controller='autoComplete'
            domain='testingv.Employee'
            primarybind='department.id'
            hidden='hidden3'
            searchField='name'
            value=''/>
       </html>    
   


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



That is it - this should now allow you to have a relationship selection without much hassle.

Please note MyCity and Mycountry was just an example, you could create and call any of your own classes as long as the g:autocomplete tags then match the actual domain classes etc :

Country|City, Continent|Country, Department|Employee

The list goes on...
