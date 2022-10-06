//issue (New/Updated JIRA OBJ) = replica (Incoming ADO OBJ)

if(firstSync){
    def apMap = ["SharpTop\\_Demo\\ST\\Platform\\Accessibility":"AC",
             "SharpTop\\_Demo\\ST\\Clocks\\Clocks - Continuous Improvement":"CCI"]
             
    def typeMapping = ["Task":"Sub-task",
                    "Product Backlog Item":"Story",
                    "Feature":"Epic"]
             
   issue.projectKey   =  apMap[replica.areaPath]
   // Set type name from source issue, if not found set a default
   issue.typeName     = nodeHelper.getIssueType(typeMapping[replica.type?.name], issue.projectKey)?.name ?: "Story"
}


if(issue.typeName == "Product Backlog Item" ){
    def statusMapping = ["New":"Draft",
                        "Approved":"Open",
                        "Committed":"In Progress",
                        "ReadyQA":"In Progress",
                        "Done":"Done",
                        "Removed":"Cancelled"]
    issue.setStatus(statusMap[replica.status.name])
}
if(issue.typeName == "Task" ){
    def statusMapping = ["To Do":"Open",
                        "In Progress":"In Progress",
                        "Done":"Done",
                        "Removed":"Done"]
    issue.setStatus(statusMap[replica.status.name])
}
if(issue.typeName == "Feature" ){
    def statusMapping = ["New":"Open",
                        "In Progress":"In Progress",
                        "Done":"Code Complete",
                        "Removed":"Cancelled"]
    issue.setStatus(statusMap[replica.status.name])
}


issue.summary      = replica.summary
issue.description  = replica.description
issue.attachments  = attachmentHelper.mergeAttachments(issue, replica)
issue.labels       = replica.labels

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

def assignee = nodeHelper.getUserByEmail(replica.customKeys."assignee_email")
if(nodeHelper.isUserAssignable(issue.projectKey, assignee)){
    issue.assignee = assignee
}else{
    issue.assignee = nodeHelper.getUserByEmail("greg.patterson@ceridian.com")
}

issue.fixVersions = [nodeHelper.createVersion(issue, replica.customKeys."EstimatedRelease", "ADO Created Version")]

/*
Comment Synchronization

Sync comments with the original author if the user exists in the local instance
Remove original Comments sync line if you are using this approach
issue.comments = commentHelper.mergeComments(issue, replica){ it.executor = nodeHelper.getUserByEmail(it.author?.email) }
*/
issue.comments     = commentHelper.mergeComments(issue, replica)

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