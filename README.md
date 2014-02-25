ajaxdependancyselection 0.23
=======================


Ajaxdependancyselection is a Grails plugin which makes use of jquery to provide either select or auto complete form fields. This can be any combination of either fully dependent objects or full dependent as well as no reference bindings.

A common problem when it comes to making a website is having objects that are independant and when a user selects an option what to do next ? do you refresh the page to update the next set of values or look into some jquery/java scripts to auto update the next set of select option.


# Installation:

Add plugin Dependency :

	compile ":ajaxdependancyselection:0.24" (unreleased fixing some issues)

Or via grails command line:

	grails install-plugin ajaxdependancyselection




# Autocomplete requirements:	
	compile ":jquery-ui:1.10.3"
	

# Autocomplete configuration  jquery, jquery-ui libraries:
  
your layouts main.gsp: (add jquery-ui and jquery - or add them into ApplicationResources.groovy and ensure you refer to it in your main.gsp or relevant file

	<g:javascript library="jquery-ui"/>
	<g:javascript library="jquery"/>
	…
	<g:layoutHead/>


The jquery-ui should only be required for autocomplete calls, jquery will be needed by all the other functionality of this plugin, so please ensure you have enabled jquery in your gsp and is installed.

	

# version info:

0.23 - removed null from values updated (default additional selection field added when values update), this now means user has to still choose this value 

0.22 - added required by default set to required for all taglib calls.
       if user wishes not to require a field then they must specifiy:
       required="false" in any of the below calls

0.19 - Broken build - there were issues with the tidyup I did with selectSecondary, totally forgot it was being used by selectPrimary. 0.20 should be fine


# Example site:

Using this plugin with the grails framework  you are able to achieve this without all of the complications. Refer to this sample project which makes use of all of the examples below with some objects already pre-added to the sample projecet. Found here in this sample project:

https://github.com/vahidhedayati/ajaxdependancyselectexample


Use Europe/United Kingdom/London or Oxford for a full completed example within the above example project when loading it up.




# Taglibs provided:

	g:selectController
	g:selectPrimary
	g:selectSecondary
	g:autocomplete
	g:autoCompletePrimary
	g:autoCompleteSecondary
	g:selectPrimaryNR
	g:selectSecondaryNR
	g:autoCompleteSecondaryNR




# g:selectController 
Controller action discovery 


This will display all your controllers then let you Choose available actions of this controller:
https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/example.gsp 
Line 210 onwards has an example.Typically maybe used for identifying controller name and its available actions to store against something maybe for your own custom authentication control that binds all this with something else.
 
Here are the values explained:


	<g:selectController
	id="selectPrimaryTest22"		// Required - your objectID referred to by css has no importance
	setId="ControllerActions"		// Required the ID of your next selectBox to update actions
	name="mycontrollers" 			// Required - your form field name	
	searchField='name'			// optional from 0.24+ - search name of controllers
	collectField='name' 			// Optional - will default to searchField
	noSelection="['': 'Please choose Controller']"  //default message for no selection by use
	controller = "something" 		// Optional - default "autoComplete" (part of this plugin)
	action = "something" 			// Optional - default "ajaxSelectControllerAction" (part of this plugin)
	appendValue='*'				// Optional set a value to be appended to the list
	appendName='All Items' 			// only optional if above not defined
        required="false"			// Optional - add this if you wish to disable required set by default
	value="${params.mycontrollers}"	 	//your value if you are posting form back
	/>

This now gets passed to a standard select call where it has an id of "ControllerActions":


	<g:select 
	id="ControllerActions"		//Required - your ObjectID - very important!
	name="myname" 
	optionKey="name" 
	optionValue="name"
	from="[]" 
	required="required" 
	noSelection="['': 'Please choose controller']" 
	/> 
	
The from on this is set to [] which gets filled in by g:selectController setId return call.





# g:selectPrimary 
(relationship: fully dependent ) in conjunction with g:selectSecondary

Example domainClasses:


	class PrimaryDomain {
 		String name
 		static hasMany = [ secondarydomain: SecondaryDomain ]
	}

	class SecondaryDomain {
 		String name
		static belongsTo = [ primarydomain:  PrimaryDomain ]
	}
	
	
Bindid would be:	

	bindid="primarydomain.id"

This you would use g:selectPrimary the bindid is primarydomain.id the field highlighted in bold above as the bindid


Here are the values explained:


	<g:selectPrimary 
	id="MyContinent2" 		// Required - your objectID referred to by css has no importance
	setId="MyCountry"  		// Required the ID of your next selectBox to update actions
	name="MyContinent" 		// Required - your form field name
	noSelection="['': 'Please choose Continent']"  //default message for 
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "ajaxSelectSecondary" (part of this plugin)
	appendValue='*'			// Optional set a value to be appended to the list
	appendName='All Items'		// If you set appendValue then set the display name for it
	bindid="mycontinent.id"		// Explained above in example domainClasses	
	domain='your.package.MyContinent'	// Required your primary domainClass full classpath.
	searchField='continentName'  		// Required - search for field called contintentName
	collectField='id' 			// Required : if not defaults to searchField value 
	domain2='your.package.MyCountry'	// Required your secondary depenent domainClass full classpath.
	searchField2='countryName'		//  Required - search for field called contintentName
	collectField2='id'
	required="false"		// optional add this if you wish to disable required set by default
	value="${params.MyContinent}"	// your value if you are posting form back
        />
         

Now this can be either passed toa normal select or to a  g:selectSecondary where if if you have further dependency you wish to interogate.

So in the case of Country city, the above is fine to call to a city select area that is a simple call like this:

	<g:select  
	id="MyCountry"  
	name="MyCity1"
	optionKey="id"
	optionValue="name" 
	from="[]" 
	required="required" 
	noSelection="['': 'Please choose Country']" />
        
        
        
If however this was a multi nested dependency situation you could proceed to selectSecondary where this will update either a final call just like per above or call selectSeondary over and over again until nesting is completed with final select as above. again ensure you are making proper calls your optionKey and Value needs to match your own table naming convention or in simple terms  

	<option value=YourDefindOptionKey>YourDefinedOptionValue</option>

is what is represented on the html end





# g:selectSecondary 
(relationship: fully dependent ) in conjunction with g:selectPrimary


This is a tag that can be used over and over again to go through nested situations, you can also use the selectSeondaryNR features to interact from selectPrimary or selectSecondary, this will be final part of document
Back to g:selectSecondary example:



        <g:selectSecondary 
        id="MyCountry" 			// Required - must be setId of previous selectPrimary or selectSecondary	
        name="MyCountry"		// Required - your field name for this select box
        domain2='your.package.MyCity'	// Requird - your next nested call or dependent class 
        bindid="mycountry.id"		// Like in primary this is your bind id of the 3rd belongsTo.id
        searchField2='cityName'		// table field to display
        collectField2='id'		// table field to set as value
        appendValue='*'			// Optional set a value to be appended to the list
        appendName='All Countries'	// If you set appendValue then set the display name for it
        noSelection="['': 'Please choose Continent']"	//default no selection value
        setId="MyCity"			//Required - your next select id to update
        value="${params.MyCountry}"	// Default value of select box
        required="false"		// optional add this if you wish to disable required set by default
        />




In this secondary Call we have defined a domain2 and search and collectField2 now this is 


## no reference mapping
	class PrimaryDomain {
 		String name
 		static hasMany = [ secondarydomain: SecondaryDomain ]
	}

	class SecondaryDomain {
 		String name
		static belongsTo = [PrimaryDomain ]
	}

Now within your call the bindid would be:	

	bindid="secondarydomain"


This you would use g:selectPrimaryNR the bindid is secondarydomain the field highlighted in bold above as the bindid


Notice in the PrimaryNR the bindid is the primary hasMany mapping and has no .id


Here are two domain classes with a no reference set up and require g:selectSecondaryNR feature: 

		class MyCity {

			String cityName
			MyCountry mycountry
			static hasMany=[myborough: MyBorough]
			String toString()  { "${cityName}"}
		}
		
		class MyBorough {
			String actualName
			static belongsTo = [MyCity]
			String toString() { "${actualName}" }
		}













1.1 Here is the GSP making a nested call where an element has a no reference relationship, the gsp page in the example called norefselectionext.gsp goes back out of a NR relationship and calls Streets domain after borough to then load up a further relationsip


	  <form method=post action=example5>
	  <g:selectPrimary id="MyContinent2" name="MyContinent2"
	     domain='ajaxdependancyselectexample.MyContinent'
            searchField='continentName'
            collectField='id'

            domain2='ajaxdependancyselectexample.MyCountry'
            bindid="mycontinent.id"
            searchField2='countryName'
            collectField2='id'
            
            appendValue='*'
            appendName='All Items'
            
            noSelection="['': 'Please choose Continent']"
            setId="MyCountry11"
            value=''/>

        <g:selectSecondary id="MyCountry11" name="MyCountry11"
            domain2='ajaxdependancyselectexample.MyCity'
            bindid="mycountry.id"
            searchField2='cityName'
            collectField2='id'

            appendValue='optional_Additional_Value_'
            appendName='Optional Additional Name'
            noSelection="['': 'Please choose Continent']"
            setId="MyCity11"
            
            appendValue='*'
            appendName='All Items'
            
            value=''/>





        <g:selectSecondaryNR id="MyCity11" name="MyCity11"
            domain='ajaxdependancyselectexample.MyCity'
            bindid="myborough"
            searchField='cityName'
            collectField='id'

            domain2='ajaxdependancyselectexample.MyBorough'
             searchField2='actualName'
            collectField2='id'




            noSelection="['': 'Please choose City']"
            setId="MyBorough11"
            
            appendValue='*'
            appendName='All Items'
            
            
            value=''/>


            <g:select name="MyBorough11" id="MyBorough11" 
            optionKey="id" optionValue="name"
            from="[]" required="required"  noSelection="['': 'Please choose City']" />

        <br> <input type=submit value=go> </form>
        
        


I have added Example 2 above which works fine on verion 0.14 onwards.. It has new searchField2 and collectionField2, if these are not defined it will default to searchField and collectField. Due to this object containing a cityName and countryName there had to be differet definition made. Sorry my examples were all very basic I have had to update plugin to work with more dynamic setups.

# 1.2 g:selectPrimary Multi element autocomplete example

	<form method=post action=exampl5>
	<g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='testingv.MyContinent'
        domain2='testingv.MyCountry'
        bindid="mycontinent.id"
        searchField='name'
        collectField='id'
        noSelection="['': 'Please choose Continent']" 
        setId="MyCountry1"
        value=''/>
        
	<g:selectSecondary id="MyCountry1" name="MyCountry1"
	domain2='testingv.MyCity'
        bindid="mycountry.id"
        searchField2='name'
        collectField2='id'
        noSelection="['': 'Please choose Continent']" 
        setId="MyCity1"
        value=''/>
        

    	<g:select name="MyCity1" id="MyCity1"  
        optionKey="id" optionValue="name" 
        from="[]" required="required" noSelection="['': 'Please choose Country']" />
 
     	<input type=submit value=go>  
    	</form>

The only thing different in this block is the new usage of <g:selectSecondary, this now only takes in domain2 - which is the  secondary domain that it has a relationship with in the case of this example MyCountry has a relationship with MyCity, the very last call returns back to basic g:select call. Feel free to repeat g:selectSecondary on more objects finalising the call by the final g:select for the last object that is not supposed to be mapped to anything.



# 1.3 g:selectPrimary Multiple times on one gp:

	<g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='testingv.MyContinent'
        domain2='testingv.MyCountry'
        bindid="mycontinent.id"
        searchField='name'
        collectField='id'
        noSelection="['': 'Please choose Continent']" 
        setId="MyCountry1"
        value=''/>
        
	<g:select name="MyCountry1" id="MyCountry1"  
        optionKey="id" optionValue="name" 
        from="[]" noSelection="['': 'Please choose Continent']" />


	<g:selectPrimary id="MyCountry22" name="MyCountry22"
	domain='testingv.MyCountry'
	domain2='testingv.MyCity'
        bindid="mycountry.id"
        searchField='name'
        collectField='id'
        noSelection="['': 'Please choose Country']" 
        setId="MyCity11"
        value=''/>

    	<g:select name="MyCity11" id="MyCity11"  
        optionKey="id" optionValue="name" 
        from="[]" required="required" noSelection="['': 'Please choose Country']" />



Above example is two calls of selectPrimary on one gsp and can be called more times if required

If you are using g:selectPrimary/g:selectSecondary multiple times, please ensure the ids: i.e. id="selectPrimaryTest2" differ per call. Since JavaScript created is bound to this set id name.





# 2.0 g:autoCompletePrimary/Secondary

This part of the plugin expands on this idea: https://github.com/alidadasb/CountryCityAutoComplete, the g:autocomplete option has a variety methods to be called :





# 2.1 No Reference Auto Complete from 0.16
			
			<form method=post action=example5>
			<label>Continent:</label>
			<g:autoCompletePrimary id="primarySearch1"  
			name="NAMEOFcontinentName"
			domain='ajaxdependancyselectexample.MyContinent'
			searchField='continentName'
			collectField='id'
			setId="secondarySearch2"
			hidden='hidden3'
			value=''/>
			
			<input type=hidden id="hidden3" name="continentId" value=""/>
			
			<label>Country:</label> 
			<g:autoCompleteSecondary id="secondarySearch2" 
			name="NAMEOFcountryName" 
			domain='ajaxdependancyselectexample.MyCountry' 
			primarybind='mycontinent.id' 
			hidden='hidden3' 
			hidden2='hidden4' 
			searchField='countryName' 
			collectField='id'
			setId="secondarySearch3" 
			value=''/>
			
			<input type=hidden id="hidden4" name="countryId" value=""/>
			
			<label>City:</label>
			<g:autoCompleteSecondary id="secondarySearch3" 
			name="NAMEOFcityName" 
			domain='ajaxdependancyselectexample.MyCity' 
			primarybind='mycountry.id' 
			hidden='hidden4' 
			hidden2='hidden5' 
			searchField='cityName' 
			collectField='id' 
			setId="secondarySearch4"
			value=''/>
			
			<input type=hidden id="hidden5" name="cityId" value=""/>
			
			<label>Borough:</label>
			<g:autoCompleteSecondaryNR id="secondarySearch4" 
			name="NAMEOFcityName" 
			domain='ajaxdependancyselectexample.MyCity' 
			domain2='ajaxdependancyselectexample.MyBorough' 
			primarybind='myborough' 
			hidden='hidden5' 
			hidden2='hidden6' 
			searchField='actualName' 
			collectField='id' 
			
			value=''/>
			
			<input type=hidden id="hidden6" name="BoroughID" value=""/>
			
			<input type=submit value=go> </form>
			



# 2.2 g:autocomplete Multi element autocomplete example

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

	<input type=hidden id="hidden5" name="cityId" required="required"  value=""/>

	<input type=submit value=go> </form>


Above returns:

	[countryId:4, continentName:Europe, countryName:France, continentId:2, cityId:4, cityName:Paris, action:exampl5, controller:myCountry]

	
g:autoCompletePrimary initially followed by g:autoCompleteSecondary as many times as required and on final g:autoCompleteSecondary there is no setId=

Each object as a hidden variable which binds to its own hidden field, this stores the ids of the auto completed objects and the actual auto complete values are the names as can be seen in the output above

PrimaryObject has its own hiddenField, secondary objects all have two definitions, hidden in secondary is the value from above hidden field and hidden2 is the hidden field for this secondary object to put its ID into and where another secondary object would query again..


# 2.3 g:autocomplete Multiple calls on one gsp

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
	<g:autoCompleteSecondary 
