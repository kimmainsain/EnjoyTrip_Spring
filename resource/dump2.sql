use enjoytrip;

delimiter $$
drop procedure `delete_route`;
CREATE DEFINER=`ssafy`@`%` PROCEDURE `delete_route`(
    in editable tinyint(1),
    in param int
)
begin
    if editable = 1 then
delete from route_bookmark where route_bookmark.route_id = param;
delete from route_review where route_review.route_id = param;
delete from route_detail where route_detail.route_id = param;
delete from route where id = param;
end if;
end
$$
delimiter ;

delimiter $$
create
definer = ssafy@`%` procedure update_route_detail_with_authorization(IN editable tinyint(1), IN route_id int,
                                                                         IN route_sequence int, IN attraction_id int)
begin
    if editable = 1 then
        insert into route_detail (`route_id`, `route_sequence`, `attraction_id`) values (route_id, route_sequence, attraction_id)
            on duplicate key update attraction_id = VALUES(attraction_id);
end if;
end;
$$
delimiter ;