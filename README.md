ajaxdependancyselection 0.26
=======================


Ajaxdependancyselection is a Grails plugin which makes use of jquery to provide either select or auto complete form fields. This can be any combination of either fully dependent objects or full dependent as well as no reference bindings.

A common problem when it comes to making a website is having objects that are independant and when a user selects an option what to do next ? do you refresh the page to update the next set of values or look into some jquery/java scripts to auto update the next set of select option.


## Installation:
Add plugin Dependency :

	compile ":ajaxdependancyselection:0.26" 

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
	g:selectAutoComplete
	g:autocomplete
	g:autoCompletePrimary
	g:autoCompleteSecondary
	g:selectPrimaryNR
	g:selectSecondaryNR
	g:autoCompleteSecondaryNR
	g:selectController
	g:autoCompleteHeader
	g:selectPrimary -  custom controller/action sample

### g:selectPrimary 
###### (relationship: fully dependent ) in conjunction with g:selectSecondary
###### refer to above notes on jquery requirements

###### Fully dependent domainClasses:


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
	domain='yourApp.MyContinent'	// Required your primary domainClass full classpath.
	searchField='continentName'  		// Required - search for field called contintentName
	collectField='id' 			// Required : if not defaults to searchField value 
	domain2='yourApp.MyCountry'	// Required your secondary depenent domainClass full classpath.
	searchField2='countryName'		//  Required - search for field called contintentName
	collectField2='id'
	required="false"		// optional add this if you wish to disable required set by default
	value="${params.MyContinent}"	// your value if you are posting form back
	
	
	//Optional Filtering methods 0.27+ To enable filtering add the following
	filter='_ON'				// If you provide _ON it will provide a tick box when use selects and inputs terms 
								// it will then match return results to match their filter - reducing overall select items
								// Please note if you set this to "F" then it will not show the tick box and default all return results starting with F
    hidden="hiddenNew"<br>		//The hidden field name to store user search output - unsure what to be used for
	 
    
	
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

###### (relationship: fully dependent ) in conjunction with g:selectPrimary
###### refer to above notes on jquery requirements


This is a tag that can be used over and over again to go through nested situations, you can also use the selectSeondaryNR features to interact from selectPrimary or selectSecondary, this will be final part of document
Back to g:selectSecondary example:



    <g:selectSecondary 
	id="MyCountry" 			// Required - must be setId of previous selectPrimary or selectSecondary
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "ajaxSelectSecondary" (part of this plugin)
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
    
    
    
  	//Optional Additional input in order to get filtering to work on selectSecondary  
  	// This information will not work on this current class and will make things appear rather confusing
  	// Please refer to Various or example.gsp within the example site this has a working version of all of this
  	domain='ajaxdependancyselectexample.MyCity'  //Where this is current domainClass
    searchField='cityName'		//Where this is current SearchField for this domainClass
    collectField='id'			// where this is current return value for this domainClass
    filter='_ON'				//Needed to activate tick box
    filterbind='mycountry.id'	// The actual bindid of this table and your previous selection
    hidden="hidden6"			//This hidden field to set value
    prevId="MyCountry11"		//The previousID of your g:selectPrimary or g:selectSecondary - This must exist for all this to work 
    
  
    
    
        />





##g:selectAutoComplete

This is a new feature from 0.26+, it allows you to set up a select box from which auto complete values are generated depending on what user selects.



	<g:selectAutoComplete 
	id="MyContinent2"		// The id of your select Box 
	id2="MyCountry2" 		// The id + name of your autoComplete Box
	name="MyContinent2"		// The name of this select Field
    domain="yourApp.MyContinent"	//The name of your primary domainClass to list in select box 
    searchField="continentName"		// The table Field to query/display
    collectField="id"				// The field to collect in option box
 	primarybind="mycontinent.id"		// The binding field Name between SecondaryTables belongsTo and its .id
    domain2="yourApp.MyCountry"		// The secondary domain to query and produce auto completeFrom
    searchField2="countryName"		// The table field of this domain to display/query 
    collectField2="id"				// The id of this domain to collect
    
    appendValue=""					//Optional append a value to list given
    appendName="values updated"		// To go with the appendValue its description
    noSelection="['': 'Please choose Continent']" 	// No selection default value
    setId="MyCountry121"			// The Id to set it auto generates all this in code
    hidden='hidden3'				// hiddenField to update when doing selection
    hidden2='hidden4'				// hidden Field to update after autoComplete ended
    setId2="Something"				// Optional not messed with this - but be able to extend so autoComplete box can update some other call
    value=""/>
    
    
    

