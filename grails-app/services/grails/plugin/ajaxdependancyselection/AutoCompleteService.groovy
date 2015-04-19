package grails.plugin.ajaxdependancyselection

import grails.converters.JSON
import grails.web.Action
import java.lang.reflect.Method
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

import java.lang.reflect.Method

class AutoCompleteService {

    static transactional = false

    def grailsApplication

    def autocomplete(String domain, String searchField, String collectField,
                     String term, String max, String orderBy ) {

        if (verifySecurity(domain, searchField, collectField)) {
            def domainClass = grailsApplication.getDomainClass(domain).clazz
            def results = domainClass?.createCriteria()?.list {
                ilike searchField, term + '%'
                maxResults(Integer.parseInt(max,10))
                order(searchField, orderBy)
            }
            if (results.size()< 5){
                results = domainClass?.createCriteria()?.list {
                    ilike searchField, "%${term}%"
                    maxResults(Integer.parseInt(max,10))
                    order(searchField, orderBy)
                }
            }
            results = results?.collect {[label:it."${collectField}"] }?.unique()
            return results as JSON
        }
    }

    def autocompletePrimaryAction(String domain, String searchField, String collectField,
                                  String term, String max, String orderBy) {

        ArrayList primarySelectList = []

        if (verifySecurity(domain, searchField, collectField)) {
            def domainClass = grailsApplication.getDomainClass(domain).clazz
            def query = {
                or{
                    ilike searchField, term + '%'
                }
                projections {
                    property(collectField)
                    property(searchField)
                }
                maxResults(Integer.parseInt(max,10))
                order(searchField, orderBy)
            }
            def query1 = {
                or{
                    ilike searchField, "%${term}%"
                }
                projections {
                    property(collectField)
                    property(searchField)
                }
                maxResults(Integer.parseInt(max,10))
                order(searchField, orderBy)
            }
            def results = domainClass?.createCriteria()?.list(query)
            if (results.size()< 5){
                results = domainClass?.createCriteria()?.list(query1)
            }
            if (results) {
                primarySelectList = resultSet1(results)
            }
        }
        return primarySelectList as JSON
    }


    def autocompleteSecondaryAction(String domain, String searchField, String collectField, String term,
                                    String max, String orderBy, String primarybind, Long primaryid) {

        ArrayList primarySelectList = []

        if (verifySecurity(domain, searchField, collectField)) {
            def domainClass = grailsApplication.getDomainClass(domain).clazz
            def query = {
                eq (primarybind, primaryid)
                and{
                    ilike searchField, term + '%'
                }
                projections {
                    property(collectField)
                    property(searchField)
                }
                maxResults(Integer.parseInt(max,10))
                order(searchField, orderBy)
            }
            def query1 = {
                eq (primarybind, primaryid.toLong())
                and{

                    ilike searchField, "%${term}%"
                }
                projections {
                    property(collectField)
                    property(searchField)
                }
                maxResults(Integer.parseInt(max,10))
                order(searchField, orderBy)
            }
            def results = domainClass?.createCriteria()?.list(query)
            if (results.size()< 5){
                results = domainClass?.createCriteria()?.list(query1)
            }
            if (results) {
                primarySelectList = resultSet1(results)
            }
        }
        return primarySelectList as JSON
    }


    // No reference auto complete service
    def autocompleteSecondaryNR (String term, String domain, String domain2, String searchField,
                                 String collectField, String primarybind, Long primaryid) {

        ArrayList primarySelectList = []

        if (verifySecurity(domain2, searchField, collectField)) {
            def domainClass2 = grailsApplication?.getDomainClass(domain2)?.clazz
            def domainClass = grailsApplication?.getDomainClass(domain)?.clazz
            def domaininq = domainClass.get(primaryid)
            //def primarySelectList = []
            domaininq."${primarybind}".each { dq ->
                def query = {
                    eq (searchField , dq.toString().trim())
                    and { ilike  searchField ,term+ '%'}
                    projections {
                        property(collectField)
                        property(searchField)
                    }
                    order(searchField)
                }
                def query1 = {
                    eq (searchField , dq.toString().trim())
                    and { ilike  searchField ,"%${term}%"}
                    projections {
                        property(collectField)
                        property(searchField)
                    }
                    order(searchField)
                }
                def results = domainClass2?.createCriteria()?.list(query)
                if (results.size()< 5){
                    results = domainClass2?.createCriteria()?.list(query1)
                }
                if (results) {
                    primarySelectList = resultSet1(results)
                }
            }
        }
        return primarySelectList as JSON
    }


