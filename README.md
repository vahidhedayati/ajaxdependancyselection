ajaxdependancyselection
=======================


Ajaxdependancyselection is a Grails plugin which makes use of jquery to provide either select or auto complete form fields. This can be any combination of either fully dependent objects or full dependent as well as no reference bindings.

A common problem when it comes to making a website is having objects that are independant and when a user selects an option what to do next ? do you refresh the page to update the next set of values or look into some jquery/java scripts to auto update the next set of select option.


## Installation:
Add plugin Dependency :

        compile ":ajaxdependancyselection:0.45"
[codebase for grails 2.X](https://github.com/vahidhedayati/ajaxdependancyselection/tree/grails2)

Dependency (Grails 3.X) :
```
    compile "org.grails.plugins:ajaxdependancyselection:1.0"
```

[codebase for grails 2.X](https://github.com/vahidhedayati/ajaxdependancyselection/)


#### Grails 3: https://bintray.com/artifact/download/vahid/maven/ajaxdependancyselection-1.0.jar


### 0.43 + Security Configuration:

In order to ensure you only allow this plugin to search desired domainClasses as well as restricted to only search/collect fields that will be used within the plugin calls. Simply add something like this below to your Config.groovy. Covering the full domainClass and its packaging convention the search/collect fields you wish to call from within the plugin calls. This now means anything outside of this scope should fail if anyone attempts to break out of the plugin..

```groovy
ads {
	security = "enabled"
	whitelist = [
			[dc:'ajaxdependancyselectexample.MyContinent', collect:'id', search:'continentName'],
			[dc:'ajaxdependancyselectexample.MyCountry', collect:'id', search:'countryName'],
			[dc:'ajaxdependancyselectexample.MyCity', collect:'id', search:'cityName']
	]
}
```


## Autocomplete requirements:	
 To use the autocomplete features simply enable this taglib call:


```gsp
<ads:loadjui/>
```
Within your layout/main.gsp or a gsp page that uses autoComplete and requires jquery-ui

You can also add:
```gsp
loadjui='enable'
```
To any ads:autoComplete tags and you should only need to call it once on a given page. So on one of the ads:autoComplete calls should suffice.


##### Websocket multi-select + auto complete(html5) 

[boselecta](https://github.com/vahidhedayati/grails-boselecta-plugin), no jquery and no jquery-ui used. Secure information flow. downside - client needs to have websocket supported browser.



## version info:
	
```
0.45/1.0 - Fixed jquey-ui image issues, fixed controller action selection. hardcoded jquery removed from  _selectJs.gsp
0.44 - a println left in security - as part of upgrade to grails3 (now working) all code has been reviewed and a major tidy up lock down carried out.

0.43 - Security configuration added, you can now define which domainClasses are searchable and what fields can be searched. I can see the initial primary selection still works but this is due to entire list being returned - beyond this nothing else will work if security enabled and locked down. Review instructions on security at the very top of this README.

0.42-SNAPSHOT3 & SNAPSHOT2 : https://github.com/vahidhedayati/ajaxdependancyselection/issues/9

0.42-SNAPSHOT1 :  https://github.com/vahidhedayati/ajaxdependancyselection/issues/8

0.42-SNAPSHOT: https://github.com/vahidhedayati/ajaxdependancyselection/issues/7


0.42 - 	https://github.com/vahidhedayati/ajaxdependancyselection/issues/7#issuecomment-55927174 as per request - primaryList
		can now be provided via main call i.e. controller providing the list to the taglib, domain can be set to 
		an invalid value. Please refer to example6.gsp within ajaxdependancyselectexample project.
		
		
0.41 - 	multiple added to all select calls so multiple=true or false can be defined (optional)
		multiple="multiple" multiple="false" multiple="true"
		fixed issue for require required - both now accepted.
		
0.40 - 	0.39 was incomplete - a few issues with primary/secondary selection - EDIT mode. should now be fixed.

0.39 -	secondaryValue='2' attribute added to selectPrimary/selectSecondary. Related to this question: 
		http://stackoverflow.com/questions/23220436/ajax-dependency-selection-plugin/24745037
		

0.38 - 	appendName appendValue values not being passed from tagLib to services..

0.37 -	selectJs files updated logic added to if autocomp is set in 
		any ads:selectPrimary/Secondary then it will look for autocompleteSecondary 
		tag to fullfill autocomplete refer to selectauto3.gsp
		
0.36 -	minor bug in selectController '' used instead of "" for attrs.id so id was being shown as attrs.id
 
0.35 - 	Made values updated default values optional - if appendValue is given in ads:select then it will set appendName to default value
 	
0.34 - 	Now supporting multiple dependency calls, in a given ads:select you can now declare upto 5 depended object bound to a
 		primary or secondary selection.
 		
0.33 - 	Too minor to mention
0.32 - 	Further tidyup of duplicate javascripts, cleaner calls made within taglib and reduction of duplicate gsp pages, issue
		with return results in controller displayed undefined when no results found - fixed.
		
0.31 - 	Tidyup of javascript calls within taglib, fixed secondaryNR filter2 issues - filtering now working across all select functions

0.30 - 	Issues with filtering and end box with no filter not displaying values - tidyup

0.29 - 	FilterDisplay and filterType intro - user override of action controllers for filtering js and controller/actions added

0.28 - 	Tidy up - and further work on specific filtering for selectSecondary

0.27 - 	Filtering of selectPrimary

0.26 - 	Added selectAutoComplete

0.23 - 	Removed null from values updated (default additional selection field added when values update), this now means user has to 
		still choose this value
		 
0.22 - Added required by default set to required for all taglib calls. 

0.19 - Broken build - there were issues with the tidyup I did with selectSecondary, totally forgot it was being used by 
			selectPrimary. 0.20 should be fine

```

## Example site:

Using this plugin with the grails framework  you are able to achieve this without all of the complications. Refer to this sample project which makes use of all of the examples below with some objects already pre-added to the sample projecet. Found here in this sample project:

[Example grails project grails 2.4.2](https://github.com/vahidhedayati/ajaxdependancyselectexample)

[Example grails project grails 2.5](https://github.com/vahidhedayati/testads5)

[Example grails project grails 2.4.4](https://github.com/vahidhedayati/testad)

[Example grails project grails 3.0.1](https://github.com/vahidhedayati/testads)

 for  [grails ajaxdependancyselection plugin](http://grails.org/plugin/ajaxdependancyselection).[Issues can be reported here](https://github.com/vahidhedayati/ajaxdependancyselection/issues). For further documentation and examples, check out the [wiki](https://github.com/vahidhedayati/ajaxdependancyselection/wiki)

I have included the text and html samples from all of the examples in the example site. Use Europe/United Kingdom/London or Oxford for a full completed example within the above example project when loading it up.


## General note over naming conventions.
In the examples provided domainClasses has been defined as countryName cityName and so forth, this was really an example to show it can be any naming convention besides the standard id, name column naming convention. Feel free to name things as you desire i.e. this is not a requirement of the plugin.


## Taglibs provided:


	ads:selectPrimary
	ads:selectSecondary
	ads:selectAutoComplete
	ads:autocomplete
	ads:autoCompletePrimary
	ads:autoCompleteSecondary
	ads:selectPrimaryNR
	ads:selectSecondaryNR
	ads:autoCompleteSecondaryNR
	ads:selectController
	ads:autoCompleteHeader
	
	ads:selectPrimary -  custom controller/action sample
	ads:selectPrimary/Secondary Filtering
	ads:selectPrimary/Secondary support for domain3 domain4 domain5 domain6 domain7 and domain8 which can all be added: 
								-- to any selectPrimary or Secondary call, each domain is a bound object
								-- This means if a Country has many cities and many mayors upon country update relevant cities and mayors are loaded
								-- extend this now across upto domain8 for each object initiall called
								Please read below on the how to and refer to the example site for a very basic demo.
								

### ads:selectPrimary 
###### (relationship: fully dependent ) in conjunction with ads:selectSecondary
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

This you would use ads:selectPrimary the bindid is primarydomain.id the field highlighted in bold above as the bindid

```gsp
   <ads:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='ajaxdependancyselectexample.MyCountry'
        searchField='countryName'
        collectField='id'
        
        domain2='ajaxdependancyselectexample.MyCity'
        bindid="mycountry.id"
        searchField2='cityName'
        collectField2='id'

		 multiple='true'
		 
        noSelection="['': 'Please choose Country']" 
        setId="MyCity1"
        value=''/>
```        
[Entire input with explaination](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:selectPrimary---entire-input-values)




Now this can be either passed toa normal select or to a  ads:selectSecondary where if if you have further dependency you wish to interogate.

So in the case of Country city, the above is fine to call to a city select area that is a simple call like this:
```gsp
	<g:select  
	id="MyCountry"  
	name="MyCity1"
	optionKey="id"
	optionValue="name" 
	from="[]" 
	required="required" 
	noSelection="['': 'Please choose Country']" />
        
```        

If however this was a multi nested dependency situation you could proceed to selectSecondary where this will update either a final call just like per above or call selectSecondary over and over again until nesting is completed with final select as above. again ensure you are making proper calls your optionKey and Value needs to match your own table naming convention or in simple terms  

	<option value=YourDefindOptionKey>YourDefinedOptionValue</option>

is what is represented on the html end





### ads:selectSecondary 

###### (relationship: fully dependent ) in conjunction with ads:selectPrimary
###### refer to above notes on jquery requirements


This is a tag that can be used over and over again to go through nested situations, you can also use the selectSecondaryNR features to interact from selectPrimary or selectSecondary, this will be final part of document
Back to ads:selectSecondary example:
```gsp
<ads:selectSecondary id="MyCountry11" name="MyCountry11"
    domain2='ajaxdependancyselectexample.MyCity'
    bindid="mycountry.id"
    searchField2='cityName'
    collectField2='id'
    
    
     appendValue=''
     appendName='Updated'
    
    
    multiple='true'
    
    noSelection="['': 'Please choose Continent']" 
    setId="MyCity11"
    value="${params.MyCountry11}"/>
```
[ads:selectSecondary entire input and explaination](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:selectSecondary-entire-input)



##ads:selectAutoComplete

This is a new feature from 0.26+, it allows you to set up a select box from which auto complete values are generated depending on what user selects.
```gsp
<ads:selectAutoComplete 
    id="MyContinent2" 
    id2="MyCountry2" 
    name="MyContinent2"
    domain="ajaxdependancyselectexample.MyContinent"
    searchField="continentName"
    collectField="id"
    primarybind="mycontinent.id"
    domain2="ajaxdependancyselectexample.MyCountry"
    searchField2="countryName"
    collectField2="id"
    
    appendValue=""
    appendName="values updated"
    noSelection="['': 'Please choose Continent']" 
    setId="MyCountry121"
    hidden='hidden3'
    hidden2='hidden4'
    value=""/>
```

[Explaination/example found here](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/From-Select-to-AutoComplete)

    
    
###ads:selectPrimary/Secondary to ads:autoCompleteSecondary
If you want to pass from selections to auto complete then from 0.37+ release you should be able to pass parameter autocomp="1" to any of the ads:select methods followed by a call to ads:autocompleteSecondary.. [from-selection-to-autocomplete---how-to](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/from-selection-to-autocomplete---how-to)
    

###ads:autocomplete
This is a simple auto complete tag lib that allows you to auto complete from a single table, refer to above notes on jquery & jquery-ui requirements
```gsp
<ads:autocomplete id="primarySearch" name="myId"
domain='ajaxdependancyselectexample.MyCountry'
searchField='countryName'
collectField='id'
value=''/>
<input type=submit value=go> </form>
```
[all ads:autocomplete calls](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/autoComplete-full-input)

	
###ads:autoCompletePrimary
```gsp
<ads:autoCompletePrimary id="primarySearch1"  
name="NAMEOFcontinentName"
domain='ajaxdependancyselectexample.MyContinent'
searchField='continentName'
collectField='id'

multiple='false'

setId="secondarySearch2"
hidden='hidden3'
value=''/>
```

[autoCompletePrimay explained](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/autoCompletePrimary---full-detail)


###ads:autoCompleteSecondary
```gsp
<ads:autoCompleteSecondary id="secondarySearch2" 
name="NAMEOFcountryName" 
domain='ajaxdependancyselectexample.MyCountry' 
primarybind='mycontinent.id' 
hidden='hidden3' 
hidden2='hidden4' 
multiple='false'
searchField='countryName' 
collectField='id'
setId="secondarySearch3" 
value=''/>
```
[autoCompleteSecondary explained](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:autoCompleteSecondary-full-detail)

			

###ads:selectPrimaryNR

###### No reference mapping
```groovy
	class PrimaryDomain {
 		String name
 		static hasMany = [ secondarydomain: SecondaryDomain ]
	}

	class SecondaryDomain {
 		String name
		static belongsTo = [PrimaryDomain ]
	}
```

Now within your call the bindid would be:	

	bindid="secondarydomain"


This you would use ads:selectPrimaryNR the bindid is secondarydomain the field highlighted in bold above as the bindid


Notice in the PrimaryNR the bindid is the primary hasMany mapping and has no .id


Example call:

[ads:selectPrimaryNR-Full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:selectPrimaryNR-Full-details)



###ads:selectSecondaryNR


[ads:selectSecondaryNR--full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:selectSecondaryNR--full-details)




###ads:autoCompleteSecondaryNR
[ads:autoCompleteSecondaryNR-full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:autoCompleteSecondaryNR-full-details)
			



### ads:selectController 
###### Controller action discovery, refer to above notes on jquery requirements


This will display all your controllers then let you Choose available actions of this controller:
https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/example.gsp 
Line 210 onwards has an example.Typically maybe used for identifying controller name and its available actions to store against something maybe for your own custom authentication control that binds all this with something else.
 
Here are the values explained:


[ads:selectController-full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/ads:selectController-full-details)

This now gets passed to a standard select call where it has an id of "ControllerActions":


[gccontroller-actions-or-reciever-selection-box](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/gccontroller-actions-or-reciever-selection-box)
	
The from on this is set to [] which gets filled in by ads:selectController setId return call.



## ads:autoCompleteHeader
###### Loads in the ajaxLoader if your site is not already doing it
	<ads:autoCompleteHeader />


## ads:selectPrimary - Custom
###### Customising your own calls:

This is covered in the sample project labelled as custom example. Create your own controller which is set to do a custom verification:
```groovy
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

```
Create a call for this: 
```gsp
<ads:selectPrimary id="MyContinent2" name="MyContinent2"
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
    
    
    
<g:select name="MyCountry" id="MyCountry121"  from="[]" required="required" 
noSelection="['': 'Please choose Continent']" />
```

I will be updating this so that less input is required when custom action controller is defined.

## ads:selectPrimary/Secondary Filtering
###### Filtering can be hard coded per select call or can be left for the user to filter output of a selectbox. this can be either useful if you only want to show specific results from selection for specific user or possibly when you wish user to speed up selection process and let them control the output of the selectbox.

Most basic example is a hard coded version: /ajaxdependancyselectionexample/myContinent/norefselectSecondaryFilteringFixed.gsp and or https://github.com/vahidhedayati/ajaxdependancyselection/wiki/Nested-Selection-from-fully-fixed-search-all-that-way-including-a-secondaryNR

This is where you start with your <ads:selectPrimary  filter="something" filter2="another"
This will now filter results for primary matching against something and the next secondary call to filter against another

On your next <ads:selectSeconday you simply call filter2="somethingelse"

This will now pass this filter to next object being completed and all the way to last standard call which will end in standard <g:select box but will still be filtered.


The other way is to enable filtering and let user control the filter, to do this simply switch filter="_ON" in each <ads:selectPrimary/Secondary call

In primary it is just as simple as switching it on, within secondary calls it gets a little more complex.

###### Primary & PrimaryNR in full:
	
To enable user driver filtering - add this
```gsp	
filter="_ON"
hidden="hiddenFieldForPrimary"
```
To enable hard coded filtering add this:
```gsp
filter="SOMETHING"
```

With either a fixed filter above or dynamic user controlled followed by: Only needed if you want to define you next select filter physically
```gsp		
filter2="B"					
```		
		
Optional stuff:	
put this in so with filtering no results are shown if user does not match or as it starts and only show matching results in select to what user inputs
```gsp
filterDisplay="none" 			
```		
							 	
								 	
Values are : S E A - Start of Record End of Record or by default if not defined Any part of record i.e. wild card search.
```gsp
filterType ="A" 			
```     

Override optional stuff:

if you wish to point the filtering actions to another location at the moment default autoComplete controller from plugin
```gsp
filterController="YourController" 	
```   

if you wish to define your own action for the above controller call
```gsp
filteraction="your action"
```   

Default is loadFilterWord this loads up the user input box for end user filtering
```gsp
filteraction="loadFilterWord"

```   
The action that returns the primary list the default value is as defined
```gsp
filteraction2="returnPrimarySearch"

```  


Config.groovy overrides:
```groovy
ajaxdependancyselection.filterField='/autoComplete/filterField'
ajaxdependancyselection.selectBasicJS='/autoComplete/selectJs'
```
     		 				
    	
   
###### Secondary & Secondary NR in full:
 	
    

To enable user driver filtering - add this
```gsp	
filter="_ON"
hidden="hiddenFieldForPrimary"
```

To enable hard coded filtering for the next field add this: Only needed if you want to define you next select filter physically	
```gsp
filter2="SOMETHING"							
```
		
		
Optional stuff: put this in so with filtering no results are shown if user does not match or as it starts and only show matching results in
```gsp		
filterDisplay="none"		
```

select to what user inputs: Values are : S E A - Start of Record End of Record or by default if not defined Any part of record i.e. wild card search.
```gsp
filterType ="A" 			
```     	


### Override optional stuff:

If you wish to point the filtering actions to another location at the moment default autoComplete controller from plugin
```gsp
filterController="YourController" 	
```

If you wish to define your own action for the above controller call 	
```gsp
filteraction="your action"								
```

Default is loadFilterWord this loads up the user input box for end user filtering
```gsp
filteraction="loadFilterWord2"		
```

The action that returns the secondary search whilst filter results
```gsp
filteraction2="secondarySearch"		
```

Config.groovy overrides:
```groovy
ajaxdependancyselection.filterField='/autoComplete/filterField'
ajaxdependancyselection.selectSecondaryJsFilter='/autoComplete/selectJs1'  {Secondary default}
ajaxdependancyselection.selectSecondaryJsFilter='/autoComplete/selectJsNr1'  {SecondaryNR default}
```

[For other examples of filtering, check out](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/Select-Box-Filtering-results-userdefined-or-fixed-filtering) or the filtering example page within the example project site.





## ads:selectPrimary/Secondary Multi domainClass loading:

###### Feature since 0.34 

This allows you to load up domain3, domain4, domain5, domain6, domain7, domain8 per Primary or Secondary Call. 
Each of them can then have the same nesting meaning some wild dependencies can be created and complex selecting provided.

The how to:

```gsp
<ads:selectPrimary id="MyDepartments" name="MyDepartments"
domain='ajaxdependancyselectexample.Departments'
searchField='name'
collectField='id'
noSelection="['': 'Please choose Department']" 

domain2='ajaxdependancyselectexample.Employee'
bindid="department.id"
searchField2='name'
collectField2='id'
setId="employeeID"


domain3='ajaxdependancyselectexample.Documents'
bindid3="department.id"
searchField3='name'
collectField3='id'
setId3="documentsId"
filter3="L"

value=''/>

<g:select name="employee" id="employeeID" optionKey="id" optionValue="name" from="[]" 
required="required" noSelection="['': 'Please choose department']" />

<g:select name="document" id="documentsId" optionKey="id" optionValue="name" from="[]" 
required="required" noSelection="['': 'Please choose department']" />
```



The Primary block or Secondary Block simply create a new element that kind of follows the domain2 object 
but in short everything ends with the correct numbering sequence. As you can see the bindings employeeID and documentsId got updated with relevant values that were depedent upong primary selection.



Take a look at the multidomain example in the wiki or within the example project where it covers:
##### Example1: Simple multi domain dependency call one object two dependencies with filtering on one of the calls
##### Example2: Simple multi domain dependency call one object two dependencies with no filtering
##### Example3: Multi domain dependency call to domain3 and domain4
##### Example4: Multi domain dependency call to domain3 and domain4 with domain4 then having its own multi depenency relatiobship

This last example shown here:

		
Please note only the first computer from each initial department selected has any further values.

```gsp
<form method=post action=example5>

<ads:selectPrimary id="MyDepartments141" name="MyDepartments141"
domain='ajaxdependancyselectexample.Departments'
searchField='name'
collectField='id'
noSelection="['': 'Please choose Department']" 

domain2='ajaxdependancyselectexample.Employee'
bindid="department.id"
searchField2='name'
collectField2='id'
setId="employeeID141"

domain3='ajaxdependancyselectexample.Documents'
bindid3="department.id"
searchField3='name'
collectField3='id'
setId3="documentsId141"

domain4='ajaxdependancyselectexample.Computers'
bindid4="department.id"
searchField4='pcName'
collectField4='id'
setId4="computersId141"
value=''/>

<g:select name="employee" id="employeeID141" optionKey="id" optionValue="name" from="[]" 
required="required" noSelection="['': 'Please choose department']" />
<g:select name="document" id="documentsId141" optionKey="id" optionValue="name" from="[]" 
required="required" noSelection="['': 'Please choose department']" />



<ads:selectSecondary id="computersId141" name="computersId141"

domain2='ajaxdependancyselectexample.Os'
bindid="computers.id"
searchField2='osName'
collectField2='id'
setId="Os13"

domain3='ajaxdependancyselectexample.Users'
bindid3="computers.id"
searchField3='userName'
collectField3='id'
setId3="userId13"

appendValue=''
appendName='Updated'

noSelection="['': 'Please choose Department']" 

value="${params.computersId141}"/>


<g:select name="Os" id="Os13" optionKey="id" optionValue="pcName" from="[]" 
required="required" noSelection="['': 'Please choose Computer']" />

<g:select name="users" id="userId13" optionKey="id" optionValue="userName" 
from="[]" required="required" noSelection="['': 'Please choose computer']" />

<input type=submit value=go>
</form>
		
```


### 0.40+ Edit an existing page using ajaxdependancyselection.
This may be rather difficult but if the values are then stored on DB and you wish to further edit the defined stored values you now can from version 0.40+.
Please refer to https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/testedit.gsp. This page has fixed values defined with an additional variable defined within selectPrimary/Secondary:

```gsp
value='2'
secondaryValue='2'
```

Although in the given gsp example page the values are hardcoded, in real life those be the values returned from db

```gsp
value="${mydomainClass.primaryValue}"
secondaryValue="${mydomainClass.secondaryValue}"
```
So in selectPrimary/selectSecondary you define both the value and secondaryValue and the plugin will assign them to the relevant boxes. please follow the example to get an idea



### END OF MAIN DOCUMENTATION
### --------------------------
	 
    
##### Further Examples below




### Example no reference domain classes:

Here are two domain classes with a no reference set up and require ads:selectSecondaryNR feature: 
```groovy
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

```


### Examples of primary to secondary then to seoncdaryNR
This non working gsp has some examples: https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/noRefAutoComplete.gsp

Here is the GSP making a nested call where an element has a no reference relationship, the gsp page in the example called norefselectionext.gsp goes back out of a NR relationship and calls Streets domain after borough to then load up a further relationsip

[select-primary-to-secondary-then-to-seoncdaryNR-nested-call](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/select-primary-to-secondary-then-to-seoncdaryNR-nested-call)	
        
        
### Examples Auto Complete Secondary to secondaryNR

[This non working gsp has some examples](https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/noRefAutoComplete.gsp)

[autocomplete-primary-to-secondary-to-secondaryNR-nested-call](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/autocomplete-primary-to-secondary-to-secondaryNR-nested-call)
			
			
## Thanks for contributions from 

Alidad's [CountryCityAutoComplete project](https://github.com/alidadasb/CountryCityAutoComplete) which inspired this plugin.

Burt Beckwith for helping clean up the code.

Domurtag for identifying and helping improve plugin documentation and features.

