ajaxdependancyselection 0.36
=======================


Ajaxdependancyselection is a Grails plugin which makes use of jquery to provide either select or auto complete form fields. This can be any combination of either fully dependent objects or full dependent as well as no reference bindings.

A common problem when it comes to making a website is having objects that are independant and when a user selects an option what to do next ? do you refresh the page to update the next set of values or look into some jquery/java scripts to auto update the next set of select option.


## Installation:
Add plugin Dependency :

	compile ":ajaxdependancyselection:0.36" 

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
	
```
0.36 -	minor bug in selectController '' used instead of "" for attrs.id so id was being shown as attrs.id
 
0.35 - 	Made values updated default values optional - if appendValue is given in g:select then it will set appendName to default value
 	
0.34 - 	Now supporting multiple dependency calls, in a given g:select you can now declare upto 5 depended object bound to a
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

https://github.com/vahidhedayati/ajaxdependancyselectexample

##### For further documentation and examples visit https://github.com/vahidhedayati/ajaxdependancyselection/wiki
###### I have included the text and html samples from all of the examples in the example site.

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
	g:selectPrimary/Secondary Filtering
	g:selectPrimary/Secondary support for domain3 domain4 domain5 domain6 domain7 and domain8 which can all be added: 
								-- to any selectPrimary or Secondary call, each domain is a bound object
								-- This means if a Country has many cities and many mayors upon country update relevant cities and mayors are loaded
								-- extend this now across upto domain8 for each object initiall called
								Please read below on the how to and refer to the example site for a very basic demo.
								

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

```gsp
   <g:selectPrimary id="MyContinent1" name="MyContinent1"
        domain='ajaxdependancyselectexample.MyCountry'
        searchField='countryName'
        collectField='id'
        
        domain2='ajaxdependancyselectexample.MyCity'
        bindid="mycountry.id"
        searchField2='cityName'
        collectField2='id'

        noSelection="['': 'Please choose Country']" 
        setId="MyCity1"
        value=''/>
```        
[Entire input with explaination](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:selectPrimary---entire-input-values)




Now this can be either passed toa normal select or to a  g:selectSecondary where if if you have further dependency you wish to interogate.

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





### g:selectSecondary 

###### (relationship: fully dependent ) in conjunction with g:selectPrimary
###### refer to above notes on jquery requirements


This is a tag that can be used over and over again to go through nested situations, you can also use the selectSecondaryNR features to interact from selectPrimary or selectSecondary, this will be final part of document
Back to g:selectSecondary example:
```gsp
<g:selectSecondary id="MyCountry11" name="MyCountry11"
    domain2='ajaxdependancyselectexample.MyCity'
    bindid="mycountry.id"
    searchField2='cityName'
    collectField2='id'
    
    
     appendValue=''
     appendName='Updated'
    
    
    noSelection="['': 'Please choose Continent']" 
    setId="MyCity11"
    value="${params.MyCountry11}"/>
```
[g:selectSecondary entire input and explaination](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:selectSecondary-entire-input)



##g:selectAutoComplete

This is a new feature from 0.26+, it allows you to set up a select box from which auto complete values are generated depending on what user selects.
```gsp
<g:selectAutoComplete 
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

    
    

###g:autocomplete
This is a simple auto complete tag lib that allows you to auto complete from a single table, refer to above notes on jquery & jquery-ui requirements
```gsp
<g:autocomplete id="primarySearch" name="myId"
domain='ajaxdependancyselectexample.MyCountry'
searchField='countryName'
collectField='id'
value=''/>
<input type=submit value=go> </form>
```
[all g:autocomplete calls](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/autoComplete-full-input)

	
###g:autoCompletePrimary
```gsp
<g:autoCompletePrimary id="primarySearch1"  
name="NAMEOFcontinentName"
domain='ajaxdependancyselectexample.MyContinent'
searchField='continentName'
collectField='id'
setId="secondarySearch2"
hidden='hidden3'
value=''/>
```

[autoCompletePrimay explained](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/autoCompletePrimary---full-detail)


###g:autoCompleteSecondary
```gsp
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
```
[autoCompleteSecondary explained](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:autoCompleteSecondary-full-detail)

			

###g:selectPrimaryNR

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


This you would use g:selectPrimaryNR the bindid is secondarydomain the field highlighted in bold above as the bindid


Notice in the PrimaryNR the bindid is the primary hasMany mapping and has no .id


Example call:

[g:selectPrimaryNR-Full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:selectPrimaryNR-Full-details)



###g:selectSecondaryNR


[g:selectSecondaryNR--full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:selectSecondaryNR--full-details)




###g:autoCompleteSecondaryNR
[g:autoCompleteSecondaryNR-full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:autoCompleteSecondaryNR-full-details)
			



### g:selectController 
###### Controller action discovery, refer to above notes on jquery requirements


This will display all your controllers then let you Choose available actions of this controller:
https://github.com/vahidhedayati/ajaxdependancyselectexample/blob/master/grails-app/views/myContinent/example.gsp 
Line 210 onwards has an example.Typically maybe used for identifying controller name and its available actions to store against something maybe for your own custom authentication control that binds all this with something else.
 
Here are the values explained:


[g:selectController-full-details](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/g:selectController-full-details)

This now gets passed to a standard select call where it has an id of "ControllerActions":


[gccontroller-actions-or-reciever-selection-box](https://github.com/vahidhedayati/ajaxdependancyselection/wiki/gccontroller-actions-or-reciever-selection-box)
	
The from on this is set to [] which gets filled in by g:selectController setId return call.



## g:autoCompleteHeader
###### Loads in the ajaxLoader if your site is not already doing it
	<g:autoCompleteHeader />


## g:selectPrimary - Custom
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
    
    
    
<g:select name="MyCountry" id="MyCountry121"  from="[]" required="required" 
noSelection="['': 'Please choose Continent']" />
```

I will be updating this so that less input is required when custom action controller is defined.

## g:selectPrimary/Secondary Filtering
###### Filtering can be hard coded per select call or can be left for the user to filter output of a selectbox. this can be either useful if you only want to show specific results from selection for specific user or possibly when you wish user to speed up selection process and let them control the output of the selectbox.

Most basic example is a hard coded version: /ajaxdependancyselectionexample/myContinent/norefselectSecondaryFilteringFixed.gsp and or https://github.com/vahidhedayati/ajaxdependancyselection/wiki/Nested-Selection-from-fully-fixed-search-all-that-way-including-a-secondaryNR

This is where you start with your <g:selectPrimary  filter="something" filter2="another"
This will now filter results for primary matching against something and the next secondary call to filter against another

On your next <g:selectSeconday you simply call filter2="somethingelse"

This will now pass this filter to next object being completed and all the way to last standard call which will end in standard <g:select box but will still be filtered.


The other way is to enable filtering and let user control the filter, to do this simply switch filter="_ON" in each <g:selectPrimary/Secondary call

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





## g:selectPrimary/Secondary Multi domainClass loading:

###### Feature since 0.34 

This allows you to load up domain3, domain4, domain5, domain6, domain7, domain8 per Primary or Secondary Call. 
Each of them can then have the same nesting meaning some wild dependencies can be created and complex selecting provided.

The how to:

```gsp
<g:selectPrimary id="MyDepartments" name="MyDepartments"
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

<g:selectPrimary id="MyDepartments141" name="MyDepartments141"
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



<g:selectSecondary id="computersId141" name="computersId141"

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


### END OF MAIN DOCUMENTATION
### --------------------------
	 
    
##### Further Examples below




### Example no reference domain classes:

Here are two domain classes with a no reference set up and require g:selectSecondaryNR feature: 
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