    def returnControllerList() {
        def clazz = grailsApplication?.controllerClasses?.logicalPropertyName
        def results = clazz?.collect {[	'id': it, 'name': it ]}?.unique()
        return results
    }

    def selectSecondary(String domain2, String searchField, String collectField, Long id, String bindid,
                        String filter=null, String filterType=null) {

        ArrayList primarySelectList = []

        if (verifySecurity(domain2, searchField, collectField)) {
            if (domain2 && id) {
                def domainClass = grailsApplication?.getDomainClass(domain2)?.clazz
                def query = {
                    eq  (bindid, id)
                    if ((filter)&&(!filter.toString().equals('null'))) {
                        def myfilter = parseFilter(filter,filterType)
                        and { ilike  searchField ,myfilter}
                    }
                    projections {
                        property(collectField)
                        property(searchField)
                    }
                    order(searchField)
                }
                def results = domainClass?.createCriteria()?.list(query)
                if (results) {
                    primarySelectList = resultSet2(results)
                }
            }
        }
        return primarySelectList as JSON
    }

    // This is now set up for secondary filtering method
    def secondarySearch(String collectField, String searchField,  Long prevValue=null,
                        String domain=null, String term=null, String filterType=null) {

        ArrayList primarySelectList = []

        if (verifySecurity(domain, searchField, collectField)) {
            if (domain && prevValue) {
                def domainClass = grailsApplication?.getDomainClass(domain)?.clazz
                def query = {
                    eq  (filterbind, prevValue)
                    if ((term)&&(!term.toString().equals('null'))) {
                        def filter="${term}"
                        def myfilter = parseFilter(filter,filterType)
                        and { ilike  searchField ,myfilter}
                    }
                    projections {
                        property(collectField)
                        property(searchField)
                    }
                    order(searchField)
                }
                def results = domainClass?.createCriteria()?.list(query)
                if (results) {
                    primarySelectList = resultSet2(results)
                }
            }
        }
        return primarySelectList as JSON
    }

    // No reference selection method i.e. belongsTo = UpperClass
    def selectSecondaryNR(String domain2, String searchField, String collectField, String domain,
                          Long id, String bindid, String filter2=null, String filterType2=null) {


        ArrayList primarySelectList = []

        if (verifySecurity(domain2, searchField, collectField)) {
            if ((domain2) && (domain) &&(id)) {
                def domainClass2 = grailsApplication?.getDomainClass(domain2)?.clazz
                def domainClass = grailsApplication?.getDomainClass(domain)?.clazz
                def domaininq = domainClass?.get(id.toLong())
                if (domaininq) {
                    domaininq."${bindid}".each { dq ->
                        def query = {
                            if ((filter2)&&(!filter2.toString().equals('null'))) {
                                def filter = filter2
                                def filterType = filterType2
                                def myfilter = parseFilter(filter,filterType)
                                and { ilike  searchField ,myfilter}
                            }
                            eq (searchField , dq.toString().trim())
                            projections {
                                property(collectField)
                                property(searchField)
                            }
                            order(searchField)
                        }
                        def results = domainClass2?.createCriteria()?.list(query)
                        results.each {
                            def primaryMap = [:]
                            primaryMap.put('id', it[0])
                            primaryMap.put('name', it[1])
                            primarySelectList.add(primaryMap)
                        }
                    }
                }
            }
        }
        return primarySelectList as JSON
    }


