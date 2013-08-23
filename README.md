ajaxdependancyselection
=======================

Grails plugin using auto complete to fill first form field, using the id it binds to second form field and auto complete option of 2nd field based on first chosen auto completed box. This is in cases where domain object 1 hasMany of domainclass2 and domainclass2 belongs to domainclass1



# Required:

jquery-ui 
{this plugin } ajaxdependancyselection



# Howto:

1. your layouts main.gsp: (add jquery-ui and jquery - or add them into ApplicationResources.groovy and ensure you refer to it in your main.gsp or relevant file


      <g:javascript library="jquery-ui"/>
      <g:javascript library="jquery"/>
      <g:layoutHead/>



2. 2 domain classes that depend on each other here is an example:


    package testingv
    class MyCountry {
      String name
       static hasMany=[mycity: MyCity]
        static constraints = {}
     }


    package testingv
    class MyCity {
     String name
     MyCountry mycountry
     static constraints = {}
    }


Now generated controllers and views for above, once done edit the controller for master which is MyCountry add :

    def example2() {
     [countryInstance: new MyCountry(params)]
    }
      
Ofcourse calling it what you what and ensuring the new MyCountry matches actual class 


The GSP:

          <label>Countries:</label>
          <g:autoCompletePrimary id="primarySearch"
                action='autocompletePrimaryAction'
                controller='autoComplete'
                domain='testingv.MyCountry'
                searchField='name'
                collectField='id'
                hidden='hidden2'
                value=''/>


      <input type=hidden id="hidden2" name="hiddenField" value=""/>


      <label>Cities:</label>
      <g:autoCompleteSecondary id="secondarySearch" name="appId"
                action='autocompleteSecondaryAction'
                controller='autoComplete'
                domain='testingv.MyCity'
                primarybind='mycountry.id'
                hidden='hidden2'
                searchField='name'
                value=''/>


Thats it !!

This is now using the plugin to work out the values.

I need to explain a few things first though for it be used on your setup


Explaination 1:
This does work on any object but only allows 1 usage per gsp page which means you can not call it twice on one gsp page this is due to :

      g:autoCompletePrimary
      g:autoCompleteSecondary
      
since it can only be used once


Explaination 2 on Primary:

          domain='testingv.MyCountry'
                searchField='name'
                collectField='id'
                hidden='hidden2'


The first is full path to actual domain class including the package name, search field is what to look for, the collectionField is what to send to secondary field, hidden field is the id of the hiddenfield



Explaination 3 on Secondary:

      primarybind='mycountry.id'
      
      
This must match the field that there is a corelation with in the sql it will by mycountry_id in the domain class it was MyCountry mycountry, in this it needs to be 

       name you selected.id
       
       

Seriously that is it 


		
