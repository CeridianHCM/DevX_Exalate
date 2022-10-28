//workItem (New/Updated ADO OBJ) = replica (Incoming JIRA OBJ)

if(firstSync){
    def apMap = ["BCIO":"SharpTop\\Benefits\\Benefits - Continuous Improvements",
                "ECF":"SharpTop\\Benefits\\Enhanced Carrier Feeds",
                "EGBA":"SharpTop\\Benefits\\Enhanced Global Benefits Admin",
                "RB":"SharpTop\\Benefits\\Reimagine Benefits",
				"CCI":"SharpTop\\Clocks\\Clocks - CI",
                "NCU":"SharpTop\\Clocks\\New Clocks UX Redesign",
                "TCP":"SharpTop\\Clocks\\Unified Clock",
                "CTCI":"SharpTop\\Core Technology\\Continuous Improvements",
                //"GPTCI":"SharpTop\\Global Pay and Tax\\AP\\ANZ",
                "APJ":"SharpTop\\Global Pay and Tax\\AP",
                "GPTCI":"SharpTop\\Global Pay and Tax\\Continuous Improvements",
                "PAYSERV":"SharpTop\\Global Pay and Tax\\EMEA",
                "PCCC":"SharpTop\\Global Pay and Tax\\Global Compliance",
                "TP":"SharpTop\\Global Pay and Tax\\Tax and Payment Gateway",
                //"GPTCI":"SharpTop\\Global Pay and Tax\\Unified",
                //"GPTCI":"SharpTop\\Global Pay and Tax\\User Experience",
                "AOM":"SharpTop\\Human Resources\\Agile Org Management",
                //"HRCI":"SharpTop\\Human Resources\\Data Management",
                "E20":"SharpTop\\Human Resources\\Employee 2.0",
                //"HRCI":"SharpTop\\Human Resources\\Geographic Pay Zones",
                //"HRCI":"SharpTop\\Human Resources\\Global Person",
                "HRCI":"SharpTop\\Human Resources\\HR_CI",
                "ICI":"SharpTop\\Integration\\Continuous Improvements",
                "ES":"SharpTop\\Integration\\Event Services",
                "ISTUDIO":"SharpTop\\Integration\\Integration Studio",
                "MAR":"SharpTop\\Marketplace\\Reports",
                "PECI":"SharpTop\\People Experience\\Continuous Improvements",
                "HUB":"SharpTop\\People Experience\\Dayforce Hub",
                "ADE":"SharpTop\\People Intelligence\\Analytic Data Ecosystem",
                "ADC":"SharpTop\\People Intelligence\\Analytics Data Connector",
                "DSE":"SharpTop\\People Intelligence\\Data Science and Engineering",
                "DS":"SharpTop\\People Intelligence\\Data Studio",
                "PICI":"SharpTop\\People Intelligence\\People Intelligence Continuous Improvements",
                "SDA":"SharpTop\\People Intelligence\\Strategic Data Analytics",
                //"PCI":"SharpTop\\Platform\\Always-on Admin Service",
                "AMF":"SharpTop\\Platform\\Application Metadata Framework",
                "RE":"SharpTop\\Platform\\Background Job Engine",
                "CA":"SharpTop\\Platform\\Centralized Audit",
                //"PCI":"SharpTop\\Platform\\Document Management",
                "EUC20":"SharpTop\\Platform\\End User Security 2.0",
                //"PCI":"SharpTop\\Platform\\Event Driven",
                //"PCI":"SharpTop\\Platform\\Messaging",
                "MCI":"SharpTop\\Platform\\Mobile - Continuous Improvements",
                "PLA20":"SharpTop\\Platform\\Person Access 2.0",
                "PCI":"SharpTop\\Platform\\Platform - Continuous Improvements",
                //"PCI":"SharpTop\\Platform\\PXT",
                "AC":"SharpTop\\PublicSector\\Accessibility",
                "PSCI":"SharpTop\\PublicSector\\Core HR",
                //"ISTUDIO":"SharpTop\\PublicSector\\Integration",
                //"WCI":"SharpTop\\PublicSector\\WFM",
                //"TCI":"SharpTop\\Talent\\ANZ",
                "CI":"SharpTop\\Talent\\Career Intelligence",
                //"TCI":"SharpTop\\Talent\\Career Profile",
                "COM":"SharpTop\\Talent\\Compensation",
                //"TCI":"SharpTop\\Talent\\Engagement Survey",
                //"TCI":"SharpTop\\Talent\\LMS",
                "OMF":"SharpTop\\Talent\\Onboarding",
                //"TCI":"SharpTop\\Talent\\Performance",
                //"TCI":"SharpTop\\Talent\\Recruiting",
                //"TCI":"SharpTop\\Talent\\Succession",
                "TCI":"SharpTop\\Talent\\Talent_CI",
                "UECI":"SharpTop\\User Experience\\Continuous Improvements",
                "WU":"SharpTop\\Wallet and Consumer Services\\Wallet",
                "GL":"SharpTop\\Global Pay and Tax\\Continuous Improvements",
                //"WCI":"SharpTop\\Workforce Management\\Admin Page",
                //"WCI":"SharpTop\\Workforce Management\\Alerts",
                //"WCI":"SharpTop\\Workforce Management\\Approvals",
                //"WCI":"SharpTop\\Workforce Management\\Background Jobs",
                //"DF":"SharpTop\\Workforce Management\\Centralized Scheduling",
                //"WCI":"SharpTop\\Workforce Management\\Entitlement",
                //"WCI":"SharpTop\\Workforce Management\\GDPR and PDM",
                //"WCI":"SharpTop\\Workforce Management\\Import Exports",
                //"WCI":"SharpTop\\Workforce Management\\Long Service Leave",
                //"WCI":"SharpTop\\Workforce Management\\Multiweek Scheduling",
                //"WCI":"SharpTop\\Workforce Management\\Pay Compliance",
//                "":"SharpTop\\Workforce Management\\riteq", // MISSING
                "DP":"SharpTop\\Workforce Management\\Scheduling",
                //"DF":"SharpTop\\Workforce Management\\Shift Aceptance",
                "DF":"SharpTop\\Workforce Management\\WFM - Continuous Improvements",
                //"WCI":"SharpTop\\Workforce Management\\TAFW in Weeks and Days",
                //"WCI":"SharpTop\\Workforce Management\\Time",
                "WCI":"SharpTop\\Workforce Management\\WFM - Continuous Improvements"]
   // Set type name from source entity, if not found set a default
   workItem.projectKey  =  "SharpTop"
   workItem.iterationPath = "SharpTop"
   workItem.areaPath = apMap[replica.project.key]
   
    def typeMapping = ["Sub-task":"Task",
                    "Story":"Product Backlog Item",
                    "Epic":"Feature"]

   workItem.typeName = nodeHelper.getIssueType(typeMapping[replica.type?.name], issue.projectKey)?.name ?: "Feature"
   workItem.summary      = replica.summary
//   workItem.description  = replica.description
   if(replica.parentId){
  
    def localParent = syncHelper.getLocalIssueKeyFromRemoteId(replica.parentId)
  
    if(localParent){
        issue.parentId = localParent.id
    }
}
syncHelper.syncBackAfterProcessing()
}
//syncHelper.syncBackAfterProcessing()
//workItem.summary      = replica.summary
//workItem.description  = replica.description
//workItem.attachments  = attachmentHelper.mergeAttachments(workItem, replica)
//workItem.comments     = commentHelper.mergeComments(workItem, replica)
//workItem.labels       = replica.labels
//workItem.priority     = replica.priority
/*
Area Path Sync
This also works for iterationPath field
*/


