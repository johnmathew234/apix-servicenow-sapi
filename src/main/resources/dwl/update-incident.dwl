%dw 2.0
output application/xml
ns ns0 http://www.service-now.com/incident
---
{
	ns0#update: {
		number: payload.errorCode,
		caused_by: payload.errorType,
		description: payload.errorDescription,
		correlation_id: payload.correlationId,
		calender_stc: payload.timestamp,
		short_description: payload.apiName,
		category: payload.apiVersion,
		sys_id: attributes.uriParams.incidentID as String
	}
}