###g:autocomplete
This is a simple auto complete tag lib that allows you to auto complete from a single table, refer to above notes on jquery & jquery-ui requirements

	<g:autocomplete 
	id="primarySearch" 	// Required - your objectID referred to by css has no importance
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "autocomplete" (part of this plugin)
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


	<g:autoCompletePrimary id="primarySearch1"  	// Required - your objectID referred to by css has no importance
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "autocompletePrimaryAction" (part of this plugin)
	name="fieldName"			// Required - your objectID referred to by css has no importance
	domain='yourApp.MyContinent'// Required what domainClass to search	
	searchField='continentName'			// Required the table field to search and display
	collectField='id'				// optional  the table field to use as value - if not set defaults to above
	setId="secondarySearch2"			// Required - your next autocomplete id to update
	hidden='hidden3'				// Required - this is the hidden field where value is also stored
	value=''					// your value if you wish
	max="10"			// Optional max records to show default is 10
	order="asc"			// optional default is asc
	required="false"		// optional add this if you wish to disable required set by default
	/>



###g:autoCompleteSecondary



	<g:autoCompleteSecondary	
	id="secondarySearch2"		// Required - must be setId of previous autoCompletePrimary or Secondary	
	name="NAMEOFcountryName" 	// Required - your feild name
	domain='yourApp.MyCountry' 	// Required the next domain you are about to query
	primarybind='mycontinent.id' 		// Required the no relation reference from class 2
	hidden='hidden3' 			// Required the hidden field to collect from
	hidden2='hidden4' 		// Required the hidden field to set
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "autocompleteSecondaryAction" (part of this plugin)
	searchField='countryName' 	// Required - the table field to search and display
	collectField='id'		// optional - not setting will default to searchField value
	setId="secondarySearch3" 	// Required - The next autoComplete Id to set
	value=''/>
			

###g:selectPrimaryNR

###### No reference mapping
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


Example call:

	<g:selectPrimaryNR 
	id="selectZip" 			// Required - your objectID referred to by css has no importance
	name="selectZip"		// Required - your feild name
	domain='yourPackage.Postcode' 	// Required - the domainClass to query
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "ajaxSelectSecondaryNR" (part of this plugin)
	searchField='postcode' 		// Required the table field to search and display
	collectField='id'		// optional not setting will default to searchField value
	bindid="myarea"			// Required - refer to No reference note above
	domain2='Area' 			// Required the secondary domain to query
	searchField2='area' 		// Required the secondary table field to search
	collectField2='id'		// optional if not set will default to value of searchField2
	noSelection="['null': 'Please choose zipcode']"  // No selection text
	appendValue='*'			// Optional set a value to be appended to the list
	appendName='All Items'		// If you set appendValue then set the display name for it
	setId="PickupIDbinder" 		// Required - The next autoComplete Id to set
	/> 
	
	Pick up area: 
	<g:select 
	name="selectPickup" 
	id="PickupIDbinder"		// ID matches the setId of above call 
	optionKey="id" 
	optionValue="name" 
	from="[]" 			// from is [] and filled from above completed value
	noSelection="['null': 'Please choose zipcode']" />




