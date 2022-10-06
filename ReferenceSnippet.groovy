import java.text.SimpleDateFormat
if(firstSync){
   issue.projectKey   = "COM" 
   issue.typeName     = nodeHelper.getIssueType(replica.type?.name, issue.projectKey)?.name ?: "Bug"
   
   if(issue.typeName == "Bug"){
       issue."Bugs CF" = "yes"
   }
}

issue.customFields."formattingtest" = nodeHelper.toMarkDownFromHtml(replica.customFields."formattingtest")


issue.summary      = replica.summary 
issue.description  = replica.description

 issue.comments = nodeHelper.toMarkDownComments(commentHelper.mergeComments(issue, replica))


issue.attachments  = attachmentHelper.mergeAttachments(issue, replica)
issue.labels       = replica.labels






def sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
if(replica.dueDate){
    issue.due = sdf.parse(replica.dueDate)
}


def statusMapping = ["New":"open",
                    "Active":"In Review",
                    "Active":"In Progress",
                    "Resolved":"Resolved",
                    "Resolved":"Cancelled",
                    "Resolved":"Defer",
                    "Active":"Reopened",
                    "Active":"Pending with Customer",
                    "Closed":"QC Closed"]
def remoteStatusName = replica.status.name
issue.setStatus(statusMapping[remoteStatusName] ?: remoteStatusName)



//priority
def priorityMapping = [
        // remote side priority <-> local side priority            
          "1" : "High", "2":"Medium","3":"Low","4":"Lowest"
    ]
def priorityName = priorityMapping[replica.priority?.name]  
issue.priority = nodeHelper.getPriority(priorityName)


issue."Serverity" = replica?.serverity

//Change default user!
def defaultUser = nodeHelper.getUserByEmail("mathieu.lepoutre@idalko.com")
issue.assignee  = nodeHelper.getUserByEmail(replica.assignee?.email) ?: defaultUser

issue.fixVersions = [nodeHelper.createVersion(issue, replica.integrationBuild, "Default description")]


issue."Actual Results" = replica?.reproSteps
issue."Severity" = replica?.severity 
issue."Expected Results"  = replica?.steps 
issue."System Environment" = replica?.systemInfo
issue."External Issue ID" =  replica.key 

