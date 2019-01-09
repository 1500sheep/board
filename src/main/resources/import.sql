INSERT INTO role(role_id, role) VALUES(1, '관리자');
INSERT INTO role(role_id, role) VALUES(2, '유저');

INSERT INTO user (user_id, email, password, name, phone_number, is_deleted, created_date, modified_date) VALUES(1, 'admin@gooroomee.com', '$2a$10$ydT.gK1UOZO0sNCuG0TAAuHbD9k7n4uH177.rl6vNbDt1Fs1sLGXm', '관리자', '010-1234-2222',  false, now(), now());
INSERT INTO user (user_id, email, password, name, phone_number, is_deleted, created_date, modified_date) VALUES(2, 'james.kang@gooroomee.com', '$2a$10$ydT.gK1UOZO0sNCuG0TAAuHbD9k7n4uH177.rl6vNbDt1Fs1sLGXm', '제임스캉', '010-1234-2222',  false, now(), now());
INSERT INTO user (user_id, email, password, name, phone_number, is_deleted, created_date, modified_date) VALUES(3, 'awesome@gooroomee.com', '$2a$10$ydT.gK1UOZO0sNCuG0TAAuHbD9k7n4uH177.rl6vNbDt1Fs1sLGXm', '짱짱맨구루미', '010-1234-2222',  false, now(), now());

INSERT INTO user_roles(user_user_id, roles_role_id) VALUES(1,1);


INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(1, 3, 'title1', 'title1 content1', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(2, 2, 'title2', 'title2 content2', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(3, 2, 'title3', 'title3 content3', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(4, 3, 'title4 gooroomee', 'content4', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(5, 2, 'title5', 'content5', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(6, 3, 'title6', 'content6 gooroomee', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(7, 3, 'title7', 'content7', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(8, 2, 'title8 gooroomee', 'content8', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(9, 2, 'title9', 'content9', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(10, 3, 'title10', 'content10 gooroomee', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(11, 2, 'title11', 'content11', now(), now(), false);
INSERT INTO post(post_id, writer_user_id, title, content, created_date, modified_date, is_deleted) VALUES(12, 3, 'title12', 'content12', now(), now(), false);


INSERT INTO reply(reply_id, post_post_id, writer_user_id, comment, created_date, modified_date, is_deleted) VALUES(1, 12, 3, 'comment1', now(), now(), false);
INSERT INTO reply(reply_id, post_post_id, writer_user_id, comment, created_date, modified_date, is_deleted) VALUES(2, 12, 2, 'comment2', now(), now(), false);
INSERT INTO reply(reply_id, post_post_id, writer_user_id, comment, created_date, modified_date, is_deleted) VALUES(3, 12, 2, 'comment3', now(), now(), false);