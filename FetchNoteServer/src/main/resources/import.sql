--Test Dummy Data
--user
INSERT INTO `user` (id, email, nickname, exp) VALUES (1, "kimcoding@code.states", "김코딩", 150);
INSERT INTO `user` (id, email, nickname, exp) VALUES (2, "parkhacker@code.states", "박해커", 1000);
INSERT INTO `user` (id, email, nickname, exp) VALUES (3, "test@code.states", "이하수", 50000);
INSERT INTO `user` (id, email, nickname, exp) VALUES (4, "test@code.states", "최고수", 500000);

--game
INSERT INTO `game` (id, name, image) VALUES (1, "러그 오브 레전드", "0");
INSERT INTO `game` (id, name, image) VALUES (2, "피파 오프라인 4", "0");
INSERT INTO `game` (id, name, image) VALUES (3, "백준 온라인", "0");

--like_game
INSERT INTO `like_game` (id, game_id, user_id) VALUES (1, 1, 1);
INSERT INTO `like_game` (id, game_id, user_id) VALUES (2, 1, 2);
INSERT INTO `like_game` (id, game_id, user_id) VALUES (3, 2, 1);
INSERT INTO `like_game` (id, game_id, user_id) VALUES (4, 2, 2);
INSERT INTO `like_game` (id, game_id, user_id) VALUES (5, 3, 4);

--patch
INSERT INTO `patches` (id, user_id, game_id, title, body, `right`, wrong, created_at, updated_at) VALUES (1, 1, 1, "11.01 패치내역", "저는 거짓말 안합니다", 3, 2, now(), now());
INSERT INTO `patches` (id, user_id, game_id, title, body, `right`, wrong, created_at, updated_at) VALUES (2, 2, 1, "11.02 패치내역", "좋아요 많이 눌러주세요", 10, 0, now(), now());
INSERT INTO `patches` (id, user_id, game_id, title, body, `right`, wrong, created_at, updated_at) VALUES (3, 3, 2, "신규 아이콘 카드 업데이트", "아이콘 박지성 너무좋아요", 1, 11, now(), now());
INSERT INTO `patches` (id, user_id, game_id, title, body, `right`, wrong, created_at, updated_at) VALUES (4, 4, 3, "신규 알고리즘 업데이트", "저는 거짓말 안합니다", 20, 2, now(), now());
INSERT INTO `patches` (user_id, game_id, title, body) VALUES (1, 1, "TEST TITLE", "TEST BODY");

--checked_patch
INSERT INTO `checked_patch` (id, patch_id, user_id) VALUES (1, 1, 1);
INSERT INTO `checked_patch` (id, patch_id, user_id) VALUES (2, 2, 1);
INSERT INTO `checked_patch` (id, patch_id, user_id) VALUES (3, 3, 1);
INSERT INTO `checked_patch` (id, patch_id, user_id) VALUES (4, 1, 2);
INSERT INTO `checked_patch` (patch_id, user_id, is_first, `right`, wrong) VALUES (1, 3, true, false, false);
INSERT INTO `checked_patch` (patch_id, user_id, is_first, `right`, wrong) VALUES (2, 3, true, true, false);
INSERT INTO `checked_patch` (patch_id, user_id, is_first, `right`, wrong) VALUES (3, 3, true, false, true);

--patch_comment
INSERT INTO `patch_comment` (id, patch_id, user_id, comment, created_at, updated_at) VALUES (1, 1, 1, "밸런스 개판이네 개발자 누구냐", now(), now());
INSERT INTO `patch_comment` (id, patch_id, user_id, comment, created_at, updated_at) VALUES (2, 1, 2, "너가 못하는거 아닌가?", now(), now());