    def returnPrimarySearch(String json, String filter, String className, String domain, String searchField,
                            String collectField, String filterType,  String filterDisplay=null) {

        if (verifySecurity(domain, searchField, collectField)) {
            if (!className.equals('')) {
                def clazz = grailsApplication.getDomainClass(className).clazz
                if (filter && (!filter.toString().equals('null'))) {
                    def query = {
                        def myfilter = parseFilter(filter,filterType)
                        and { ilike  searchField ,myfilter}
                        //}
                        if (json.equals('json')) {
                            projections {
                                property(collectField)
                                property(searchField)
                            }
                        }
                        order(searchField)
                    }
                    def results = clazz?.createCriteria()?.list(query)
                    if (!results) {
                        if (filterDisplay.toString().toLowerCase().equals('all')) {
                            if (json.equals('json')) {
                                def mylist = clazz.list()
                                def newlist = mylist?.collect {['id': it."${collectField}", 'name': it."${searchField}" ]}?.unique()
                                return newlist as JSON

                            }else{
                                clazz.list()
                            }
                        }
                    }else{
                        if (json.equals('json')) {
                            if (results) {
                                def primarySelectList = resultSet2(results)
                                return primarySelectList as JSON
                            }
                        }else{
                            results
                        }
                    }
                    //return results
                }else{
                    if (filterDisplay.toString().toLowerCase().equals('all')) {
                        if (json.equals('json')) {
                            def mylist = clazz.list()
                            def newlist = mylist?.collect {['id': it."${collectField}", 'name': it."${searchField}" ]}?.unique()
                            return newlist as JSON
                        }else{
                            clazz.list()
                        }
                    }
                }
            }
        }
    }

    def returnControllerActions(String s) {
        String domclass = currentController(s)
        ArrayList mList = []
        grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controller ->
            Class controllerClass = controller.clazz
            if (controllerClass.name.endsWith(domclass.toString())) {
                String logicalControllerName = controller.logicalPropertyName
                controllerClass.methods.each { Method method ->
                    if (method.getAnnotation(Action)) {
                        mList.add(method.name)
                    }
                }
            }
        }
        def results = list?.collect {['id':it,'name':it] }?.unique()
        return results as JSON
    }


    private ArrayList resultSet1(def results) {
        ArrayList primarySelectList = []
        if (results) {
            results.each {
                def primaryMap = [:]
                primaryMap.put('id', it[0])
                primaryMap.put('label', it[1])
                primarySelectList.add(primaryMap)
            }
        }
        return primarySelectList
    }

    private ArrayList resultSet2(def results) {
        ArrayList primarySelectList = []
        if (results) {
            results.each {
                def primaryMap = [:]
                primaryMap.put('id', it[0])
                primaryMap.put('name', it[1])
                primarySelectList.add(primaryMap)
            }
        }
        return primarySelectList
    }

    private List returnPrimaryList(String className) {
        if (!className.equals('')) {
            Class clazz = grailsApplication?.getDomainClass(className)?.clazz
            clazz?.list()
        }
    }

    private String parseFilter(def filter,def filterType) {
        String myfilter = '%'+filter+'%'
        if (filterType.equals('S')) {
            myfilter = filter+'%'
        } else if (filterType.equals('E')) {
            myfilter = '%'+filter
        }
        return myfilter
    }

    // Security addition for 0.43
    public Boolean verifySecurity (String domainClass, String search, String collection) {
        boolean status = false
        if (config) {
            def clist = config.whitelist
            if (config.security && config.security == 'enabled') {
                if (clist.dc && clist.dc.contains(domainClass))  {
                    clist.each {cl ->
                        if ((cl.dc == domainClass) && (cl.search && cl.search == search)
                                && (cl.collect && cl.collect == collection)) {
                            status = true
                        }
                    }
                }
            }else{
                status = true
            }
        }else{
            status = true
        }
        return status
    }

    private ConfigObject getConfig() {
        grailsApplication.config?.ads
    }

    private String currentController(String s) {
        s.substring(0,1).toUpperCase() + s.substring(1)+"Controller"
    }
}