/*
Set Area Path Manually
workItem.areaPath = "Name of the project\\name of the area"
Set Area Path based on remote side drop-down list
Change "area-path-select-list" to actual custom field name
workItem.areaPath = replica.customFields."area-path-select-list"?.value?.value
Set Area Path based on remote side text field
Change "area-path" to actual custom field name
workItem.areaPath = replica.customFields."area-path".value
*/

/*
Status Synchronization
Sync status according to the mapping [remote workItem status: local workItem status]
If statuses are the same on both sides don"t include them in the mapping
def statusMapping = ["Open":"New", "To Do":"Open"]
def remoteStatusName = replica.status.name
workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
*/
/*def remoteStatusName = replica.status.name
if(workItem.typeName == "Product Backlog Item" ){ //Jira Story
    def statusMapping = ["Draft":"New",
                        "Open":"Approved",
                       // "In Progress":"Committed",
                       // "In Progress":"ReadyQA",
                        "Done":"Done",
                        "Cancelled":"Removed"]

    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}
if(workItem.typeName == "Task" ){ //Jira Sub-Task
    def statusMapping = ["Open":"To Do",
                        "In Progress":"In Progress",
                        "Done":"Done"]
//                        "Done":"Removed"] //missing on specs
    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}
if(workItem.typeName == "Feature" ){ //Jira Epic
    def statusMapping = ["Open":"New",
                        "In Progress":"In Progress",
                        "Code Complete":"Done",
                        "Cancelled":"Removed"]
    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}*/


/*def assignee = nodeHelper.getUserByEmail(replica.assignee?.email)
if(assignee){
workItem.assignee = assignee
}else{
workItem.assignee = nodeHelper.getUserByEmail("greg.patterson@ceridian.com")
}*/

//workItem.assignee = nodeHelper.getUserByEmail("greg.patterson@ceridian.com", workItem.projectKey)

//Fields Configuration
def defaultUser = nodeHelper.getUserByEmail("greg.patterson@ceridian.com", workItem.projectKey)
workItem."System.CreatedBy" = nodeHelper.getUserByEmail(replica.reporter?.email, workItem.projectKey) ?: defaultUser
//workItem."System.AssignedTo".value = nodeHelper.getUserByEmail(replica.assignee?.email) ?: defaultUser

//workItem."Dayforce.Team" = replica."Feature Team"// Team
//workItem."Planned Release" = replica.fixVersions?.collect{it.name}.join(",")
//workItem."Business Purpose" = replica."Purpose"
//workItem."Product Team" = replica."Product Team"