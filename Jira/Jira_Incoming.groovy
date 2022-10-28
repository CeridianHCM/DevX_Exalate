//issue (New/Updated JIRA OBJ) = replica (Incoming ADO OBJ)

if(firstSync){
    def apMap = ["SharpTop\\Benefits\\Benefits - Continuous Improvements":"BCIO",
                "SharpTop\\Benefits\\Enhanced Carrier Feeds":"ECF",
                "SharpTop\\Benefits\\Enhanced Global Benefits Admin":"EGBA",
                "SharpTop\\Benefits\\Reimagine Benefits":"RB",
                "SharpTop\\Clocks\\Clocks - CI":"CCI",
                "SharpTop\\Clocks\\New Clock UX Redesign":"NCU",
                "SharpTop\\Clocks\\Unified Clock":"TCP",
                "SharpTop\\Core Technology\\Continuous Improvements":"CTCI",
                "SharpTop\\Global Pay and Tax\\AP\\ANZ":"GPTCI",
                "SharpTop\\Global Pay and Tax\\AP":"APJ",
                "SharpTop\\Global Pay and Tax\\Continuous Improvements":"GPTCI",
                "SharpTop\\Global Pay and Tax\\EMEA":"PAYSERV",
                "SharpTop\\Global Pay and Tax\\Global Compliance":"PCCC",
                "SharpTop\\Global Pay and Tax\\Tax and Payment Gateway":"TP",
                "SharpTop\\Global Pay and Tax\\Unified":"GPTCI",
                "SharpTop\\Global Pay and Tax\\User Experience":"GPTCI",
                "SharpTop\\Human Resources\\Agile Org Management":"AOM",
                "SharpTop\\Human Resources\\Data Management":"HRCI",
                "SharpTop\\Human Resources\\Employee 2.0":"E20",
                "SharpTop\\Human Resources\\Geographic Pay Zones":"HRCI",
                "SharpTop\\Human Resources\\Global Person":"HRCI",
                "SharpTop\\Human Resources\\HR_CI":"HRCI",
                "SharpTop\\Integration\\Continuous Improvements":"ICI",
                "SharpTop\\Integration\\Event Services":"ES",
                "SharpTop\\Integration\\Integration Studio":"ISTUDIO",
                "SharpTop\\Marketplace\\Reports":"MAR",
                "SharpTop\\People Experience\\Continuous Improvements":"PECI",
                "SharpTop\\People Experience\\Dayforce Hub":"HUB",
                "SharpTop\\People Intelligence\\Analytic Data Ecosystem":"ADE",
                "SharpTop\\People Intelligence\\Analytics Data Connector":"ADC",
                "SharpTop\\People Intelligence\\Data Science and Engineering":"DSE",
                "SharpTop\\People Intelligence\\Data Studio":"DS",
                "SharpTop\\People Intelligence\\People Intelligence Continuous Improvements":"PICI",
                "SharpTop\\People Intelligence\\Strategic Data Analytics":"SDA",
                "SharpTop\\Platform\\Always-on Admin Service":"PCI",
                "SharpTop\\Platform\\Application Metadata Framework":"AMF",
                "SharpTop\\Platform\\Background Job Engine":"RE",
                "SharpTop\\Platform\\Centralized Audit":"CA",
                "SharpTop\\Platform\\Document Management":"PCI",
                "SharpTop\\Platform\\End User Security 2.0":"EUC20",
                "SharpTop\\Platform\\Event Driven":"PCI",
                "SharpTop\\Platform\\Messaging":"PCI",
                "SharpTop\\Platform\\Mobile - Continuous Improvements":"MCI",
                "SharpTop\\Platform\\Person Access 2.0":"PLA20",
                "SharpTop\\Platform\\Platform - Continuous Improvements":"PCI",
                "SharpTop\\Platform\\PXT":"PCI",
                "SharpTop\\PublicSector\\Accessibility":"AC",
                "SharpTop\\PublicSector\\Core HR":"PSCI",
                "SharpTop\\PublicSector\\Integration":"ISTUDIO",
                "SharpTop\\PublicSector\\WFM":"WCI",
                "SharpTop\\Talent\\ANZ":"TCI",
                "SharpTop\\Talent\\Career Intelligence":"CI",
                "SharpTop\\Talent\\Career Profile":"TCI",
                "SharpTop\\Talent\\Compensation":"COM",
                "SharpTop\\Talent\\Engagement Survey":"TCI",
                "SharpTop\\Talent\\LMS":"TCI",
                "SharpTop\\Talent\\Onboarding":"OMF",
                "SharpTop\\Talent\\Performance":"TCI",
                "SharpTop\\Talent\\Recruiting":"TCI",
                "SharpTop\\Talent\\Succession":"TCI",
                "SharpTop\\User Experience\\Continuous Improvements":"UECI",
                "SharpTop\\Wallet and Consumer Services\\Wallet":"WU",
                "SharpTop\\Workforce Management\\Admin Page":"WCI",
                "SharpTop\\Workforce Management\\Alerts":"WCI",
                "SharpTop\\Workforce Management\\Approvals":"WCI",
                "SharpTop\\Workforce Management\\Background Jobs":"WCI",
                "SharpTop\\Workforce Management\\Centralized Scheduling":"DF",
                "SharpTop\\Workforce Management\\Entitlement":"WCI",
                "SharpTop\\Workforce Management\\GDPR and PDM":"WCI",
                "SharpTop\\Workforce Management\\Import Exports":"WCI",
                "SharpTop\\Workforce Management\\Long Service Leave":"WCI",
                "SharpTop\\Workforce Management\\Multiweek Scheduling":"WCI",
                "SharpTop\\Workforce Management\\Pay Compliance":"WCI",
//                \\"SharpTop\\Workforce Management\\riteq":"", // MISSING Project
                "SharpTop\\Workforce Management\\Scheduling":"DP",
                "SharpTop\\Workforce Management\\Shift Acceptance":"DF",
                "SharpTop\\Workforce Management\\TAFW in Weeks and Days":"WCI",
                "SharpTop\\Workforce Management\\Time":"WCI",
                "SharpTop\\Workforce Management\\WFM - Continuous Improvements":"WCI"]
    issue.projectKey   =  apMap[replica.areaPath]   
//    issue.projectKey   =  "AC"
    def typeMapping = ["Task":"Sub-task",
                    "Product Backlog Item":"Story",
                    "Feature":"Epic"]
             
// Set type name from source issue, if not found set a default
   def remoteType = replica.type?.name
 if (replica.type?.name == "Feature") {
    issue.typeName = "Epic"
 
    // set the epic name to the same as the issue summary
    issue.customFields."Epic Name".value = replica.summary 
    issue.customFields."ADO Id".value = replica.key 
    issue.summary      = replica.summary
    issue.description  = replica.description
  } else {
    // Set type name from source issue, if not found set a default
    issue.typeName = nodeHelper.getIssueType(typeMapping[remoteType], issue.projectKey)?.name ?: "Story"
//   issue.customFields."P&T Teams Involved".value = "None"
//    issue.customFields."Non P&T Teams Involved".value = "None"
    issue.customFields."ADO Id".value = replica.key
    issue.summary      = replica.summary
    issue.description  = replica.description ?: replica.summary
}
if(replica.parentId){
  
    def localParent = syncHelper.getLocalIssueKeyFromRemoteId(replica.parentId)
  
    if(localParent){
        issue.parentId = localParent.id
    }
}
}

