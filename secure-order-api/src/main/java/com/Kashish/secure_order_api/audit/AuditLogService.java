package com.Kashish.secure_order_api.audit;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class AuditLogService 
{
	private final AuditLogRepository auditLogRepository;

	public AuditLogService(AuditLogRepository auditLogRepository) 
	{
		this.auditLogRepository = auditLogRepository;
	}
	
	public void log(String username,String action)
	{
		AuditLog auditLog=new AuditLog();
		auditLog.setUsername(username);
		auditLog.setAction(action);
		auditLog.setTimestamp(LocalDateTime.now());
		
		auditLogRepository.save(auditLog);
	}
	
	

}
