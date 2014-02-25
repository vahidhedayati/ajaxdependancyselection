ajaxdependancyselection 0.23
=======================


Ajaxdependancyselection is a Grails plugin which makes use of jquery to provide either select or auto complete form fields. This can be any combination of either fully dependent objects or full dependent as well as no reference bindings.

A common problem when it comes to making a website is having objects that are independant and when a user selects an option what to do next ? do you refresh the page to update the next set of values or look into some jquery/java scripts to auto update the next set of select option.


## Installation:
Add plugin Dependency :

	compile ":ajaxdependancyselection:0.24" (unreleased fixing some issues)

Or via grails command line:

	grails install-plugin ajaxdependancyselection




## Autocomplete requirements:	

	compile ":jquery-ui:1.10.3"
	

## Autocomplete configuration  
###jquery, jquery-ui libraries:
your layouts main.gsp: (add jquery-ui and jquery - or add them into ApplicationResources.groovy and ensure you refer to it in your main.gsp or relevant file

	<g:javascript library="jquery-ui"/>
	<g:javascript library="jquery"/>
	…
	<g:layoutHead/>


The jquery-ui should only be required for autocomplete calls, jquery will be needed by all the other functionality of this plugin, so please ensure you have enabled jquery in your gsp and is installed.

	

## version info:
	0.23 - removed null from values updated (default additional selection field added when values update), this now means user has to still choose this value 
	0.22 - added required by default set to required for all taglib calls. 
	0.19 - Broken build - there were issues with the tidyup I did with selectSecondary, totally forgot it was being used by selectPrimary. 0.20 should be fine


## Example site:
Using this plugin with the grails framework  you are able to achieve this without all of the complications. Refer to this sample project which makes use of all of the examples below with some objects already pre-added to the sample projecet. Found here in this sample project:

https://github.com/vahidhedayati/ajaxdependancyselectexample


Use Europe/United Kingdom/London or Oxford for a full completed example within the above example project when loading it up.




## Taglibs provided:


	g:selectPrimary
	g:selectSecondary
	g:autocomplete
	g:autoCompletePrimary
	g:autoCompleteSecondary
	g:selectPrimaryNR
	g:selectSecondaryNR
	g:autoCompleteSecondaryNR
	g:selectController


### g:selectPrimary 
(relationship: fully dependent ) in conjunction with g:selectSecondary, refer to above notes on jquery requirements

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





### g:selectSecondary 
(relationship: fully dependent ) in conjunction with g:selectPrimary, refer to above notes on jquery requirements


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



###g:autocomplete
This is a simple auto complete tag lib that allows you to auto complete from a single table, refer to above notes on jquery & jquery-ui requirements

	<g:autocomplete 
	id="primarySearch" 	// Required - your objectID referred to by css has no importance
	name="myId"		// Required - your form field name
	domain='your.class.MyCountry'	// Required what domainClass to search
	searchField='name'		// Required the table field to search and display
	collectField='id'		// optional  the table field to use as value - if not set defaults to above
	value="${params.myId"
	max="10"			// Optional max records to show default is 10
	order="asc"			// optional default is asc
	required="false"		// optional add this if you wish to disable required set by default
	/>
	
###g:autoCompletePrimary


	<g:autoCompletePrimary id="primarySearch1"  
			name="NAMEOFcontinentName"
			domain='ajaxdependancyselectexample.MyContinent'
			searchField='continentName'
			collectField='id'
			setId="secondarySearch2"
			hidden='hidden3'
			value=''/>



###g:autoCompleteSecondary


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
			

###g:selectPrimaryNR



### no reference mapping
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


###g:selectSecondaryNR

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



###g:autoCompleteSecondaryNR

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
			
			

### g:selectController 
Controller action discovery, refer to above notes on jquery requirements


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














        
        



			






	
	





### Example no reference domain classes:

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





### Examples of primary to secondary then to seoncdaryNR

Here is the GSP making a nested call where an element has a no reference relationship, the gsp page in the example called norefselectionext.gsp goes back out of a NR relationship and calls Streets domain after borough to then load up a further relationsip


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
        
        
### Examples Auto Complete Secondary to secondaryNR



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
			
			
## Thanks to:



### Alidad 
Plugin expands on this idea: https://github.com/alidadasb/CountryCityAutoComplete

### Burt for cleaning it all up 


### 

