1. Find the first value order by device_events_ts, we can use below hack:

    MAX(STRUCT(device_event_ts, build)).col2 AS build
               
2. Concat all non-null string
       concat_ws('&', collect_set(IF(concat(test_id,':',bucket)  LIKE '%:%', concat(test_id,':',bucket) , NULL)))
                                     as active_exp_map
                                     
3. Check if file has schema to apply on:

SELECT * FROM
(select get_json_object(regexp_replace(devices, '\\[|\\]', ''), '$.esn') AS dd, *  from
sbschema.roku_fact_web_logs12 
where  date_key='2020-04-26' AND action not in ('Pageview')) t
WHERE dd is NULL
AND COALESCE(devices,'') NOT IN ('','[{}]')

4. 