issue.customFields."ADO Id".value = replica.key

if(issue.typeName == "Story" ){
    def statusMap = ["New":"Draft",
                        "Approved":"Open",
                        "Committed":"In Progress",
                        "ReadyQA":"In Progress",
                        "Done":"Done",
                        "Removed":"Cancelled"]
    issue.setStatus(statusMap[replica.status.name])
}
if(issue.typeName == "Sub-task" ){
    def statusMap = ["To Do":"Open",
                        "In Progress":"In Progress",
                        "Done":"Done",
                        "Removed":"Done"]
    issue.setStatus(statusMap[replica.status.name])
}
if(issue.typeName == "Epic" ){
    def statusMap = ["New":"Open",
                        "In Progress":"In Progress",
                        "Done":"Released (Shipped)",
                        "Removed":"Cancelled"]
    issue.setStatus(statusMap[replica.status.name])
}


//issue.summary      = replica.summary
//issue.description  = replica.description
//issue.attachments  = attachmentHelper.mergeAttachments(issue, replica)
//issue.labels = replica.labels.collect { it.label = it.label.trim().replace(" ", "_"); it }

/*
User Synchronization (Assignee/Reporter)
Set a Reporter/Assignee from the source side, if the user can't be found set a default user
You can use this approach for custom fields of type User
*/
/*
def reporter = nodeHelper.getUserByEmail(replica.reporter?.email)
if(nodeHelper.isUserAssignable(issue.projectKey, reporter)){
    issue.reporter = reporter
}else{
    issue.reporter = nodeHelper.getUserByEmail("greg.patterson@ceridian.com")
}
*/

/*def assignee = nodeHelper.getUserByEmail(replica.customKeys."assignee_email"?.email)
if(nodeHelper.isUserAssignable(issue.projectKey, assignee)){
    issue.assignee = assignee
}else{
    issue.assignee = nodeHelper.getUserByEmail("dharma.ramos@ceridian.com")
}*/

//issue.fixVersions = [nodeHelper.createVersion(issue, replica.customKeys."EstimatedRelease", "ADO Created Version")]

/*
Comment Synchronization
Sync comments with the original author if the user exists in the local instance
Remove original Comments sync line if you are using this approach
issue.comments = commentHelper.mergeComments(issue, replica){ it.executor = nodeHelper.getUserByEmail(it.author?.email) }
*/
//issue.comments     = commentHelper.mergeComments(issue, replica)

/*
Status Synchronization
Sync status according to the mapping [remote issue status: local issue status]
If statuses are the same on both sides don't include them in the mapping
def statusMapping = ["Open":"New", "To Do":"Backlog"]
def remoteStatusName = replica.status.name
issue.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
*/

/*
Custom Fields
This line will sync Text, Option(s), Number, Date, Organization, and Labels CFs
For other types of CF check documentation
issue.customFields."CF Name".value = replica.customFields."CF Name".value
*/
//issue."Purpose" = replica."Purpose"?.value
//issue."Availability Status" = replica."Availability Status"?.value
//issue."Product Team" = replica."Product Team"?.value
//issue."Feature Team" = replica."FeatureTeam"?.value
//issue.customFields."Product Team".value = replica.customFields."Product Team"?.value