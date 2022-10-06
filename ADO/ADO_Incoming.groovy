//workItem (New/Updated ADO OBJ) = replica (Incoming JIRA OBJ)

if(firstSync){
    def apMap = ["AC":"SharpTop\\_Demo\\ST\\Platform\\Accessibility",
             "CCI":"SharpTop\\_Demo\\ST\\Clocks\\Clocks - Continuous Improvement"]
   // Set type name from source entity, if not found set a default
   workItem.projectKey  =  "SharpTop"
   workItem.iterationPath = "SharpTop\\1Current\\863 S5"
   workItem.areaPath = apMap[replica.project.key]
   
    def typeMapping = ["Sub-task":"Task",
                    "Story":"Product Backlog Item",
                    "Epic":"Feature"]

   workItem.typeName = nodeHelper.getIssueType(typeMapping[replica.type?.name], issue.projectKey)?.name ?: "Task"
}

workItem.summary      = replica.summary
workItem.description  = replica.description
workItem.attachments  = attachmentHelper.mergeAttachments(workItem, replica)
workItem.comments     = commentHelper.mergeComments(workItem, replica)
workItem.labels       = replica.labels
workItem.priority     = replica.priority
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
def remoteStatusName = replica.status.name
if(workItem.typeName == "Story" ){ //ADO PBI
    def statusMapping = ["Draft":"New",
                        "Open":"Approved",
                       // "In Progress":"Committed",
                       // "In Progress":"ReadyQA",
                        "Done":"Done",
                        "Cancelled":"Removed"]

    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}
if(workItem.typeName == "Sub-task" ){ //ADO Task
    def statusMapping = ["Open":"To Do",
                        "In Progress":"In Progress",
                        "Done":"Done",
                        "Done":"Removed"]
    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}
if(workItem.typeName == "Epic" ){ //ADO Feature
    def statusMapping = ["Open":"New",
                        "In Progress":"In Progress",
                        "Code Complete":"Done",
                        "Cancelled":"Removed"]
    workItem.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)
}




def assignee = nodeHelper.getUserByEmail(replica.assignee?.email, workItem.projectKey)
if(assignee?.trim()){
    workItem.assignee = assignee
}else{
    workItem.assignee = nodeHelper.getUserByEmail("greg.patterson@ceridian.com", workItem.projectKey)
}


def defaultUser = nodeHelper.getUserByEmail("greg.patterson@ceridian.com", workItem.projectKey)
workItem."System.CreatedBy" = nodeHelper.getUserByEmail(replica.reporter?.email, workItem.projectKey) ?: defaultUser
//workItem."System.AssignedTo".value = nodeHelper.getUserByEmail(replica.assignee?.email) ?: defaultUser

workItem."Dayforce.Team" = replica."Feature Team"// Team