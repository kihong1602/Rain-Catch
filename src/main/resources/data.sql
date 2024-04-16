start transaction;
insert into user (id, create_date, update_date, email, image, nick_name, provider_type, role)
values (1, now(), now(), 'kks4517@naver.com',
        'http://k.kakaocdn.net/dn/JJEUL/btszGRCwUMV/rVA2FyXPfOb67gNKwvOlL1/img_640x640.jpg', '기홍', 'KAKAO', 'USER');
insert into rental_station (id, create_date, update_date, address, name, point)
values (1, now(), now(), '서울특별시 은평구 서오릉로94', '삼성타운아파트', POINT(37.6095200026749, 126.921700201605)),
       (2, now(), now(), '서울 은평구 서오릉로 93', '아이&유안경콘택트', POINT(37.6082331420116, 126.920788502823)),
       (3, now(), now(), '서울 은평구 서오릉로 86', '컴포즈커피 역촌중앙점', POINT(37.6078717971096, 126.921797061702));
insert into umbrella (id, create_date, update_date, umbrella_status, current_station)
values (1, now(), now(), 'AVAILABLE', 1),
       (2, now(), now(), 'AVAILABLE', 2),
       (3, now(), now(), 'AVAILABLE', 3),
       (4, now(), now(), 'RENTED', null),
       (5, now(), now(), 'RENTED', null),
       (6, now(), now(), 'RENTED', null),
       (7, now(), now(), 'LOST', null),
       (8, now(), now(), 'LOST', null);
commit;