###g:selectSecondaryNR


	<g:selectSecondaryNR 
	id="MyCity11" 		// Required - must be setId of previous selectPrimary or Secondary
	name="MyCity11"		// Required - your feild name
	domain='yourApp.MyCity'	// Required - the domainClass to query
	bindid="myborough"	// Required - refer to Fully dependent domainClasses notes above
	searchField='cityName'	// Required the table field to search and display
	collectField='id'	// optional not setting will default to searchField value
	domain2='yourApp.MyBorough'	// Required the secondary domain to query	
	searchField2='actualName'	// Required the secondary table field to search
	collectField2='id'		// optional if not set will default to value of searchField2
	controller = "something" 	// Optional - default "autoComplete" (part of this plugin)
	action = "something" 		// Optional - default "ajaxSelectSecondaryNR" (part of this plugin)
	noSelection="['': 'Please choose City']"	// No selection text	
	setId="MyBorough11"		// Required - The next autoComplete Id to set or last call to normal select
	appendValue='*'			// Optional set a value to be appended to the list
	appendName='All Items'		// If you set appendValue then set the display name for it
	value=''
	/>




###g:autoCompleteSecondaryNR
	<input type=hidden id="hidden5" name="cityId" value=""/>
	<g:autoCompleteSecondaryNR 
	id="secondarySearch4" 		// Required - must be setId of previous autoCompletePrimary or Secondary
	name="NAMEOFcityName" 		// Required - your feild name
	domain='yourApp.MyCity' 	// Required - the domainClass to query
	domain2='yourApp.MyBorough' 	// Required the secondary domain to query
	primarybind='myborough' 	// Required - refer to No reference note above
	hidden='hidden5' 		// Required the hidden field to collect from
	hidden2='hidden6' 		// Required the hidden field to set
	searchField='actualName' 	// Required the table field to search and display
	collectField='id' 		// optional not setting will default to searchField value
	
	value=''/>
	<input type=hidden id="hidden6" name="BoroughID" value=""/>
			



### g:selectController 
####### Controller action discovery, refer to above notes on jquery requirements


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



## g:autoCompleteHeader
####### Loads in the ajaxLoader if your site is not already doing it
	<g:autoCompleteHeader />


## g:selectPrimary - Custom
####### Customising your own calls:

This is covered in the sample project labelled as custom example. Create your own controller which is set to do a custom verification:

	def selectCountries() {
		if (params.id) {
			println params
			String continentName = params.searchField
			Long continentId = params.id as Long
			MyContinent continent = MyContinent.get(continentId)

			/* Either this method or below method which is much shorter
			def primarySelectList = []
			MyCountry.findAllByMycontinentAndCountryNameLike(continent, "F%").each {
				def primaryMap = [:]
				primaryMap.put('id', it.id)
				primaryMap.put('name', it.countryName)
				primarySelectList.add(primaryMap)
			}
			render primarySelectList as JSON
			*/
			
			// Shorter method
			def countries=MyCountry.findAllByMycontinentAndCountryNameLike(continent, "F%")
			def results = countries.collect {[	'id': it.id, 'name': it.countryName ]}.unique()
			render results as JSON
		}
	}


Create a call for this: 

	<g:selectPrimary id="MyContinent2" name="MyContinent2"
	domain="ajaxdependancyselectexample.MyContinent"
	searchField="continentName"
	collectField="id"

	controller="MyContinent"
	action="selectCountries"

	domain2="ajaxdependancyselectexample.MyCountry"
	bindid="mycontinent.id"
	searchField2="countryName"
	appendValue=""
	appendName="values updated"
	collectField2="id"
	noSelection="['': 'Please choose Continent']" 
	setId="MyCountry121"
	value=""/>
    
    
    
	 <g:select name="MyCountry" id="MyCountry121"  from="[]" required="required" noSelection="['': 'Please choose Continent']" />
	 
I will be updating this so that less input is required when custom action controller is defined.


	 
    
##### Examples below




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
This non working gsp has some examples: https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/noRefAutoComplete.gsp

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

This non working gsp has some examples: https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/noRefAutoComplete.gsp

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



##### Alidad 
Plugin expands on this idea: https://github.com/alidadasb/CountryCityAutoComplete

##### Burt for cleaning it all up 


##### domurtag for identifying and helping improve plugin

