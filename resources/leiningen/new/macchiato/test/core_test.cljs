(ns {{project-ns}}.core-test
    (:require
    [pjstadig.humane-test-output]
    [cljs.test :refer-macros [is are deftest testing use-fixtures]]
    [{{project-ns}}.core]))

(deftest test-core
  (is (= true true)))


