SELECT  batch_id,t_stamp IS NOT NULL as status,t_stamp,ip_in.ip,country
FROM ip_in
LEFT JOIN ip_out
ON ip_in.ip=ip_out.ip;