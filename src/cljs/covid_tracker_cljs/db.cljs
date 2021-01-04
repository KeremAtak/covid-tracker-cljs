(ns covid-tracker-cljs.db)

(def app-db
  {:background-color 50
   :provinces
   [{:province "Uusimaa" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Pohjois-Karjala" :municipalities ["Joensuu" "Liperi" "Rääkkylä" "Nurmes"
                                                  "Lieksa" "Outokumpu" "Polvijärvi" "Kitee"
                                                  "Kontiolahti"]}
    {:province "Uusimaa1" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa2" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa3" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa4" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}
    {:province "Uusimaa5" :municipalities ["Helsinki" "Espoo" "Vantaa" "Lohja"]}]
   :scale 1
   :shapes {:ahvenanmaa {:points [:g6 :h6 :i6 :j6 :k6]
                         :offset [20 200]
                         :scale 1}
            :etela-karjala {:points [:d2 :c3 :h3 :i3 :d3 :e3 :f3 :g3 :j2 :i2 :h2 :g2 :c2]
                            :offset [130 165]
                            :scale 1}
            :etela-pohjanmaa {:points [:m5 :i5 :z2 :w2 :v2 :h5 :d5 :e5 :f5 :u5 :t5 :s5 :r5 :q5 :p5 :o5]
                              :offset [58 140]
                              :scale 1}
            :etela-savo {:points [:u1 :t1 :s1 :r1 :a2 :b2 :c2 :g2 :h2 :i2 :j2 :k2 :l2 :m2 :n2 :o2 :v1]
                         :offset [100 110]
                         :scale 1}
            :lappi {:points [:m6 :c :d :e :f :n6 :g :h :i :j :k :l :m :n :o :p :o6]
                    :offset [100 0]
                    :scale 1}
            :kainuu {:points [:r :e1 :f1 :g1 :h1 :i1 :j1 :k1 :l1 :m1 :u :t :s]
                     :offset [100 27]
                     :scale 1}
            :kanta-hame {:points [:u3 :t3 :s3 :z3 :a4 :l4 :m4 :o4 :p4]
                         :offset [40 210]
                         :scale 1}
            :keski-pohjanmaa {:points [:a1 :b1 :z :b3 :z2 :i5 :j5 :k5 :l5]
                              :offset [50 110]
                              :scale 1}
            :keski-suomi {:points [:w :v :z1 :w1 :v1 :o2 :n2 :m2 :p2 :q2 :t2 :u2 :v2 :w2 :z2 :b3 :w]
                          :offset [80 160]
                          :scale 1}
            :kymenlaakso {:points [:l2 :k2 :j2 :g3 :f3 :e3 :j3 :k3 :l3 :m3 :n3 :o3 :q3]
                          :offset [90 160]
                          :scale 1}
            :paijat-hame {:points [:p2 :m2 :l2 :q3 :o3 :n3 :p3 :s3 :t3 :u3 :v3 :w3 :q2]
                          :offset [60 190]
                          :scale 1}
            :pirkanmaa {:points [:h5 :v2 :u2 :t2 :q2 :w3 :v3 :u3 :p4 :o4 :m4 :n4 :v4 :w4 :z4 :a5 :b5 :d5]
                        :offset [30 180]
                        :scale 1}
            :pohjanmaa {:points [:l5 :k5 :j5 :i5 :m5 :o5 :p5 :q5 :r5 :s5 :t5 :u5 :g5 :d6 :c6 :b6 :a6 :z5 :w5]
                        :offset [-15 125]
                        :scale 1}
           :pohjois-karjala {:points [:m1 :l1 :k1 :j1 :f2 :e2 :d2 :c2 :b2 :a2 :r1 :q1 :p1 :o1 :n1]
                              :offset [150 50]
                              :scale 1}
            :pohjois-pohjanmaa {:points [:m :l :k :q :r :s :t :u :v :w :b3 :z :b1 :a1 :c1 :d1]
                                :offset [0 60]
                                :scale 1}
            :pohjois-savo {:points [:u :m1 :n1 :o1 :p1 :q1 :r1 :s1 :t1 :u1 :v1 :w1 :z1 :v]
                           :offset [120 60]
                           :scale 1}
            :satakunta {:points [:d5 :b5 :a5 :z4 :w4 :v4 :u4 :t4 :f6 :e6 :g5 :u5 :f5 :e5]
                         :offset [10 150]
                         :scale 1}
            :uusimaa {:points [:s3 :p3 :n3 :m3 :l3 :i4 :h4 :g4 :e4 :d4 :c4 :b4 :a4 :z3]
                      :offset [55 230]
                      :scale 1}
            :varsinais-suomi {:points [:t4 :u4 :v4 :n4 :m4 :l4 :a4 :b4 :c4 :d4 :q4 :r4 :s4]
                              :offset [10 200]
                              :scale 1}}
   :map-dots {:a [0 670] :b [400 670] :c [117 46] :d [155 89] :e [198 96] :f [238 11] :g [298 33]
              :h [285 104] :i [322 158] :j [298 205] :k [312 232] :l [267 276] :m [195 289] :n [172 273]
              :o [172 214] :p [163 154] :q [332 279] :r [321 296] :s [257 324] :t [259 361] :u [243 391]
              :v [223 428] :w [198 418] :z [179 413] :a1 [152 387] :b1 [164 387] :c1 [182 339] :d1 [208 328]
              :e1 [325 320] :f1 [321 341] :g1 [332 340] :h1 [334 365] :i1 [349 388] :j1 [328 410] :k1 [325 401]
              :l1 [290 400] :m1 [286 409] :n1 [296 442] :o1 [308 457] :p1 [305 472] :q1 [285 495] :r1 [299 507]
              :s1 [281 507] :t1 [271 523] :u1 [260 490] :v1 [240 494] :w1 [232 472] :z1 [219 462] :a2 [315 495]
              :b2 [328 521] :c2 [327 538] :d2 [343 529] :e2 [382 459] :f2 [360 430] :g2 [311 551] :h2 [292 553]
              :i2 [270 565] :j2 [254 576] :k2 [243 569] :l2 [232 569] :m2 [227 546] :n2 [234 546] :o2 [225 513]
              :p2 [213 536] :q2 [202 543] :t2 [183 543] :u2 [176 511] :v2 [158 496] :w2 [176 478] :z2 [170 448]
              :b3 [191 426] :c3 [325 554] :d3 [294 589] :e3 [279 607] :f3 [263 600] :g3 [253 590] :h3 [303 581]
              :i3 [300 590] :j3 [270 619] :k3 [253 615] :l3 [237 623] :m3 [234 614] :n3 [226 602] :o3 [234 593]
              :p3 [212 604] :q3 [225 580] :r3 [200 599] :s3 [189 598] :t3 [193 577] :u3 [184 566] :v3 [183 556]
              :w3 [198 555] :z3 [173 608] :a4 [150 607] :b4 [143 619] :c4 [140 635] :d4 [121 641] :e4 [118 658]
              :g4 [163 644] :h4 [166 649] :i4 [181 638] :l4 [137 600] :m4 [126 591] :n4 [128 583] :o4 [149 586]
              :p4 [163 570] :q4 [97 643] :r4 [73 635] :s4 [69 609] :t4 [73 577] :u4 [93 588] :v4 [122 580]
              :w4 [113 566] :z4 [107 550] :a5 [117 539] :b5 [113 518] :d5 [123 501] :e5 [103 510] :f5 [96 517]
              :g5 [76 517] :h5 [138 493] :i5 [152 431] :j5 [150 419] :k5 [149 406] :l5 [133 402] :m5 [143 423]
              :o5 [119 436] :p5 [121 447] :q5 [107 450] :r5 [108 467] :s5 [94 461] :t5 [88 476] :u5 [86 514]
              :w5 [120 410] :z5 [110 422] :a6 [101 433] :b6 [89 436] :c6 [82 458] :d6 [73 472] :e6 [84 542]
              :f6 [77 562] :g6 [37 629] :h6 [27 640] :i6 [13 632] :j6 [11 616] :k6 [33 617] :m6 [99 59] 
              :n6 [268 0] :o6 [144 105]}})
