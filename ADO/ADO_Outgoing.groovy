//REPLICA (Will be the JIRA OBJ) = workItem (Current ADO OBJ)

replica.key            = workItem.key
replica.assignee      = workItem.assignee
replica.reporter      = workItem.reporter
replica.summary        = workItem.summary
replica.description    = nodeHelper.stripHtml(workItem.description)
replica.type           = workItem.type
replica.status         = workItem.status
//replica.labels         = workItem.labels
replica.priority       = workItem.priority
//replica.comments       = nodeHelper.stripHtmlFromComments(workItem.comments)
//replica.attachments    = workItem.attachments
replica.project        = workItem.project
replica.areaPath       = workItem.areaPath
replica.iterationPath  = workItem.iterationPath
replica.parentId       = workItem.parentId
//replica.reporter = workItem.reporter


//Send a Custom Field value
//replica.customFields."CF Name" = workItem.customFields."CF Name"
replica."EstimatedRelease" = workItem."Dayforce.EstimatedRelease" // Planned Release

replica."Feature Team" = workItem."Dayforce.Team" // Team
//replica.customKeys."FTeam" = workItem."Dayforce.Team"
replica.customKeys."EstimatedRelease" = workItem."Dayforce.EstimatedRelease"  // Planned Release
replica.customKeys."reporter_email"  = workItem.reporter
replica.customKeys."assignee_email"  = workItem.assignee
//replica.customFields."FTeam" = workItem."Dayforce.Team"
replica."Product Team" = workItem."Product Team"
replica."Purpose"      = workItem."Purpose"
replica."Availability Status"  = workItem."Availability Status"