import net.sf.json.JSONObject
import org.junit.Test

class JsonGen {
  @Test
  def genJson(): Unit ={
    val json = JSONObject.fromObject("{\n    \"_id\": \"5994f6d6917cb7151a7c7afa\",\n    \"index\": 0,\n    \"guid\": \"21b9fb4a-817f-4147-bdbe-077671e6924d\",\n    \"isActive\": false,\n    \"balance\": \"$3,051.23\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 39,\n    \"eyeColor\": \"green\",\n    \"name\": {\n      \"first\": \"Stella\",\n      \"last\": \"Petersen\"\n    },\n    \"company\": \"ZIDOX\",\n    \"email\": \"stella.petersen@zidox.biz\",\n    \"phone\": \"+1 (990) 536-2358\",\n    \"address\": \"137 Lott Avenue, Cavalero, Alaska, 2840\",\n    \"about\": \"Deserunt voluptate eu Lorem eiusmod non tempor et occaecat. Quis deserunt proident magna non amet. Magna consequat deserunt adipisicing consectetur ipsum est Lorem reprehenderit veniam Lorem laborum et. Labore pariatur enim proident nulla. Ullamco laboris ut voluptate veniam enim in. Consequat cillum culpa aute ea mollit ullamco commodo elit laborum nulla exercitation. Duis dolor id do consequat reprehenderit incididunt esse cillum labore.\",\n    \"registered\": \"Sunday, September 13, 2015 8:37 PM\",\n    \"latitude\": \"-65.019455\",\n    \"longitude\": \"-167.873058\",\n    \"tags\": [\n      \"et\",\n      \"tempor\",\n      \"deserunt\",\n      \"do\",\n      \"ea\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Valentine Mullen\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Lupe Bennett\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Greta Blackburn\"\n      }\n    ],\n    \"greeting\": \"Hello, Stella! You have 6 unread messages.\",\n    \"favoriteFruit\": \"banana\"\n  },\n  {\n    \"_id\": \"5994f6d6273a4023ebd016dc\",\n    \"index\": 1,\n    \"guid\": \"b5ae37d7-255d-43ad-bd5e-95117bdb516c\",\n    \"isActive\": false,\n    \"balance\": \"$2,548.16\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 39,\n    \"eyeColor\": \"green\",\n    \"name\": {\n      \"first\": \"Robyn\",\n      \"last\": \"George\"\n    },\n    \"company\": \"ANIXANG\",\n    \"email\": \"robyn.george@anixang.io\",\n    \"phone\": \"+1 (915) 482-2098\",\n    \"address\": \"182 Moore Street, Brookfield, Virgin Islands, 627\",\n    \"about\": \"Labore adipisicing id dolor qui ea commodo exercitation nulla minim ex nisi nisi. Veniam eu elit incididunt elit do aliqua culpa. Velit reprehenderit cillum enim fugiat commodo nisi aliqua labore elit proident tempor consectetur. Labore mollit occaecat sunt tempor tempor cupidatat do excepteur ut do dolor dolore elit irure.\",\n    \"registered\": \"Saturday, January 21, 2017 11:42 AM\",\n    \"latitude\": \"-46.427025\",\n    \"longitude\": \"-1.184509\",\n    \"tags\": [\n      \"enim\",\n      \"aliquip\",\n      \"ipsum\",\n      \"consequat\",\n      \"duis\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Allison Dodson\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Rodriquez Murphy\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Catherine Ortiz\"\n      }\n    ],\n    \"greeting\": \"Hello, Robyn! You have 7 unread messages.\",\n    \"favoriteFruit\": \"banana\"\n  },\n  {\n    \"_id\": \"5994f6d647b96557d6b3dc33\",\n    \"index\": 2,\n    \"guid\": \"2eab16d6-9ad0-4806-9ff8-d10644da66d8\",\n    \"isActive\": false,\n    \"balance\": \"$1,301.24\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 36,\n    \"eyeColor\": \"green\",\n    \"name\": {\n      \"first\": \"Russo\",\n      \"last\": \"Hopper\"\n    },\n    \"company\": \"RETROTEX\",\n    \"email\": \"russo.hopper@retrotex.com\",\n    \"phone\": \"+1 (985) 572-3068\",\n    \"address\": \"902 Highland Avenue, Bainbridge, New Mexico, 9902\",\n    \"about\": \"Laborum labore in laboris irure. Sit aliqua adipisicing sunt veniam. Exercitation ex aute excepteur voluptate occaecat. Commodo quis consequat adipisicing qui dolore pariatur laborum anim irure est deserunt proident aliquip tempor. Adipisicing dolore aliqua consequat dolor ut eiusmod mollit sit. Adipisicing in qui officia cupidatat exercitation minim nulla est deserunt.\",\n    \"registered\": \"Wednesday, June 17, 2015 3:56 PM\",\n    \"latitude\": \"84.757735\",\n    \"longitude\": \"121.472852\",\n    \"tags\": [\n      \"et\",\n      \"sit\",\n      \"minim\",\n      \"cillum\",\n      \"est\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Sheri Bowers\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Graves Pennington\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Hines Cox\"\n      }\n    ],\n    \"greeting\": \"Hello, Russo! You have 10 unread messages.\",\n    \"favoriteFruit\": \"banana\"\n  },\n  {\n    \"_id\": \"5994f6d6f1cd6df42a393446\",\n    \"index\": 3,\n    \"guid\": \"954f5d77-20ac-4186-bc25-c8adfbbd3dda\",\n    \"isActive\": false,\n    \"balance\": \"$1,030.08\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 24,\n    \"eyeColor\": \"blue\",\n    \"name\": {\n      \"first\": \"Farrell\",\n      \"last\": \"Osborn\"\n    },\n    \"company\": \"XERONK\",\n    \"email\": \"farrell.osborn@xeronk.net\",\n    \"phone\": \"+1 (855) 508-2348\",\n    \"address\": \"430 Kingston Avenue, Riverton, West Virginia, 1647\",\n    \"about\": \"Consequat id incididunt dolore aliqua. Sunt deserunt laboris commodo consectetur aliqua excepteur pariatur ipsum tempor laborum proident Lorem id. In culpa ad labore culpa ex Lorem labore nisi officia consequat. Elit sunt nisi qui anim ad aute eu qui fugiat tempor nisi voluptate proident magna. Veniam est incididunt veniam anim mollit cillum ut aute irure non nulla consectetur velit veniam. Et laboris irure et excepteur.\",\n    \"registered\": \"Monday, August 22, 2016 11:24 PM\",\n    \"latitude\": \"-34.760184\",\n    \"longitude\": \"147.073716\",\n    \"tags\": [\n      \"ipsum\",\n      \"qui\",\n      \"ex\",\n      \"dolore\",\n      \"ea\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Bridges Noble\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Beverly Lowe\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Eula Frazier\"\n      }\n    ],\n    \"greeting\": \"Hello, Farrell! You have 7 unread messages.\",\n    \"favoriteFruit\": \"apple\"\n  },\n  {\n    \"_id\": \"5994f6d6be52e75bcf0b3a6f\",\n    \"index\": 4,\n    \"guid\": \"6272965f-5cd8-4dc6-87e0-a28c47007a52\",\n    \"isActive\": false,\n    \"balance\": \"$2,631.76\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 20,\n    \"eyeColor\": \"blue\",\n    \"name\": {\n      \"first\": \"Berry\",\n      \"last\": \"Clayton\"\n    },\n    \"company\": \"ASSURITY\",\n    \"email\": \"berry.clayton@assurity.us\",\n    \"phone\": \"+1 (864) 590-3931\",\n    \"address\": \"530 Cypress Court, Linganore, New Hampshire, 1455\",\n    \"about\": \"Sint veniam ut sint dolore anim aute fugiat duis occaecat velit. Ea amet consequat magna labore elit ea laboris. Dolore consequat nisi laborum duis voluptate veniam proident velit commodo consequat dolore deserunt. Culpa anim nulla ad dolor dolore nisi nulla labore ut in duis pariatur aliqua qui. Non proident culpa id adipisicing duis velit Lorem aliqua ut eu. Cillum irure est esse proident adipisicing et pariatur fugiat. Lorem nostrud ut tempor nisi commodo fugiat cillum nisi non ex non adipisicing.\",\n    \"registered\": \"Tuesday, December 8, 2015 5:20 PM\",\n    \"latitude\": \"87.295661\",\n    \"longitude\": \"2.634152\",\n    \"tags\": [\n      \"elit\",\n      \"cillum\",\n      \"in\",\n      \"commodo\",\n      \"sint\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Mildred Sears\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Carmella Hooper\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Kimberly Ayers\"\n      }\n    ],\n    \"greeting\": \"Hello, Berry! You have 7 unread messages.\",\n    \"favoriteFruit\": \"banana\"\n  },\n  {\n    \"_id\": \"5994f6d69d9971adb74ad7b7\",\n    \"index\": 5,\n    \"guid\": \"757d5301-4f81-4750-9f8f-cd2cc148c7d7\",\n    \"isActive\": false,\n    \"balance\": \"$2,692.50\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 40,\n    \"eyeColor\": \"green\",\n    \"name\": {\n      \"first\": \"Hicks\",\n      \"last\": \"Mayer\"\n    },\n    \"company\": \"COLAIRE\",\n    \"email\": \"hicks.mayer@colaire.biz\",\n    \"phone\": \"+1 (820) 529-3943\",\n    \"address\": \"152 Russell Street, Bethpage, Missouri, 231\",\n    \"about\": \"Culpa proident dolore ea occaecat aliquip anim aute dolor fugiat commodo nisi. Enim reprehenderit consequat culpa qui reprehenderit tempor elit qui in id. In consectetur culpa ut minim tempor. Do ad ipsum veniam proident enim cillum fugiat magna eiusmod mollit elit enim commodo non. Laboris adipisicing eiusmod non do pariatur sunt mollit.\",\n    \"registered\": \"Tuesday, July 19, 2016 9:13 PM\",\n    \"latitude\": \"38.710257\",\n    \"longitude\": \"-122.192283\",\n    \"tags\": [\n      \"dolore\",\n      \"mollit\",\n      \"anim\",\n      \"nostrud\",\n      \"occaecat\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Johnston Scott\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Adrienne Faulkner\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Nellie Britt\"\n      }\n    ],\n    \"greeting\": \"Hello, Hicks! You have 10 unread messages.\",\n    \"favoriteFruit\": \"apple\"\n  },\n  {\n    \"_id\": \"5994f6d6a82571c7f96f2eaf\",\n    \"index\": 6,\n    \"guid\": \"8d7bf609-4a46-41fb-a8e9-cac43e91e58e\",\n    \"isActive\": false,\n    \"balance\": \"$3,952.07\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 37,\n    \"eyeColor\": \"blue\",\n    \"name\": {\n      \"first\": \"Reeves\",\n      \"last\": \"Park\"\n    },\n    \"company\": \"QUANTASIS\",\n    \"email\": \"reeves.park@quantasis.co.uk\",\n    \"phone\": \"+1 (958) 463-2002\",\n    \"address\": \"895 Essex Street, Smeltertown, Illinois, 6345\",\n    \"about\": \"Voluptate labore mollit est sunt laboris ex sit do voluptate ullamco quis laborum reprehenderit magna. Laborum consectetur ullamco dolore ad amet nostrud enim. Excepteur cupidatat incididunt eu est ipsum id. Proident et laboris deserunt sunt mollit labore cillum duis anim amet laborum consectetur consequat proident. Esse incididunt irure exercitation irure ad ullamco adipisicing exercitation.\",\n    \"registered\": \"Wednesday, January 29, 2014 8:03 PM\",\n    \"latitude\": \"-88.394536\",\n    \"longitude\": \"122.114806\",\n    \"tags\": [\n      \"occaecat\",\n      \"mollit\",\n      \"nisi\",\n      \"fugiat\",\n      \"id\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Gladys Christian\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Ursula Shelton\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Marva Livingston\"\n      }\n    ],\n    \"greeting\": \"Hello, Reeves! You have 6 unread messages.\",\n    \"favoriteFruit\": \"apple\"\n  },\n  {\n    \"_id\": \"5994f6d666dea5044b9b55b8\",\n    \"index\": 7,\n    \"guid\": \"b3bc0a33-b772-48c1-8943-b406a44686a5\",\n    \"isActive\": false,\n    \"balance\": \"$3,873.09\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 37,\n    \"eyeColor\": \"brown\",\n    \"name\": {\n      \"first\": \"Ryan\",\n      \"last\": \"Bell\"\n    },\n    \"company\": \"BESTO\",\n    \"email\": \"ryan.bell@besto.name\",\n    \"phone\": \"+1 (904) 574-3308\",\n    \"address\": \"271 Furman Avenue, Broadlands, Alabama, 105\",\n    \"about\": \"Id sint enim amet nulla ut commodo enim in ea. Tempor nulla est cupidatat laborum mollit. Tempor ad esse sunt commodo sunt irure deserunt. Reprehenderit culpa ullamco irure ex voluptate nisi consequat ad aliqua aliquip.\",\n    \"registered\": \"Saturday, July 19, 2014 2:27 AM\",\n    \"latitude\": \"8.271399\",\n    \"longitude\": \"-151.00626\",\n    \"tags\": [\n      \"pariatur\",\n      \"fugiat\",\n      \"amet\",\n      \"incididunt\",\n      \"sunt\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Solis Church\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Gomez Mclean\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Woodard Powell\"\n      }\n    ],\n    \"greeting\": \"Hello, Ryan! You have 5 unread messages.\",\n    \"favoriteFruit\": \"strawberry\"\n  },\n  {\n    \"_id\": \"5994f6d64356260de5d42e2d\",\n    \"index\": 8,\n    \"guid\": \"fda9680c-d196-4a92-bf01-165cb5fec7cb\",\n    \"isActive\": false,\n    \"balance\": \"$2,080.11\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 39,\n    \"eyeColor\": \"green\",\n    \"name\": {\n      \"first\": \"Holmes\",\n      \"last\": \"Haynes\"\n    },\n    \"company\": \"STELAECOR\",\n    \"email\": \"holmes.haynes@stelaecor.ca\",\n    \"phone\": \"+1 (904) 522-2275\",\n    \"address\": \"141 Garden Place, Enoree, American Samoa, 477\",\n    \"about\": \"Amet ut commodo ullamco minim ad esse aute eiusmod ipsum quis occaecat ut Lorem minim. In dolor fugiat labore consequat ullamco qui duis esse commodo officia ullamco nulla proident. Magna fugiat enim fugiat irure ullamco amet proident eiusmod dolore. Duis pariatur deserunt aliqua laboris irure quis irure elit.\",\n    \"registered\": \"Friday, November 20, 2015 7:30 PM\",\n    \"latitude\": \"-83.689881\",\n    \"longitude\": \"37.243359\",\n    \"tags\": [\n      \"minim\",\n      \"enim\",\n      \"occaecat\",\n      \"labore\",\n      \"cillum\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Acosta Frederick\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Jewell Sanford\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Lynda Bullock\"\n      }\n    ],\n    \"greeting\": \"Hello, Holmes! You have 8 unread messages.\",\n    \"favoriteFruit\": \"banana\"\n  },\n  {\n    \"_id\": \"5994f6d665118cb274ff697b\",\n    \"index\": 9,\n    \"guid\": \"45cac5c3-3606-404f-b8f0-2881a4bf6f27\",\n    \"isActive\": true,\n    \"balance\": \"$2,939.02\",\n    \"picture\": \"http://placehold.it/32x32\",\n    \"age\": 33,\n    \"eyeColor\": \"blue\",\n    \"name\": {\n      \"first\": \"Jannie\",\n      \"last\": \"James\"\n    },\n    \"company\": \"XUMONK\",\n    \"email\": \"jannie.james@xumonk.org\",\n    \"phone\": \"+1 (835) 405-3270\",\n    \"address\": \"439 Kings Place, Englevale, North Carolina, 103\",\n    \"about\": \"Laboris quis velit ad ad pariatur. Excepteur aliquip consequat consequat voluptate ut deserunt irure excepteur. Sint magna incididunt irure minim culpa voluptate sit reprehenderit proident non Lorem do officia. Anim incididunt laborum veniam aliqua. Anim anim labore adipisicing veniam officia qui est culpa minim commodo mollit esse.\",\n    \"registered\": \"Thursday, May 1, 2014 7:13 PM\",\n    \"latitude\": \"-56.367616\",\n    \"longitude\": \"-99.531022\",\n    \"tags\": [\n      \"dolore\",\n      \"aliqua\",\n      \"velit\",\n      \"ad\",\n      \"incididunt\"\n    ],\n    \"range\": [\n      0,\n      1,\n      2,\n      3,\n      4,\n      5,\n      6,\n      7,\n      8,\n      9\n    ],\n    \"friends\": [\n      {\n        \"id\": 0,\n        \"name\": \"Beatrice Hester\"\n      },\n      {\n        \"id\": 1,\n        \"name\": \"Floyd Haney\"\n      },\n      {\n        \"id\": 2,\n        \"name\": \"Lynn Lindsey\"\n      }\n    ],\n    \"greeting\": \"Hello, Jannie! You have 8 unread messages.\",\n    \"favoriteFruit\": \"strawberry\"\n  }")
    println(json)
  }
}