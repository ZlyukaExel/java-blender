[33mcommit 680a91c58e69881d946a7245823bb06c32681c71[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m, [m[1;31morigin/master[m[33m, [m[1;31morigin/HEAD[m[33m)[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Fri Jan 23 19:47:44 2026 +0300

    lumination menu added

[33mcommit aa02478e87322164a18019c64ad89307cc72a84d[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 17:58:36 2026 +0300

    linked some methods to ModelMenu

[33mcommit 14ce7472b50c133f3cdc90f5fadba8a7bd29b36d[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 17:40:16 2026 +0300

    fixed normals calculator after loading model

[33mcommit f5ebcc7f26f5da2f4f9086b81c6c6ee812805aab[m[33m ([m[1;31morigin/pozz2[m[33m, [m[1;32mpozz2[m[33m)[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 17:17:57 2026 +0300

    manual merge with PashaSuperProgrammists commit

[33mcommit cea26b5c434eb021c488bae73edc87f0c9b6b740[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 12:44:19 2026 +0300

    now modelMesh is drawn on top of model

[33mcommit 914a97decfb92bc8eaf6d06a6aeaed52fd542323[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 12:28:30 2026 +0300

    replaced normals calculation from read method of OBJReader to loadModel method of BlenderController

[33mcommit 209442f3b88337cdc390b4f5ab3817b78fa8a314[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 12:19:35 2026 +0300

    render optimization

[33mcommit 1f4fedcde7216da9c15840a49852243cfceb9f23[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 11:30:08 2026 +0300

    small restucture and rework of shaders

[33mcommit 1027a262ae8e55aab9e6dc6b29a48d8cb2a0be7f[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 23 11:07:24 2026 +0300

    added getTransformedModel method to Model, Color of material is no longer final

[33mcommit e210fdff9df847627d6e7ec919b90942da97ea9a[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 20:12:09 2026 +0300

    reworked brilliance_factor a bit, now it acts like powers of 2

[33mcommit aade51df6632d154f73f64710d56d1e8da6553cc[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 19:55:08 2026 +0300

    render is fully done! restructured it again, added brilliance factor for material

[33mcommit 08afa8db9095d897faad9f4083f97620329fbb05[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 16:19:50 2026 +0300

    clean up in renderEngine

[33mcommit c7603d43dff03ec0f5e37db711aa07231251a3bb[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 16:02:52 2026 +0300

    REWROTE RENDER AGAINNNN

[33mcommit d91325631830838ce8651ed203cd05660ed286f2[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 15:19:40 2026 +0300

    added some setters/getters to model

[33mcommit ae7fff94f8cb8e6e579b50adf4c7b6040f3741f7[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 13:41:23 2026 +0300

    restructured a bit

[33mcommit af35bbdc636ba8e384e987ab15f6d2806ad35d0a[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 13:28:12 2026 +0300

    added intensity for lightSource

[33mcommit b8eba4191e6e9bd5e1e4abb3502f52d5fcda1600[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 12:56:30 2026 +0300

    lightSource small fix, covered with tests. small render optimization. now camera spawns at (0, 0, 75.0)

[33mcommit a2fe83e506f9a262f212d93ac5ff3eff92b348b4[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Thu Jan 22 12:12:34 2026 +0300

    added setters/getters for RGB in lightSource

[33mcommit 32aacab4adea1d7e691441e1ea506a6700b86938[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Wed Jan 21 20:01:00 2026 +0300

    now rotation values are degrees

[33mcommit d03fc1d2ab79090ccd9ea97eac2df91557619f3d[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Wed Jan 21 19:52:40 2026 +0300

    now camera spawns at (0, 0, 200). small render fix

[33mcommit ffb5188481642ea478da4b3f7a83759a42cee75e[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Wed Jan 21 19:35:22 2026 +0300

    REWROTE WHOLE RENDER. fixed lumination. now you can move lightSources.

[33mcommit b28df21ee905e66dbd3314327f037d7b432f20ca[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Wed Jan 21 16:49:22 2026 +0300

    added some changes to SceneObject, now you cannot do some transformations to camera/lightSource. added some constuctors to camera

[33mcommit b393666e9e9ab21577782a1430fe2a3487519808[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 19:05:01 2026 +0300

    object active setting ui added

[33mcommit 05b9d8f6104075fb4b659eb6f5a49a087edceb7f[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 18:49:33 2026 +0300

    polygon grid ui switch added

[33mcommit 35c26aac3935954222e0e7cf7cf848942b62e8c8[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 16:36:32 2026 +0300

    RENDER HUGE FIX(still fixing btw). Fixed viewMatrix in Camera. Added useTexture flag in Material

[33mcommit 11f9b0b770c1cfad90388d926573cfb044ba7a62[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 14:58:34 2026 +0300

    deleted old triangulator, restructured project a bit, aded tests for triangulator

[33mcommit a93f4c4a946da41b2339c29edb516a8cef6c6d61[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 14:34:13 2026 +0300

    added ZBuffer tests. now render works with different lightSource colors.

[33mcommit 1c742a84e9e96e4f34284c03935b8041ea4d6f9e[m
Merge: e371b53 48f6f3d
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 13:54:18 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit e371b536e3236ae9cfcb3ac92d26b386a9fefeb7[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 13:54:02 2026 +0300

    fixed ZBuffer

[33mcommit 48f6f3dc549858ddcb65aee7765a79b9f07f1765[m
Merge: 0fdcdba 2f457b9
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 13:51:36 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 0fdcdbad368ba13f3302749c936cbf9fa00bf960[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 13:51:28 2026 +0300

    some more file improvements

[33mcommit 2f457b93b37137d38d9b6b2676cea38435f72403[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 13:47:23 2026 +0300

    lumination is working now(w, w/o texture), position of lightSource is not changing! neither position of camera does!

[33mcommit 75653f82828ce4eac502371d42bf1e7f7feed37b[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 13:41:10 2026 +0300

    some filereading and naming changes

[33mcommit 83bebb6cae97f6ef1bab53da830c6075703831fd[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Tue Jan 20 13:12:54 2026 +0300

    srollable double fields added

[33mcommit b7a11ab7dc5b82a044a9fe6ea9ccddcadc090f55[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Tue Jan 20 12:19:57 2026 +0300

    render HUGE fix, now works really well(still problem with view/projection matrix)

[33mcommit 30b49b84398c311aa4baabffd754fd05420fba90[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 18:57:23 2026 +0300

    normals calculator, triangulator, applying texture now works by pressing buttons in menu. still fixing this сучий render..

[33mcommit 9965f43e1a14a9a9f03082875fc395b6649c9bee[m
Merge: 2f5f838 92f2ed2
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 16:09:23 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 92f2ed208e3068c664bac354f056cb90369d66c6[m
Merge: 1a186b4 5e741d0
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Mon Jan 19 16:06:24 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 1a186b47438deb7601ccaeb4d3999d2ede7fe841[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Mon Jan 19 16:06:11 2026 +0300

    added some features in ui

[33mcommit 2f5f83860708793100624b608e568346c7945869[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 15:22:23 2026 +0300

    render fix5, deleted some tests

[33mcommit 5e741d06f0ca189806922b74a36f164ba213ab8f[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 15:22:23 2026 +0300

    render fix5

[33mcommit 50c2e6e8a09089f569f89305b2d57d684ebc128c[m
Merge: 7d30293 a753f88
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 12:06:31 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 7d302931f0f25f1c29619e156a21cab386339054[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Jan 19 12:04:17 2026 +0300

    render fix4

[33mcommit a753f8848e5ebaf6ae4d3f4d5fcd01237b915ecb[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Mon Jan 19 11:56:12 2026 +0300

    ui enhanced

[33mcommit 5191ae58402f26c6e708c6690c21616117b153c0[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sun Jan 18 19:12:01 2026 +0300

    unique object names added

[33mcommit 0b55ca43eb27228f0b75ec7fdf146fc6da95e991[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sun Jan 18 14:22:15 2026 +0300

    returned normalization

[33mcommit 1dc0ffbc0e3c98fb7eaf58fb017d3f790eab1499[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sun Jan 18 14:18:10 2026 +0300

    ui transform functionality

[33mcommit 3aa295ac0a004d59eca1be283158bcf989301d49[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 20:59:47 2026 +0300

    removed my debug

[33mcommit 7c11c56db1b1dc9a168dcbbdc38e50ecfcae9dbb[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 20:04:55 2026 +0300

    fixed obj writer

[33mcommit d1185ffe958c8dfdc77bd6202e1962ae37cbc985[m
Merge: eafc43d aa72a8f
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 18:54:50 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit eafc43dd4836fdf496b25bb42ecf9da6ed74b35d[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 18:54:37 2026 +0300

    render fix3

[33mcommit aa72a8f3f1d3cced132061cc06c46f110b97c543[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:54:09 2026 +0300

    something

[33mcommit 7cef1a2c359b6a7aa1821d2f51f3c4b91529f1c2[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:31:33 2026 +0300

    debug removed

[33mcommit 811de186115d70599c3a03fc961461f1b52015ea[m
Merge: ac3f84b 63d2f42
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:30:24 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 63d2f42bd71bc0beec41fa9a2af5f91fc91f520a[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 18:28:21 2026 +0300

    render fix2

[33mcommit ac3f84b993310f8f4d050b30f58bd027bdec0dfb[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:27:34 2026 +0300

    QoL changes

[33mcommit 2575d152ea315ceecdcf716034555143c4dd8f98[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:10:36 2026 +0300

    can move objects now

[33mcommit 9c4d339835d7dd8bc340e0b58be49501651f6093[m
Merge: a79ffc9 20b1f96
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:06:10 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit a79ffc93f2721407ae1f9a4bc6d71d28a96e2bc8[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 18:06:06 2026 +0300

    i need to pull

[33mcommit 20b1f96d4de83553afa59fbf17ed5c36007f2c01[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 17:56:59 2026 +0300

    added move method to SceneObject

[33mcommit de8a87977c57223bb434c64bee2aa41fd3700228[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 17:47:32 2026 +0300

    fixing render

[33mcommit ab86b1210fcfd329f60062b5b11bcbb80468192d[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 17:09:24 2026 +0300

    fixed app

[33mcommit 6afe22b9355a5815d71dadd50e792f2ac38aa399[m
Merge: 4a9cc99 e081cf5
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 16:00:17 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 4a9cc994c1e69bd8dbfe6974f901f21899cadaff[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 16:00:13 2026 +0300

    finished working on renderEngine

[33mcommit e081cf5c89e04e2a80a8b6367cfea85b796b695d[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 15:51:09 2026 +0300

    ui right box added (unfunctional)

[33mcommit b2eaf2baa479213dd1e1415be5b63936e4d34152[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 15:03:52 2026 +0300

    merging

[33mcommit aaea8d1f70a5e1ae7e5ae971281ee4e6e2853e9f[m
Merge: 182128c 498703f
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 15:03:11 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit 182128c818336c14e2b469a31a01bc3da1f21629[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 15:00:30 2026 +0300

    reworking render

[33mcommit 498703ff37f1a8b50f5b205a39bbd95f4326b69f[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 14:27:24 2026 +0300

    fixed file dublication and added models and light sources lists in the scene

[33mcommit e59aad5a45c9fbe7b40e2365c8cd71872e56de0a[m
Merge: d7fbc4a cd119ca
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 14:10:57 2026 +0300

    Merge remote-tracking branch 'origin/master'
    
    # Conflicts:
    #       src/main/java/super_puper_mega_programmisty/blender/graphics/camera/Camera.java
    #       src/main/java/super_puper_mega_programmisty/blender/graphics/light/LightSource.java
    #       src/main/java/super_puper_mega_programmisty/blender/graphics/model/Model.java

[33mcommit d7fbc4a834524ce4438fbf00ec09aa61cc24ca4c[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sat Jan 17 14:05:00 2026 +0300

    working on ui

[33mcommit cd119ca811db1126841dae011ccb377e2be78ebb[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 13:18:01 2026 +0300

    camera, model and lightSource now extend SceneObject(model still implements Transformable)2

[33mcommit b855cecd9a4b510174725b2b0de2abaeff1a6fde[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 13:10:41 2026 +0300

    camera, model and lightSource now extend SceneObject(model still implements Transformable)

[33mcommit 43f20fc52db7637c5d7c5873bd95d278360e1938[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Sat Jan 17 12:27:01 2026 +0300

    tests dop

[33mcommit f1bbc858e426593d9956a51bda9513aeb2781c7b[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 12:17:45 2026 +0300

    transform fix

[33mcommit c3482a088fd96f3e316717feb7fc23843548be1c[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Sat Jan 17 11:57:36 2026 +0300

    fixed rwiter and reader, REMOVED redundant Main, added empty class for SceneObject

[33mcommit 7443654457a2757e45ff3519c6b6e3614b2e924e[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 22:40:09 2026 +0300

    fix1 for view and projection matrixes

[33mcommit f2af795dfa61875036c83dcb16f0010d8a583f30[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Fri Jan 16 21:55:11 2026 +0300

    cleanup

[33mcommit 14bed2321224b524ea4120e1c399225be651f231[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Fri Jan 16 20:51:25 2026 +0300

    moved packages

[33mcommit 896f8ea2b539982f2a6e8d87dde143756585f19b[m
Merge: cc7529a 50a85a6
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Fri Jan 16 20:41:22 2026 +0300

    Merge branch 'master' of https://github.com/ZlyukaExel/super-mega-puper-blender-jabba

[33mcommit cc7529aa51e6152dc747c2ab1dbe4518ee296505[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Fri Jan 16 20:35:28 2026 +0300

    working on scene and ui

[33mcommit 50a85a6e42ece6ca788c74b36c1150e0f4d56147[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Fri Jan 16 19:48:49 2026 +0300

    перенос пакета

[33mcommit 469754fb4d56609db5b9a38dbd49c4fcd72facf2[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 19:00:47 2026 +0300

    small fix

[33mcommit 04caf52b263ebfac7cab35e05f139c5e1e8a391a[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 18:06:55 2026 +0300

    finished simple modelRender method. fixed failing while building project

[33mcommit e4e7b6b381955fda6264ea97dc4b530d6fd5f997[m
Merge: 575b629 3556285
Author: Arina <arina_gubina06@mail.ru>
Date:   Fri Jan 16 17:47:08 2026 +0300

    2

[33mcommit 575b629c4b064e12618c521dc78f20c87a0b28c7[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Fri Jan 16 17:46:03 2026 +0300

    Убран float из camera

[33mcommit 3556285b83ec7bbda59909f01cc0ff18a8a1b5af[m
Merge: 30ad617 18ad0c9
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 17:21:15 2026 +0300

    merging

[33mcommit 30ad617510212ca7119360a74f21d755f54f25af[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 17:16:53 2026 +0300

    Added PolydonRasterization.

[33mcommit 18ad0c9ec6accdd02bf741c105d4a2ef4603a744[m
Merge: 007467c a42c6ca
Author: Arina <arina_gubina06@mail.ru>
Date:   Fri Jan 16 17:06:57 2026 +0300

    4z.2

[33mcommit 007467c2216e466d330671bc89be296912c4a099[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Fri Jan 16 17:05:19 2026 +0300

    4z.1

[33mcommit a42c6ca8d363758524445da287115e8daaaf5ead[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 16:07:51 2026 +0300

    removed .idea

[33mcommit 487123979dd737cc4541c6bf8caf41ab93f5f54d[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Fri Jan 16 16:03:54 2026 +0300

    Added triandulator(no tests). Added TriangleRasterization(no tests) with ZBuffer, working on RenderEngine

[33mcommit 389828d59a048efd92be7857e7598b58ddc6e7ae[m
Merge: 3a422df b4ca130
Author: Arina <arina_gubina06@mail.ru>
Date:   Thu Jan 15 15:30:22 2026 +0300

    4.r.s.2

[33mcommit 3a422df6a9fc6a962834cd9b441f3537c52dbacb[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Thu Jan 15 15:23:23 2026 +0300

    4.r.s

[33mcommit b4ca130f3158dcc46df4cf0b888bc3b81877a912[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 15:10:56 2025 +0300

    added NormalsCalculator

[33mcommit a5ff840307f65cc534d6f430399072f15b677e36[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 12:55:02 2025 +0300

    added tests for triangulation. Started linking it ti project

[33mcommit e43be2f7d95798eb481b7c8a1d67ab10b9f958ef[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 12:29:07 2025 +0300

    relinked everything to math module.

[33mcommit 747a09d8b6e1fd47edd3bd1e39538ae4f434a305[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 11:58:44 2025 +0300

    structured project a bit. linked OBJWriter to math module. Added empty Scene and LightSource classes

[33mcommit d2957261644f341ceecc425a8485ad8b794fdb22[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 10:47:03 2025 +0300

    removed .idea

[33mcommit ad08bf8b02130b2b2d20511b579d63d7491a4115[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Mon Dec 29 10:40:32 2025 +0300

    fixed pom.xml

[33mcommit 26604d9f93e4bd6fb09096cc7c56c59c0f709b6b[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Sun Dec 28 12:47:37 2025 +0300

    Affine

[33mcommit c227f63fc0518a3da986b2aa8219a8c692706f88[m
Merge: 5a22732 4c11fa3
Author: Arina <arina_gubina06@mail.ru>
Date:   Wed Dec 24 23:10:52 2025 +0300

    Camera

[33mcommit 5a22732523f56e11d1d028498d287deb5758747b[m
Author: Arina <arina_gubina06@mail.ru>
Date:   Wed Dec 24 23:07:25 2025 +0300

    Camera

[33mcommit 4c11fa35c4d5c56376ffeb3964c4a0f3fbb1477c[m
Author: PoZZ_rar <iliakonchakov54@gmail.com>
Date:   Wed Dec 24 20:15:25 2025 +0300

    added math module with vectors and matrix, covered with tests

[33mcommit b5567f97a9e152f28195228103b3eabad85d2fad[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Wed Dec 24 18:13:57 2025 +0300

    working on structure

[33mcommit c52bf1ea4d5347b77aaac64b3205934fba853624[m
Author: ZlyukaExel <fantom.exel@gmail.com>
Date:   Sun Dec 21 17:53:42 2025 +0300

    initial commit
