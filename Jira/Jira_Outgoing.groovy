//replica (Will be the ADO OBJ) = issue (Current JIRA OBJ)

replica.key            = issue.key
replica.type           = issue.type
replica.assignee       = isscue.assignee
replica.reporter       = issue.reporter
replica.summary        = issue.summary
//replica.description    = issue.description
//replica.labels         = issue.labels
//replica.comments       = issue.comments
replica.resolution     = issue.resolution
//replica.status         = issue.status
replica.parentId       = issue.parentId
//replica.priority       = issue.priority
//replica.attachments    = issue.attachments
replica.project        = issue.project
replica.sprint         = issue.sprint
//replica.fixVersions    = issue.fixVersions

//Comment these lines out if you are interested in sending the full list of versions and components of the source project. 
//replica.project.versions = []
//replica.project.components = []

/*
Custom Fields
replica.customFields."CF Name" = issue.customFields."CF Name"
*/
//replica."Purpose" = issue."Purpose"
//replica."Availability Status" = issue."Availability Status"
//replica."Product Team" = issue."Product Team"

//replica."Team" = issue."Feature Team" //only sync FROM ADO


//replica.customKeys."FTeam" = workItem."RD_CM"
//replica.customKeys."EstimatedRelease" = workItem."Dayforce.EstimatedRelease" ?: "0.0" // Planned Release
replica.customKeys."reporter_email"  = issue.reporter
replica.customKeys."assignee_email"  = issue.assignee
//replica.customFields."FTeam" = workItem."Dayforce.Team"
//replica.customFields."EstimatedRelease" = workItem."Dayforce.EstimatedRelease" ?: "0.0" // Planned Release
//replica.customFields."reporter_email"  = issue.reporter?.email ?: "EMPTYJ"
//replica.customFields."assignee_email"  = issue.assignee